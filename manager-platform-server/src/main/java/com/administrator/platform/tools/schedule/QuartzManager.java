package com.administrator.platform.tools.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.listener.SchedulerJobListener;
import com.administrator.platform.listener.SelfSchedulerListener;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.util.SpringBeanUtil;

/**
 * 调度运行类
 * 
 * @author 孙留平
 * @since 2019年8月13日 15:39:17
 * @version 1.0.0
 */
@Component
public class QuartzManager {
	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

	@Autowired
	private SelfSchedulerListener schedulerListener;

	@Autowired
	private SchedulerJobListener schedulerJobListener;

	@Autowired
	private Scheduler scheduler;

	/**
	 * 任务开启全局主函数
	 * 
	 * @param args
	 * @throws SchedulerException
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws SchedulerException {
		if (null == scheduler) {
			scheduler = (StdScheduler) SpringBeanUtil
			        .getBeanFromSpringByBeanName("schedulerFactoryBean");
		}

		if (scheduler.isShutdown() || !scheduler.isStarted()) {
			scheduleClean();
		}
		scheduler.getListenerManager().addJobListener(schedulerJobListener);
		scheduler.getListenerManager().addSchedulerListener(schedulerListener);
	}

	public void addScheduleJob(TimerTask timerTask,
	        TimerTaskPolicy taskPolicy) {
		logger.debug("增加一个任务:{}:使用策略:{}", timerTask, taskPolicy);
		try {
			String name = timerTask.getTaskName();
			String group = timerTask.getTaskGroup();

			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put(TaskDefine.TASK_KEY, timerTask);
			jobDataMap.put(TaskDefine.TASK_POLICY_KEY, taskPolicy);

			Map<String, Object> params = StringUtil
			        .changeStringParamsToMap(timerTask.getOtherParams());
			jobDataMap.put(TaskDefine.OTHER_PARAMS_KEY, params);
			JobDetail job = JobBuilder.newJob(TaskJob.class)
			        .withIdentity(name, group).storeDurably(false)
			        .requestRecovery().setJobData(jobDataMap).build();

			Trigger trg = TriggerBuilder.newTrigger().forJob(job)
			        .withIdentity(name, group).withSchedule(CronScheduleBuilder
			                .cronSchedule(timerTask.getConfig()))
			        .build();

			scheduler.scheduleJob(job, trg);
			scheduler.start();
		} catch (Exception e) {
			logger.error("任务执行失败：{}", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void scheduleClean() throws SchedulerException {

		List<String> gns = scheduler.getTriggerGroupNames();
		for (String group : gns) {
			List<TriggerKey> triggersInGroup = (List<TriggerKey>) scheduler
			        .getTriggerKeys(GroupMatcher.groupEquals(group));
			scheduler.unscheduleJobs(triggersInGroup);
		}

		gns = scheduler.getJobGroupNames();
		for (String job : gns) {
			Set<JobKey> jobKeys = scheduler
			        .getJobKeys(GroupMatcher.groupEquals(job));
			for (JobKey jobKey : jobKeys) {
				scheduler.deleteJob(jobKey);
			}
		}
	}

	public void taskClean(TimerTask task) throws SchedulerException {
		String name = task.getTaskName();
		String group = task.getTaskGroup();
		scheduler.unscheduleJob(TriggerKey.triggerKey(name, group));
		scheduler.deleteJob(JobKey.jobKey(name, group));
	}

	public void taskStop(TimerTask task) throws SchedulerException {
		logger.debug("停止任务:{}", task);
		String name = task.getTaskName();
		String group = task.getTaskGroup();
		scheduler.unscheduleJob(TriggerKey.triggerKey(name, group));
		logger.debug("停止任务:{}:结束", task);
	}

	public void taskStart(TimerTask task, TimerTaskPolicy taskPloy)
	        throws SchedulerException {
		logger.debug("开启任务执行:{}", task);
		String name = task.getTaskName();
		String group = task.getTaskGroup();
		Trigger trigger = scheduler
		        .getTrigger(TriggerKey.triggerKey(name, group));
		JobDetail job = scheduler.getJobDetail(JobKey.jobKey(name, group));
		taskClean(task);
		if (trigger != null && job != null) {
			scheduler.scheduleJob(job, trigger);
		} else {
			addScheduleJob(task, taskPloy);
		}
	}

	public void scheduleStart() throws SchedulerException {
		if (scheduler == null || scheduler.isStarted()) {
			return;
		}
		scheduler.start();
	}

	public void scheduleStop() throws SchedulerException {
		if (scheduler == null || scheduler.isShutdown()) {
			return;
		}
		scheduler.shutdown();
	}

	public int getSingleValueOfDate(Date date, int index) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Integer> result = new ArrayList<Integer>(6);
		String str = sdf.format(date);
		String[] dateTime = str.split("\\s");
		if (dateTime.length == 2) {
			String[] dateStr = dateTime[0].split("-");
			String[] time = dateTime[1].split(":");
			for (String s : dateStr) {
				result.add(Integer.parseInt(s));
			}
			for (String s : time) {
				result.add(Integer.parseInt(s));
			}
		}
		return result.get(index);
	}
}
