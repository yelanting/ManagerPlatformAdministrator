/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月16日 下午1:39:16
 * @see:
 */
package com.administrator.platform.vo;

public class RegisterBean {
	private String username;

	private String userNickname;

	private String password;

	private String confirmPassword;
	private String sex;

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

	public RegisterBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public RegisterBean() {
		super();
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	@Override
	public String toString() {
		return "RegisterBean [username=" + username + ", userNickname="
		        + userNickname + ", password=" + password + ", confirmPassword="
		        + confirmPassword + ", sex=" + sex + "]";
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
