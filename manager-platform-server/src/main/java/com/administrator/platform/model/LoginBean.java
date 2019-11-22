/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月16日 下午1:39:16
 * @see:
 */
package com.administrator.platform.model;

public class LoginBean {
	private String username;

	private String password;

	private String captcha;

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

	@Override
	public String toString() {
		return "LoginBean [username=" + username + ", password=" + password
		        + ", captcha=" + captcha + "]";
	}

	public LoginBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginBean() {
		super();
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
