/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.SysUserRole;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface SysUserRoleService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUserRoleRole>
	 * @return
	 */
	List<SysUserRole> findAllSysUserRoleList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : SysUserRole
	 * @param sysUserRole
	 * @return
	 */
	SysUserRole addSysUserRole(SysUserRole sysUserRole);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : SysUserRole
	 * @param sysUserRole
	 * @return
	 */
	SysUserRole updateSysUserRole(SysUserRole sysUserRole);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : SysUserRole
	 * @param id
	 * @return
	 */
	SysUserRole getSysUserRoleById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : SysUserRole
	 * @param sysUserRole
	 * @return
	 */
	SysUserRole getSysUserRoleByObject(SysUserRole sysUserRole);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteSysUserRole(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteSysUserRole(Long[] ids);

	/**
	 * 删除用户对应的角色关系
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param id
	 */
	void deleteSysUserRoleBySysUserId(Long id);

	/**
	 * 查询一个对应关系是否存在
	 * 
	 * @see :
	 * @param :
	 * @return : SysUserRole
	 * @param userId
	 * @param roleId
	 * @return
	 */
	SysUserRole getSysUserRoleByUserIdAndRoleId(Long userId, Long roleId);
}
