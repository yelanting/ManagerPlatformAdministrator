/**
 * Project Name: manager-platform-server
 * File Name: PipeLineConfigVo.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月10日 上午9:44:19
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年10月10日 上午9:44:19
 * @see :
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipeLineConfigVo {
	private Long id;
	private String creator;
	private String modifier;
	private String isDeleted;
	private String pipelineId;
	private String sign;
	private String version;
	private String flow;
	private String settings;
	private Long pictureFileId;
	private List triggerVoList;

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
	 * @return the pipelineId
	 */
	public String getPipelineId() {
		return pipelineId;
	}

	/**
	 * @param pipelineId
	 *            the pipelineId to set
	 */
	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign
	 *            the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
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
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}

	/**
	 * @param flow
	 *            the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}

	/**
	 * @return the settings
	 */
	public String getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(String settings) {
		this.settings = settings;
	}

	/**
	 * @return the pictureFileId
	 */
	public Long getPictureFileId() {
		return pictureFileId;
	}

	/**
	 * @param pictureFileId
	 *            the pictureFileId to set
	 */
	public void setPictureFileId(Long pictureFileId) {
		this.pictureFileId = pictureFileId;
	}

	/**
	 * @return the triggerVoList
	 */
	public List getTriggerVoList() {
		return triggerVoList;
	}

	/**
	 * @param triggerVoList
	 *            the triggerVoList to set
	 */
	public void setTriggerVoList(List triggerVoList) {
		this.triggerVoList = triggerVoList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "PipeLineConfigVo [id=" + id + ", creator=" + creator
		        + ", modifier=" + modifier + ", isDeleted=" + isDeleted
		        + ", pipelineId=" + pipelineId + ", sign=" + sign + ", version="
		        + version + ", flow=" + flow + ", settings=" + settings
		        + ", pictureFileId=" + pictureFileId + ", triggerVoList="
		        + triggerVoList + "]";
	}

}
