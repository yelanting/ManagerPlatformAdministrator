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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.SysUserFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.SysRoleMapper;
import com.administrator.platform.mapper.SysUserMapper;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.SysUserService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	private static final Logger logger = LoggerFactory
	        .getLogger(SysUserServiceImpl.class);
	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	/**
	 * @see com.administrator.platform.service.SysUserService#findAllSysUserList()
	 */
	@Override
	public List<SysUser> findAllSysUserList() {
		return sysUserMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see com.administrator.platform.service.SysUserService#addSysUser(com.administrator.platform.model.SysUser)
	 */
	@Override
	public SysUser addSysUser(SysUser sysUser) {
		ValidationUtil.validateNull(sysUser, null);
		validateInput(sysUser);

		try {
			sysUserMapper.insert(sysUser);
			return sysUser;
		} catch (Exception e) {
			logger.error("添加系统用户失败:{},{}", sysUser, e.getMessage());
			throw new BusinessValidationException("添加系统用户失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.SysUserService#deleteSysUser(java.lang.Long)
	 */
	@Override
	public int deleteSysUser(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			sysUserMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			logger.error("根据系统用户id删除系统用户失败:{},{}", id, e.getMessage());
			throw new BusinessValidationException(
			        "根据系统用户id删除系统用户失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.SysUserService#deleteSysUser(java.lang.Long[])
	 */
	@Override
	public int deleteSysUser(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			sysUserMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据ids批量删除系统用户失败" + e.getMessage());
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.SysUserService#findEnvAddrrsByUserAccount(java.lang.String)
	 */
	@Override
	public SysUser findSysUserByUserAccount(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return sysUserMapper.findSysUserByUserAccount(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.SysUserService#findEnvAddrrsByUserAccount(java.lang.String)
	 */
	@Override
	public List<SysUser> findSysUsersByUserAccountLike(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return sysUserMapper.findSysUsersByUserAccountLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param sysUser
	 *            : 待校验的地址对象
	 */
	private void validateInput(SysUser sysUser) {
		// 判空
		ValidationUtil.validateNull(sysUser, null);

		if (!StringUtil.isStringAvaliable(sysUser.getUserAccount())) {
			throw new BusinessValidationException("系统用户帐号不能为空！");
		}

		// 验证系统用户名称长度
		ValidationUtil.validateStringAndLength(sysUser.getUserAccount(), null,
		        SysUserFormDefinition.SYSUSER_ACCOUNT_LENGTH, "系统用户帐号");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param sysUser:地址对象
	 * @see com.administrator.platform.service.SysUserService#updateSysUser(com.administrator.platform.model.SysUser)
	 */
	@Override
	public SysUser updateSysUser(SysUser sysUser) {
		ValidationUtil.validateNull(sysUser, null);

		SysUser currentSysUser = getSysUserByObject(sysUser);

		if (null == currentSysUser) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		sysUser.setId(currentSysUser.getId());

		// 校验输入内容
		validateInput(sysUser);
		try {
			sysUserMapper.updateByPrimaryKey(sysUser);
			return sysUser;
		} catch (Exception e) {
			logger.error("更新系统用户失败：{},{}", sysUser, e.getMessage());
			throw new BusinessValidationException("更新系统用户信息失败!!!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.SysUserService#getSysUserById(java.lang.Long)
	 */
	@Override
	public SysUser getSysUserById(Long id) {
		ValidationUtil.validateNull(id, null);
		return sysUserMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.SysUserService#getSysUserByObject(com.administrator.platform.model.SysUser)
	 */
	@Override
	public SysUser getSysUserByObject(SysUser sysUser) {
		ValidationUtil.validateNull(sysUser, null);
		return getSysUserById(sysUser.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.SysUserService#findSysUserByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<SysUser> findSysUserByPage(Integer page, Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return sysUserMapper.findAll(pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {
		logger.debug("查询用户:{}是否存在", username);
		SysUser sysUser = sysUserMapper.findSysUserByUserAccount(username);

		if (null == sysUser) {
			throw new BusinessValidationException("用户名不存在");
		}

		sysUser.setSysRoles(sysRoleMapper.getSysRolesByUserId(sysUser.getId()));
		return sysUser;
	}
}
