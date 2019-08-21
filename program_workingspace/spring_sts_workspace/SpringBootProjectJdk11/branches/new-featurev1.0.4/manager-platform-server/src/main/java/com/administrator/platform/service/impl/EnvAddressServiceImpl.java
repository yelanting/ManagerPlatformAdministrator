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
import com.administrator.platform.definition.form.EnvAddressFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.EnvAddressMapper;
import com.administrator.platform.model.EnvAddress;
import com.administrator.platform.service.EnvAddressService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class EnvAddressServiceImpl implements EnvAddressService {
    private static final Logger logger = LoggerFactory
            .getLogger(EnvAddressServiceImpl.class);

    @Autowired
    private EnvAddressMapper envAddressMapper;

    /**
     * @see com.administrator.platform.service.EnvAddressService#findAllEnvAddressList()
     */
    @Override
    public List<EnvAddress> findAllEnvAddressList() {
        return envAddressMapper.findAll();
    }

    /**
     * 
     * 添加测试环境地址信息实现方法
     * 
     * @see
     *      com.administrator.platform.service.EnvAddressService#addEnvAddress(com.administrator.platform.model.EnvAddress)
     */
    @Override
    public EnvAddress addEnvAddress(EnvAddress envAddress) {
        ValidationUtil.validateNull(envAddress, null);
        validateInput(envAddress);
        try {
            envAddressMapper.insert(envAddress);
            return envAddress;
        } catch (Exception e) {
            logger.error("新增测试环境地址失败:{},{}", envAddress, e.getMessage());
            throw new BusinessValidationException("新增测试环境地址失败!");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.EnvAddressService#deleteEnvAddress(java.lang.Long)
     */
    @Override
    public int deleteEnvAddress(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            envAddressMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("根据ID删除失败");
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.EnvAddressService#deleteEnvAddress(java.lang.Long[])
     */
    @Override
    public int deleteEnvAddress(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            envAddressMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            logger.error("批量删除失败:{},{}", ids, e.getMessage());
            throw new BusinessValidationException("根据IDS批量删除失败");
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.EnvAddressService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<EnvAddress> findEnvAddressesByProjectName(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return envAddressMapper.findEnvAddressesByProjectName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.EnvAddressService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<EnvAddress> findEnvAddressesByProjectNameLike(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return envAddressMapper
                .findEnvAddressesByProjectNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param envAddress
     *            : 待校验的地址对象
     */
    private void validateInput(EnvAddress envAddress) {
        // 判空
        ValidationUtil.validateNull(envAddress, null);

        if (!StringUtil.isStringAvaliable(envAddress.getProjectName())) {
            throw new BusinessValidationException("项目名称不能为空");
        }

        ValidationUtil.validateStringAndLength(envAddress.getServerUrl(), null,
                EnvAddressFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器URL地址");
        ValidationUtil.validateStringAndLength(envAddress.getProjectName(),
                null, EnvAddressFormDefinition.COMMON_FORM_FIELD_LENGTH,
                "项目名称");
        ValidationUtil.validateStringAndLength(envAddress.getUsername(), null,
                EnvAddressFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器登陆用户名");
        ValidationUtil.validateStringAndLength(envAddress.getPassword(), null,
                EnvAddressFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器登陆密码");
        ValidationUtil.validateStringAndLength(envAddress.getDescription(),
                null, EnvAddressFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH,
                "备注");

    }

    /**
     * 修改测试环境地址
     * 
     * @param envAddress:地址对象
     * @see com.administrator.platform.service.EnvAddressService#updateEnvAddress(com.administrator.platform.model.
     *      EnvAddress)
     */
    @Override
    public EnvAddress updateEnvAddress(EnvAddress envAddress) {
        ValidationUtil.validateNull(envAddress, null);
        EnvAddress currentEnvAddress = getEnvAddressByObject(envAddress);

        if (null == currentEnvAddress) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        envAddress.setId(currentEnvAddress.getId());

        // 校验输入内容
        validateInput(envAddress);
        try {
            envAddressMapper.updateByPrimaryKey(envAddress);
            return envAddress;
        } catch (Exception e) {
            logger.error("更新测试环境地址失败:{},{}", envAddress, e.getMessage());
            throw new BusinessValidationException("新增测试环境地址失败!");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.EnvAddressService#getEnvAddressById(java.lang.Long)
     */
    @Override
    public EnvAddress getEnvAddressById(Long id) {
        ValidationUtil.validateNull(id, null);
        return envAddressMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.EnvAddressService#getEnvAddressByObject(com.administrator.platform.model.
     *      EnvAddress)
     */
    @Override
    public EnvAddress getEnvAddressByObject(EnvAddress envAddress) {
        ValidationUtil.validateNull(envAddress, null);
        return getEnvAddressById(envAddress.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * 
     * @see com.administrator.platform.service.EnvAddressService#findEnvAddressByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<EnvAddress> findEnvAddressByPage(Integer page, Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return envAddressMapper.findAll(pageable);
    }

}
