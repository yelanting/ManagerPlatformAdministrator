/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.OtherAddress;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface OtherAddressService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<OtherAddress>
     * @return
     */
    List<OtherAddress> findAllOtherAddressList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : OtherAddress
     * @param otherAddress
     * @return
     */
    OtherAddress addOtherAddress(OtherAddress otherAddress);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : OtherAddress
     * @param otherAddress
     * @return
     */
    OtherAddress updateOtherAddress(OtherAddress otherAddress);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : OtherAddress
     * @param id
     * @return
     */
    OtherAddress getOtherAddressById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : OtherAddress
     * @param otherAddress
     * @return
     */
    OtherAddress getOtherAddressByObject(OtherAddress otherAddress);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteOtherAddress(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteOtherAddress(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<OtherAddress>
     * @param searchContent
     * @return
     */
    List<OtherAddress> findOtherAddressesByProjectName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<OtherAddress>
     * @param searchContent
     * @return
     */
    List<OtherAddress> findOtherAddressesByProjectNameLike(
            String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<OtherAddress>
     * @param page
     * @param size
     * @return
     */
    Page<OtherAddress> findOtherAddressByPage(Integer page, Integer size);

}
