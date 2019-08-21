/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:12:12
 * @see:
 */
package com.administrator.platform.exception.domain;

import com.administrator.platform.exception.base.handler.ExceptionHandler;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:12:12
 * @see :
 *      默认异常处理类，如果自定义的异常类继承自BaseAppException或者BaseAppRuntimeException并且异常处理类没有配置，
 *      则使用该类 。e.g. @Exceptional(errorCode = "SE100-01") public class SystemException
 *      extends BaseAppRuntimeException
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public Result handleException(String errorCode, Exception bex, Result result) {
        result.setMessage("异常代码:[" + errorCode + "] " + bex.getMessage());
        result.setErrorCode(errorCode);
        return result;
    }

}
