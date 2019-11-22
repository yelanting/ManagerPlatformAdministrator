/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月27日 上午9:34:58
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;
import com.administrator.platform.tools.vcs.common.VCSType;

public class JavaParserRecord extends BaseDomain {
	/**
	 * @see 项目名称
	 */
	private String projectName;
	/**
	 * @see 存放位置
	 */
	private String pinYinName;

	/**
	 * 代码管理系统用户名称
	 */
	private String username;

	/**
	 * 代码管理系统用户密码
	 */
	private String password;

	/**
	 * 版本控制系统类型
	 */
	private VCSType versionControlType;

	/**
	 * 新版本的版本路径
	 */
	private String newerRemoteUrl;
	/**
	 * 新版本
	 */
	private String newerVersion;

	/**
	 */
	private String resultUrl;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPinYinName() {
		return pinYinName;
	}

	public void setPinYinName(String pinYinName) {
		this.pinYinName = pinYinName;
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

	public VCSType getVersionControlType() {
		return versionControlType;
	}

	public void setVersionControlType(VCSType versionControlType) {
		this.versionControlType = versionControlType;
	}

	public String getNewerRemoteUrl() {
		return newerRemoteUrl;
	}

	public void setNewerRemoteUrl(String newerRemoteUrl) {
		this.newerRemoteUrl = newerRemoteUrl;
	}

	public String getNewerVersion() {
		return newerVersion;
	}

	public void setNewerVersion(String newerVersion) {
		this.newerVersion = newerVersion;
	}

	@Override
	public String toString() {
		return "JavaParserRecord [projectName=" + projectName + ", pinYinName="
		        + pinYinName + ", username=" + username + ", password="
		        + password + ", versionControlType=" + versionControlType
		        + ", newerRemoteUrl=" + newerRemoteUrl + ", newerVersion="
		        + newerVersion + ", resultUrl=" + resultUrl + "]";
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

}
