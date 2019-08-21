/**
 * @author : 孙留平
 * @since : 2019年5月9日 上午11:21:47
 * @see:
 */
package com.administrator.platform.model;

/**
 * @author : Administrator
 * @since : 2019年5月9日 上午11:21:47
 * @see : 云效接入数据
 */
public class YunXiaoConnectAutoTestTask {
	/**
	 * @see 云效测试件ID，云效提供（回调时使⽤）
	 */
	private String testSuiteRunId;

	/**
	 * @see 测试平台测试件ID，对应脚本名称
	 */
	private String suiteId;

	/**
	 * @see 云效提供的回调url，自动化任务执行完之后回调
	 */
	private String callBackUrl;

	/**
	 * @return the testSuiteRunId
	 */
	public String getTestSuiteRunId() {
		return testSuiteRunId;
	}

	/**
	 * @param testSuiteRunId
	 *            the testSuiteRunId to set
	 */
	public void setTestSuiteRunId(String testSuiteRunId) {
		this.testSuiteRunId = testSuiteRunId;
	}

	/**
	 * @return the suiteId
	 */
	public String getSuiteId() {
		return suiteId;
	}

	/**
	 * @param suiteId
	 *            the suiteId to set
	 */
	public void setSuiteId(String suiteId) {
		this.suiteId = suiteId;
	}

	/**
	 * @return the callBackUrl
	 */
	public String getCallBackUrl() {
		return callBackUrl;
	}

	/**
	 * @param callBackUrl
	 *            the callBackUrl to set
	 */
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YunXiaoConnectAutoTestTask [testSuiteRunId=" + testSuiteRunId
		        + ", suiteId=" + suiteId + ", callBackUrl=" + callBackUrl + "]";
	}

}
