/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.GoodType;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface GoodTypeService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<GoodType>
     * @return
     */
    List<GoodType> findAllGoodTypeList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : GoodType
     * @param goodType
     * @return
     */
    GoodType addGoodType(GoodType goodType);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : GoodType
     * @param goodType
     * @return
     */
    GoodType updateGoodType(GoodType goodType);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : GoodType
     * @param id
     * @return
     */
    GoodType getGoodTypeById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : GoodType
     * @param goodType
     * @return
     */
    GoodType getGoodTypeByObject(GoodType goodType);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteGoodType(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteGoodType(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<GoodType>
     * @param searchContent
     * @return
     */
    List<GoodType> findGoodTypesByTypeName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<GoodType>
     * @param searchContent
     * @return
     */
    List<GoodType> findGoodTypesByTypeNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<GoodType>
     * @param page
     * @param size
     * @return
     */
    Page<GoodType> findGoodTypeByPage(Integer page, Integer size);

}
