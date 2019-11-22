/**
 * Project Name: manager-platform-server
 * File Name: ProjectParticipantInfo.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年11月6日 下午5:16:21
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年11月6日 下午5:16:21
 * @see :
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectParticipantInfo {
	private Long id;
	private Long projectId;
	private String userName;
	private String role;
	private String exterName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the exterName
	 */
	public String getExterName() {
		return exterName;
	}

	/**
	 * @param exterName
	 *            the exterName to set
	 */
	public void setExterName(String exterName) {
		this.exterName = exterName;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "ProjectParticipantInfo [id=" + id + ", projectId=" + projectId
		        + ", userName=" + userName + ", role=" + role + ", exterName="
		        + exterName + "]";
	}

}
