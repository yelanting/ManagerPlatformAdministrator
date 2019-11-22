package com.administrator.platform.util.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.administrator.platform.config.JwtAuthenticatioToken;

/**
 * Security相关操作
 * 
 * @author 孙留平
 * @date 2019年8月26日 13:42:27
 */
public class SecurityUtils {
	private SecurityUtils() {

	}

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SecurityUtils.class);

	/**
	 * 系统登录认证
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @param authenticationManager
	 * @return
	 */
	public static JwtAuthenticatioToken login(HttpServletRequest request,
	        String username, String password,
	        AuthenticationManager authenticationManager) {
		LOGGER.info("SecurityUtils.login正在登陆");

		JwtAuthenticatioToken token = new JwtAuthenticatioToken(username,
		        password);
		token.setDetails(
		        new WebAuthenticationDetailsSource().buildDetails(request));
		// 执行登录认证过程
		Authentication authentication = authenticationManager
		        .authenticate(token);
		// 认证成功存储认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 生成令牌并返回给客户端
		token.setToken(JwtTokenUtils.generateToken(authentication));
		return token;
	}

	/**
	 * 获取令牌进行认证
	 * 
	 * @param request
	 */
	public static void checkAuthentication(HttpServletRequest request) {
		// 获取令牌并根据令牌获取登录认证信息
		Authentication authentication = JwtTokenUtils
		        .getAuthenticationeFromToken(request);
		// 设置登录认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 获取当前用户名
	 * 
	 * @return
	 */
	public static String getUsername() {
		String username = null;
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal != null && principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			}
		}
		return username;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public static String getUsername(Authentication authentication) {
		String username = null;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal != null && principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			}
		}
		return username;
	}

	/**
	 * 获取当前登录信息
	 * 
	 * @return
	 */
	public static Authentication getAuthentication() {
		if (SecurityContextHolder.getContext() == null) {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext()
		        .getAuthentication();
		return authentication;
	}

}
