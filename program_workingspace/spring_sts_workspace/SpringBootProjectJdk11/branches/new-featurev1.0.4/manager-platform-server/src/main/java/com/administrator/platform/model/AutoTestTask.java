/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:43:56
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.constdefine.JenkinsDefine;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:43:56
 * @see :
 */

public class AutoTestTask extends BaseDomain {
	/**
	 * @see 任务名称
	 */
	private String taskName;

	/**
	 * @see 执行机ip
	 */
	private String executorIp;

	/**
	 * @see 服务器端口
	 */
	private Integer serverPort = 23;

	/**
	 * @see 登陆用户名
	 */
	private String loginUserName = "administrator";

	/**
	 * @see 登陆密码
	 */

	private String loginPassword = "AutoTest123";

	/**
	 * @see 执行脚本
	 */

	private String toExecuteScriptName;

	/**
	 * @see 执行结果
	 */

	private String executeResultUrl;

	/**
	 * @see 执行详情查看的地址
	 */
	private String executeDetailUrl;
	/**
	 * @see 执行日志
	 */
	private String executeDetailOutPut;

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the executorIp
	 */
	public String getExecutorIp() {
		return executorIp;
	}

	/**
	 * @param executorIp
	 *            the executorIp to set
	 */
	public void setExecutorIp(String executorIp) {
		this.executorIp = executorIp;
	}

	/**
	 * @return the serverPort
	 */
	public Integer getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the loginUserName
	 */
	public String getLoginUserName() {
		return loginUserName;
	}

	/**
	 * @param loginUserName
	 *            the loginUserName to set
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword
	 *            the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * @return the toExecuteScriptName
	 */
	public String getToExecuteScriptName() {
		return toExecuteScriptName;
	}

	/**
	 * @param toExecuteScriptName
	 *            the toExecuteScriptName to set
	 */
	public void setToExecuteScriptName(String toExecuteScriptName) {
		this.toExecuteScriptName = toExecuteScriptName;
	}

	/**
	 * @return the executeResultUrl
	 */
	public String getExecuteResultUrl() {
		return executeResultUrl;
	}

	/**
	 * @param executeResultUrl
	 *            the executeResultUrl to set
	 */
	public void setExecuteResultUrl(String executeResultUrl) {
		this.executeResultUrl = executeResultUrl;
	}

	/**
	 * @return the executeDetailUrl
	 */
	public String getExecuteDetailUrl() {
		return executeDetailUrl;
	}

	/**
	 * @param executeDetailUrl
	 *            the executeDetailUrl to set
	 */
	public void setExecuteDetailUrl(String executeDetailUrl) {
		this.executeDetailUrl = executeDetailUrl;
	}

	/**
	 * @return the executeDetailOutPut
	 */
	public String getExecuteDetailOutPut() {
		return executeDetailOutPut;
	}

	/**
	 * @param executeDetailOutPut
	 *            the executeDetailOutPut to set
	 */
	public void setExecuteDetailOutPut(String executeDetailOutPut) {
		this.executeDetailOutPut = executeDetailOutPut;
	}

	/**
	 * 从云效接入输入参数中，转化
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param yunXiaoConnectAutoTestTask
	 * @return
	 */
	public static AutoTestTask fromYunXiaoConnectAutoTestTask(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask) {
		AutoTestTask autoTestTask = new AutoTestTask();

		ValidationUtil.validateStringNullOrEmpty(
		        yunXiaoConnectAutoTestTask.getTestSuiteRunId(),
		        "testSuiteRunId不能空或空白");

		String suiteId = yunXiaoConnectAutoTestTask.getSuiteId();

		ValidationUtil.validateStringNullOrEmpty(suiteId, "suiteId不能空或空白");
		ValidationUtil.validateStringContainBlackInStartOrEnd(suiteId, null);

		if (suiteId.length() != suiteId.trim().length()) {
			throw new BusinessValidationException("suiteId前后不能有空格或空白");
		}

		suiteId = suiteId.trim();
		autoTestTask.setToExecuteScriptName(suiteId.trim());

		if (suiteId.contains(JenkinsDefine.DEFAULT_PYTHON_SUFFIX)) {
			autoTestTask.setTaskName(suiteId.substring(0, suiteId.length()
			        - (JenkinsDefine.DEFAULT_PYTHON_SUFFIX.length())));
		} else {
			autoTestTask.setTaskName(yunXiaoConnectAutoTestTask.getSuiteId());
		}
		return autoTestTask;
	}
}
