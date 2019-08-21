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

import com.administrator.platform.model.OtherAddress;
import com.administrator.platform.service.OtherAddressService;
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
@RequestMapping("/otherAddress")
@Api("其他环境地址相关API")
public class OtherAddressController {
    private static final Logger logger = LoggerFactory
            .getLogger(OtherAddressController.class);
    @Autowired
    private OtherAddressService otherAddressService;

    /**
     * 查询其他环境地址列表，查询所有
     * 
     * @param :
     * @return : ResponseData
     */
    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询其他环境地址列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询其他环境信息列表");
        List<OtherAddress> otherAddressPage = null;
        ResponseData responseData = new ResponseData();
        otherAddressPage = otherAddressService.findAllOtherAddressList();
        responseData.setSuccess(true);
        responseData.setData(otherAddressPage);
        responseData.setMsg(null);
        responseData.setRows(otherAddressPage.size());
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
    @ApiOperation(value = "根据项目名称查询其他环境地址列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据项目名称查询其他环境信息列表");
        List<OtherAddress> otherAddressPage = null;
        ResponseData responseData = new ResponseData();
        otherAddressPage = otherAddressService
                .findOtherAddressesByProjectNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(otherAddressPage);
        responseData.setMsg(null);
        responseData.setRows(otherAddressPage.size());
        return responseData;
    }

    /**
     * 添加其他环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param otherAddress
     */
    @PostMapping(value = "/addOtherAddress")
    @ApiOperation(value = "添加其他环境地址")
    @ResponseBody
    public ResponseData addOtherAddress(
            @ModelAttribute OtherAddress otherAddress) {
        logger.debug("添加其他环境地址:{}", otherAddress);
        ResponseData responseData = new ResponseData();
        OtherAddress otherAddressAdded = otherAddressService
                .addOtherAddress(otherAddress);
        responseData.setSuccess(true);
        responseData.setData(otherAddressAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 更新其他环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param otherAddress
     */
    @PostMapping(value = "/updateOtherAddress")
    @ApiOperation(value = "修改其他环境地址")
    @ResponseBody
    public ResponseData updateOtherAddress(
            @ModelAttribute OtherAddress otherAddress) {
        logger.debug("修改其他环境地址:{}", "otherAddress");
        ResponseData responseData = new ResponseData();
        OtherAddress otherAddressAdded = otherAddressService
                .updateOtherAddress(otherAddress);
        responseData.setSuccess(true);
        responseData.setData(otherAddressAdded);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 删除其他环境地址-单个
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param id
     */
    @PostMapping(value = "/deleteOtherAddress")
    @ApiOperation(value = "删除其他环境地址")
    @ResponseBody
    public ResponseData deleteOtherAddress(@RequestParam("id") Long id) {
        logger.debug("删除其他环境地址:{}", id);
        ResponseData responseData = new ResponseData();
        OtherAddress currentOtherAddress = otherAddressService
                .getOtherAddressById(id);
        responseData.setData(currentOtherAddress);
        otherAddressService.deleteOtherAddress(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 批量删除其他环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteOtherAddressInBatch")
    @ApiOperation(value = "批量删除其他环境地址")
    @ResponseBody
    public ResponseData deleteOtherAddressInBatch(
            @RequestParam("ids[]") Long[] ids) {
        logger.debug("批量删除其他环境地址");
        ResponseData responseData = new ResponseData();
        otherAddressService.deleteOtherAddress(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 分页查询其他环境地址
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/getOtherAddressListForPage")
    @ApiOperation(value = "分页查询其他环境地址信息")
    @ResponseBody
    public ResponseData getOtherAddressListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.debug("分页查询其他环境地址,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<OtherAddress> thisOtherAddressPage = otherAddressService
                .findOtherAddressByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisOtherAddressPage.getContent());
        responseData.setRows(thisOtherAddressPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }
}
