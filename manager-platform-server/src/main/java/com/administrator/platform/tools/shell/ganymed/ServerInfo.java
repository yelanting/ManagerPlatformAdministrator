/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年7月29日 下午9:51:48
 * @see:
 */
package com.administrator.platform.tools.shell.ganymed;

public class ServerInfo {
	private String host;
	private int port;
	private String username;
	private String password;

	public ServerInfo(String host, int port, String username, String password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public ServerInfo(String host, String username, String password) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = 22;
	}

	public ServerInfo(String host) {
		super();
		this.host = host;
		this.port = 22;
		this.username = SshDefine.USERNAME;
		this.password = SshDefine.PASSWORD;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

}
