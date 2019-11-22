/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月16日 上午9:07:39
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.DefaultSysRoleAndSysUserConst;
import com.administrator.platform.enums.RoleType;
import com.administrator.platform.model.SysRole;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.SysRoleService;
import com.administrator.platform.service.SysUserRoleService;
import com.administrator.platform.service.SysUserService;

@Service("systemUserAndRoleDataInit")
public class SystemUserAndRoleDataInit {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 创建默认角色
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void initRole() {

		SysRole sysRoleDba = new SysRole();
		sysRoleDba.setRoleType(RoleType.ROLE_DBA);
		sysRoleDba.setRoleCnName(DefaultSysRoleAndSysUserConst.ROLE_DBA_CN);
		sysRoleDba.setComments("系统运维角色");
		sysRoleDba.setRoleName(DefaultSysRoleAndSysUserConst.ROLE_DBA_CN);
		sysRoleDba.setDeleted(false);
		checkAndInsertSysRole(sysRoleDba);

		SysRole sysRole = new SysRole();
		sysRole.setRoleType(RoleType.ROLE_ADMIN);
		sysRole.setRoleCnName(DefaultSysRoleAndSysUserConst.ROLE_ADMIN_CN);
		sysRole.setComments("系统管理员角色");
		sysRole.setRoleName(DefaultSysRoleAndSysUserConst.ROLE_ADMIN_CN);
		sysRole.setDeleted(false);
		checkAndInsertSysRole(sysRole);

		SysRole sysRoleUser = new SysRole();
		sysRoleUser.setRoleType(RoleType.ROLE_NORMAL);
		sysRoleUser.setRoleCnName(DefaultSysRoleAndSysUserConst.ROLE_USER_CN);
		sysRoleUser.setComments("普通用户角色");
		sysRoleUser.setRoleName(DefaultSysRoleAndSysUserConst.ROLE_USER_CN);
		sysRoleUser.setDeleted(false);
		checkAndInsertSysRole(sysRoleUser);
	}

	/**
	 * 检查角色是否存在，不存在，则创建
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param styRole
	 */
	private void checkAndInsertSysRole(SysRole sysRole) {
		SysRole currentRoleOfThisName = sysRoleService
		        .findSysRoleByRoleName(sysRole.getRoleName());

		if (null == currentRoleOfThisName) {
			sysRoleService.addSysRole(sysRole);
		} else {
			sysRole.setId(currentRoleOfThisName.getId());
			sysRoleService.updateSysRole(sysRole);
		}
	}

	/**
	 * 创建默认用户
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void initUser() {

		List<SysRole> sysAdminRole = new ArrayList<>();
		sysAdminRole.add(sysRoleService.findSysRoleByRoleName(
		        DefaultSysRoleAndSysUserConst.ROLE_ADMIN_CN));

		List<SysRole> sysCommonRole = new ArrayList<>();
		sysCommonRole.add(sysRoleService.findSysRoleByRoleName(
		        DefaultSysRoleAndSysUserConst.ROLE_USER_CN));

		List<SysRole> sysDbaRole = new ArrayList<>();
		sysDbaRole.add(sysRoleService.findSysRoleByRoleName(
		        DefaultSysRoleAndSysUserConst.ROLE_DBA_CN));

		/**
		 * 创建默认admin用户，给dba权限
		 */
		SysUser sysUserAdmin = new SysUser();
		sysUserAdmin.setAdmin(true);
		sysUserAdmin.setMobile(DefaultSysRoleAndSysUserConst.DEFAULT_MOBILE);
		sysUserAdmin.setSex("男");
		sysUserAdmin.setUserAccount(
		        DefaultSysRoleAndSysUserConst.SYSUSER_ADMIN_USER);
		sysUserAdmin.setUserNickname(
		        DefaultSysRoleAndSysUserConst.SYSUSER_ADMIN_USER);
		sysUserAdmin
		        .setUserPassword(DefaultSysRoleAndSysUserConst.USER_PASSWORD);
		sysUserAdmin.setSysRoles(sysDbaRole);

		checkAndInsertSysUser(sysUserAdmin);

		/**
		 * 创建默认sunliuping用户。给admin权限
		 */
		SysUser sysUsersSunliuping = new SysUser();
		sysUsersSunliuping.setAdmin(true);
		sysUsersSunliuping
		        .setMobile(DefaultSysRoleAndSysUserConst.DEFAULT_MOBILE);
		sysUsersSunliuping.setSex("男");
		sysUsersSunliuping.setUserAccount(
		        DefaultSysRoleAndSysUserConst.SYSUSER_SUNLIUPING_USER);
		sysUsersSunliuping.setUserNickname(
		        DefaultSysRoleAndSysUserConst.SYSUSER_SUNLIUPING_USER);
		sysUsersSunliuping
		        .setUserPassword(DefaultSysRoleAndSysUserConst.USER_PASSWORD);
		sysUsersSunliuping.setSysRoles(sysAdminRole);

		checkAndInsertSysUser(sysUsersSunliuping);

		/**
		 * 创建默认访客用户，给普通权限
		 */
		SysUser sysUserGuest = new SysUser();
		sysUserGuest.setAdmin(false);
		sysUserGuest.setMobile(DefaultSysRoleAndSysUserConst.DEFAULT_MOBILE);
		sysUserGuest.setSex("男");
		sysUserGuest.setUserAccount(
		        DefaultSysRoleAndSysUserConst.SYSUSER_NORMAL_USER);
		sysUserGuest.setUserNickname(
		        DefaultSysRoleAndSysUserConst.SYSUSER_NORMAL_USER);
		sysUserGuest
		        .setUserPassword(DefaultSysRoleAndSysUserConst.USER_PASSWORD);
		sysUserGuest.setSysRoles(sysCommonRole);
		checkAndInsertSysUser(sysUserGuest);
	}

	/**
	 * 检查角色是否存在，不存在，则创建
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param styRole
	 */
	private void checkAndInsertSysUser(SysUser sysUser) {
		SysUser currentUserOfThisName = sysUserService
		        .findSysUserByUserAccount(sysUser.getUserAccount());

		if (null == currentUserOfThisName) {
			sysUserService.addSysUser(sysUser);
		} else {
			sysUser.setId(currentUserOfThisName.getId());
			sysUserService.updateSysUser(sysUser);
		}
	}

	@PostConstruct
	private void initRoleAndUser() {
		initRole();

		initUser();

	}
}
