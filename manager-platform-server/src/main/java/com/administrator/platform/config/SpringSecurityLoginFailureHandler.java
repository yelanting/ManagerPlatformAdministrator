/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月26日 上午9:37:48
 * @see:
 */
package com.administrator.platform.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * spring security的登陆失败处理类
 * 
 * @author : Administrator
 * @since : 2019年8月26日 上午9:53:57
 * @see :
 */
@Component
public class SpringSecurityLoginFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SpringSecurityLoginFailureHandler.class);

	public SpringSecurityLoginFailureHandler() {
		this.setDefaultFailureUrl("/login?error=true");
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	        HttpServletResponse response, AuthenticationException exception)
	        throws IOException, ServletException {
		/**
		 * 保存登陆日志
		 */
		LOGGER.error("登录系统失败，失败原因:{}", exception.getMessage());
		super.onAuthenticationFailure(request, response, exception);
	}
}
