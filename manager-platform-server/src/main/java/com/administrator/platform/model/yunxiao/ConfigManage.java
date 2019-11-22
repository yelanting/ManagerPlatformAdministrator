/**
 * Project Name: manager-platform-server
 * File Name: PipeLine.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月9日 上午9:19:53
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年10月9日 上午9:19:53
 * @see : 云效流水线
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigManage extends YunXiaoPipeLineDetail {
	private Long id;
	private Long gmtCreate;
	private String creator;
	private String projectName;
	private String projectType;
	private String projectDesc;
	private String productLineName;

	private Long planStartTime;
	private Long planReleaseTime;
	private Long actualStartTime;
	private Long actualReleaseTime;
	private String projectStatus;
	private String needTest;
	private Long companyId;
	private String userName;
	private String buildOrder;
	private String planPubTime;
	private String actualPubTime;
	private String haveBiz;
	private String akProjectId;
	private String sourceName;
	private String sourceId;
	private String sourceUrl;

	private List<ProjectApp> atonProjectAppList;
	private List<ProjectParticipantInfo> atonProjectParticipantInfoList;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
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
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the projectDesc
	 */
	public String getProjectDesc() {
		return projectDesc;
	}

	/**
	 * @param projectDesc
	 *            the projectDesc to set
	 */
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	/**
	 * @return the productLineName
	 */
	public String getProductLineName() {
		return productLineName;
	}

	/**
	 * @param productLineName
	 *            the productLineName to set
	 */
	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}

	/**
	 * @return the planStartTime
	 */
	public Long getPlanStartTime() {
		return planStartTime;
	}

	/**
	 * @param planStartTime
	 *            the planStartTime to set
	 */
	public void setPlanStartTime(Long planStartTime) {
		this.planStartTime = planStartTime;
	}

	/**
	 * @return the planReleaseTime
	 */
	public Long getPlanReleaseTime() {
		return planReleaseTime;
	}

	/**
	 * @param planReleaseTime
	 *            the planReleaseTime to set
	 */
	public void setPlanReleaseTime(Long planReleaseTime) {
		this.planReleaseTime = planReleaseTime;
	}

	/**
	 * @return the actualStartTime
	 */
	public Long getActualStartTime() {
		return actualStartTime;
	}

	/**
	 * @param actualStartTime
	 *            the actualStartTime to set
	 */
	public void setActualStartTime(Long actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	/**
	 * @return the actualReleaseTime
	 */
	public Long getActualReleaseTime() {
		return actualReleaseTime;
	}

	/**
	 * @param actualReleaseTime
	 *            the actualReleaseTime to set
	 */
	public void setActualReleaseTime(Long actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}

	/**
	 * @return the projectStatus
	 */
	public String getProjectStatus() {
		return projectStatus;
	}

	/**
	 * @param projectStatus
	 *            the projectStatus to set
	 */
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	/**
	 * @return the needTest
	 */
	public String getNeedTest() {
		return needTest;
	}

	/**
	 * @param needTest
	 *            the needTest to set
	 */
	public void setNeedTest(String needTest) {
		this.needTest = needTest;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	 * @return the buildOrder
	 */
	public String getBuildOrder() {
		return buildOrder;
	}

	/**
	 * @param buildOrder
	 *            the buildOrder to set
	 */
	public void setBuildOrder(String buildOrder) {
		this.buildOrder = buildOrder;
	}

	/**
	 * @return the planPubTime
	 */
	public String getPlanPubTime() {
		return planPubTime;
	}

	/**
	 * @param planPubTime
	 *            the planPubTime to set
	 */
	public void setPlanPubTime(String planPubTime) {
		this.planPubTime = planPubTime;
	}

	/**
	 * @return the actualPubTime
	 */
	public String getActualPubTime() {
		return actualPubTime;
	}

	/**
	 * @param actualPubTime
	 *            the actualPubTime to set
	 */
	public void setActualPubTime(String actualPubTime) {
		this.actualPubTime = actualPubTime;
	}

	/**
	 * @return the haveBiz
	 */
	public String getHaveBiz() {
		return haveBiz;
	}

	/**
	 * @param haveBiz
	 *            the haveBiz to set
	 */
	public void setHaveBiz(String haveBiz) {
		this.haveBiz = haveBiz;
	}

	/**
	 * @return the akProjectId
	 */
	public String getAkProjectId() {
		return akProjectId;
	}

	/**
	 * @param akProjectId
	 *            the akProjectId to set
	 */
	public void setAkProjectId(String akProjectId) {
		this.akProjectId = akProjectId;
	}

	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the sourceUrl
	 */
	public String getSourceUrl() {
		return sourceUrl;
	}

	/**
	 * @param sourceUrl
	 *            the sourceUrl to set
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	/**
	 * @return the atonProjectAppList
	 */
	public List<ProjectApp> getAtonProjectAppList() {
		return atonProjectAppList;
	}

	/**
	 * @param atonProjectAppList
	 *            the atonProjectAppList to set
	 */
	public void setAtonProjectAppList(List<ProjectApp> atonProjectAppList) {
		this.atonProjectAppList = atonProjectAppList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "ConfigManage [id=" + id + ", gmtCreate=" + gmtCreate
		        + ", creator=" + creator + ", projectName=" + projectName
		        + ", projectType=" + projectType + ", projectDesc="
		        + projectDesc + ", productLineName=" + productLineName
		        + ", planStartTime=" + planStartTime + ", planReleaseTime="
		        + planReleaseTime + ", actualStartTime=" + actualStartTime
		        + ", actualReleaseTime=" + actualReleaseTime
		        + ", projectStatus=" + projectStatus + ", needTest=" + needTest
		        + ", companyId=" + companyId + ", userName=" + userName
		        + ", buildOrder=" + buildOrder + ", planPubTime=" + planPubTime
		        + ", actualPubTime=" + actualPubTime + ", haveBiz=" + haveBiz
		        + ", akProjectId=" + akProjectId + ", sourceName=" + sourceName
		        + ", sourceId=" + sourceId + ", sourceUrl=" + sourceUrl
		        + ", atonProjectAppList=" + atonProjectAppList
		        + ", atonProjectParticipantInfoList="
		        + atonProjectParticipantInfoList + "]";
	}

	/**
	 * @return the atonProjectParticipantInfoList
	 */
	public List<ProjectParticipantInfo> getAtonProjectParticipantInfoList() {
		return atonProjectParticipantInfoList;
	}

	/**
	 * @param atonProjectParticipantInfoList
	 *            the atonProjectParticipantInfoList to set
	 */
	public void setAtonProjectParticipantInfoList(
	        List<ProjectParticipantInfo> atonProjectParticipantInfoList) {
		this.atonProjectParticipantInfoList = atonProjectParticipantInfoList;
	}

}
