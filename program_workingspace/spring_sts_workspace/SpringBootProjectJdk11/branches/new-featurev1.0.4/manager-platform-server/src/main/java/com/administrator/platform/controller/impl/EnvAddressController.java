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

import com.administrator.platform.model.EnvAddress;
import com.administrator.platform.service.EnvAddressService;
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
@RequestMapping("/envAddress")
@Api("测试环境地址相关API")
public class EnvAddressController {
    private static final Logger logger = LoggerFactory
            .getLogger(EnvAddressController.class);
    @Autowired
    private EnvAddressService envAddressService;

    /**
     * 查询测试环境地址列表，查询所有
     * 
     * @param :
     * @return : ResponseData
     */
    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询测试环境地址列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询测试环境信息列表");
        List<EnvAddress> envAddressPage = null;
        ResponseData responseData = new ResponseData();
        envAddressPage = envAddressService.findAllEnvAddressList();
        responseData.setSuccess(true);
        responseData.setData(envAddressPage);
        responseData.setMsg(null);
        responseData.setRows(envAddressPage.size());
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
    @ApiOperation(value = "根据项目名称查询测试环境地址列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据项目名称查询测试环境信息列表");
        List<EnvAddress> envAddressPage = null;
        ResponseData responseData = new ResponseData();
        envAddressPage = envAddressService
                .findEnvAddressesByProjectNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(envAddressPage);
        responseData.setMsg(null);
        responseData.setRows(envAddressPage.size());
        return responseData;
    }

    /**
     * 添加测试环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param envAddress
     */
    @PostMapping(value = "/addEnvAddress")
    @ApiOperation(value = "添加测试环境地址")
    @ResponseBody
    public ResponseData addEnvAddress(@ModelAttribute EnvAddress envAddress) {
        logger.debug("添加测试环境地址:{}", envAddress);
        ResponseData responseData = new ResponseData();
        EnvAddress envAddressAdded = envAddressService
                .addEnvAddress(envAddress);
        responseData.setSuccess(true);
        responseData.setData(envAddressAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 更新测试环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param envAddress
     */
    @PostMapping(value = "/updateEnvAddress")
    @ApiOperation(value = "修改测试环境地址")
    @ResponseBody
    public ResponseData updateEnvAddress(
            @ModelAttribute EnvAddress envAddress) {
        logger.debug("修改测试环境地址:{}", envAddress);
        ResponseData responseData = new ResponseData();
        EnvAddress envAddressAdded = envAddressService
                .updateEnvAddress(envAddress);
        responseData.setSuccess(true);
        responseData.setData(envAddressAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 删除测试环境地址-单个
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param id
     */
    @PostMapping(value = "/deleteEnvAddress")
    @ApiOperation(value = "删除测试环境地址")
    @ResponseBody
    public ResponseData deleteEnvAddress(@RequestParam("id") Long id) {
        logger.debug("删除测试环境地址:{}", id);
        ResponseData responseData = new ResponseData();
        EnvAddress currentEnvAddress = envAddressService.getEnvAddressById(id);
        responseData.setData(currentEnvAddress);
        envAddressService.deleteEnvAddress(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 批量删除测试环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteEnvAddressInBatch")
    @ApiOperation(value = "批量删除测试环境地址")
    @ResponseBody
    public ResponseData deleteEnvAddressInBatch(
            @RequestParam("ids[]") Long[] ids) {
        logger.debug("批量删除测试环境地址");
        ResponseData responseData = new ResponseData();
        envAddressService.deleteEnvAddress(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 分页查询测试环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/getEnvAddressListForPage")
    @ApiOperation(value = "分页查询测试环境地址信息")
    @ResponseBody
    public ResponseData getEnvAddressListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.debug("分页查询测试环境地址,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<EnvAddress> thisEnvAddressPage = envAddressService
                .findEnvAddressByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisEnvAddressPage.getContent());
        responseData.setRows(thisEnvAddressPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }
}
