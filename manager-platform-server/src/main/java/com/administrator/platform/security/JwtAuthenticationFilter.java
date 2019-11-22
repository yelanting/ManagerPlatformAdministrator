package com.administrator.platform.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.administrator.platform.util.security.SecurityUtils;

/**
 * 登录认证过滤器
 * 
 * @author 孙留平
 * @date 2019年8月26日 13:47:04
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(JwtAuthenticationFilter.class);

	@Autowired
	public JwtAuthenticationFilter(
	        AuthenticationManager authenticationManager) {
		super(authenticationManager);
		LOGGER.info(
		        "JwtAuthenticationFilter.JwtAuthenticationFilter( AuthenticationManager authenticationManager");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	        HttpServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		LOGGER.info(
		        "JwtAuthenticationFilter.doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)");
		// 获取token, 并检查登录状态
		SecurityUtils.checkAuthentication(request);
		chain.doFilter(request, response);
	}

}