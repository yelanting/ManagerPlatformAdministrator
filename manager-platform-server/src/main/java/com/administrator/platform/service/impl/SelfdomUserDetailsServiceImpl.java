/**
 * @author : 孙留平
 * @since : 2019年4月4日 下午2:14:14
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.administrator.platform.model.SecurityUser;
import com.administrator.platform.model.SysRole;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.SysRoleService;
import com.administrator.platform.service.SysUserService;

/**
 * @author : Administrator
 * @since : 2019年4月4日 下午2:14:14
 * @see :
 */
public class SelfdomUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 重写加载用户方法
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

		SysUser user = sysUserService.findSysUserByUserAccount(username);
		if (user == null) {
			throw new UsernameNotFoundException(
			        "Username " + username + " not found");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		List<SysRole> sysRoles = sysRoleService
		        .getSysRolesByUserId(user.getId());

		for (SysRole sysRole : sysRoles) {
			authorities.add(new SimpleGrantedAuthority(
			        sysRole.getRoleType().getRoleTypeEnName()));
		}

		return new SecurityUser(user);
	}
}
