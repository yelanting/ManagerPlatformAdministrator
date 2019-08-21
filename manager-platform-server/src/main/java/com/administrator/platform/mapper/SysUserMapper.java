package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.SysRole;
import com.administrator.platform.model.SysUser;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface SysUserMapper {
	/**
	 * 根据主键删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUser>
	 * @param userAccount
	 * @return
	 */
	SysUser findSysUserByUserAccount(String userAccount);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<SysUser>
	 * @param userAccount
	 * @return
	 */
	List<SysUser> findSysUsersByUserAccountLike(String userAccount);

	/**
	 * 根据ID批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	/**
	 * 根据ID查询
	 * 
	 * @see :
	 * @param :
	 * @return : SysUser
	 * @param id
	 * @return
	 */

	SysUser getById(Long id);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<SysUser>
	 */
	Page<SysUser> findAll(Pageable pageable);

	/**
	 * 根据系统用户批量删除
	 * 
	 * @see :
	 * @param id
	 *            :系统用户id
	 * @return : void
	 */
	List<SysUser> findAll();

	void deleteSysUsersBySysUserTypeId(Long id);

	List<SysRole> getUserRolesByUserId(Long id);
}
