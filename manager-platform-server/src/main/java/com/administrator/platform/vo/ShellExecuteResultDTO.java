/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月1日 下午1:14:50
 * @see:
 */
package com.administrator.platform.vo;

public class ShellExecuteResultDTO extends ServerInfoDTO {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ShellExecuteResultDTO [result=" + result + ", getResult()="
		        + getResult() + ", toString()=" + super.toString()
		        + ", getServerName()=" + getServerName() + ", getServerIp()="
		        + getServerIp() + ", getUsername()=" + getUsername()
		        + ", getPassword()=" + getPassword() + ", getServerType()="
		        + getServerType() + ", isCanBeConnected()=" + isCanBeConnected()
		        + ", getId()=" + getId() + ", getCreateUser()="
		        + getCreateUser() + ", getUpdateUser()=" + getUpdateUser()
		        + ", getSortField()=" + getSortField() + ", getOrder()="
		        + getOrder() + ", getCreateDate()=" + getCreateDate()
		        + ", getUpdateDate()=" + getUpdateDate() + ", getMobile()="
		        + getMobile() + ", getDescription()=" + getDescription()
		        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
		        + "]";
	}

}
