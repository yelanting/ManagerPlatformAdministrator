/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:05
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.SysRole;
import com.administrator.platform.service.SysRoleService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:05
 * @see :
 */
@Controller
@RequestMapping("/sys/sysRole")
@Api("系统角色相关API")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询系统角色列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        List<SysRole> sysRolePage = sysRoleService.findAllSysRoleList();
        return ResponseData.getSuccessResult(sysRolePage, sysRolePage.size());
    }

    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据系统角色名称查询系统角色列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {

        List<SysRole> sysRolePage = sysRoleService
                .findSysRolesByRoleNameLike(searchContent);
        return ResponseData.getSuccessResult(sysRolePage, sysRolePage.size());
    }

    @PostMapping(value = "/addSysRole")
    @ApiOperation(value = "添加系统角色")
    @ResponseBody
    public ResponseData addSysRole(@ModelAttribute SysRole sysRole) {
        return ResponseData
                .getSuccessResult(sysRoleService.addSysRole(sysRole));
    }

    @PostMapping(value = "/updateSysRole")
    @ApiOperation(value = "修改系统角色")
    @ResponseBody
    public ResponseData updateSysRole(@ModelAttribute SysRole sysRole) {

        return ResponseData
                .getSuccessResult(sysRoleService.updateSysRole(sysRole));
    }

    @PostMapping(value = "/deleteSysRole")
    @ApiOperation(value = "删除系统角色")
    @ResponseBody
    public ResponseData deleteSysRole(@RequestParam("id") Long id) {

        SysRole currentSysRole = sysRoleService.getSysRoleById(id);
        sysRoleService.deleteSysRole(id);
        return ResponseData.getSuccessResult(currentSysRole);
    }

    @PostMapping(value = "/deleteSysRoleInBatch")
    @ApiOperation(value = "批量删除系统角色")
    @ResponseBody
    public ResponseData deleteSysRoleInBatch(
            @RequestParam("ids[]") Long[] ids) {
        sysRoleService.deleteSysRole(ids);
        return ResponseData.getSuccessResult(ids);
    }

    @PostMapping(value = "/getSysRoleListForPage")
    @ApiOperation(value = "分页查询系统角色信息")
    @ResponseBody
    public ResponseData getSysRoleListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        Page<SysRole> thisSysRolePage = sysRoleService.findSysRoleByPage(page,
                size);
        return ResponseData.getSuccessResult(thisSysRolePage,
                thisSysRolePage.getSize());
    }

    @GetMapping(value = "/getDetailById")
    @ApiOperation(value = "查询详情")
    @ResponseBody
    public ResponseData getDetailById(@RequestParam("id") Long id) {

        return ResponseData.getSuccessResult(sysRoleService.getSysRoleById(id));
    }
}
