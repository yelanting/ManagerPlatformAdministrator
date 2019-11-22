/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.TimerTaskFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.TimerTaskMapper;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.TimerTaskPolicyService;
import com.administrator.platform.service.TimerTaskService;
import com.administrator.platform.tools.schedule.QuartzManager;
import com.administrator.platform.tools.schedule.TaskDefine;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class TimerTaskServiceImpl implements TimerTaskService {
	private static final Logger logger = LoggerFactory
	        .getLogger(TimerTaskServiceImpl.class);
	@Autowired
	private TimerTaskMapper timerTaskMapper;

	@Autowired
	private TimerTaskPolicyService timerTaskPolicyService;

	@Autowired
	private QuartzManager quartzManager;

	/**
	 * @see com.administrator.platform.service.TimerTaskService#findAllTimerTaskList()
	 */
	@Override
	public List<TimerTask> findAllTimerTaskList() {
		return timerTaskMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see
	 *      com.administrator.platform.service.TimerTaskService#addTimerTask(com.administrator.platform.model.TimerTask)
	 */
	@Override
	public TimerTask addTimerTask(TimerTask timerTask) {
		ValidationUtil.validateNull(timerTask, null);
		validateInput(timerTask);

		TimerTaskPolicy timerTaskPolicy = timerTaskPolicyService
		        .getTimerTaskPolicyById(timerTask.getPolicyId());

		if (null == timerTaskPolicy) {
			throw new BusinessValidationException("定时任务关联的定时任务类型不存在！");
		}

		if (!findTimerTasksByTimerTaskName(timerTask.getTaskName()).isEmpty()) {
			throw new BusinessValidationException("任务名已经存在，不可重复添加！");
		}
		try {
			timerTaskMapper.insert(timerTask);
			return timerTask;
		} catch (Exception e) {
			logger.error("添加定时任务失败:{},{}", timerTask, e.getMessage());
			throw new BusinessValidationException("添加定时任务失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#deleteTimerTask(java.lang.Long)
	 */
	@Override
	public int deleteTimerTask(Long id) {
		ValidationUtil.validateNull(id, null);

		/**
		 * 先变更他的状态。在执行删除
		 */

		TimerTask timerTask = getTimerTaskById(id);

		if (null == timerTask) {
			throw new BusinessValidationException("id为[" + id + "]的定时任务不存在");
		}

		/**
		 * 如果没有关闭 先关闭
		 */
		if (!timerTask.getClosed()) {
			changeTimerTaskStatus(id, TaskDefine.CLOSE_TASK_OPERATION);
		}

		try {
			timerTaskMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			logger.error("根据定时任务id删除定时任务失败:{},{}", id, e.getMessage());
			throw new BusinessValidationException(
			        "根据定时任务id删除定时任务失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#deleteTimerTask(java.lang.Long[])
	 */
	@Override
	public int deleteTimerTask(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);

		for (Long eachId : ids) {
			TimerTask timerTask = getTimerTaskById(eachId);
			if (null == timerTask) {
				continue;
			}
			/**
			 * 如果没有关闭 先关闭
			 */
			if (!timerTask.getClosed()) {
				changeTimerTaskStatus(eachId, TaskDefine.CLOSE_TASK_OPERATION);
			}
		}
		try {
			timerTaskMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据ids批量删除定时任务失败" + e.getMessage());
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#findEnvAddrrsByTimerTaskName(java.lang.String)
	 */
	@Override
	public List<TimerTask> findTimerTasksByTimerTaskName(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return timerTaskMapper.findTimerTasksByTimerTaskName(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#findEnvAddrrsByTimerTaskName(java.lang.String)
	 */
	@Override
	public List<TimerTask> findTimerTasksByTimerTaskNameLike(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return timerTaskMapper.findTimerTasksByTimerTaskNameLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param timerTask
	 *            : 待校验的地址对象
	 */
	private void validateInput(TimerTask timerTask) {

		logger.debug("开始校验输入内容:{}", timerTask);
		// 判空
		ValidationUtil.validateNull(timerTask, null);

		if (!StringUtil.isStringAvaliable(timerTask.getTaskName())) {
			throw new BusinessValidationException("定时任务名称不能为空！");
		}

		// 验证定时任务名称长度
		ValidationUtil.validateStringAndLength(timerTask.getTaskName(), null,
		        TimerTaskFormDefinition.GOOD_NAME_LENGTH, "项目名称");
		// 验证定时任务类型
		if (null == timerTask.getPolicyId()) {
			throw new BusinessValidationException("定时任务类型不能为空！");
		}

		// 验证定时任务描述

		ValidationUtil.validateStringAndLength(timerTask.getDescription(), null,
		        TimerTaskFormDefinition.GOOD_DESC_FIELD_MAX_LENGTH, "定时任务描述");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param timerTask:地址对象
	 * @see com.administrator.platform.service.TimerTaskService#updateTimerTask(com.administrator.platform.model.
	 *      TimerTask)
	 */
	@Override
	public TimerTask updateTimerTask(TimerTask timerTask) {
		ValidationUtil.validateNull(timerTask, null);

		TimerTask currentTimerTask = getTimerTaskByObject(timerTask);

		if (null == currentTimerTask) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		timerTask.setId(currentTimerTask.getId());

		// 校验输入内容
		validateInput(timerTask);

		if (findTimerTaskWithNameExceptThisId(timerTask.getTaskName(),
		        timerTask.getId())) {
			throw new BusinessValidationException("任务名已经存在");
		}

		try {
			timerTaskMapper.updateByPrimaryKey(timerTask);
			doTask(timerTask);
			return timerTask;
		} catch (Exception e) {
			logger.error("更新定时任务失败：{},{}", timerTask, e.getMessage());
			throw new BusinessValidationException("更新定时任务信息失败!!!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.TimerTaskService#getTimerTaskById(java.lang.Long)
	 */
	@Override
	public TimerTask getTimerTaskById(Long id) {
		logger.debug("根据ID查询定时任务详情:{}", id);
		ValidationUtil.validateNull(id, null);
		TimerTask timerTask = timerTaskMapper.selectByPrimaryKey(id);
		logger.debug("详情:{}", timerTask);
		return timerTask;
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#getTimerTaskByObject(com.administrator.platform.model.
	 *      TimerTask)
	 */
	@Override
	public TimerTask getTimerTaskByObject(TimerTask timerTask) {
		ValidationUtil.validateNull(timerTask, null);
		return getTimerTaskById(timerTask.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#findTimerTaskByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<TimerTask> findTimerTaskByPage(Integer page, Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return timerTaskMapper.findAll(pageable);
	}

	@Override
	public TimerTask changeTimerTaskStatus(Long id, String operation) {
		logger.debug("变更任务状态");

		TimerTask timerTask = getTimerTaskById(id);

		if (TaskDefine.OPEN_TASK_OPERATION.equalsIgnoreCase(operation)) {
			timerTask.setClosed(false);
		} else if (TaskDefine.CLOSE_TASK_OPERATION
		        .equalsIgnoreCase(operation)) {
			timerTask.setClosed(true);
		} else {
			timerTask.setClosed(true);
		}
		updateTimerTask(timerTask);

		return timerTask;
	}

	private void doTask(TimerTask timerTask) {
		try {
			if (!timerTask.getClosed()) {
				logger.debug("任务是开启状态。执行");
				// 先关闭，应该是quartz的bug
				quartzManager.taskStop(timerTask);
				quartzManager.taskStart(timerTask, timerTaskPolicyService
				        .getTimerTaskPolicyById(timerTask.getPolicyId()));
			} else {
				quartzManager.taskStop(timerTask);
			}
		} catch (SchedulerException e) {
			logger.error("定时任务执行出现异常:{}", e.getMessage());
		} catch (Exception e) {
			logger.error("出现异常，{}", e.getMessage());
		}
	}

	/**
	 * 判断非当前id的记录中，是否存在同名
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param taskName
	 * @param id
	 * @return
	 */
	private boolean findTimerTaskWithNameExceptThisId(String taskName,
	        Long id) {
		List<TimerTask> searchList = timerTaskMapper
		        .findTimerTaskWithNameExceptThisId(taskName, id);
		return null != searchList && !searchList.isEmpty();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#openTimerTaskInBatch(java.lang.Long[])
	 * @param ids
	 * @return
	 */
	@Override
	public List<TimerTask> openTimerTaskInBatch(Long[] ids) {

		List<TimerTask> allTimerTasks = new ArrayList<>();

		for (int i = 0; i < ids.length; i++) {
			TimerTask timerTask = getTimerTaskById(ids[i]);

			if (null != timerTask) {
				changeTimerTaskStatus(ids[i], TaskDefine.OPEN_TASK_OPERATION);
				allTimerTasks.add(timerTask);
			}
		}
		return allTimerTasks;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#openAllTimerTaskInBatch()
	 * @return
	 */
	@Override
	public List<TimerTask> openAllTimerTaskInBatch() {
		List<TimerTask> allTimerTasks = findAllTimerTaskList();

		for (TimerTask timerTask : allTimerTasks) {
			changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.OPEN_TASK_OPERATION);
		}
		return allTimerTasks;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#closeTimerTaskInBatch(java.lang.Long[])
	 * @param ids
	 * @return
	 */
	@Override
	public List<TimerTask> closeTimerTaskInBatch(Long[] ids) {
		List<TimerTask> allTimerTasks = new ArrayList<>();

		for (int i = 0; i < ids.length; i++) {
			TimerTask timerTask = getTimerTaskById(ids[i]);

			if (null != timerTask) {
				changeTimerTaskStatus(ids[i], TaskDefine.CLOSE_TASK_OPERATION);
				allTimerTasks.add(timerTask);
			}
		}
		return allTimerTasks;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.TimerTaskService#closeAllTimerTaskInBatch()
	 * @return
	 */
	@Override
	public List<TimerTask> closeAllTimerTaskInBatch() {
		List<TimerTask> allTimerTasks = findAllTimerTaskList();

		for (TimerTask timerTask : allTimerTasks) {
			changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.CLOSE_TASK_OPERATION);
		}
		return allTimerTasks;
	}
}
