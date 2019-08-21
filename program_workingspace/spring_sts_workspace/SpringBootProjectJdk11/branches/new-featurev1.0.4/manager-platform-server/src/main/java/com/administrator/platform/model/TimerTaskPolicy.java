/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月7日 上午9:08:38
 * @see: 定时任务的策略
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;

public class TimerTaskPolicy extends BaseDomain {

	private static final long serialVersionUID = 991451115775770758L;
	/**
	 * 中文名称
	 */
	private String cname;
	/**
	 * 英文名称
	 */
	private String ename;
	/**
	 * 目前只支持java代码
	 */

	private String code;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "TimerTaskPolicy [cname=" + cname + ", ename=" + ename
		        + ", code=" + code + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((ename == null) ? 0 : ename.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimerTaskPolicy other = (TimerTaskPolicy) obj;
		if (cname == null) {
			if (other.cname != null) {
				return false;
			}
		} else if (!cname.equals(other.cname)) {
			return false;
		}
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (ename == null) {
			if (other.ename != null) {
				return false;
			}
		} else if (!ename.equals(other.ename)) {
			return false;
		}
		return true;
	}
}
