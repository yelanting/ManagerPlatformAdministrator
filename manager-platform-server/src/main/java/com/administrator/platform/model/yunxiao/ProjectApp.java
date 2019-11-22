/**
 * Project Name: manager-platform-server
 * File Name: ProjectApp.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年11月6日 下午5:07:28
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */

package com.administrator.platform.model.yunxiao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年11月6日 下午5:07:28
 * @see : 云效需要的实体类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectApp {
	private Long id;
	private Long gmtCreate;

	private Long gmtModified;
	private Long projectId;
	private Long appId;
	private String appName;
	private String branchUrl;
	private String appStatus;
	private String appInfo;
	private String vcsAuthInfo;
	private String appType;
	private String devType;
	private String lastPackage;
	private String integrationStatus;
	private String codeVersion;
	private String versionCompareResult;
	private String group;
	private String artifact;
	private String version;
	private String remark;
	private String integrationTime;
	private String deployOrder;
	private String oldVersion;
	private String packageType;
	private String envIds;

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
	 * @return the gmtCreate
	 */
	public Long getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate
	 *            the gmtCreate to set
	 */
	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return the gmtModified
	 */
	public Long getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
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
	 * @return the appId
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the branchUrl
	 */
	public String getBranchUrl() {
		return branchUrl;
	}

	/**
	 * @param branchUrl
	 *            the branchUrl to set
	 */
	public void setBranchUrl(String branchUrl) {
		this.branchUrl = branchUrl;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus
	 *            the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the appInfo
	 */
	public String getAppInfo() {
		return appInfo;
	}

	/**
	 * @param appInfo
	 *            the appInfo to set
	 */
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}

	/**
	 * @return the vcsAuthInfo
	 */
	public String getVcsAuthInfo() {
		return vcsAuthInfo;
	}

	/**
	 * @param vcsAuthInfo
	 *            the vcsAuthInfo to set
	 */
	public void setVcsAuthInfo(String vcsAuthInfo) {
		this.vcsAuthInfo = vcsAuthInfo;
	}

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 *            the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return the devType
	 */
	public String getDevType() {
		return devType;
	}

	/**
	 * @param devType
	 *            the devType to set
	 */
	public void setDevType(String devType) {
		this.devType = devType;
	}

	/**
	 * @return the lastPackage
	 */
	public String getLastPackage() {
		return lastPackage;
	}

	/**
	 * @param lastPackage
	 *            the lastPackage to set
	 */
	public void setLastPackage(String lastPackage) {
		this.lastPackage = lastPackage;
	}

	/**
	 * @return the integrationStatus
	 */
	public String getIntegrationStatus() {
		return integrationStatus;
	}

	/**
	 * @param integrationStatus
	 *            the integrationStatus to set
	 */
	public void setIntegrationStatus(String integrationStatus) {
		this.integrationStatus = integrationStatus;
	}

	/**
	 * @return the codeVersion
	 */
	public String getCodeVersion() {
		return codeVersion;
	}

	/**
	 * @param codeVersion
	 *            the codeVersion to set
	 */
	public void setCodeVersion(String codeVersion) {
		this.codeVersion = codeVersion;
	}

	/**
	 * @return the versionCompareResult
	 */
	public String getVersionCompareResult() {
		return versionCompareResult;
	}

	/**
	 * @param versionCompareResult
	 *            the versionCompareResult to set
	 */
	public void setVersionCompareResult(String versionCompareResult) {
		this.versionCompareResult = versionCompareResult;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the artifact
	 */
	public String getArtifact() {
		return artifact;
	}

	/**
	 * @param artifact
	 *            the artifact to set
	 */
	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the integrationTime
	 */
	public String getIntegrationTime() {
		return integrationTime;
	}

	/**
	 * @param integrationTime
	 *            the integrationTime to set
	 */
	public void setIntegrationTime(String integrationTime) {
		this.integrationTime = integrationTime;
	}

	/**
	 * @return the deployOrder
	 */
	public String getDeployOrder() {
		return deployOrder;
	}

	/**
	 * @param deployOrder
	 *            the deployOrder to set
	 */
	public void setDeployOrder(String deployOrder) {
		this.deployOrder = deployOrder;
	}

	/**
	 * @return the oldVersion
	 */
	public String getOldVersion() {
		return oldVersion;
	}

	/**
	 * @param oldVersion
	 *            the oldVersion to set
	 */
	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}

	/**
	 * @return the packageType
	 */
	public String getPackageType() {
		return packageType;
	}

	/**
	 * @param packageType
	 *            the packageType to set
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	/**
	 * @return the envIds
	 */
	public String getEnvIds() {
		return envIds;
	}

	/**
	 * @param envIds
	 *            the envIds to set
	 */
	public void setEnvIds(String envIds) {
		this.envIds = envIds;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "ProjectApp [id=" + id + ", gmtCreate=" + gmtCreate
		        + ", gmtModified=" + gmtModified + ", projectId=" + projectId
		        + ", appId=" + appId + ", appName=" + appName + ", branchUrl="
		        + branchUrl + ", appStatus=" + appStatus + ", appInfo="
		        + appInfo + ", vcsAuthInfo=" + vcsAuthInfo + ", appType="
		        + appType + ", devType=" + devType + ", lastPackage="
		        + lastPackage + ", integrationStatus=" + integrationStatus
		        + ", codeVersion=" + codeVersion + ", versionCompareResult="
		        + versionCompareResult + ", group=" + group + ", artifact="
		        + artifact + ", version=" + version + ", remark=" + remark
		        + ", integrationTime=" + integrationTime + ", deployOrder="
		        + deployOrder + ", oldVersion=" + oldVersion + ", packageType="
		        + packageType + ", envIds=" + envIds + "]";
	}
}
