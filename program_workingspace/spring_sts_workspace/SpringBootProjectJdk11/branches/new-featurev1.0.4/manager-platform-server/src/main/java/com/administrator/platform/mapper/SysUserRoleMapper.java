package com.administrator.platform.mapper;

import java.util.List;

import com.administrator.platform.model.SysUserRole;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface SysUserRoleMapper {
    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

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

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

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

    SysUserRole getById(Long id);

    /**
     * 
     * @see :
     * @param id
     *            :系统用户id
     * @return : void
     */
    List<SysUserRole> findAll();

}
