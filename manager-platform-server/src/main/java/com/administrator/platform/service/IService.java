/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */

package com.administrator.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 
 * @author : Administrator
 * @since : 2019年3月8日 上午9:55:17
 * @see :
 */
@Service
public interface IService<T> {

    T selectByKey(Object key);

    int save(T entity);

    int saveNotNull(T entity);

    int delete(Object key);

    int deleteByExample(Object example);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);

    int selectCountByExample(Object example);

    // TODO 其他...
}
