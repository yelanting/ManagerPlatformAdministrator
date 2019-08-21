/**
 * @author : 孙留平
 * @since : 2018年12月15日 下午6:29:22
 * @see:
 */
package com.administrator.platform.mapper;

/**
 * @author : Administrator
 * @since : 2018年12月15日 下午6:29:22
 * @see :
 */

import com.administrator.platform.model.Book;

public interface BookMapper {
    /**
     * 根据主键删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int insert(Book record);

    /**
     * 部分字段插入
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int insertSelective(Book record);

    /**
     * 根据主键查询
     * 
     * @see :
     * @param :
     * @return : Book
     * @param id
     * @return
     */
    Book selectByPrimaryKey(Long id);

    /**
     * 选择性更新
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * 更新记录
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int updateByPrimaryKey(Book record);
}
