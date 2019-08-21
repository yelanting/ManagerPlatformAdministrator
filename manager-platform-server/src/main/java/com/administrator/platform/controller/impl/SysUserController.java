/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:05
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

import com.administrator.platform.core.base.util.CalendarUtil;
import com.administrator.platform.model.SysUser;
import com.administrator.platform.service.SysUserService;
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
@RequestMapping("/sys/sysUser")
@Api("系统用户相关API")
public class SysUserController {
    private static final Logger logger = LoggerFactory
            .getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询系统用户列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询系统用户列表");
        List<SysUser> sysUserPage = null;
        ResponseData responseData = new ResponseData();
        sysUserPage = sysUserService.findAllSysUserList();
        responseData.setSuccess(true);
        responseData.setData(sysUserPage);
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据系统用户名称查询系统用户列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据系统用户名称查询系统用户列表");
        List<SysUser> sysUserPage = null;
        ResponseData responseData = new ResponseData();
        sysUserPage = sysUserService
                .findSysUsersByUserAccountLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(sysUserPage);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/addSysUser")
    @ApiOperation(value = "添加系统用户")
    @ResponseBody
    public ResponseData addSysUser(@ModelAttribute SysUser sysUser) {
        logger.info("添加系统用户");
        ResponseData responseData = new ResponseData();
        sysUser.setCreateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        SysUser sysUserAdded = sysUserService.addSysUser(sysUser);
        responseData.setSuccess(true);
        responseData.setData(sysUserAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/updateSysUser")
    @ApiOperation(value = "修改系统用户")
    @ResponseBody
    public ResponseData updateSysUser(@ModelAttribute SysUser sysUser) {
        logger.info("修改系统用户");
        ResponseData responseData = new ResponseData();
        sysUser.setUpdateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        SysUser sysUserAdded = sysUserService.updateSysUser(sysUser);
        responseData.setSuccess(true);
        responseData.setData(sysUserAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteSysUser")
    @ApiOperation(value = "删除系统用户")
    @ResponseBody
    public ResponseData deleteSysUser(@RequestParam("id") Long id) {
        logger.info("删除系统用户");
        ResponseData responseData = new ResponseData();
        SysUser currentSysUser = sysUserService.getSysUserById(id);
        responseData.setData(currentSysUser);
        sysUserService.deleteSysUser(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteSysUserInBatch")
    @ApiOperation(value = "批量删除系统用户")
    @ResponseBody
    public ResponseData deleteSysUserInBatch(
            @RequestParam("ids[]") Long[] ids) {
        logger.info("批量删除系统用户");
        ResponseData responseData = new ResponseData();
        sysUserService.deleteSysUser(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getSysUserListForPage")
    @ApiOperation(value = "分页查询系统用户信息")
    @ResponseBody
    public ResponseData getSysUserListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.info("分页查询系统用户,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<SysUser> thisSysUserPage = sysUserService.findSysUserByPage(page,
                size);
        responseData.setSuccess(true);
        responseData.setData(thisSysUserPage.getContent());
        responseData.setRows(thisSysUserPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/getDetailById")
    @ApiOperation(value = "查询详情")
    @ResponseBody
    public ResponseData getDetailById(@RequestParam("id") Long id) {
        logger.info("根据id获取详细信息:{}", id);
        ResponseData responseData = new ResponseData();
        SysUser thisSysUser = sysUserService.getSysUserById(id);
        responseData.setSuccess(true);
        responseData.setData(thisSysUser);
        responseData.setMsg(null);
        return responseData;
    }
}
