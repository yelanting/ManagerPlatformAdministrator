package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.OtherAddress;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface OtherAddressMapper {
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

    int insert(OtherAddress record);

    int insertSelective(OtherAddress record);

    OtherAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OtherAddress record);

    int updateByPrimaryKey(OtherAddress record);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<OtherAddress>
     * @param projectName
     * @return
     */
    List<OtherAddress> findOtherAddressesByProjectName(String projectName);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<OtherAddress>
     * @param projectName
     * @return
     */
    List<OtherAddress> findOtherAddressesByProjectNameLike(String projectName);

    /**
     * 根据ID批量删除
     * 
     * @see :
     * @param :
     * @return : void
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 分页查询
     * 
     * @see :
     * @param pageable:分页信息
     * @return : Page<OtherAddress>
     */
    Page<OtherAddress> findAll(Pageable pageable);

    List<OtherAddress> findAll();
}
