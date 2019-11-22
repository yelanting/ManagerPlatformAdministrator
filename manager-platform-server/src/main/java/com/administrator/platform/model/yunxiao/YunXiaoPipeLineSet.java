/**
 * Project Name: manager-platform-server
 * File Name: PipeLineSet.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月10日 上午9:04:19
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.util.List;

/**
 * @author : 孙留平
 * @since : 2019年10月10日 上午9:04:19
 * @see : 流水线组
 */
public class YunXiaoPipeLineSet {
	private Long id;
	private String creator;
	private String modifier;
	private String pipelineSetName;
	private String refObjType;
	private String refIbjId;
	private Long companyId;
	private boolean deleted;
	private List topology;
	private String projectName;

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
	 * @return the refObjType
	 */
	public String getRefObjType() {
		return refObjType;
	}

	/**
	 * @param refObjType
	 *            the refObjType to set
	 */
	public void setRefObjType(String refObjType) {
		this.refObjType = refObjType;
	}

	/**
	 * @return the refIbjId
	 */
	public String getRefIbjId() {
		return refIbjId;
	}

	/**
	 * @param refIbjId
	 *            the refIbjId to set
	 */
	public void setRefIbjId(String refIbjId) {
		this.refIbjId = refIbjId;
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
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the topology
	 */
	public List getTopology() {
		return topology;
	}

	/**
	 * @param topology
	 *            the topology to set
	 */
	public void setTopology(List topology) {
		this.topology = topology;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "YunXiaoPipeLineSet [id=" + id + ", creator=" + creator
		        + ", modifier=" + modifier + ", pipelineSetName="
		        + pipelineSetName + ", refObjType=" + refObjType + ", refIbjId="
		        + refIbjId + ", companyId=" + companyId + ", deleted=" + deleted
		        + ", topology=" + topology + ", projectName=" + projectName
		        + "]";
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

}
