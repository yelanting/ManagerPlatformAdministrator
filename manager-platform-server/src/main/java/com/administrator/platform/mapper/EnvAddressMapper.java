package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.EnvAddress;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface EnvAddressMapper {
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
     * 新增记录
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int insert(EnvAddress record);

    /**
     * 根据部分属性插入
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int insertSelective(EnvAddress record);

    /**
     * 根据主键查询
     * 
     * @see :
     * @param :
     * @return : EnvAddress
     * @param id
     * @return
     */
    EnvAddress selectByPrimaryKey(Long id);

    /**
     * 根据部分属性修改
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(EnvAddress record);

    /**
     * 根据主键更新内容
     * 
     * @see :
     * @param :
     * @return : int
     * @param record
     * @return
     */
    int updateByPrimaryKey(EnvAddress record);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @param projectName
     * @return
     */
    List<EnvAddress> findEnvAddressesByProjectName(String projectName);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @param projectName
     * @return
     */
    List<EnvAddress> findEnvAddressesByProjectNameLike(String projectName);

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
     * @return : Page<EnvAddress>
     */
    Page<EnvAddress> findAll(Pageable pageable);

    /**
     * 查询所有数据
     * 
     * @see :
     * @param :
     * @return : List<EnvAddress>
     * @return
     */
    List<EnvAddress> findAll();
}
