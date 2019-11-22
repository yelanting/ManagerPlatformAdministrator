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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.SysRoleFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.SysRoleMapper;
import com.administrator.platform.model.SysRole;
import com.administrator.platform.service.SysRoleService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	private static final Logger logger = LoggerFactory
	        .getLogger(SysRoleServiceImpl.class);
	@Autowired
	private SysRoleMapper sysRoleMapper;

	/**
	 * @see com.administrator.platform.service.SysRoleService#findAllSysRoleList()
	 */
	@Override
	public List<SysRole> findAllSysRoleList() {
		logger.debug("查询系统角色列表");
		return sysRoleMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see com.administrator.platform.service.SysRoleService#addSysRole(com.administrator.platform.model.SysRole)
	 */
	@Override
	public SysRole addSysRole(SysRole sysRole) {
		ValidationUtil.validateNull(sysRole, null);
		validateInput(sysRole);
		try {
			logger.info("添加系统角色");
			sysRoleMapper.insert(sysRole);
			return sysRole;
		} catch (Exception e) {
			logger.error("添加系统角色失败:{},{}", sysRole, e.getMessage());
			throw new BusinessValidationException("添加系统角色失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.SysRoleService#deleteSysRole(java.lang.Long)
	 */
	@Override
	public int deleteSysRole(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			logger.info("删除系统角色");
			sysRoleMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			logger.error("根据系统角色id删除系统角色失败:{},{}", id, e.getMessage());
			throw new BusinessValidationException(
			        "根据系统角色id删除系统角色失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.SysRoleService#deleteSysRole(java.lang.Long[])
	 */
	@Override
	public int deleteSysRole(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			logger.info("批量删除系统角色");
			sysRoleMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据ids批量删除系统角色失败" + e.getMessage());
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see
	 */
	@Override
	public SysRole findSysRoleByRoleName(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return sysRoleMapper.findSysRoleByRoleName(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.SysRoleService#findEnvAddrrsByRoleName(java.lang.String)
	 */
	@Override
	public List<SysRole> findSysRolesByRoleNameLike(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.info("根据系统角色名称查询系统角色列表");
		return sysRoleMapper.findSysRolesByRoleNameLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param sysRole
	 *            : 待校验的地址对象
	 */
	private void validateInput(SysRole sysRole) {
		// 判空
		ValidationUtil.validateNull(sysRole, null);

		if (!StringUtil.isStringAvaliable(sysRole.getRoleName())) {
			throw new BusinessValidationException("系统角色名称不能为空！");
		}

		// 验证系统角色名称长度
		ValidationUtil.validateStringAndLength(sysRole.getRoleName(), null,
		        SysRoleFormDefinition.SYSROLE_NAME_LENGTH, "系统角色名称");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param sysRole:地址对象
	 * @see com.administrator.platform.service.SysRoleService#updateSysRole(com.administrator.platform.model.SysRole)
	 */
	@Override
	public SysRole updateSysRole(SysRole sysRole) {
		ValidationUtil.validateNull(sysRole, null);

		SysRole currentSysRole = getSysRoleByObject(sysRole);

		if (null == currentSysRole) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		sysRole.setId(currentSysRole.getId());

		// 校验输入内容
		validateInput(sysRole);
		try {
			logger.info("修改系统角色");
			sysRoleMapper.updateByPrimaryKey(sysRole);
			return sysRole;
		} catch (Exception e) {
			logger.error("更新系统角色失败：{},{}", sysRole, e.getMessage());
			throw new BusinessValidationException("更新系统角色信息失败!!!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.SysRoleService#getSysRoleById(java.lang.Long)
	 */
	@Override
	public SysRole getSysRoleById(Long id) {
		ValidationUtil.validateNull(id, null);
		logger.info("根据id获取详细信息:{}", id);
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.SysRoleService#getSysRoleByObject(com.administrator.platform.model.SysRole)
	 */
	@Override
	public SysRole getSysRoleByObject(SysRole sysRole) {
		ValidationUtil.validateNull(sysRole, null);
		return getSysRoleById(sysRole.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.SysRoleService#findSysRoleByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<SysRole> findSysRoleByPage(Integer page, Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		logger.info("分页查询系统角色,查询页码:{},每页数量:{}", page, size);
		Pageable pageable = PageRequest.of(page - 1, size);
		return sysRoleMapper.findAll(pageable);
	}

	/**
	 * 根据角色Id获取该角色ID下的用户列表
	 * 
	 * @see
	 * @param id：角色ID
	 * @return List<SysUser>
	 */
	@Override
	public List<SysRole> getSysRolesByUserId(Long id) {
		logger.debug("查询用户id:{}所拥有的角色", id);
		List<SysRole> sysRolesOfUserId = sysRoleMapper.getSysRolesByUserId(id);
		logger.debug("查询用户id:{}所拥有的角色信息为:{}", id, sysRolesOfUserId);
		return sysRolesOfUserId;
	}
}
