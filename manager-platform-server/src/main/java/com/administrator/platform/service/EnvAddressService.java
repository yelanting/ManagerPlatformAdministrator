/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.EnvAddress;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface EnvAddressService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @return
     */
    List<EnvAddress> findAllEnvAddressList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : EnvAddress
     * @param envAddress
     * @return
     */
    EnvAddress addEnvAddress(EnvAddress envAddress);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : EnvAddress
     * @param envAddress
     * @return
     */
    EnvAddress updateEnvAddress(EnvAddress envAddress);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : EnvAddress
     * @param id
     * @return
     */
    EnvAddress getEnvAddressById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : EnvAddress
     * @param envAddress
     * @return
     */
    EnvAddress getEnvAddressByObject(EnvAddress envAddress);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteEnvAddress(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteEnvAddress(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @param searchContent
     * @return
     */
    List<EnvAddress> findEnvAddressesByProjectName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @param searchContent
     * @return
     */
    List<EnvAddress> findEnvAddressesByProjectNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<EnvAddress>
     * @param page
     * @param size
     * @return
     */
    Page<EnvAddress> findEnvAddressByPage(Integer page, Integer size);

}
