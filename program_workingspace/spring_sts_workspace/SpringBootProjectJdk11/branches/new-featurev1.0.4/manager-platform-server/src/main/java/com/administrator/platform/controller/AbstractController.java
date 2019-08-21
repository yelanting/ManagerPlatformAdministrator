package com.administrator.platform.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller公共组件
 * 
 * @author : Administrator
 * @since : 2019年3月8日 上午9:55:17
 * @see :
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    protected String getContextPath() {
        return request.getContextPath();
    }

}
