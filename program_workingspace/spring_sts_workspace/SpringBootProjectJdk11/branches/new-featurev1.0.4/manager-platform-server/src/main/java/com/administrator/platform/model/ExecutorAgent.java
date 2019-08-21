/**
 * @author : 孙留平
 * @since : 2019年5月13日 上午9:45:16
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2019年5月13日 上午9:45:16
 * @see :
 */
public class ExecutorAgent extends BaseDomain {
	private String executorName;
	private String executorIp;
	private Boolean offline;
	private Boolean idle;

	/**
	 * @return the executorName
	 */
	public String getExecutorName() {
		return executorName;
	}

	/**
	 * @param executorName
	 *            the executorName to set
	 */
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
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
	 * @return the offline
	 */
	public Boolean getOffline() {
		return offline;
	}

	/**
	 * @param offline
	 *            the offline to set
	 */
	public void setOffline(Boolean offline) {
		this.offline = offline;
	}

	/**
	 * @return the idle
	 */
	public Boolean getIdle() {
		return idle;
	}

	/**
	 * @param idle
	 *            the idle to set
	 */
	public void setIdle(Boolean idle) {
		this.idle = idle;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExecutorAgent [executorName=" + executorName + ", executorIp="
		        + executorIp + ", offline=" + offline + ", idle=" + idle + "]";
	}

}
