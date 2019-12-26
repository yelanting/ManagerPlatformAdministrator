/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import com.administrator.platform.constdefine.TimerTaskAndRelations;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.model.yunxiao.ConfigManage;
import com.administrator.platform.service.ConfigManageService;
import com.administrator.platform.service.TimerTaskPolicyService;
import com.administrator.platform.service.TimerTaskService;
import com.administrator.platform.tools.schedule.TaskDefine;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
// @Service(value = "configManageService")
@Order(3)
public class ConfigManageServiceImpl implements ConfigManageService {
	private static final String PIPELINE_TIMERTASK_PREFIX = "cmTaskWithId";
	private static final Logger logger = LoggerFactory
	        .getLogger(ConfigManageServiceImpl.class);

	@Autowired
	private TimerTaskService timerTaskService;

	@Autowired
	private TimerTaskPolicyService timerTaskPilicyService;

	@Autowired
	private YunXiaoHttpOpeation yunXiaoHttpOpeation;

	/**
	 * @see com.administrator.platform.service.ConfigManageService#findAllConfigManageList()
	 */
	@Override
	public List<ConfigManage> findAllConfigManageList() {
		logger.debug("获取所有覆盖率信息列表");
		List<ConfigManage> allConfigManages = yunXiaoHttpOpeation
		        .getConfigManageList();
		logger.debug("当前列表:{}", allConfigManages);

		return allConfigManages;
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.ConfigManageService#findEnvAddrrsByName(java.lang.String)
	 */
	@Override
	public List<ConfigManage> findConfigManagesByName(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称搜索覆盖率信息数据:{}", searchContent);

		List<ConfigManage> configManageList = findAllConfigManageList();

		List<ConfigManage> configManageWithName = new ArrayList<>();

		for (int i = 0; i < configManageList.size(); i++) {
			if (configManageList.get(i).getName().equals(searchContent)) {
				configManageWithName.add(configManageList.get(i));
			}
		}
		return configManageWithName;
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.ConfigManageService#findEnvAddrrsByName(java.lang.String)
	 */
	@Override
	public List<ConfigManage> findConfigManagesByNameLike(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称模糊搜索覆盖率信息数据:{}", searchContent);
		List<ConfigManage> configManageList = findAllConfigManageList();

		List<ConfigManage> configManageWithName = new ArrayList<>();

		for (int i = 0; i < configManageList.size(); i++) {
			if (configManageList.get(i).getName().contains(searchContent)) {
				configManageWithName.add(configManageList.get(i));
			}
		}
		return configManageWithName;
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.ConfigManageService#getConfigManageById(java.lang.Long)
	 */
	@Override
	public ConfigManage getConfigManageById(Long id) {
		ValidationUtil.validateNull(id, null);
		logger.debug("根据ID查询详情:{}", id);

		List<ConfigManage> configManageList = findAllConfigManageList();
		for (int i = 0; i < configManageList.size(); i++) {
			if (id.equals(configManageList.get(i).getId())) {
				return configManageList.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.ConfigManageService#getConfigManageByObject(com.administrator.platform.model.
	 *      ConfigManage)
	 */
	@Override
	public ConfigManage getConfigManageByObject(ConfigManage configManage) {
		ValidationUtil.validateNull(configManage, null);
		logger.debug("根据内容查询详情:{}", configManage);
		return getConfigManageById(configManage.getId());
	}

	@Override
	public void timerTaskBuildConfigManage(ConfigManage configManage) {
		logger.info("构建配置管理流水线:{}", configManage);

		try {
			ConfigManage buildConfigManage = yunXiaoHttpOpeation
			        .buildConfigManage(configManage);
			logger.debug("定时构建了配置管理流水线成功:{}", configManage);
		} catch (Exception e) {
			logger.error("构建配置管理流水线:{}失败:{}", configManage, e.getMessage());
			throw new BusinessValidationException("构建配置管理流水线失败");
		}

	}

	@Override
	public TimerTask configTimerTaskAndChangeStatus(String cronConfig,
	        Long configManageId, boolean enabled) {
		ConfigManage configManage = getConfigManageById(configManageId);

		TimerTask timerTask = null;

		List<TimerTask> currentTimerTasksWithThisName = timerTaskService
		        .findTimerTasksByTimerTaskName(
		                PIPELINE_TIMERTASK_PREFIX + configManage.getId());

		/**
		 * 如果当前没有这个任务，则创建，如果有，则更新
		 */
		if (currentTimerTasksWithThisName.isEmpty()) {
			timerTask = new TimerTask();
		} else {
			timerTask = currentTimerTasksWithThisName.get(0);
		}

		/**
		 * 定义策略
		 */

		timerTask.setTaskName(PIPELINE_TIMERTASK_PREFIX + configManage.getId());
		timerTask.setConfig(cronConfig);
		timerTask.setClosed(!enabled);
		timerTask
		        .setTaskGroup(TaskDefine.DEFAULT_TASK_GROUP_NAME_CONFIG_MANAGE);
		TimerTaskPolicy timerTaskPolicy = timerTaskPilicyService
		        .findTimerTaskPoliciesByEname(
		                TimerTaskAndRelations.CONFIG_MANAGE_POLICY_ENAME)
		        .get(0);

		timerTask.setPolicyId(timerTaskPolicy.getId());

		String otherParams = String.format("%s=%s",
		        TaskDefine.CONFIG_MANAGE_ID_KEY, configManage.getId());
		timerTask.setOtherParams(otherParams);
		timerTask.setDescription(String.format("这是为Id:%s的配置管理数据,创建的自动构建任务。",
		        configManage.getId()));

		if (currentTimerTasksWithThisName.isEmpty()) {
			timerTask = timerTaskService.addTimerTask(timerTask);
		} else {
			timerTask = timerTaskService.updateTimerTask(timerTask);
		}

		if (enabled) {
			timerTaskService.changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.OPEN_TASK_OPERATION);
		} else {
			timerTaskService.changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.CLOSE_TASK_OPERATION);
		}

		return timerTask;
	}
}
