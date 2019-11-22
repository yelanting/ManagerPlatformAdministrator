/**
 * @author : 孙留平
 * @since : 2019年4月3日 下午7:23:16
 * @see:
 */
package com.administrator.platform.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.administrator.platform.config.JwtAuthenticatioToken;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.LoginBean;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.LoginService;
import com.administrator.platform.service.SysUserService;
import com.administrator.platform.util.security.PasswordUtils;
import com.administrator.platform.util.security.SecurityUtils;
import com.administrator.platform.vo.RegisterBean;
import com.google.code.kaptcha.Constants;

/**
 * @author : Administrator
 * @since : 2019年4月3日 下午7:23:16
 * @see :
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LoginServiceImpl.class);

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * @see
	 *      com.administrator.platform.service.LoginService#login(com.administrator.
	 *      platform.model.SysUser)
	 */
	@Override
	public SysUser login(SysUser sysUser) {
		UserDetails currentSysUserDetail = sysUserService
		        .loadUserByUsername(sysUser.getUserAccount());

		SysUser currentSysUser = (SysUser) currentSysUserDetail;
		if (null == currentSysUser) {
			throw new BusinessValidationException("用户名或密码不正确");
		}

		if (currentSysUser.getUserPassword().equals(sysUser.getUserPassword())
		        || currentSysUser.getUserPassword().equals(bCryptPasswordEncoder
		                .encode(sysUser.getUserPassword()))) {

			return currentSysUser;
		}

		throw new BusinessValidationException("用户名或者密码不正确");
	}

	/**
	 * @see
	 *      com.administrator.platform.service.LoginService#logout(com.administrator.
	 *      platform.model.SysUser)
	 */
	@Override
	public void logout(SysUser sysUser) {

	}

	@Override
	public JwtAuthenticatioToken login(LoginBean loginBean,
	        HttpServletRequest httpServletRequest,
	        AuthenticationManager authenticationManager) {

		String captcha = loginBean.getCaptcha();
		// 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
		Object kaptcha = httpServletRequest.getSession()
		        .getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (kaptcha == null) {
			throw new BusinessValidationException("验证码已失效");
		}
		if (!captcha.equals(kaptcha)) {
			throw new BusinessValidationException("验证码不正确");
		}
		// 用户信息
		SysUser user = sysUserService
		        .findSysUserByUserAccount(loginBean.getUsername());
		// 账号不存在、密码错误
		if (user == null) {
			throw new BusinessValidationException("帐号不存在或者密码错误");
		}

		if (!PasswordUtils.matches(loginBean.getPassword(),
		        user.getPassword())) {
			throw new BusinessValidationException("帐号不存在或者密码错误");
		}

		// 账号锁定
		if (!user.isEnabled()) {
			throw new BusinessValidationException("帐号已被锁定,请联系管理员");
		}

		return SecurityUtils.login(httpServletRequest, loginBean.getUsername(),
		        loginBean.getPassword(), authenticationManager);
	}

	@Override
	public SysUser logout(String username) {
		LOGGER.info("用户:{}退出", username);
		SysUser sysUser = sysUserService.findSysUserByUserAccount(username);
		return sysUser;
	}

	@Override
	public SysUser register(RegisterBean registerBean) {
		LOGGER.info("开始注册用户：{}", registerBean);
		SysUser sysUser = new SysUser();
		sysUser.setUserAccount(registerBean.getUsername());

		if (!registerBean.getPassword()
		        .equals(registerBean.getConfirmPassword())) {
			throw new BusinessValidationException("两次输入的密码不相同");
		}

		sysUser.setUserPassword(registerBean.getPassword());
		sysUser.setUserNickname(registerBean.getUserNickname());
		sysUser.setSex(registerBean.getSex());
		return sysUserService.addSysUser(sysUser);
	}
}
