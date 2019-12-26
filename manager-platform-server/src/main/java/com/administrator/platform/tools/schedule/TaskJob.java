package com.administrator.platform.tools.schedule;

import java.lang.reflect.Method;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.CodeCoverageService;
import com.administrator.platform.util.SpringBeanUtil;

/**
 * 自定义的任务类，负责处理执行的任务细节
 * 
 * @author : Administrator
 * @since : 2019年8月13日 下午3:39:29
 * @see :
 */
@Component("taskJob")
@Order(4)
public class TaskJob implements Job {

	@Autowired
	@Order(3)
	private CodeCoverageService codeCoverageService;

	@Autowired
	@Order(3)
	// private PipeLineService pipeLineService;
	private static final char SEP = '.';
	private static Logger logger = LoggerFactory.getLogger(TaskJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Object taskPloyObj = arg0.getJobDetail().getJobDataMap()
		        .get(TaskDefine.TASK_POLICY_KEY);

		Object params = arg0.getJobDetail().getJobDataMap()
		        .get(TaskDefine.OTHER_PARAMS_KEY);

		Map<String, Object> paramsKeyMap = null;
		if (params instanceof Map) {
			paramsKeyMap = (Map) params;
		}

		TimerTaskPolicy taskPloy = null;
		if (null != taskPloyObj && taskPloyObj instanceof TimerTaskPolicy) {
			taskPloy = (TimerTaskPolicy) taskPloyObj;
			if (null != paramsKeyMap && !paramsKeyMap.entrySet().isEmpty()) {
				logger.debug("传入的参数为:{}", paramsKeyMap);
				executePloyWithOtherParams(taskPloy, paramsKeyMap);
			} else {
				executePloy(taskPloy);
			}

		} else {
			logger.error("策略为空");
		}
	}

	private void executePloy(TimerTaskPolicy taskPolicy) {
		try {
			String code = taskPolicy.getCode();
			logger.info("策略代码:{}", code);
			String domain = code.substring(0, code.lastIndexOf(SEP));
			String method = code.substring(code.lastIndexOf(SEP) + 1);
			Object clazz = SpringBeanUtil.getBeanFromSpringByBeanName(domain);
			Method m = clazz.getClass().getMethod(method);
			m.invoke(clazz);
		} catch (Exception e) {
			logger.error("job执行错误", e);
		}
	}

	/**
	 * 执行带有参数的任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param taskPolicy
	 * @param otherParams
	 */
	private void executePloyWithOtherParams(TimerTaskPolicy taskPolicy,
	        Map<String, Object> otherParams) {
		try {
			String code = taskPolicy.getCode();
			logger.info("策略代码:{}", code);
			String domain = code.substring(0, code.lastIndexOf(SEP));
			String method = code.substring(code.lastIndexOf(SEP) + 1);
			Object clazz = SpringBeanUtil.getBeanFromSpringByBeanName(domain);

			/**
			 * 判断是不是覆盖率信息来的数据
			 */
			if (otherParams.containsKey(TaskDefine.CODE_COVERAGER_ID_KEY)) {
				Method m = clazz.getClass().getMethod(method,
				        CodeCoverage.class);
				String objectValue = (String) otherParams
				        .getOrDefault(TaskDefine.CODE_COVERAGER_ID_KEY, null);

				logger.debug("当前的参数列表:{}", otherParams);
				logger.debug("参数中有没有codeCoverageId:{}", otherParams
				        .containsKey(TaskDefine.CODE_COVERAGER_ID_KEY));
				logger.debug("获取到的codeCoverageId为:{}", objectValue);

				if (StringUtil.isEmpty(objectValue)) {
					logger.debug("没有找到codeCoverageId,不执行任务");
					return;
				}

				codeCoverageService = (CodeCoverageService) SpringBeanUtil
				        .getBeanFromSpringByBeanName("codeCoverageService");

				Long codeCoverageInfoId = Long.parseLong(objectValue);
				CodeCoverage codeCoverage = codeCoverageService
				        .getCodeCoverageById(codeCoverageInfoId);

				if (StringUtil.isEmpty(objectValue)) {
					logger.debug("没有找到codeCoverageId为:{}的覆盖率信息，不执行任务",
					        codeCoverageInfoId);
					return;
				}
				m.invoke(clazz, codeCoverage);
			} else if (otherParams.containsKey(TaskDefine.PIPELINE_ID_KEY)) {
				// Method m = clazz.getClass().getMethod(method, PipeLine.class);
				// String objectValue = (String) otherParams
				// .getOrDefault(TaskDefine.PIPELINE_ID_KEY, null);
				//
				// logger.debug("当前的参数列表:{}", otherParams);
				// logger.debug("参数中有没有pipeLineId:{}",
				// otherParams.containsKey(TaskDefine.PIPELINE_ID_KEY));
				// logger.debug("获取到的pipeLineId为:{}", objectValue);
				//
				// if (StringUtil.isEmpty(objectValue)) {
				// logger.debug("没有找到codeCoverageId,不执行任务");
				// return;
				// }
				//
				// pipeLineService = (PipeLineService) SpringBeanUtil
				// .getBeanFromSpringByBeanName("pipeLineService");
				//
				// Long pipeLineId = Long.parseLong(objectValue);
				// PipeLine pipeLine = pipeLineService.getPipeLineById(pipeLineId);
				//
				// if (StringUtil.isEmpty(objectValue)) {
				// logger.debug("没有找到pipeLineId为:{}的流水线信息，不执行任务", pipeLineId);
				// return;
				// }
				// m.invoke(clazz, pipeLine);
			} else {
				Method m = clazz.getClass().getMethod(method,
				        CodeCoverage.class);
				m.invoke(clazz);
			}

		} catch (Exception e) {
			logger.error("job执行错误", e);
			throw new BusinessValidationException("job执行异常");
		}
	}
}
