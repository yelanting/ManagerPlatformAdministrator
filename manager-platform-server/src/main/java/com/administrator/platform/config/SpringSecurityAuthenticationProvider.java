/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月26日 上午10:17:12
 * @see:
 */
package com.administrator.platform.config;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.administrator.platform.core.base.util.Md5Util;
import com.administrator.platform.service.SysUserService;

@Component
public class SpringSecurityAuthenticationProvider
        implements AuthenticationProvider {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SpringSecurityAuthenticationProvider.class);

	@Autowired
	private SysUserService sysUserService;

	@Override
	public Authentication authenticate(Authentication authentication)
	        throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		LOGGER.info("登陆信息：username{},password:{}", username, password);

		UserDetails userDetails = sysUserService.loadUserByUsername(username);
		if (!userDetails.getPassword().equals(password) && !userDetails
		        .getPassword().equals(Md5Util.createMd5Str(password))) {
			throw new DisabledException("User name or password is invalid！");
		}

		Collection<? extends GrantedAuthority> authorities = userDetails
		        .getAuthorities();
		return new UsernamePasswordAuthenticationToken(userDetails, password,
		        authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
