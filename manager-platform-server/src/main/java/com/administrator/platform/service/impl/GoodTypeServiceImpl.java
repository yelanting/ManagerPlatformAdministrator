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
import com.administrator.platform.definition.form.GoodTypeFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.GoodTypeMapper;
import com.administrator.platform.model.GoodType;
import com.administrator.platform.service.GoodTypeService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class GoodTypeServiceImpl implements GoodTypeService {
    private static final Logger logger = LoggerFactory
            .getLogger(GoodTypeServiceImpl.class);
    @Autowired
    private GoodTypeMapper goodTypeMapper;

    @Autowired
    private GoodServiceImpl goodService;

    /**
     * @see com.administrator.platform.service.GoodService#findAllGoodList()
     */
    @Override
    public List<GoodType> findAllGoodTypeList() {
        return goodTypeMapper.findAll();
    }

    /**
     * 
     * 添加测试环境地址信息实现方法
     * 
     * @see
     *      com.administrator.platform.service.GoodService#addGood(com.administrator.platform.model.Good)
     */
    @Override
    public GoodType addGoodType(GoodType goodType) {
        ValidationUtil.validateNull(goodType, null);
        validateInput(goodType);
        try {
            goodTypeMapper.insert(goodType);
            return goodType;
        } catch (Exception e) {
            logger.error("添加物品种类失败:{},{}", goodType, e.getMessage());
            throw new BusinessValidationException("添加物品种类失败！！！");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long)
     */
    @Override
    public int deleteGoodType(Long id) {

        ValidationUtil.validateNull(id, null);
        GoodType goodType = goodTypeMapper.selectByPrimaryKey(id);

        if (null == goodType) {
            throw new BusinessValidationException("id为" + id + "的物品种类不存在！");
        }
        try {
            goodService.deleteGoodsByGoodTypeId(id);
            goodTypeMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("删除物品种类失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long[])
     */
    @Override
    public int deleteGoodType(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {

            for (Long id : ids) {
                goodService.deleteGoodsByGoodTypeId(id);
            }

            goodTypeMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException(
                    "批量删除物品种类失败：" + e.getMessage());
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<GoodType> findGoodTypesByTypeName(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return goodTypeMapper.findGoodTypesByTypeName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<GoodType> findGoodTypesByTypeNameLike(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return goodTypeMapper.findGoodTypesByTypeNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param envAddress
     *            : 待校验的地址对象
     */
    private void validateInput(GoodType goodType) {
        // 判空
        ValidationUtil.validateNull(goodType, null);

        if (!StringUtil.isStringAvaliable(goodType.getTypeName())) {
            throw new BusinessValidationException("类型名称不能为空");
        }

        ValidationUtil.validateStringAndLength(goodType.getTypeName(), null,
                GoodTypeFormDefinition.GOODTYPE_NAME_LENGTH, "物品类型");
        ValidationUtil.validateStringAndLength(goodType.getTypeDesc(), null,
                GoodTypeFormDefinition.GOODTYPE_DESC_MAX_LENGTH, "物品类型描述");
    }

    /**
     * 修改测试环境地址
     * 
     * @param envAddress:地址对象
     * @see com.administrator.platform.service.GoodService#updateGood(com.administrator.platform.model.
     *      Good)
     */
    @Override
    public GoodType updateGoodType(GoodType goodType) {
        ValidationUtil.validateNull(goodType, null);

        GoodType currentGoodType = getGoodTypeByObject(goodType);

        if (null == currentGoodType) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        goodType.setId(currentGoodType.getId());

        // 校验输入内容
        validateInput(goodType);
        try {
            goodTypeMapper.updateByPrimaryKey(goodType);
            return goodType;
        } catch (Exception e) {
            logger.error("更新物品种类失败:{},{}", goodType, e.getMessage());
            throw new BusinessValidationException("更新物品种类失败！！！");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.GoodService#getGoodById(java.lang.Long)
     */
    @Override
    public GoodType getGoodTypeById(Long id) {
        ValidationUtil.validateNull(id, null);
        return goodTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.GoodService#getGoodByObject(com.administrator.platform.model.
     *      Good)
     */
    @Override
    public GoodType getGoodTypeByObject(GoodType goodType) {
        ValidationUtil.validateNull(goodType, null);
        return getGoodTypeById(goodType.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * 
     * @see com.administrator.platform.service.GoodService#findGoodByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<GoodType> findGoodTypeByPage(Integer page, Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return goodTypeMapper.findAll(pageable);
    }
}
