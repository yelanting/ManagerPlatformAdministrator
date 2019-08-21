/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月8日 上午10:22:22
 * @see:
 */
package com.administrator.platform.listener;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.administrator.platform.model.TimerTaskMonitor;
import com.administrator.platform.service.TimerTaskMonitorService;

@Component("schedulerJobListener")
public class SchedulerJobListener implements JobListener {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SchedulerJobListener.class);

	private static final String LISTENER_NAME = "QuartSchedulerJobListener";

	private Long thisTimerTaskMonitorId;

	public Long getThisTimerTaskMonitorId() {
		return thisTimerTaskMonitorId;
	}

	public void setThisTimerTaskMonitorId(Long thisTimerTaskMonitorId) {
		this.thisTimerTaskMonitorId = thisTimerTaskMonitorId;
	}

	@Autowired
	private TimerTaskMonitorService timerTaskMonitorService;

	@Override
	public String getName() {
		LOGGER.debug("任务名称:{}", LISTENER_NAME);
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		LOGGER.debug("=================任务调度前======================");
		JobKey jobNameKey = context.getJobDetail().getKey();

		String jobName = jobNameKey.getName();
		String groupName = jobNameKey.getName();
		TimerTaskMonitor timerTaskMonitor = new TimerTaskMonitor();
		timerTaskMonitor.setJobName(jobName);
		timerTaskMonitor.setStartDate(new Date());
		TimerTaskMonitor timerTaskMonitorInserted = timerTaskMonitorService
		        .addTimerTaskMonitor(timerTaskMonitor);
		thisTimerTaskMonitorId = timerTaskMonitorInserted.getId();

		LOGGER.debug("新增了一个任务监控:{}", timerTaskMonitorInserted);
		LOGGER.debug("当前的任务监控id：{}", thisTimerTaskMonitorId);
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOGGER.debug("job  was rejected !!!!!");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
	        JobExecutionException jobException) {

		Date finishDate = new Date();
		JobKey jobKey = context.getJobDetail().getKey();
		String jobName = jobKey.getName();
		String jobGroup = jobKey.getGroup();
		LOGGER.debug("定时任务:{}, 执行完毕", jobName);
		LOGGER.debug("任务执行之后，保存的任务id：{}", thisTimerTaskMonitorId);
		TimerTaskMonitor timerTaskMonitor = timerTaskMonitorService
		        .getTimerTaskMonitorById(thisTimerTaskMonitorId);
		timerTaskMonitor.setEndDate(finishDate);
		/**
		 * 有异常
		 */
		boolean success = false;
		String description = null;
		if (jobException != null && !jobException.getMessage().equals("")) {
			LOGGER.error("定时任务执行失败，出现了异常:{}", jobException.getMessage());
			description = "定时任务执行失败，出现了异常:" + jobException.getMessage();
			success = false;
		} else {
			description = String.format("定时任务:[%s],所属组:[%s] 执行完毕,用时:[%s]ms.",
			        jobName, jobGroup, context.getJobRunTime());
			success = true;
		}
		timerTaskMonitor.setDescription(description);
		timerTaskMonitor.setSuccess(success);
		timerTaskMonitorService.updateTimerTaskMonitor(timerTaskMonitor);
		LOGGER.debug("=================更新任务监控成功!!!!=================");
	}
}
