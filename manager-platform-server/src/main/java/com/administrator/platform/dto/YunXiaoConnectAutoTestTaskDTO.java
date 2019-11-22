/**
 * @author : 孙留平
 * @since : 2019年5月9日 上午11:25:59
 * @see:
 */
package com.administrator.platform.dto;

import com.administrator.platform.model.YunXiaoConnectAutoTestTask;

/**
 * @author : Administrator
 * @since : 2019年5月9日 上午11:25:59
 * @see : 云效接入数据的dto
 */
public class YunXiaoConnectAutoTestTaskDTO extends YunXiaoConnectAutoTestTask {

	@Override
	public String toString() {
		return "YunXiaoConnectAutoTestTaskDTO [getTestSuiteRunId()="
		        + getTestSuiteRunId() + ", getSuiteId()=" + getSuiteId()
		        + ", getCallBackUrl()=" + getCallBackUrl() + ", toString()="
		        + super.toString() + ", getClass()=" + getClass()
		        + ", hashCode()=" + hashCode() + "]";
	}

}
