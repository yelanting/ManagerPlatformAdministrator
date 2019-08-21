/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:02:41
 * @see:
 */
package com.administrator.platform.exception.annotation;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:02:41
 * @see :异常类注解，用于标示该异常的异常码和异常处理类，在系统启动时通过spring自动将异常码、异常、异常处理类关联起来
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.administrator.platform.exception.base.handler.ExceptionHandler;
import com.administrator.platform.exception.domain.DefaultExceptionHandler;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Exceptional {
    /**
     * 是否日志记录，默认为true，记录日志
     * 
     */
    boolean isLogging() default true;

    /**
     * 异常码
     */
    String errorCode();

    /**
     * 异常级别
     */
    String expLevel() default "error";

    /**
     * 异常处理器,默认处理器为DefaultExceptionHandler
     */
    Class<? extends ExceptionHandler> handler() default DefaultExceptionHandler.class;
}
