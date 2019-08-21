/**
 * @author : 孙留平
 * @since : 2018年12月21日 20:35:15
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.Menu;
import com.administrator.platform.service.MenuService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2018年12月21日 20:35:15
 * @see :
 */
@Controller
@RequestMapping("/menu")
@Api("菜单相关API")
public class MenuController {
    private static final Logger logger = LoggerFactory
            .getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表，查询所有
     * 
     * @param :
     * @return : ResponseData
     */
    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询菜单列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询菜单信息列表");
        List<Menu> menuPage = null;
        ResponseData responseData = new ResponseData();
        menuPage = menuService.findAllMenuList();
        responseData.setSuccess(true);
        responseData.setData(menuPage);
        responseData.setMsg(null);
        responseData.setRows(menuPage.size());
        return responseData;
    }

    /**
     * 根据项目名称查询
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param searchContent
     * @return
     */
    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据项目名称查询菜单列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据项目名称查询菜单信息列表");
        List<Menu> menuPage = null;
        ResponseData responseData = new ResponseData();
        menuPage = menuService.findMenuesByNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(menuPage);
        responseData.setMsg(null);
        responseData.setRows(menuPage.size());
        return responseData;
    }

    /**
     * 添加菜单
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param menu
     */
    @PostMapping(value = "/addMenu")
    @ApiOperation(value = "添加菜单")
    @ResponseBody
    public ResponseData addMenu(@ModelAttribute Menu menu) {
        logger.debug("添加菜单:{}", menu);
        ResponseData responseData = new ResponseData();
        Menu menuAdded = menuService.addMenu(menu);
        responseData.setSuccess(true);
        responseData.setData(menuAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 更新菜单
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param menu
     */
    @PostMapping(value = "/updateMenu")
    @ApiOperation(value = "修改菜单")
    @ResponseBody
    public ResponseData updateMenu(@ModelAttribute Menu menu) {
        logger.debug("修改菜单:{}", "menu");
        ResponseData responseData = new ResponseData();
        Menu menuAdded = menuService.updateMenu(menu);
        responseData.setSuccess(true);
        responseData.setData(menuAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 删除菜单-单个
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param id
     */
    @PostMapping(value = "/deleteMenu")
    @ApiOperation(value = "删除菜单")
    @ResponseBody
    public ResponseData deleteMenu(@RequestParam("id") Long id) {
        logger.debug("删除菜单:{}", id);
        ResponseData responseData = new ResponseData();
        Menu currentMenu = menuService.getMenuById(id);
        responseData.setData(currentMenu);
        menuService.deleteMenu(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 批量删除菜单
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteMenuInBatch")
    @ApiOperation(value = "批量删除菜单")
    @ResponseBody
    public ResponseData deleteMenuInBatch(@RequestParam("ids[]") Long[] ids) {
        logger.debug("批量删除菜单");
        ResponseData responseData = new ResponseData();
        menuService.deleteMenu(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 分页查询菜单
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/getMenuListForPage")
    @ApiOperation(value = "分页查询菜单信息")
    @ResponseBody
    public ResponseData getMenuListForPage(@RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.debug("分页查询菜单,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<Menu> thisMenuPage = menuService.findMenuByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisMenuPage.getContent());
        responseData.setRows(thisMenuPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }
}
