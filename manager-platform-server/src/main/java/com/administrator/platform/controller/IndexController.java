/**
 * @author : 孙留平
 * @since : 2018年9月9日 下午3:00:49
 * @see:
 */
package com.administrator.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author : Administrator
 * @since : 2018年9月9日 下午3:00:49
 * @see :
 */
@Controller
@Api("首页相关API")
public class IndexController {
	@GetMapping(value = { "/", "/home", "/index", "/index.html" })
	@ApiOperation("重定向到首页页面")
	public String index() {
		return "index";
	}
}
