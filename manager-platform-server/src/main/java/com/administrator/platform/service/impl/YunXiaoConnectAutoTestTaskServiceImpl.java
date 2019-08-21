/**
 * @author : 孙留平
 * @since : 2019年5月16日 下午3:45:07
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.YunXiaoBusinessValidationException;
import com.administrator.platform.model.AutoTestTask;
import com.administrator.platform.model.YunXiaoConnectAutoTestTask;
import com.administrator.platform.model.yunxiao.CallBackYunXiaoParamDTO;
import com.administrator.platform.service.TaskService;
import com.administrator.platform.service.YunXiaoCallBackService;
import com.administrator.platform.service.YunXiaoConnectAutoTestTaskService;

/**
 * @author : Administrator
 * @since : 2019年5月16日 下午3:45:07
 * @see :
 */

@Service
public class YunXiaoConnectAutoTestTaskServiceImpl
        implements YunXiaoConnectAutoTestTaskService {

	private static final Logger logger = LoggerFactory
	        .getLogger(YunXiaoCallBackServiceImpl.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private YunXiaoCallBackService yunXiaoCallBackService;

	private ThreadPoolExecutor threadPoolExcutor;
	private ThreadFactory threadFactory;

	/**
	 * 初始化线程池
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */

	@PostConstruct
	private void initThreadPool() {
		threadFactory = Executors.defaultThreadFactory();
		threadPoolExcutor = new ThreadPoolExecutor(5, 10, 200,
		        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5),
		        threadFactory);
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.
	 *      YunXiaoConnectAutoTestTaskService#getAutoTestTaskExecutionReport(com.
	 *      tianque.yunxiao.connect.server.dto.YunXiaoConnectAutoTestTask)
	 */
	@Override
	public void getAutoTestTaskExecutionReport(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask,
	        HttpServletResponse httpServletResponse) {
		logger.info("getAutoTestTaskExecutionReport入参:{}",
		        yunXiaoConnectAutoTestTask);
		validateInput(yunXiaoConnectAutoTestTask);
		AutoTestTask autoTestTask = AutoTestTask
		        .fromYunXiaoConnectAutoTestTask(yunXiaoConnectAutoTestTask);
		taskService.downloadExecutionReport(autoTestTask, httpServletResponse);
	}

	/**
	 * 云效调用自动化任务执行
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.YunXiaoConnectAutoTestTaskService#executeAutoTestTask(com.tianque.yunxiao.connect.server.dto.YunXiaoConnectAutoTestTask)
	 */
	@Override
	public AutoTestTask executeAutoTestTask(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask) {
		logger.info("executeAutoTestTask入参:{}", yunXiaoConnectAutoTestTask);
		AutoTestTask autoTestTask = AutoTestTask
		        .fromYunXiaoConnectAutoTestTask(yunXiaoConnectAutoTestTask);

		if (!taskService.checkTaskNameExists(autoTestTask.getTaskName())) {
			taskService.addAutoTestTask(autoTestTask);
		}

		// 调用任务执行
		taskService.executeTask(autoTestTask);

		Thread thread = threadFactory.newThread(() -> {
			logger.info("开始轮询检测当前任务是否已经执行完毕");
			boolean isRunning = false;
			while (true) {
				isRunning = taskService
				        .isTaskRunning(autoTestTask.getTaskName());
				if (!isRunning) {
					logger.info("任务执行完毕，回调云效:{}", yunXiaoConnectAutoTestTask);
					CallBackYunXiaoParamDTO callBackYunXiaoParamDTO = new CallBackYunXiaoParamDTO();
					callBackYunXiaoParamDTO.setSuccess("1");
					callBackYunXiaoParamDTO.setTotal("1");
					callBackYunXiaoParamDTO.setRelatedRunDetail("");
					callBackYunXiaoParamDTO.setTestSuiteRunId(
					        yunXiaoConnectAutoTestTask.getTestSuiteRunId());
					callBackYunXiaoParamDTO.setLog("");
					callBackYunXiaoParamDTO.setCompleted("0");
					callBackYunXiaoParamDTO.setIps("");
					callBackYunXiaoParamDTO.setRunCaseDoList("");
					callBackYunXiaoParamDTO.setCallBackUrl(
					        yunXiaoConnectAutoTestTask.getCallBackUrl());
					yunXiaoCallBackService
					        .autoTestResultCallBack(callBackYunXiaoParamDTO);
					return;
				}
				try {
					logger.debug("任务正在执行，等待5S再次查询");
					Thread.sleep(5 * 1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.setName("执行查询job任务的线程");
		// 异步起线程，检查状态
		threadPoolExcutor.execute(thread);

		return autoTestTask;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.
	 *      YunXiaoConnectAutoTestTaskService#getAutoTestTaskProcess(com.tianque.
	 *      yunxiao.connect.server.dto.YunXiaoConnectAutoTestTask)
	 */
	@Override
	public AutoTestTask getAutoTestTaskProcess(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask) {
		logger.info("getAutoTestTaskProcess入参:{}", yunXiaoConnectAutoTestTask);
		AutoTestTask autoTestTask = AutoTestTask
		        .fromYunXiaoConnectAutoTestTask(yunXiaoConnectAutoTestTask);
		return taskService.getExecutionProcess(autoTestTask);
	}

	/**
	 * 校验函数
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param yunXiaoConnectAutoTestTask
	 */
	public void validateInput(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask) {
		logger.info("validateInput入参:{}", yunXiaoConnectAutoTestTask);
		ValidationUtil.validateNull(yunXiaoConnectAutoTestTask, "接入参数不能为null");
		ValidationUtil.validateStringNullOrEmpty(
		        yunXiaoConnectAutoTestTask.getTestSuiteRunId(),
		        "云效传入的接入testSuiteRunId不能为空或空白");
		ValidationUtil.validateStringNullOrEmpty(
		        yunXiaoConnectAutoTestTask.getSuiteId(),
		        "云效传入的接入suiteId不能为空或空白");
		ValidationUtil.validateStringNullOrEmpty(
		        yunXiaoConnectAutoTestTask.getCallBackUrl(),
		        "云效传入的接入callBackUrl不能为空或空白");
	}

	/**
	 * 取消任务执行
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.YunXiaoConnectAutoTestTaskService#cancelAutoTestTask(com.tianque.yunxiao.connect.server.model.YunXiaoConnectAutoTestTask)
	 */
	@Override
	public AutoTestTask cancelAutoTestTask(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask) {
		logger.info("cancelAutoTestTask入参:{}", yunXiaoConnectAutoTestTask);
		AutoTestTask autoTestTask = AutoTestTask
		        .fromYunXiaoConnectAutoTestTask(yunXiaoConnectAutoTestTask);

		if (!taskService.checkTaskNameExists(autoTestTask.getTaskName())) {
			throw new YunXiaoBusinessValidationException("任务不存在，不能取消");
		}

		logger.info("取消任务执行");
		// 调用任务执行
		taskService.cancelTask(autoTestTask);
		return autoTestTask;
	}
}
