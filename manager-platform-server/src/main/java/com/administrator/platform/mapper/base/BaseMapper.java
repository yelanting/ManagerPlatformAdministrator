/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午1:58:16
 * @see:
 */
package com.administrator.platform.mapper.base;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午1:58:16
 * @see :
 */

import java.util.List;
import java.util.Map;

import com.administrator.platform.core.base.DruidSpringMonitor;

public interface BaseMapper<T, S> extends DruidSpringMonitor {
    /**
     * 根据ID获取实体对象
     * 
     * @param id
     *            记录ID
     * @return 实体对象
     */
    public T get(Long id);

    /**
     * 保存实体对象
     * 
     * @param entity
     *            对象
     * @return ID
     */
    public Long add(T entity);

    /**
     * 更新实体
     * 
     * @see
     * @param entity
     * 
     * @return int 影响实体数
     */
    public int update(T entity);

    /**
     * 根据ID删除实体对象
     * 
     * @see :
     * @param id:
     *            记录ID
     * @return 删除结果，影响记录个数
     */
    public int delete(Long id);

    /**
     * 根据ID数组删除实体对象
     * 
     * @see
     * @param ids
     *            ID数组
     * @return 删除结果，影响记录个数
     */
    public int delete(Long[] ids);

    /**
     * 根据SearchVo进行查询(提供分页、查找、排序功能)
     * 
     * @see
     * @param searchVo
     *            查询VO实体
     * 
     * @return Pager对象
     */
    public List<T> queryForPageInfo(S searchVo);

    /**
     * 分页查询
     * 
     * @see : 分页查询
     * @param searchVo:查询VO
     * @return : List<T>
     * @param searchVo
     * @return
     */
    public List<T> queryForPageInfoByVoHelper(S searchVo);

    /**
     * 
     * 分页条件查询
     * 
     * @see
     * @param searchVO:查询封装对象
     * @return : List<T>
     * @param searchVo
     * @return
     */
    public List<T> queryForPageInfoByConditionHelper(S searchVo);

    /**
     * 分页查询，map作为参数
     * 
     * @see : map参数查询分页数据
     * 
     * 
     * @param :
     * @return : List<T>
     * @param map
     * @return
     */
    public List<T> queryForPageInfoByMapHelper(Map<String, Object> map);

}
