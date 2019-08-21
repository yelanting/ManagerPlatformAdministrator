/**
 * @author : 孙留平
 * @since : 2019年4月4日 下午2:55:42
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.TimerTaskAndRelations;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.TimerTaskPolicyService;
import com.administrator.platform.service.TimerTaskService;
import com.administrator.platform.tools.schedule.TaskDefine;

/**
 * @author : Administrator
 * @since : 2019年4月4日 下午2:55:42
 * @see :
 */

@Service("timerTaskDataInit")
public class TimerTaskDataInit {
	/**
	 * 初始化策略
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	@Autowired
	private TimerTaskPolicyService timerTaskPolicyService;

	/**
	 * 启动那些未关闭的定时任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */

	@Autowired
	private TimerTaskService timerTaskService;

	/**
	 * 初始化 定时策略
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */

	@PostConstruct
	public void initBasicTimerTaskPolicy() {
		initTimerTaskPolicyOfCollectingCodeCoverageData();
	}

	/**
	 * 初始化 一个定时采集覆盖率数据的策略
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void initTimerTaskPolicyOfCollectingCodeCoverageData() {
		TimerTaskPolicy timerTaskPolicy = new TimerTaskPolicy();
		timerTaskPolicy.setCname(
		        TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CNAME);
		timerTaskPolicy.setEname(
		        TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_ENAME);
		timerTaskPolicy.setDescription(
		        TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_DESC);
		timerTaskPolicy.setCode(
		        TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CODE);

		List<TimerTaskPolicy> currentTimerTaskPoliciesWithThisName = timerTaskPolicyService
		        .findTimerTaskPoliciesByCname(
		                TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CNAME);
		boolean notExists = currentTimerTaskPoliciesWithThisName.isEmpty();
		// 如果不存在，则创建
		if (notExists) {
			timerTaskPolicyService.addTimerTaskPolicy(timerTaskPolicy);
			return;
		}

		// 如果有且一样，则啥都不干
		if (timerTaskPolicy
		        .equals(currentTimerTaskPoliciesWithThisName.get(0))) {
			return;
		}
		// 如果有但是不一样，则更新
		timerTaskPolicy
		        .setId(currentTimerTaskPoliciesWithThisName.get(0).getId());
		timerTaskPolicyService.updateTimerTaskPolicy(timerTaskPolicy);
	}

	@PostConstruct
	public void startAllTimerTaskThatIsNotClosed() {
		List<TimerTask> timerTasks = timerTaskService.findAllTimerTaskList();

		for (int i = 0; i < timerTasks.size(); i++) {
			TimerTask timerTask = timerTasks.get(i);

			/**
			 * 如果没有关闭
			 */
			if (!timerTask.getClosed()) {
				timerTaskService.changeTimerTaskStatus(timerTask.getId(),
				        TaskDefine.OPEN_TASK_OPERATION);
			}
		}
	}
}
