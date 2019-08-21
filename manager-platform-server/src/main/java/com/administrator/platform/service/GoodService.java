/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.Good;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface GoodService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<Good>
     * @return
     */
    List<Good> findAllGoodList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : Good
     * @param envAddress
     * @return
     */
    Good addGood(Good envAddress);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : Good
     * @param envAddress
     * @return
     */
    Good updateGood(Good envAddress);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : Good
     * @param id
     * @return
     */
    Good getGoodById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : Good
     * @param envAddress
     * @return
     */
    Good getGoodByObject(Good envAddress);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteGood(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteGood(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<Good>
     * @param searchContent
     * @return
     */
    List<Good> findGoodsByGoodName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<Good>
     * @param searchContent
     * @return
     */
    List<Good> findGoodsByGoodNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<Good>
     * @param page
     * @param size
     * @return
     */
    Page<Good> findGoodByPage(Integer page, Integer size);

    /**
     * 根据物品类型删除物品
     * 
     * @see : 根据物品类型删除物品
     * @param id
     *            :待删除的id
     * @return : int
     */
    int deleteGoodsByGoodTypeId(Long id);
}
