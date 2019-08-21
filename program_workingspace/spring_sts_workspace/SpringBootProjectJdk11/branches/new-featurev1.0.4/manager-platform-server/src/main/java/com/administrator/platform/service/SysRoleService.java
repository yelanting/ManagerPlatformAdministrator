/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.SysRole;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface SysRoleService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<SysRole>
     * @return
     */
    List<SysRole> findAllSysRoleList();

    /**
     * 添加
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param sysRole
     * @return
     */
    SysRole addSysRole(SysRole sysRole);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param sysRole
     * @return
     */
    SysRole updateSysRole(SysRole sysRole);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param id
     * @return
     */
    SysRole getSysRoleById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param sysRole
     * @return
     */
    SysRole getSysRoleByObject(SysRole sysRole);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteSysRole(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteSysRole(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : SysRole
     * @param searchContent
     * @return
     */
    SysRole findSysRoleByRoleName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<SysRole>
     * @param searchContent
     * @return
     */
    List<SysRole> findSysRolesByRoleNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<SysRole>
     * @param page
     * @param size
     * @return
     */
    Page<SysRole> findSysRoleByPage(Integer page, Integer size);

    /**
     * 根据用户id获取角色
     * 
     * @see :
     * @param :
     * @return : List<SysRole>
     * @param id
     * @return
     */
    List<SysRole> getSysRolesByUserId(Long id);
}
