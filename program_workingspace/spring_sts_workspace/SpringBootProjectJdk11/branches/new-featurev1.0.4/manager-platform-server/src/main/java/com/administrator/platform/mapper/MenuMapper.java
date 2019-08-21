package com.administrator.platform.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.Menu;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface MenuMapper {
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

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<Menu>
     * @param name
     * @return
     */
    List<Menu> findMenuesByName(String name);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<Menu>
     * @param name
     * @return
     */
    List<Menu> findMenuesByNameLike(String name);

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
     * @return : Page<Menu>
     */
    Page<Menu> findAll(Pageable pageable);

    List<Menu> findAll();

    List<Menu> selectMenuesByRoleId(Long roleId);

    List<Menu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap);
}
