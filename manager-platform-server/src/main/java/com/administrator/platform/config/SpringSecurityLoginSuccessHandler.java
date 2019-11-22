/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月26日 上午9:46:25
 * @see:
 */
package com.administrator.platform.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * spring security的登陆成功处理类
 * 
 * @author : Administrator
 * @since : 2019年8月26日 上午9:53:57
 * @see :
 */
@Component
public class SpringSecurityLoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SpringSecurityLoginSuccessHandler.class);

	public SpringSecurityLoginSuccessHandler() {
		this.setDefaultTargetUrl("/index");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	        HttpServletResponse response, Authentication authentication)
	        throws IOException, ServletException {
		LOGGER.info("登陆系统成功");
		/**
		 * 保存登陆信息
		 */
		super.onAuthenticationSuccess(request, response, authentication);

	}
}
