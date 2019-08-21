/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月7日 上午9:09:28
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import com.administrator.platform.model.base.BaseDomain;

public class TimerTaskMonitor extends BaseDomain {
	private static final long serialVersionUID = -4312361314158807312L;
	private String jobName;
	private Date startDate;
	private Date endDate;
	private boolean success;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "TimerTaskMonitor [jobName=" + jobName + ", startDate="
		        + startDate + ", endDate=" + endDate + ", success=" + success
		        + ", getId()=" + getId() + ", getCreateUser()="
		        + getCreateUser() + ", getUpdateUser()=" + getUpdateUser()
		        + ", getSortField()=" + getSortField() + ", getOrder()="
		        + getOrder() + ", getCreateDate()=" + getCreateDate()
		        + ", getUpdateDate()=" + getUpdateDate() + ", getMobile()="
		        + getMobile() + ", getDescription()=" + getDescription()
		        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
		        + ", toString()=" + super.toString() + "]";
	}

}
