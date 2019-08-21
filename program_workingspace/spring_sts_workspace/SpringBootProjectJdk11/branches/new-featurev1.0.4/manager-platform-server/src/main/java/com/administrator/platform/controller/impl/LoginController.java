/**
 * @author : 孙留平
 * @since : 2018年10月10日 下午3:03:21
 * @see:
 */
package com.administrator.platform.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.LoginService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;

/**
 * @author : Administrator
 * @since : 2018年10月10日 下午3:03:21
 * @see :
 */
@Controller
@Api("Login相关接口文档")
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = LoggerFactory
	        .getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@GetMapping(value = { "" })
	public String login() {
		return "page/login/login";
	}

	@PostMapping("/loginAction")
	@ResponseBody
	public ResponseData loginAction(@ModelAttribute SysUser sysUser) {
		return ResponseData.getSuccessResult(loginService.login(sysUser));
	}

	public SysUser getUser() { // 为了session从获取用户信息,可以配置如下
		SysUser user = new SysUser();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {
			user = (SysUser) auth.getPrincipal();
		}
		return user;
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes()).getRequest();
	}
}
