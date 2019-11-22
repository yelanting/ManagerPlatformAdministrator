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
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.TimerTaskAndRelations;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.model.yunxiao.PipeLine;
import com.administrator.platform.service.PipeLineService;
import com.administrator.platform.service.TimerTaskPolicyService;
import com.administrator.platform.service.TimerTaskService;
import com.administrator.platform.tools.schedule.TaskDefine;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service(value = "pipeLineService")
@Order(3)
public class PipeLineServiceImpl implements PipeLineService {
	private static final String PIPELINE_TIMERTASK_PREFIX = "plTaskWithId";
	private static final Logger logger = LoggerFactory
	        .getLogger(PipeLineServiceImpl.class);

	@Autowired
	private TimerTaskService timerTaskService;

	@Autowired
	private TimerTaskPolicyService timerTaskPilicyService;

	@Autowired
	private YunXiaoHttpOpeation yunXiaoHttpOpeation;

	/**
	 * @see com.administrator.platform.service.PipeLineService#findAllPipeLineList()
	 */
	@Override
	public List<PipeLine> findAllPipeLineList() {
		logger.debug("获取所有覆盖率信息列表");
		List<PipeLine> allPipeLines = yunXiaoHttpOpeation.getPipeLineList();
		logger.debug("当前列表:{}", allPipeLines);

		return allPipeLines;
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.PipeLineService#findEnvAddrrsByName(java.lang.String)
	 */
	@Override
	public List<PipeLine> findPipeLinesByName(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称搜索覆盖率信息数据:{}", searchContent);

		List<PipeLine> pipeLineList = findAllPipeLineList();

		List<PipeLine> pipeLineWithName = new ArrayList<>();

		for (int i = 0; i < pipeLineList.size(); i++) {
			if (pipeLineList.get(i).getName().equals(searchContent)) {
				pipeLineWithName.add(pipeLineList.get(i));
			}
		}
		return pipeLineWithName;
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.PipeLineService#findEnvAddrrsByName(java.lang.String)
	 */
	@Override
	public List<PipeLine> findPipeLinesByNameLike(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称模糊搜索覆盖率信息数据:{}", searchContent);
		List<PipeLine> pipeLineList = findAllPipeLineList();

		List<PipeLine> pipeLineWithName = new ArrayList<>();

		for (int i = 0; i < pipeLineList.size(); i++) {
			if (pipeLineList.get(i).getName().contains(searchContent)) {
				pipeLineWithName.add(pipeLineList.get(i));
			}
		}
		return pipeLineWithName;
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.PipeLineService#getPipeLineById(java.lang.Long)
	 */
	@Override
	public PipeLine getPipeLineById(Long id) {
		ValidationUtil.validateNull(id, null);
		logger.debug("根据ID查询详情:{}", id);

		List<PipeLine> pipeLineList = findAllPipeLineList();
		for (int i = 0; i < pipeLineList.size(); i++) {
			if (id.equals(pipeLineList.get(i).getId())) {
				return pipeLineList.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.PipeLineService#getPipeLineByObject(com.administrator.platform.model.
	 *      PipeLine)
	 */
	@Override
	public PipeLine getPipeLineByObject(PipeLine pipeLine) {
		ValidationUtil.validateNull(pipeLine, null);
		logger.debug("根据内容查询详情:{}", pipeLine);
		return getPipeLineById(pipeLine.getId());
	}

	@Override
	public void timerTaskBuildPipeLine(PipeLine pipeLine) {
		// List<JacocoAgentTcpServer> jacocoAgentTcpServers = JacocoAgentTcpServer
		// .parseJacocoAgentTcpServersFromPipeLine(pipeLine);
		//
		// for (int i = 0; i < jacocoAgentTcpServers.size(); i++) {
		// new ExecutionDataClient(jacocoAgentTcpServers.get(0))
		// .backUpExecData();
		// }
		logger.info("构建流水线:{}", pipeLine);

		try {
			PipeLine buildPipeLine = yunXiaoHttpOpeation
			        .buildPipeLine(pipeLine);
			logger.debug("定时构建了流水线成功:{}", pipeLine);
		} catch (Exception e) {
			logger.error("构建流水线:{}失败:{}", pipeLine, e.getMessage());
			throw new BusinessValidationException("构建流水线失败");
		}

	}

	@Override
	public TimerTask configTimerTaskAndChangeStatus(String cronConfig,
	        Long pipeLineId, boolean enabled) {
		PipeLine pipeLine = getPipeLineById(pipeLineId);

		TimerTask timerTask = null;

		List<TimerTask> currentTimerTasksWithThisName = timerTaskService
		        .findTimerTasksByTimerTaskName(
		                PIPELINE_TIMERTASK_PREFIX + pipeLine.getId());

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

		timerTask.setTaskName(PIPELINE_TIMERTASK_PREFIX + pipeLine.getId());
		timerTask.setConfig(cronConfig);
		timerTask.setClosed(!enabled);
		timerTask.setTaskGroup(TaskDefine.DEFAULT_TASK_GROUP_NAME_PIPELINE);
		TimerTaskPolicy timerTaskPolicy = timerTaskPilicyService
		        .findTimerTaskPoliciesByEname(
		                TimerTaskAndRelations.PIPELINE_CONFIG_POLICY_ENAME)
		        .get(0);

		timerTask.setPolicyId(timerTaskPolicy.getId());

		String otherParams = String.format("%s=%s", TaskDefine.PIPELINE_ID_KEY,
		        pipeLine.getId());
		timerTask.setOtherParams(otherParams);
		/**
		 * 2019年11月19日 16:08:29
		 * 
		 * @see modified by 孙留平，防止name过长导致描述过长，遂修改为id
		 * 
		 */
		timerTask.setDescription(
		        String.format("这是为id是:%s的流水线创建的自动构建任务。", pipeLine.getId()));

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
