/**
 * @author : 孙留平
 * @since : 2019年5月17日 下午2:30:43
 * @see:
 */
package com.administrator.platform.model.yunxiao;

/**
 * @author : Administrator
 * @since : 2019年5月17日 下午2:30:43
 * @see : 回调云效接口的入参
 */
public class CallBackYunXiaoParamDTO {
	/**
	 * 云效测试件ID，云效传给⾃动化测试平台，回调的时候需要带回来
	 */
	private String testSuiteRunId;
	/**
	 * 总case数
	 */
	private String total;

	/**
	 * 成功的case数
	 */
	private String success;

	/**
	 * 完成后的case总数
	 */
	private String completed;

	/**
	 * 执行机ip
	 */
	private String ips;

	/**
	 * 执行日志链接
	 */
	private String log;

	/**
	 * 任务详情链接
	 */
	private String relatedRunDetail;

	/**
	 * 测试结果总数据
	 */
	private String runCaseDoList;

	/**
	 * 回调url
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
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * @return the completed
	 */
	public String getCompleted() {
		return completed;
	}

	/**
	 * @param completed
	 *            the completed to set
	 */
	public void setCompleted(String completed) {
		this.completed = completed;
	}

	/**
	 * @return the ips
	 */
	public String getIps() {
		return ips;
	}

	/**
	 * @param ips
	 *            the ips to set
	 */
	public void setIps(String ips) {
		this.ips = ips;
	}

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * @return the relatedRunDetail
	 */
	public String getRelatedRunDetail() {
		return relatedRunDetail;
	}

	/**
	 * @param relatedRunDetail
	 *            the relatedRunDetail to set
	 */
	public void setRelatedRunDetail(String relatedRunDetail) {
		this.relatedRunDetail = relatedRunDetail;
	}

	/**
	 * @return the runCaseDoList
	 */
	public String getRunCaseDoList() {
		return runCaseDoList;
	}

	/**
	 * @param runCaseDoList
	 *            the runCaseDoList to set
	 */
	public void setRunCaseDoList(String runCaseDoList) {
		this.runCaseDoList = runCaseDoList;
	}

	/**
	 * @see : callBackUrl
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
		return "CallBackYunXiaoParamDTO [testSuiteRunId=" + testSuiteRunId
		        + ", total=" + total + ", success=" + success + ", completed="
		        + completed + ", ips=" + ips + ", log=" + log
		        + ", relatedRunDetail=" + relatedRunDetail + ", runCaseDoList="
		        + runCaseDoList + ", callBackUrl=" + callBackUrl + "]";
	}

}
