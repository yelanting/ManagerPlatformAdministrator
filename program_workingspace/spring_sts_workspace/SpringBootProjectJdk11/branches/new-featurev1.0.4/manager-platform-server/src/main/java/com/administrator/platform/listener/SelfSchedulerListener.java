/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月8日 上午10:03:25
 * @see:
 */
package com.administrator.platform.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.administrator.platform.tools.schedule.TaskDefine;

@Component("selfSchedulerListener")
public class SelfSchedulerListener implements SchedulerListener {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SelfSchedulerListener.class);

	@Override
	public void jobScheduled(Trigger trigger) {
		LOGGER.debug(
		        "======================job triggered======================");
		LOGGER.debug(trigger.getJobDataMap().getString(TaskDefine.TASK_KEY));

	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {

	}

	@Override
	public void triggerFinalized(Trigger trigger) {

	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void triggersPaused(String triggerGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void triggersResumed(String triggerGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobPaused(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobsPaused(String jobGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobResumed(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobsResumed(String jobGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerStarting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerShutdown() {
		LOGGER.error("定时器被关闭了！！");

	}

	@Override
	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulingDataCleared() {
		// TODO Auto-generated method stub

	}

}
