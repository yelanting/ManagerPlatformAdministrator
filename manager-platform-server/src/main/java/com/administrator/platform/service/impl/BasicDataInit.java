/**
 * @author : 孙留平
 * @since : 2019年4月4日 下午2:55:42
 * @see:
 */
package com.administrator.platform.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.administrator.platform.definition.form.BasicDataInitDefine;
import com.administrator.platform.enums.RoleType;
import com.administrator.platform.model.SysRole;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.SysRoleService;
import com.administrator.platform.service.SysUserRoleService;
import com.administrator.platform.service.SysUserService;

/**
 * @author : Administrator
 * @since : 2019年4月4日 下午2:55:42
 * @see :
 */

@Service
public class BasicDataInit {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private void initBasicRoles() {
		SysRole sysRoleAdmin = new SysRole();
		sysRoleAdmin.setComments("系统管理员");
		sysRoleAdmin.setDeleted(false);
		sysRoleAdmin.setRoleType(RoleType.ROLE_ADMIN);
		sysRoleAdmin.setRoleCnName("系统管理员角色");
		sysRoleAdmin.setRoleName("系统管理员角色");

		SysRole currentSysRoleAdmin = sysRoleService
		        .findSysRoleByRoleName(sysRoleAdmin.getRoleName());

		if (null == currentSysRoleAdmin) {
			sysRoleService.addSysRole(sysRoleAdmin);
		} else {
			sysRoleAdmin.setId(currentSysRoleAdmin.getId());
			sysRoleService.updateSysRole(sysRoleAdmin);
		}

		SysRole sysRoleNormal = new SysRole();
		sysRoleNormal.setComments("普通角色");
		sysRoleNormal.setDeleted(false);
		sysRoleNormal.setRoleType(RoleType.ROLE_NORMAL);
		sysRoleNormal.setRoleCnName("普通角色");
		sysRoleNormal.setRoleName("普通角色");

		SysRole currentSysRoleNormal = sysRoleService
		        .findSysRoleByRoleName(sysRoleNormal.getRoleName());

		if (null == currentSysRoleNormal) {
			sysRoleService.addSysRole(sysRoleNormal);
		} else {
			sysRoleNormal.setId(currentSysRoleNormal.getId());
			sysRoleService.updateSysRole(sysRoleNormal);
		}
	}

	private void initBasicUsers() {
		SysUser sysUserAdmin = new SysUser();
		sysUserAdmin.setAdmin(true);
		sysUserAdmin.setDeleted(false);
		sysUserAdmin.setLocked(false);
		sysUserAdmin.setMobilePhone("13800001111");
		sysUserAdmin.setSex("男");
		sysUserAdmin.setUserAccount(BasicDataInitDefine.DEFAULT_SYSUSER_ADMIN);
		sysUserAdmin.setUserNickname(BasicDataInitDefine.DEFAULT_SYSUSER_ADMIN);
		sysUserAdmin.setUserStatus(true);
		sysUserAdmin.setUserPassword(bCryptPasswordEncoder
		        .encode(BasicDataInitDefine.DEFAULT_LOGIN_PASSWORD));

		SysUser currentSysUserAdmin = sysUserService
		        .findSysUserByUserAccount(sysUserAdmin.getUserAccount());
		// 如果当前没有这个用户
		if (null == currentSysUserAdmin) {
			sysUserService.addSysUser(sysUserAdmin);
		} else {
			// 如果有这个用户
			sysUserAdmin.setId(currentSysUserAdmin.getId());
			sysUserService.updateSysUser(sysUserAdmin);
		}

		SysUser sysUserCommonUser = new SysUser();
		sysUserCommonUser.setAdmin(false);
		sysUserCommonUser.setDeleted(false);
		sysUserCommonUser.setLocked(false);
		sysUserCommonUser.setMobilePhone("13800001111");
		sysUserCommonUser.setSex("男");
		sysUserCommonUser
		        .setUserAccount(BasicDataInitDefine.DEFAULT_SYSUSER_USER);
		sysUserCommonUser
		        .setUserNickname(BasicDataInitDefine.DEFAULT_SYSUSER_USER);
		sysUserCommonUser.setUserStatus(true);
		sysUserCommonUser.setUserPassword(bCryptPasswordEncoder
		        .encode(BasicDataInitDefine.DEFAULT_LOGIN_PASSWORD));

		SysUser currentSysUserCommonUser = sysUserService
		        .findSysUserByUserAccount(sysUserCommonUser.getUserAccount());
		// 如果当前没有这个用户
		if (null == currentSysUserCommonUser) {
			sysUserService.addSysUser(sysUserCommonUser);
		} else {
			// 如果有这个用户
			sysUserCommonUser.setId(currentSysUserCommonUser.getId());
			sysUserService.updateSysUser(sysUserCommonUser);
		}
	}

	@PostConstruct
	public void initBasicRolesAndUsers() {
		initBasicRoles();
		initBasicUsers();
	}
}
