
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:YunXiaoResponseData.java
 * @author : 孙留平
 * @since : 2019年5月30日 下午2:28:30
 * @see:
 */

package com.administrator.platform.model.yunxiao;

import com.administrator.platform.vo.ResponseData;

public class YunXiaoResponseData {
	ResponseData responseData;

	public YunXiaoResponseData() {
		this.responseData = new ResponseData();
	}

	public YunXiaoResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public static YunXiaoResponseData getResult(ResponseData responseData) {
		return new YunXiaoResponseData(responseData);
	}

}
