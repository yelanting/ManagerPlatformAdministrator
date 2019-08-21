/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午1:57:38
 * @see:
 */
package com.administrator.platform.service.impl;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午1:57:38
 * @see :
 */

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.entity.message.MessageConstant;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.base.BaseMapper;
import com.administrator.platform.model.base.BaseDomain;
import com.administrator.platform.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseServiceImpl<T extends BaseDomain, SearchDomain extends BaseDomain>
        implements BaseService<T, SearchDomain> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取仓库实体
     * 
     * @see :
     * @param :
     * @return : BaseMapper<T,SearchDomain>
     * @return
     */
    public abstract BaseMapper<T, SearchDomain> getBaseDAO();

    private void validatorId(Long id) {
        if (id == null) {
            logger.error(MessageConstant.ID_CANNOT_BE_NULL);
            throw new BusinessValidationException(MessageConstant.ID_CANNOT_BE_NULL);
        }
    }

    @Override
    public T get(Long id) {
        validatorId(id);
        return getBaseDAO().get(id);
    }

    @Override
    public T add(T entity) {
        if (entity == null) {
            throw new BusinessValidationException(MessageConstant.PARAM_CANNOT_BE_NULL);
        }
        getBaseDAO().add(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        if (entity == null || entity.getId() == null) {
            throw new BusinessValidationException(MessageConstant.PARAM_CANNOT_BE_NULL);
        }
        getBaseDAO().update(entity);
        return get(entity.getId());
    }

    @Override
    public void delete(Long id) {
        validatorId(id);
        getBaseDAO().delete(id);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessValidationException(MessageConstant.ID_CANNOT_BE_NULL);
        }
        for (Long id : ids) {
            delete(id);
        }
    }

    @Override
    public PageInfo<T> queryForPageInfo(SearchDomain searchVo, Integer pageNum, Integer pageSize, String sidx,
            String sord) {
        PageHelper.startPage(pageNum, pageSize, StringUtil.joinSortFieldOrder(sidx, sord));
        List<T> list = getBaseDAO().queryForPageInfo(searchVo);
        return new PageInfo<T>(list);
    }

    @Override
    public PageInfo<T> queryForPageInfoByVoHelper(SearchDomain searchVo, Integer pageNum, Integer pageSize, String sidx,
            String sord) {
        PageHelper.startPage(pageNum, pageSize, StringUtil.joinSortFieldOrder(sidx, sord));
        List<T> list = getBaseDAO().queryForPageInfoByVoHelper(searchVo);
        return new PageInfo<T>(list);
    }

    @Override
    public PageInfo<T> queryForPageInfoByMapHelper(Map<String, Object> map, Integer pageNum, Integer pageSize,
            String sidx, String sord) {
        PageHelper.startPage(pageNum, pageSize, StringUtil.joinSortFieldOrder(sidx, sord));
        List<T> list = getBaseDAO().queryForPageInfoByMapHelper(map);
        return new PageInfo<T>(list);
    }

    @Override
    public PageInfo<T> queryForPageInfoByConditionHelper(SearchDomain searchVo, Integer pageNum, Integer pageSize,
            String sidx, String sord) {
        PageHelper.startPage(pageNum, pageSize, StringUtil.joinSortFieldOrder(sidx, sord));
        List<T> list = getBaseDAO().queryForPageInfoByConditionHelper(searchVo);
        return new PageInfo<T>(list);
    }

}
