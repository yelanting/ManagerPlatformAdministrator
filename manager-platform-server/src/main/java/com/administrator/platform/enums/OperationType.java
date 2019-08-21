/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月5日 下午12:54:13
 * @see:
 */
package com.administrator.platform.enums;

public enum OperationType {

	/**
	 * 正常
	 */
	BORROW("借出"),

	/**
	 * 已借出
	 */
	GIVE_BACK("归还");

	private String statusName;

	private OperationType(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
