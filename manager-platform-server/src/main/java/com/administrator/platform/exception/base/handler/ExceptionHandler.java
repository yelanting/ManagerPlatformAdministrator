/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:07:05
 * @see:
 */
package com.administrator.platform.exception.base.handler;

import com.administrator.platform.exception.domain.Result;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:07:05
 * @see :
 */
public interface ExceptionHandler {
    /**
     * 异常处理主体方法
     * 
     * @param errorCode
     *            异常码
     * @param bex
     *            需要处理的异常
     * @param result
     *            存储处理结果
     * @return 存储处理结果
     */
    Result handleException(String errorCode, Exception bex, Result result);
}
