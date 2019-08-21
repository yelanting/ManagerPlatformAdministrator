/**
 * @author : 孙留平
 * @since : 2019年4月3日 下午7:23:16
 * @see:
 */
package com.administrator.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.LoginService;
import com.administrator.platform.service.SysUserService;

/**
 * @author : Administrator
 * @since : 2019年4月3日 下午7:23:16
 * @see :
 */
@Service
public class LoginServiceImpl implements LoginService {
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

		System.out.println(
		        bCryptPasswordEncoder.encode(sysUser.getUserPassword()));
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

}
