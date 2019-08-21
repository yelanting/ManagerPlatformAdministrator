/**
 * @author : 孙留平
 * @since : 2018年9月9日 下午3:00:49
 * @see:
 */
package com.administrator.platform.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

/**
 * @author : Administrator
 * @since : 2018年9月9日 下午3:00:49
 * @see :
 */
@Controller
@Api("错误页面相关API")
@RequestMapping("/error")
public class DefaultErrorController implements ErrorController {

    /**
     * 转向错误页面
     * 
     * @see org.springframework.boot.web.servlet.error.ErrorController#getErrorPath()
     * @since 2018年12月10日 20:14:14
     * 
     */
    @Override
    public String getErrorPath() {
        return "page/error";
    }

    @GetMapping
    public String error() {
        return getErrorPath();
    }
}
