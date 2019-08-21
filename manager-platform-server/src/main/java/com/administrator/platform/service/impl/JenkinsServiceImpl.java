/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午7:15:13
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.JenkinsDefine;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.GlobalParam;
import com.administrator.platform.service.GlobalParamService;
import com.administrator.platform.service.JenkinsService;
import com.administrator.platform.util.JenkinsServerOperation;
import com.administrator.platform.vo.JenkinsJobVO;
import com.offbytwo.jenkins.model.Artifact;
import com.offbytwo.jenkins.model.Computer;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午7:15:13
 * @see :
 */
@Service
public class JenkinsServiceImpl implements JenkinsService {
	private static final Logger logger = LoggerFactory
	        .getLogger(JenkinsServiceImpl.class);
	private JenkinsServerOperation jenkinsServerOperation;

	@Autowired
	private GlobalParamService globalParamService;

	private void initJenkinsOperation() {
		GlobalParam jenkinsUrlParam = globalParamService
		        .findGlobalParamByParamKey(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_URL);
		GlobalParam jenkinsLoginUsername = globalParamService
		        .findGlobalParamByParamKey(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_USERNAME);

		GlobalParam jenkinsLoginPassword = globalParamService
		        .findGlobalParamByParamKey(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_PASSWORD);

		if (null != jenkinsUrlParam && null != jenkinsLoginUsername
		        && null != jenkinsLoginPassword) {

			JenkinsServerOperation currentJenkinsServerOperation = JenkinsServerOperation
			        .fromUrlAndLoginInfo(jenkinsUrlParam.getParamValue(),
			                jenkinsLoginUsername.getParamValue(),
			                jenkinsLoginPassword.getParamValue());
			logger.info("jenkins服务器初始化成功,url:{},用户名: {},password:{}",
			        jenkinsUrlParam.getParamValue(),
			        jenkinsLoginUsername.getParamValue(),
			        jenkinsLoginPassword.getParamValue());
			this.jenkinsServerOperation = currentJenkinsServerOperation;
		}
	}

	/**
	 * @see 获取执行机列表
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#getComputerList()
	 */
	@Override
	public List<Computer> getComputerList() {
		initJenkinsOperation();
		Map<String, Computer> computersInfos = jenkinsServerOperation
		        .getCurrentComputerList();
		List<Computer> computers = new ArrayList<>();
		computers.addAll(computersInfos.values());
		return computers;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#runJenkinsJob(com.tianque.yunxiao.connect.server.vo.JenkinsJobVO)
	 */
	@Override
	public void runJenkinsJob(JenkinsJobVO jenkinsJobVO) {
		initJenkinsOperation();
		GlobalParam globalParam = globalParamService.findGlobalParamByParamKey(
		        JenkinsDefine.JENKINS_KEY_OF_SVN_AUTH);

		if (null == globalParam
		        || StringUtil.isEmpty(globalParam.getParamValue())) {
			jenkinsJobVO.setSvnAuthId(JenkinsDefine.DEFAULT_SVN_AUTH_ID);

		} else {
			jenkinsJobVO.setSvnAuthId(globalParam.getParamValue());
		}

		this.jenkinsServerOperation.checkAndCreateOrUpdateJob(jenkinsJobVO);
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#getExecutionProcess(com.tianque.yunxiao.connect.server.vo.JenkinsJobVO)
	 */
	@Override
	public String getExecutionProcess(JenkinsJobVO jenkinsJobVO) {
		initJenkinsOperation();
		logger.info("根据任务:{}获取执行日志", jenkinsJobVO);
		return this.jenkinsServerOperation
		        .getJobLastBuildLogOut(jenkinsJobVO.getJobName());
	}

	/**
	 * 判断任务是否在执行
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#isJobRunning(java.lang.String)
	 */
	@Override
	public boolean isJobRunning(String jobName) {
		initJenkinsOperation();
		logger.info("判断任务是否正在执行:{}", jobName);
		return this.jenkinsServerOperation.isJobRunning(jobName);
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#getArtifactDetail(java.lang.String)
	 */
	@Override
	public InputStream getArtifactDetail(String jobName) {
		initJenkinsOperation();
		logger.info("获取job:{}的构建产物信息", jobName);

		List<Artifact> thisArtifcats = this.jenkinsServerOperation
		        .getArtifacts(jobName);

		if (thisArtifcats.isEmpty()) {
			throw new BusinessValidationException("没有构建产物");
		}

		return jenkinsServerOperation.downloadArtifact(jobName,
		        thisArtifcats.get(0));
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#getArtifactsDetail(java.lang.String)
	 */
	@Override
	public Map<String, InputStream> getArtifactsDetail(String jobName) {
		initJenkinsOperation();
		logger.info("获取job:{}的构建产物信息", jobName);

		List<Artifact> thisArtifcats = this.jenkinsServerOperation
		        .getArtifacts(jobName);

		if (thisArtifcats.isEmpty()) {
			throw new BusinessValidationException("没有构建产物");
		}

		Map<String, InputStream> artifactsStream = new HashMap<>(16);

		for (Artifact artifact : thisArtifcats) {
			InputStream inputStream = jenkinsServerOperation
			        .downloadArtifact(jobName, artifact);
			artifactsStream.put(artifact.getFileName(), inputStream);
		}
		return artifactsStream;
	}

	/**
	 * 取消job
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#cancelJob(java.lang.String)
	 */
	@Override
	public boolean cancelJob(String jobName) {
		initJenkinsOperation();
		logger.info("尝试取消任务:{}的执行", jobName);
		return this.jenkinsServerOperation.cancelJob(jobName);
	}

	/**
	 * 删除任务
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.JenkinsService#deleteJob(com.tianque.yunxiao.connect.server.vo.JenkinsJobVO)
	 */
	@Override
	public void deleteJob(JenkinsJobVO jenkinsJobVO) {
		initJenkinsOperation();
		logger.info("删除job:{}", jenkinsJobVO);
		if (null == jenkinsJobVO
		        || StringUtil.isEmpty(jenkinsJobVO.getJobName())) {
			logger.error("jenkinsjob为null，或者jobName为空或者为null，不可删除");
			return;
		}
		this.jenkinsServerOperation.deleteJob(jenkinsJobVO.getJobName());
	}
}
