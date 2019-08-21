/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:01:54
 * @see:
 */
package com.administrator.platform.exception.base;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:01:54
 * @see :
 *      业务规则校验失败异常，该类异常是由于用户误操作导致的，例如“身份证不允许重复”，只需给用户合理的提示，可以由用户自行修正.<br/>
 *      该类异常日志系统不会做任何记录<br/>
 *      该异常的异常码为BE100-01，异常处理类为BusinessValidationExceptionHandler，日志系统将不会记录该异常信息
 */

import org.springframework.stereotype.Component;

import com.administrator.platform.exception.annotation.Exceptional;
import com.administrator.platform.exception.base.handler.BusinessValidationExceptionHandler;
import com.administrator.platform.exception.constant.ExceptionCode;

@Component
@Exceptional(isLogging = false, errorCode = ExceptionCode.BUSINESS_VALIDATION, handler = BusinessValidationExceptionHandler.class)
public class BusinessValidationException extends BaseAppRuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4687039922500531976L;

    public BusinessValidationException() {
        super();
    }

    public BusinessValidationException(String message) {
        super(message);
    }
}
