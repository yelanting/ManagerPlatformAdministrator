/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月5日 下午12:54:13
 * @see:
 */
package com.administrator.platform.enums;

public enum GoodStatus {

	/**
	 * 正常
	 */
	NORMAL("正常"),

	/**
	 * 已借出
	 */
	BORROWED("已借出");

	private String statusName;

	private GoodStatus(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
