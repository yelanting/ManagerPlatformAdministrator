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

import com.administrator.platform.model.GoodType;
import com.administrator.platform.service.GoodTypeService;
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
@RequestMapping("/goodType")
@Api("物品种类相关API")
public class GoodTypeController {
    private static final Logger logger = LoggerFactory
            .getLogger(GoodTypeController.class);
    @Autowired
    private GoodTypeService goodTypeService;

    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询物品种类列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询物品种类信息列表");
        List<GoodType> goodTypePage = null;
        ResponseData responseData = new ResponseData();
        goodTypePage = goodTypeService.findAllGoodTypeList();
        responseData.setSuccess(true);
        responseData.setData(goodTypePage);
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据种类名称查询物品种类列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据种类名称查询物品种类信息列表");
        List<GoodType> goodTypePage = null;
        ResponseData responseData = new ResponseData();
        goodTypePage = goodTypeService
                .findGoodTypesByTypeNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(goodTypePage);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/addGoodType")
    @ApiOperation(value = "添加物品种类")
    @ResponseBody
    public ResponseData addGoodType(@ModelAttribute GoodType goodType) {
        logger.info("添加物品种类");
        ResponseData responseData = new ResponseData();
        GoodType goodTypeAdded = goodTypeService.addGoodType(goodType);
        responseData.setSuccess(true);
        responseData.setData(goodTypeAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/updateGoodType")
    @ApiOperation(value = "修改物品种类")
    @ResponseBody
    public ResponseData updateGoodType(@ModelAttribute GoodType goodType) {
        logger.info("修改物品种类");
        ResponseData responseData = new ResponseData();
        GoodType goodTypeAdded = goodTypeService.updateGoodType(goodType);
        responseData.setSuccess(true);
        responseData.setData(goodTypeAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteGoodType")
    @ApiOperation(value = "删除物品种类")
    @ResponseBody
    public ResponseData deleteGoodType(@RequestParam("id") Long id) {
        logger.info("删除物品种类");
        ResponseData responseData = new ResponseData();
        GoodType currentGoodType = goodTypeService.getGoodTypeById(id);
        responseData.setData(currentGoodType);
        goodTypeService.deleteGoodType(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteGoodTypeInBatch")
    @ApiOperation(value = "批量删除物品种类")
    @ResponseBody
    public ResponseData deleteGoodTypeInBatch(
            @RequestParam("ids[]") Long[] ids) {
        logger.info("批量删除物品种类");
        ResponseData responseData = new ResponseData();
        goodTypeService.deleteGoodType(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getGoodTypeListForPage")
    @ApiOperation(value = "分页查询物品种类信息")
    @ResponseBody
    public ResponseData getGoodTypeListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.info("分页查询物品种类,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<GoodType> thisGoodTypePage = goodTypeService
                .findGoodTypeByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisGoodTypePage.getContent());
        responseData.setRows(thisGoodTypePage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getGoodTypeById")
    @ApiOperation(value = "分页查询物品种类信息")
    @ResponseBody
    public ResponseData getGoodTypeById(@RequestParam("id") Long id) {
        logger.info("根据ID查询物品种类信息:{}", id);
        ResponseData responseData = new ResponseData();
        GoodType thisGoodType = goodTypeService.getGoodTypeById(id);
        responseData.setSuccess(true);
        responseData.setData(thisGoodType);
        responseData.setMsg(null);
        return responseData;
    }
}
