/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午1:52:31
 * @see:
 */
package com.administrator.platform.service;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午1:52:31
 * @see : 基础service接口
 */

import java.util.Map;

import com.administrator.platform.model.base.BaseDomain;
import com.github.pagehelper.PageInfo;

public interface BaseService<T extends BaseDomain, S extends BaseDomain> {

    /**
     * 通过Id删除
     * 
     * @see
     * 
     * @param id
     *            记录ID
     * @return 实体对象
     * @param id:
     * @return:
     */
    public T get(Long id);

    /**
     * 保存实体对象
     * 
     * @see
     * 
     * @param entity
     *            对象
     * @return ID 只会更新所有不为空的字段
     * @param record:
     * @return:
     */
    public T add(T entity);

    /**
     * 更新实体
     * 
     * @see
     * @param entity
     *            对象
     * @param record:
     * @param orderBy:
     * @return:
     */
    public T update(T entity);

    /**
     * 根据ID删除实体对象
     * 
     * @see
     * 
     * @param id
     *            记录ID
     * @param id:
     * @return:
     */
    public void delete(Long id);

    /**
     * 批量删除
     * 
     * @see
     * 
     * @param ids
     *            ID数组
     * @param ids:
     * @return:
     */
    public void delete(Long[] ids);

    /**
     * 根据SearchVo进行查询(提供分页、查找、排序功能)
     * 
     * @see
     * 
     * @param searchVo
     *            查询对象
     * 
     * @param pageNum
     *            :页数
     * @param pageSize
     *            :单页数量
     * @param sidx
     *            :排序字段
     * @param sord
     *            :排序类型
     * @return PageInfo : 单页数据
     */
    public PageInfo<T> queryForPageInfo(S searchVo, Integer pageNum, Integer pageSize, String sidx, String sord);

    /**
     * 根据SearchVo进行查询(生成分页、查找、排序功能 )
     * 
     * @param searchVo
     *            :查询对象
     * 
     * @param pageNum
     *            :页数
     * @param pageSize
     *            :单页数量
     * @param sidx
     *            :排序字段
     * @param sord
     *            :排序类型
     * @return PageInfo : 单页数据
     */
    public PageInfo<T> queryForPageInfoByVoHelper(S searchVo, Integer pageNum, Integer pageSize, String sidx,
            String sord);

    /**
     * 根据SearchVo进行查询(生成分页、查找、排序功能 )
     * 
     * @param searchVo
     *            :查询对象
     * 
     * @param pageNum
     *            :页数
     * @param pageSize
     *            :单页数量
     * @param sidx
     *            :排序字段
     * @param sord
     *            :排序类型
     * @return PageInfo : 单页数据
     */
    public PageInfo<T> queryForPageInfoByConditionHelper(S searchVo, Integer pageNum, Integer pageSize, String sidx,
            String sord);

    /**
     * 根据 map 进行查询(生成分页、查找、排序功能)
     * 
     * @param map
     *            查询对象
     * 
     * @param pageNum
     *            :页数
     * @param pageSize
     *            :单页数量
     * @param sidx
     *            :排序字段
     * @param sord
     *            :排序类型
     * @return PageInfo : 单页数据
     * 
     */
    public PageInfo<T> queryForPageInfoByMapHelper(Map<String, Object> map, Integer pageNum, Integer pageSize,
            String sidx, String sord);
}
