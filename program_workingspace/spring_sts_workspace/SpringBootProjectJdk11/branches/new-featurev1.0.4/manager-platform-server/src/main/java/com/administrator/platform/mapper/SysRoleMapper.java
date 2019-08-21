package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.SysRole;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface SysRoleMapper {
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

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param roleName
     * @return
     */
    SysRole findSysRoleByRoleName(String roleName);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<SysRole>
     * @param roleName
     * @return
     */
    List<SysRole> findSysRolesByRoleNameLike(String roleName);

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
     * @return : SysRole
     * @param id
     * @return
     */

    SysRole getById(Long id);

    /**
     * 分页查询
     * 
     * @see :
     * @param pageable:分页信息
     * @return : Page<SysRole>
     */
    Page<SysRole> findAll(Pageable pageable);

    /**
     * 根据系统用户批量删除
     * 
     * @see :
     * @param id
     *            :系统用户id
     * @return : void
     */
    List<SysRole> findAll();

    List<SysRole> getSysRolesByUserId(Long userId);
}
