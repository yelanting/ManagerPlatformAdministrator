/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.administrator.platform.model.SysUser;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface SysUserService extends UserDetailsService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUser>
	 * @return
	 */
	List<SysUser> findAllSysUserList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : SysUser
	 * @param sysUser
	 * @return
	 */
	SysUser addSysUser(SysUser sysUser);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : SysUser
	 * @param sysUser
	 * @return
	 */
	SysUser updateSysUser(SysUser sysUser);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : SysUser
	 * @param id
	 * @return
	 */
	SysUser getSysUserById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : SysUser
	 * @param sysUser
	 * @return
	 */
	SysUser getSysUserByObject(SysUser sysUser);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteSysUser(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteSysUser(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUser>
	 * @param searchContent
	 * @return
	 */
	SysUser findSysUserByUserAccount(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUser>
	 * @param searchContent
	 * @return
	 */
	List<SysUser> findSysUsersByUserAccountLike(String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<SysUser>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<SysUser> findSysUserByPage(Integer page, Integer size);
}
