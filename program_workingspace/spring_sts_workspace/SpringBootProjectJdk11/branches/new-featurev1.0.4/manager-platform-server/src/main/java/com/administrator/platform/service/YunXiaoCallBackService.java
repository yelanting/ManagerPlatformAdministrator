
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:YunXiaoCallBackService.java
 * @author : 孙留平
 * @since : 2019年5月20日 下午4:16:54
 * @see:
 */

package com.administrator.platform.service;

import com.administrator.platform.model.yunxiao.CallBackYunXiaoParamDTO;
import com.administrator.platform.vo.ResponseData;

/**
 * @author : Administrator
 * @since : 2019年5月20日 下午4:16:54
 * @see :
 */
public interface YunXiaoCallBackService {

	/**
	 * 回调接口，执行任务结果回调
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param callBackYunXiaoParamDTO
	 * @return
	 */
	ResponseData autoTestResultCallBack(
	        CallBackYunXiaoParamDTO callBackYunXiaoParamDTO);
}
