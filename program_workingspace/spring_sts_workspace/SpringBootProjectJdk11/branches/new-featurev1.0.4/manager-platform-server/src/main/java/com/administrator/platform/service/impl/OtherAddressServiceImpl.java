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
import com.administrator.platform.definition.form.OtherAddressFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.OtherAddressMapper;
import com.administrator.platform.model.OtherAddress;
import com.administrator.platform.service.OtherAddressService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class OtherAddressServiceImpl implements OtherAddressService {
    private static final Logger logger = LoggerFactory
            .getLogger(OtherAddressServiceImpl.class);

    @Autowired
    private OtherAddressMapper otherAddressMapper;

    /**
     * @see com.administrator.platform.service.OtherAddressService#findAllOtherAddressList()
     */
    @Override
    public List<OtherAddress> findAllOtherAddressList() {
        return otherAddressMapper.findAll();
    }

    /**
     * 
     * 添加其他环境地址信息实现方法
     * 
     * @see
     *      com.administrator.platform.service.OtherAddressService#addOtherAddress(com.administrator.platform.model.OtherAddress)
     */
    @Override
    public OtherAddress addOtherAddress(OtherAddress otherAddress) {
        ValidationUtil.validateNull(otherAddress, null);
        validateInput(otherAddress);
        try {
            otherAddressMapper.insert(otherAddress);
            return otherAddress;
        } catch (Exception e) {
            logger.error("新增其他环境地址失败:{},{}", otherAddress, e.getMessage());
            throw new BusinessValidationException("新增其他环境地址失败!");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.OtherAddressService#deleteOtherAddress(java.lang.Long)
     */
    @Override
    public int deleteOtherAddress(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            otherAddressMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("根据ID删除失败");
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.OtherAddressService#deleteOtherAddress(java.lang.Long[])
     */
    @Override
    public int deleteOtherAddress(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            otherAddressMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            logger.error("批量删除失败:{},{}", ids, e.getMessage());
            throw new BusinessValidationException("根据IDS批量删除失败");
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.OtherAddressService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<OtherAddress> findOtherAddressesByProjectName(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return otherAddressMapper
                .findOtherAddressesByProjectName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.OtherAddressService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<OtherAddress> findOtherAddressesByProjectNameLike(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return otherAddressMapper
                .findOtherAddressesByProjectNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param otherAddress
     *            : 待校验的地址对象
     */
    private void validateInput(OtherAddress otherAddress) {
        // 判空
        ValidationUtil.validateNull(otherAddress, null);

        if (!StringUtil.isStringAvaliable(otherAddress.getProjectName())) {
            throw new BusinessValidationException("项目名称不能为空");
        }

        ValidationUtil.validateStringAndLength(otherAddress.getUrl(), null,
                OtherAddressFormDefinition.COMMON_FORM_FIELD_LENGTH, "URL地址");
        ValidationUtil.validateStringAndLength(otherAddress.getProjectName(),
                null, OtherAddressFormDefinition.COMMON_FORM_FIELD_LENGTH,
                "项目名称");
        ValidationUtil.validateStringAndLength(otherAddress.getDescription(),
                null, OtherAddressFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH,
                "备注");

    }

    /**
     * 修改其他环境地址
     * 
     * @param otherAddress:地址对象
     * @see com.administrator.platform.service.OtherAddressService#updateOtherAddress(com.administrator.platform.model.
     *      OtherAddress)
     */
    @Override
    public OtherAddress updateOtherAddress(OtherAddress otherAddress) {
        ValidationUtil.validateNull(otherAddress, null);
        OtherAddress currentOtherAddress = getOtherAddressByObject(
                otherAddress);

        if (null == currentOtherAddress) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        otherAddress.setId(currentOtherAddress.getId());

        // 校验输入内容
        validateInput(otherAddress);
        try {
            otherAddressMapper.updateByPrimaryKey(otherAddress);
            return otherAddress;
        } catch (Exception e) {
            logger.error("更新其他环境地址失败:{},{}", otherAddress, e.getMessage());
            throw new BusinessValidationException("更新其他环境地址失败!");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.OtherAddressService#getOtherAddressById(java.lang.Long)
     */
    @Override
    public OtherAddress getOtherAddressById(Long id) {
        ValidationUtil.validateNull(id, null);
        return otherAddressMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.OtherAddressService#getOtherAddressByObject(com.administrator.platform.model.
     *      OtherAddress)
     */
    @Override
    public OtherAddress getOtherAddressByObject(OtherAddress otherAddress) {
        ValidationUtil.validateNull(otherAddress, null);
        return getOtherAddressById(otherAddress.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * 
     * @see com.administrator.platform.service.OtherAddressService#findOtherAddressByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<OtherAddress> findOtherAddressByPage(Integer page,
            Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return otherAddressMapper.findAll(pageable);
    }

}
