/**
 * @author : 孙留平
 * @since : 2019年5月10日 下午6:12:40
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2019年5月10日 下午6:12:40
 * @see :
 */
public class GlobalParam extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 6254823781364259559L;
	private String paramKey;
	private String paramValue;
	private String paramComment;

	/**
	 * @return the paramKey
	 */
	public String getParamKey() {
		return paramKey;
	}

	/**
	 * @param paramKey
	 *            the paramKey to set
	 */
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue
	 *            the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * @return the paramComment
	 */
	public String getParamComment() {
		return paramComment;
	}

	/**
	 * @param paramComment
	 *            the paramComment to set
	 */
	public void setParamComment(String paramComment) {
		this.paramComment = paramComment;
	}

	public GlobalParam(String paramKey, String paramValue,
	        String paramComment) {
		super();
		this.paramKey = paramKey;
		this.paramValue = paramValue;
		this.paramComment = paramComment;
	}
}
