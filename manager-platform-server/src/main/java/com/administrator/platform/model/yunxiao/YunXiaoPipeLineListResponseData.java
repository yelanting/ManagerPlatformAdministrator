/**
 * Project Name: manager-platform-server
 * File Name: YunXiaoPipeLineResponseData.java
 * Package Name: com.administrator.platform.model.yunxiao
 * Date: 2019年10月8日 下午2:26:41
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.model.yunxiao;

import java.util.List;

/**
 * @author : 孙留平
 * @since : 2019年10月8日 下午2:26:41
 * @see : 云效流水线接收列表对象
 */
public class YunXiaoPipeLineListResponseData {
	private List<YunXiaoPipeLineSimple> data;

	/**
	 * @return the data
	 */
	public List<YunXiaoPipeLineSimple> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<YunXiaoPipeLineSimple> data) {
		this.data = data;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * @return
	 */
	@Override
	public String toString() {
		return "YunXiaoPipeLineResponseData [data=" + data + "]";
	}
}
