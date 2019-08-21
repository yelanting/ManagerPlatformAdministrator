/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:20
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

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.XmindParserMapper;
import com.administrator.platform.model.XmindParser;
import com.administrator.platform.service.XmindParserService;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:20
 * @see :
 */
@Service
public class XmindParserServiceImpl implements XmindParserService {
    private static final Logger logger = LoggerFactory
            .getLogger(XmindParserServiceImpl.class);
    @Autowired
    private XmindParserMapper xmindParserMapper;

    /**
     * @see com.administrator.platform.service.GoodService#findAllGoodList()
     */
    @Override
    public List<XmindParser> findAllXmindParserList() {
        return xmindParserMapper.findAll();
    }

    /**
     * 
     * 添加xmind解析信息实现方法
     * 
     * @see
     */
    @Override
    public XmindParser addXmindParser(XmindParser xmindParser) {
        ValidationUtil.validateNull(xmindParser, null);
        validateInput(xmindParser);
        try {
            xmindParserMapper.insert(xmindParser);
            return xmindParser;
        } catch (Exception e) {
            logger.error("添加xmind解析失败:{},{}", xmindParser, e.getMessage());
            throw new BusinessValidationException("添加xmind解析失败！！！");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long)
     */
    @Override
    public int deleteXmindParser(Long id) {
        ValidationUtil.validateNull(id, null);
        XmindParser xmindParser = xmindParserMapper.selectByPrimaryKey(id);

        if (null == xmindParser) {
            throw new BusinessValidationException("id为" + id + "的xmind解析不存在！");
        }
        try {
            xmindParserMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException(
                    "删除xmind解析失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long[])
     */
    @Override
    public int deleteXmindParser(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            xmindParserMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException(
                    "批量删除xmind解析失败：" + e.getMessage());
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public XmindParser findXmindParserByName(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return xmindParserMapper.findXmindParserByXmindFileName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<XmindParser> findXmindParsersByNameLike(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return xmindParserMapper
                .findXmindParsersByXmindFileNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param envAddress
     *            : 待校验的地址对象
     */
    private void validateInput(XmindParser xmindParser) {
        // // 判空
        // ValidationUtil.validateNull(xmindParser, null);
        //
        // if (!StringUtil.isStringAvaliable(xmindParser.getName())) {
        // throw new BusinessValidationException("类型名称不能为空");
        // }
        //
        // ValidationUtil.validateStringAndLength(xmindParser.getName(),
        // null,
        // XmindParserFormDefinition.GOODTYPE_NAME_LENGTH, "物品类型");
        // ValidationUtil.validateStringAndLength(xmindParser.getTypeDesc(),
        // null,
        // XmindParserFormDefinition.GOODTYPE_DESC_MAX_LENGTH, "物品类型描述");
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.GoodService#getGoodById(java.lang.Long)
     */
    @Override
    public XmindParser getXmindParserById(Long id) {
        ValidationUtil.validateNull(id, null);
        return xmindParserMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.GoodService#getGoodByObject(com.administrator.platform.model.
     *      Good)
     */
    @Override
    public XmindParser getXmindParserByObject(XmindParser xmindParser) {
        ValidationUtil.validateNull(xmindParser, null);
        return getXmindParserById(xmindParser.getId());
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
    public Page<XmindParser> findXmindParserByPage(Integer page, Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return xmindParserMapper.findAll(pageable);
    }

}
