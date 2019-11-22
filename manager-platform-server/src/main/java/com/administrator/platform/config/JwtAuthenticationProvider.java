package com.administrator.platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.administrator.platform.util.security.PasswordEncoder;

/**
 * 身份验证提供者
 * 
 * @author 孙留平
 * @since 2019年8月26日 13:38:34
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(JwtAuthenticationProvider.class);

	public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
		setUserDetailsService(userDetailsService);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
	        UsernamePasswordAuthenticationToken authentication)
	        throws AuthenticationException {
		LOGGER.error("没进来");
		if (authentication.getCredentials() == null) {
			LOGGER.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage(
			        "AbstractUserDetailsAuthenticationProvider.badCredentials",
			        "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		LOGGER.error("presentedPassword：[{}]", presentedPassword);
		LOGGER.error("userDetails.getPassword():{}", userDetails.getPassword());
		// 覆写密码验证逻辑
		if (!new PasswordEncoder().matches(userDetails.getPassword(),
		        presentedPassword)) {
			LOGGER.debug(
			        "Authentication failed: password does not match stored value");
			throw new BadCredentialsException(messages.getMessage(
			        "AbstractUserDetailsAuthenticationProvider.badCredentials",
			        "Bad credentials"));
		}
	}

}