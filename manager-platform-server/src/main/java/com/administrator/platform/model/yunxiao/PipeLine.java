/**
 * Project Name: manager-platform-server
 * File Name: PipeLine.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月9日 上午9:19:53
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年10月9日 上午9:19:53
 * @see : 云效流水线
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PipeLine extends YunXiaoPipeLineDetail {
	private Long id;
	private String creator;
	private String modifier;
	private String isDeleted;

	private String name;

	private String description;
	private String status;
	private String type;
	private String subtype;
	private String readOnly;
	private String pipelineSetId;
	private String pipelineSetName;
	private String appName;
	private String appDetailUrl;
	private String projectName;
	private String projectId;
	private String owner;
	private String branch;

	private String template;

	private PipeLineConfigVo pipelineConfigVo;
	private FlowInstVo flowInstVo;

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
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier
	 *            the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the subtype
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * @param subtype
	 *            the subtype to set
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	/**
	 * @return the readOnly
	 */
	public String getReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the pipelineSetId
	 */
	public String getPipelineSetId() {
		return pipelineSetId;
	}

	/**
	 * @param pipelineSetId
	 *            the pipelineSetId to set
	 */
	public void setPipelineSetId(String pipelineSetId) {
		this.pipelineSetId = pipelineSetId;
	}

	/**
	 * @return the pipelineSetName
	 */
	public String getPipelineSetName() {
		return pipelineSetName;
	}

	/**
	 * @param pipelineSetName
	 *            the pipelineSetName to set
	 */
	public void setPipelineSetName(String pipelineSetName) {
		this.pipelineSetName = pipelineSetName;
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
	 * @return the appDetailUrl
	 */
	public String getAppDetailUrl() {
		return appDetailUrl;
	}

	/**
	 * @param appDetailUrl
	 *            the appDetailUrl to set
	 */
	public void setAppDetailUrl(String appDetailUrl) {
		this.appDetailUrl = appDetailUrl;
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
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "PipeLine [id=" + id + ", creator=" + creator + ", modifier="
		        + modifier + ", isDeleted=" + isDeleted + ", name=" + name
		        + ", description=" + description + ", status=" + status
		        + ", type=" + type + ", subtype=" + subtype + ", readOnly="
		        + readOnly + ", pipelineSetId=" + pipelineSetId
		        + ", pipelineSetName=" + pipelineSetName + ", appName="
		        + appName + ", appDetailUrl=" + appDetailUrl + ", projectName="
		        + projectName + ", projectId=" + projectId + ", owner=" + owner
		        + ", branch=" + branch + ", template=" + template
		        + ", pipelineConfigVo=" + pipelineConfigVo + ", flowInstVo="
		        + flowInstVo + "]";
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the pipelineConfigVo
	 */
	public PipeLineConfigVo getPipelineConfigVo() {
		return pipelineConfigVo;
	}

	/**
	 * @param pipelineConfigVo
	 *            the pipelineConfigVo to set
	 */
	public void setPipelineConfigVo(PipeLineConfigVo pipelineConfigVo) {
		this.pipelineConfigVo = pipelineConfigVo;
	}

	/**
	 * @return the flowInstVo
	 */
	public FlowInstVo getFlowInstVo() {
		return flowInstVo;
	}

	/**
	 * @param flowInstVo
	 *            the flowInstVo to set
	 */
	public void setFlowInstVo(FlowInstVo flowInstVo) {
		this.flowInstVo = flowInstVo;
	}

}
