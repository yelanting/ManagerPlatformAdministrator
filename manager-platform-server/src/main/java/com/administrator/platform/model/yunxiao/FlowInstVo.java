/**
 * Project Name: manager-platform-server
 * File Name: FlowInstVo.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月10日 上午9:46:47
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author : 孙留平
 * @since : 2019年10月10日 上午9:46:47
 * @see :
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowInstVo {
	private List stageTopo;

	private Stage stages;

	/**
	 * @return the stageTopo
	 */
	public List getStageTopo() {
		return stageTopo;
	}

	/**
	 * @param stageTopo
	 *            the stageTopo to set
	 */
	public void setStageTopo(List stageTopo) {
		this.stageTopo = stageTopo;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "FlowInstVo [stageTopo=" + stageTopo + "]";
	}

	/**
	 * @return the stages
	 */
	public Stage getStages() {
		return stages;
	}

	/**
	 * @param stages
	 *            the stages to set
	 */
	public void setStages(Stage stages) {
		this.stages = stages;
	}
}
