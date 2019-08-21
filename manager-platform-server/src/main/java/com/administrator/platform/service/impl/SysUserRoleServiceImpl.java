/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.SysUserRoleMapper;
import com.administrator.platform.model.SysUserRole;
import com.administrator.platform.service.SysUserRoleService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserRoleServiceImpl.class);
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * @see com.administrator.platform.service.SysUserRoleService#findAllSysUserRoleList()
     */
    @Override
    public List<SysUserRole> findAllSysUserRoleList() {
        return sysUserRoleMapper.findAll();
    }

    /**
     * 
     * 添加测试环境地址信息实现方法
     * 
     * @see com.administrator.platform.service.SysUserRoleService#addSysUserRole(com.administrator.platform.model.SysUserRole)
     */
    @Override
    public SysUserRole addSysUserRole(SysUserRole sysUserRole) {
        ValidationUtil.validateNull(sysUserRole, null);

        try {
            sysUserRoleMapper.insert(sysUserRole);
            return sysUserRole;
        } catch (Exception e) {
            logger.error("添加用户角色关联表失败:{},{}", sysUserRole, e.getMessage());
            throw new BusinessValidationException("添加用户角色关联表失败！！！");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.SysUserRoleService#deleteSysUserRole(java.lang.Long)
     */
    @Override
    public int deleteSysUserRole(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            sysUserRoleMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            logger.error("根据用户角色关联表id删除用户角色关联表失败:{},{}", id, e.getMessage());
            throw new BusinessValidationException(
                    "根据用户角色关联表id删除用户角色关联表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.SysUserRoleService#deleteSysUserRole(java.lang.Long[])
     */
    @Override
    public int deleteSysUserRole(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            sysUserRoleMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException(
                    "根据ids批量删除用户角色关联表失败" + e.getMessage());
        }
    }

    /**
     * 修改测试环境地址
     * 
     * @param sysUserRole:地址对象
     * @see com.administrator.platform.service.SysUserRoleService#updateSysUserRole(com.administrator.platform.model.SysUserRole)
     */
    @Override
    public SysUserRole updateSysUserRole(SysUserRole sysUserRole) {
        ValidationUtil.validateNull(sysUserRole, null);

        SysUserRole currentSysUserRole = getSysUserRoleByObject(sysUserRole);

        if (null == currentSysUserRole) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        sysUserRole.setId(currentSysUserRole.getId());

        try {
            sysUserRoleMapper.updateByPrimaryKey(sysUserRole);
            return sysUserRole;
        } catch (Exception e) {
            logger.error("更新用户角色关联表失败：{},{}", sysUserRole, e.getMessage());
            throw new BusinessValidationException("更新用户角色关联表信息失败!!!");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.SysUserRoleService#getSysUserRoleById(java.lang.Long)
     */
    @Override
    public SysUserRole getSysUserRoleById(Long id) {
        ValidationUtil.validateNull(id, null);
        return sysUserRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.SysUserRoleService#getSysUserRoleByObject(com.administrator.platform.model.SysUserRole)
     */
    @Override
    public SysUserRole getSysUserRoleByObject(SysUserRole sysUserRole) {
        ValidationUtil.validateNull(sysUserRole, null);
        return getSysUserRoleById(sysUserRole.getId());
    }
}
