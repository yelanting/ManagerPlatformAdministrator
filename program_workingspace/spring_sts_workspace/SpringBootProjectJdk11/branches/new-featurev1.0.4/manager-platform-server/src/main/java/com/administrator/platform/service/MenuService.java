/**
 * @author : 孙留平
 * @since : 2018年12月21日 20:30:47
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.Menu;

/**
 * @author : Administrator
 * @since : 2018年12月21日 20:30:47
 * @see :
 */
public interface MenuService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<Menu>
     * @return
     */
    List<Menu> findAllMenuList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : Menu
     * @param envAddress
     * @return
     */
    Menu addMenu(Menu envAddress);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : Menu
     * @param envAddress
     * @return
     */
    Menu updateMenu(Menu envAddress);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : Menu
     * @param id
     * @return
     */
    Menu getMenuById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : Menu
     * @param envAddress
     * @return
     */
    Menu getMenuByObject(Menu envAddress);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteMenu(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteMenu(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<Menu>
     * @param searchContent
     * @return
     */
    List<Menu> findMenuesByName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<Menu>
     * @param searchContent
     * @return
     */
    List<Menu> findMenuesByNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<Menu>
     * @param page
     * @param size
     * @return
     */
    Page<Menu> findMenuByPage(Integer page, Integer size);

}
