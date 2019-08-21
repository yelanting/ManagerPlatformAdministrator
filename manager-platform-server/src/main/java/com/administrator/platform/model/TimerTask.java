/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月7日 上午9:09:28
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;
import com.administrator.platform.tools.schedule.TaskDefine;

public class TimerTask extends BaseDomain {
	private static final long serialVersionUID = -2599252066144944822L;
	private String taskName;
	private String taskGroup = TaskDefine.DEFAULT_TASK_GROUP_NAME;
	private String config;
	private boolean closed;
	private Long policyId;

	/**
	 * 2019年8月9日 15:43:03新增参数
	 */
	private String otherParams;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean getClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(String otherParams) {
		this.otherParams = otherParams;
	}

	@Override
	public String toString() {
		return "TimerTask [taskName=" + taskName + ", taskGroup=" + taskGroup
		        + ", config=" + config + ", closed=" + closed + ", policyId="
		        + policyId + ", otherParams=" + otherParams + "]";
	}
}
