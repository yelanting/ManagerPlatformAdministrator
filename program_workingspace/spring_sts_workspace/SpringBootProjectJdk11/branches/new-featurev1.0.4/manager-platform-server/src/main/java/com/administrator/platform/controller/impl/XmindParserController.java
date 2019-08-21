/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午8:58:17
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

import com.administrator.platform.model.XmindParser;
import com.administrator.platform.service.XmindParserService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午8:58:17
 * @see :
 */
@RequestMapping("/xmindParser")
@Controller
public class XmindParserController {
    private static final Logger logger = LoggerFactory
            .getLogger(XmindParserController.class);
    @Autowired
    private XmindParserService xmindParserService;

    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询xmind解析列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        logger.info("查询xmind解析信息列表");
        List<XmindParser> xmindParserPage = null;
        ResponseData responseData = new ResponseData();
        xmindParserPage = xmindParserService.findAllXmindParserList();
        responseData.setSuccess(true);
        responseData.setData(xmindParserPage);
        responseData.setMsg(null);
        return responseData;
    }

    @GetMapping(value = "/searchList")
    @ResponseBody
    @ApiOperation(value = "根据种类名称查询xmind解析列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {
        logger.info("根据种类名称查询xmind解析信息列表");
        List<XmindParser> xmindParserPage = null;
        ResponseData responseData = new ResponseData();
        xmindParserPage = xmindParserService
                .findXmindParsersByNameLike(searchContent);
        responseData.setSuccess(true);
        responseData.setData(xmindParserPage);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/addXmindParser")
    @ApiOperation(value = "添加xmind解析")
    @ResponseBody
    public ResponseData addXmindParser(
            @ModelAttribute XmindParser xmindParser) {
        logger.info("添加xmind解析");
        ResponseData responseData = new ResponseData();
        XmindParser xmindParserAdded = xmindParserService
                .addXmindParser(xmindParser);
        responseData.setSuccess(true);
        responseData.setData(xmindParserAdded);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteXmindParser")
    @ApiOperation(value = "删除xmind解析")
    @ResponseBody
    public ResponseData deleteXmindParser(@RequestParam("id") Long id) {
        logger.info("删除xmind解析");
        ResponseData responseData = new ResponseData();
        XmindParser currentXmindParser = xmindParserService
                .getXmindParserById(id);
        responseData.setData(currentXmindParser);
        xmindParserService.deleteXmindParser(id);
        responseData.setSuccess(true);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/deleteXmindParserInBatch")
    @ApiOperation(value = "批量删除xmind解析")
    @ResponseBody
    public ResponseData deleteXmindParserInBatch(
            @RequestParam("ids[]") Long[] ids) {
        logger.info("批量删除xmind解析");
        ResponseData responseData = new ResponseData();
        xmindParserService.deleteXmindParser(ids);
        responseData.setSuccess(true);
        responseData.setData(ids);
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getXmindParserListForPage")
    @ApiOperation(value = "分页查询xmind解析信息")
    @ResponseBody
    public ResponseData getXmindParserListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.info("分页查询xmind解析,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<XmindParser> thisXmindParserPage = xmindParserService
                .findXmindParserByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisXmindParserPage.getContent());
        responseData.setRows(thisXmindParserPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }

    @PostMapping(value = "/getXmindParserById")
    @ApiOperation(value = "分页查询xmind解析信息")
    @ResponseBody
    public ResponseData getXmindParserById(@RequestParam("id") Long id) {
        logger.info("根据ID查询xmind解析信息:{}", id);
        ResponseData responseData = new ResponseData();
        XmindParser thisXmindParser = xmindParserService.getXmindParserById(id);
        responseData.setSuccess(true);
        responseData.setData(thisXmindParser);
        responseData.setMsg(null);
        return responseData;
    }
}
