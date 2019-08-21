
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:YunXiaoBusinessionValidationException.java
 * @author : 孙留平
 * @since : 2019年5月30日 下午2:40:37
 * @see: 抛给云效调用的异常
 */

package com.administrator.platform.exception;

import org.springframework.stereotype.Component;

import com.administrator.platform.exception.annotation.Exceptional;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.exception.base.handler.BusinessValidationExceptionHandler;
import com.administrator.platform.exception.constant.ExceptionCode;

@Component
@Exceptional(isLogging = true, errorCode = ExceptionCode.YUNXIAO_BUSINESS_VALIDATION, handler = BusinessValidationExceptionHandler.class)
public class YunXiaoBusinessValidationException
        extends BusinessValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6388242770218477999L;

	public YunXiaoBusinessValidationException() {
		super();
	}

	public YunXiaoBusinessValidationException(String message) {
		super(message);
	}
}
