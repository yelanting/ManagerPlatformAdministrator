/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.MenuMapper;
import com.administrator.platform.model.Menu;
import com.administrator.platform.service.MenuService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory
            .getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    /**
     * @see com.administrator.platform.service.MenuService#findAllMenuList()
     */
    @Override
    public List<Menu> findAllMenuList() {
        return menuMapper.findAll();
    }

    /**
     * 
     * 添加菜单信息实现方法
     * 
     * @see
     *      com.administrator.platform.service.MenuService#addMenu(com.administrator.platform.model.Menu)
     */
    @Override
    public Menu addMenu(Menu menu) {
        ValidationUtil.validateNull(menu, null);
        validateInput(menu);
        try {
            menuMapper.insert(menu);
            return menu;
        } catch (Exception e) {
            logger.error("新增菜单失败:{},{}", menu, e.getMessage());
            throw new BusinessValidationException("新增菜单失败!");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.MenuService#deleteMenu(java.lang.Long)
     */
    @Override
    public int deleteMenu(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            menuMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("根据ID删除失败");
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.MenuService#deleteMenu(java.lang.Long[])
     */
    @Override
    public int deleteMenu(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            menuMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            logger.error("批量删除失败:{},{}", ids, e.getMessage());
            throw new BusinessValidationException("根据IDS批量删除失败");
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.MenuService#findEnvAddrrsByMenu(java.lang.String)
     */
    @Override
    public List<Menu> findMenuesByName(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return menuMapper.findMenuesByName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see
     */
    @Override
    public List<Menu> findMenuesByNameLike(String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return menuMapper.findMenuesByNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param menu
     *            : 待校验的地址对象
     */
    private void validateInput(Menu menu) {
        // 判空
        ValidationUtil.validateNull(menu, null);

        // if (!StringUtil.isStringAvaliable(menu.getMenu())) {
        // throw new BusinessValidationException("项目名称不能为空");
        // }
        //
        // ValidationUtil.validateStringAndLength(menu.getServerUrl(), null,
        // MenuFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器URL地址");
        // ValidationUtil.validateStringAndLength(menu.getMenu(), null,
        // MenuFormDefinition.COMMON_FORM_FIELD_LENGTH, "项目名称");
        // ValidationUtil.validateStringAndLength(menu.getUsername(), null,
        // MenuFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器登陆用户名");
        // ValidationUtil.validateStringAndLength(menu.getPassword(), null,
        // MenuFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器登陆密码");
        // ValidationUtil.validateStringAndLength(menu.getDescription(), null,
        // MenuFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH, "备注");

    }

    /**
     * 修改菜单
     * 
     * @param menu:地址对象
     * @see com.administrator.platform.service.MenuService#updateMenu(com.administrator.platform.model.
     *      Menu)
     */
    @Override
    public Menu updateMenu(Menu menu) {
        ValidationUtil.validateNull(menu, null);
        Menu currentMenu = getMenuByObject(menu);

        if (null == currentMenu) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        menu.setId(currentMenu.getId());

        // 校验输入内容
        validateInput(menu);
        try {
            menuMapper.updateByPrimaryKey(menu);
            return menu;
        } catch (Exception e) {
            logger.error("更新菜单失败:{},{}", menu, e.getMessage());
            throw new BusinessValidationException("新增菜单失败!");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.MenuService#getMenuById(java.lang.Long)
     */
    @Override
    public Menu getMenuById(Long id) {
        ValidationUtil.validateNull(id, null);
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.MenuService#getMenuByObject(com.administrator.platform.model.
     *      Menu)
     */
    @Override
    public Menu getMenuByObject(Menu menu) {
        ValidationUtil.validateNull(menu, null);
        return getMenuById(menu.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * 
     * @see com.administrator.platform.service.MenuService#findMenuByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<Menu> findMenuByPage(Integer page, Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return menuMapper.findAll(pageable);
    }

}
