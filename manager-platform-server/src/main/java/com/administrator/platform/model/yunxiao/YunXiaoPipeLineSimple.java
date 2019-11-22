/**
 * Project Name: manager-platform-server
 * File Name: YunXiaoPipeLineSimple.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月8日 下午2:21:22
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.io.Serializable;

/**
 * @author : 孙留平
 * @since : 2019年10月8日 下午2:21:22
 * @see : 云效流水线简洁
 */
public class YunXiaoPipeLineSimple implements Serializable {
	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = 2434245718250873257L;
	private Long id;
	private String name;
	private String type;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "YunXiaoPipeLineSimple [id=" + id + ", name=" + name + ", type="
		        + type + "]";
	}

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
}
