package com.administrator.platform.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.administrator.platform.vo.ResponseData;

/**
 * @see 全局异常处理
 * @author Administrator
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(value = Exception.class)
    // @ResponseBody
    // public ResponseData defaultErrorHandler(HttpServletRequest request,
    // Exception exception) throws Exception {
    // logger.error("", exception);
    // final ResponseData responseData = new ResponseData();
    //
    // responseData.setMsg(exception.getMessage());
    // if (exception instanceof NoHandlerFoundException) {
    // responseData.setCode(404);
    // } else if (exception instanceof ServerErrorException) {
    // responseData.setCode(500);
    // } else {
    // responseData.setData(null);
    // responseData.setSuccess(false);
    // }
    //
    // return responseData;
    // }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(Exception exception) {
        logger.error("", exception);
        final ResponseData responseData = new ResponseData();

        responseData.setMsg(exception.getMessage());
        if (exception instanceof NoHandlerFoundException) {
            responseData.setCode(404);
        } else if (exception instanceof ServerErrorException) {
            responseData.setCode(500);
        } else {
            responseData.setData(null);
            responseData.setSuccess(false);
        }

        return responseData;
    }
}
