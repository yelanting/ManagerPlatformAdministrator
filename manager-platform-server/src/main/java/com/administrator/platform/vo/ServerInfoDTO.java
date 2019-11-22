/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年7月31日 下午5:38:02
 * @see:
 */
package com.administrator.platform.vo;

import com.administrator.platform.model.ServerInfo;

public class ServerInfoDTO extends ServerInfo {

	@Override
	public String toString() {
		return "ServerInfoDTO [getServerName()=" + getServerName()
		        + ", getServerIp()=" + getServerIp() + ", getUsername()="
		        + getUsername() + ", getPassword()=" + getPassword()
		        + ", getServerType()=" + getServerType()
		        + ", isCanBeConnected()=" + isCanBeConnected() + ", toString()="
		        + super.toString() + ", getId()=" + getId()
		        + ", getCreateUser()=" + getCreateUser() + ", getUpdateUser()="
		        + getUpdateUser() + ", getSortField()=" + getSortField()
		        + ", getOrder()=" + getOrder() + ", getCreateDate()="
		        + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
		        + ", getMobile()=" + getMobile() + ", getDescription()="
		        + getDescription() + ", getClass()=" + getClass()
		        + ", hashCode()=" + hashCode() + "]";
	}

}
