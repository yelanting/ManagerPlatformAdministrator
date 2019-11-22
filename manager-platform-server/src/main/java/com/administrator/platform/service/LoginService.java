/**
 * @author : 孙留平
 * @since : 2019年4月3日 下午7:22:07
 * @see:
 */
package com.administrator.platform.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;

import com.administrator.platform.config.JwtAuthenticatioToken;
import com.administrator.platform.model.LoginBean;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.vo.RegisterBean;

/**
 * @author : Administrator
 * @since : 2019年4月3日 下午7:22:07
 * @see :
 */
public interface LoginService {
	SysUser login(SysUser sysUser);

	void logout(SysUser sysUser);

	JwtAuthenticatioToken login(LoginBean loginBean,
	        HttpServletRequest httpServletRequest,
	        AuthenticationManager authenticationManager);

	SysUser logout(String username);

	SysUser register(RegisterBean registerBean);
}
