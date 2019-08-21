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
import com.administrator.platform.model.Good;
import com.administrator.platform.service.GoodService;
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
@RequestMapping("/good")
@Api("物品相关API")
public class GoodController {
    private static final Logger logger = LoggerFactory
            .getLogger(GoodController.class);
    @Autowired
    private GoodService goodService;

    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询物品列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询物品列表");
        List<Good> goodPage = null;
        ResponseData responseData = new ResponseData();
        goodPage = goodService.findAllGoodList();
        responseData.setSuccess(true);
        responseData.setData(goodPage);
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据物品名称查询物品列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据物品名称查询物品列表");
        List<Good> goodPage = null;
        ResponseData responseData = new ResponseData();
        goodPage = goodService.findGoodsByGoodNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(goodPage);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/addGood")
    @ApiOperation(value = "添加物品")
    @ResponseBody
    public ResponseData addGood(@ModelAttribute Good good) {
        logger.info("添加物品");
        ResponseData responseData = new ResponseData();
        good.setCreateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        Good goodAdded = goodService.addGood(good);
        responseData.setSuccess(true);
        responseData.setData(goodAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/updateGood")
    @ApiOperation(value = "修改物品")
    @ResponseBody
    public ResponseData updateGood(@ModelAttribute Good good) {
        logger.info("修改物品");
        ResponseData responseData = new ResponseData();
        good.setUpdateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        Good goodAdded = goodService.updateGood(good);
        responseData.setSuccess(true);
        responseData.setData(goodAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteGood")
    @ApiOperation(value = "删除物品")
    @ResponseBody
    public ResponseData deleteGood(@RequestParam("id") Long id) {
        logger.info("删除物品");
        ResponseData responseData = new ResponseData();
        Good currentGood = goodService.getGoodById(id);
        responseData.setData(currentGood);
        goodService.deleteGood(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteGoodInBatch")
    @ApiOperation(value = "批量删除物品")
    @ResponseBody
    public ResponseData deleteGoodInBatch(@RequestParam("ids[]") Long[] ids) {
        logger.info("批量删除物品");
        ResponseData responseData = new ResponseData();
        goodService.deleteGood(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getGoodListForPage")
    @ApiOperation(value = "分页查询物品信息")
    @ResponseBody
    public ResponseData getGoodListForPage(@RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.info("分页查询物品,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<Good> thisGoodPage = goodService.findGoodByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisGoodPage.getContent());
        responseData.setRows(thisGoodPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/getDetailById")
    @ApiOperation(value = "查询详情")
    @ResponseBody
    public ResponseData getDetailById(@RequestParam("id") Long id) {
        logger.info("根据id获取详细信息:{}", id);
        ResponseData responseData = new ResponseData();
        Good thisGood = goodService.getGoodById(id);
        responseData.setSuccess(true);
        responseData.setData(thisGood);
        responseData.setMsg(null);
        return responseData;
    }
}
