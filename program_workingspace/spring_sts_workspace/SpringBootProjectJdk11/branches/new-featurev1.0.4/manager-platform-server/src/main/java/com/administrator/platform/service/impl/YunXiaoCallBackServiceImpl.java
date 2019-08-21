
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:YunXiaoCallBackServiceImpl.java
 * @author : 孙留平
 * @since : 2019年5月20日 下午4:17:07
 * @see:
 */

package com.administrator.platform.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.administrator.platform.constdefine.YunXiaoDefine;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.GlobalParam;
import com.administrator.platform.model.yunxiao.CallBackYunXiaoParamDTO;
import com.administrator.platform.service.GlobalParamService;
import com.administrator.platform.service.YunXiaoCallBackService;
import com.administrator.platform.util.ObjectOperationUtils;
import com.administrator.platform.util.http.RestHttpClientUtil;
import com.administrator.platform.vo.ResponseData;

/**
 * @author : Administrator
 * @since : 2019年5月20日 下午4:17:07
 * @see : 云效接口回调
 */

@Service
public class YunXiaoCallBackServiceImpl implements YunXiaoCallBackService {

	private static final Logger logger = LoggerFactory
	        .getLogger(YunXiaoCallBackServiceImpl.class);

	@Autowired
	private GlobalParamService globalParamService;

	/**
	 * 自动化测试结果回调云效
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.YunXiaoCallBackService#
	 *      autoTestResultCallBack(com.tianque.yunxiao.connect.server.model.yunxiao.
	 *      CallBackYunXiaoParamDTO)
	 */
	@Override
	public ResponseData autoTestResultCallBack(
	        CallBackYunXiaoParamDTO callBackYunXiaoParamDTO) {

		logger.info("开始进行自动化任务执行结果回调,回调参数:{}", callBackYunXiaoParamDTO);

		String callBackUrl = null;

		if (StringUtil
		        .isStringAvaliable(callBackYunXiaoParamDTO.getCallBackUrl())) {
			callBackUrl = callBackYunXiaoParamDTO.getCallBackUrl();
		} else {
			GlobalParam yunXiaoAutoTestResultCallBackParam = globalParamService
			        .findGlobalParamByParamKey(
			                YunXiaoDefine.KEY_OF_AUTOTEST_RESULT_CALLBACK);

			if (null == yunXiaoAutoTestResultCallBackParam) {
				throw new BusinessValidationException("请在全局参数中配置或云效的自动化任务回调接口");
			}

			if (YunXiaoDefine.DEFAULT_INSERTED_VALUE.equals(
			        yunXiaoAutoTestResultCallBackParam.getParamValue())) {
				throw new BusinessValidationException(
				        "全局参数中配置的云效的自动化任务回调接口URL不能为默认值");
			}

			callBackUrl = yunXiaoAutoTestResultCallBackParam.getParamValue();
		}

		if (StringUtil.isEmpty(callBackUrl)) {
			throw new BusinessValidationException("云效回调URL尚未提供，无法回调");
		}

		MultiValueMap<String, Object> multiValueMap = fromCallBackParamDTO(
		        callBackYunXiaoParamDTO);

		ResponseData responseData = new RestHttpClientUtil().post(callBackUrl,
		        multiValueMap);

		logger.info("回调结果:{}", responseData);
		return responseData;
	}

	/**
	 * 把回调对象，转换成map
	 * 
	 * @see :
	 * @param :
	 * @return : MultiValueMap<String,Object>
	 * @param callBackYunXiaoParamDTO
	 * @return
	 */
	private static MultiValueMap<String, Object> fromCallBackParamDTO(
	        CallBackYunXiaoParamDTO callBackYunXiaoParamDTO) {
		return ObjectOperationUtils.fromObject(callBackYunXiaoParamDTO);
	}
}
