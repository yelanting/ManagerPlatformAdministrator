/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年7月31日 下午4:51:33
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;

public class ServerInfo extends BaseDomain {
	/**
	 */
	private static final long serialVersionUID = 1739407232617225422L;
	private String serverName;
	private String serverIp;
	private String username;
	private String password;
	private String serverType;
	private boolean canBeConnected;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public boolean isCanBeConnected() {
		return canBeConnected;
	}

	public void setCanBeConnected(boolean canBeConnected) {
		this.canBeConnected = canBeConnected;
	}

	@Override
	public String toString() {
		return "ServerInfo [serverName=" + serverName + ", serverIp=" + serverIp
		        + ", username=" + username + ", password=" + password
		        + ", serverType=" + serverType + ", canBeConnected="
		        + canBeConnected + "]";
	}
}
