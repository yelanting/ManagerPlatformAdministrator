/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月2日 下午7:51:00
 * @see:
 */
package com.administrator.platform.tools.jenkins;

public class JenkinsUser {
	private String username;
	private String password1;
	private String password2;
	private String fullname;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "JenkinsUser [username=" + username + ", password1=" + password1
		        + ", password2=" + password2 + ", fullname=" + fullname
		        + ", email=" + email + "]";
	}

}
