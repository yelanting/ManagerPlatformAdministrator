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
import com.administrator.platform.model.DubboTest;
import com.administrator.platform.service.DubboTestService;
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
@RequestMapping("/dubboTest")
@Api("dubbo接口测试相关API")
public class DubboTestController {
    private static final Logger logger = LoggerFactory
            .getLogger(DubboTestController.class);
    @Autowired
    private DubboTestService dubboTestService;

    /**
     * 查询dubbo接口测试列表，查询所有
     * 
     * @param :
     * @return : ResponseData
     */
    @GetMapping(value = "/getList")
    @ResponseBody
    @ApiOperation(value = "查询dubbo接口测试列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData getList() {
        List<DubboTest> dubboTestPage = dubboTestService.findAllDubboTestList();
        return ResponseData.getSuccessResult(dubboTestPage,
                dubboTestPage.size());
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
    @ApiOperation(value = "根据项目名称查询dubbo接口测试列表")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
    public ResponseData searchList(
            @RequestParam("searchContent") String searchContent) {

        List<DubboTest> dubboTestPage = dubboTestService
                .findDubboTestesByInterfaceNameLike(searchContent);
        return ResponseData.getSuccessResult(dubboTestPage,
                dubboTestPage.size());
    }

    /**
     * 添加dubbo接口测试
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param dubboTest
     */
    @PostMapping(value = "/addDubboTest")
    @ApiOperation(value = "添加dubbo接口测试")
    @ResponseBody
    public ResponseData addDubboTest(@ModelAttribute DubboTest dubboTest) {
        dubboTest
                .setCreateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        return ResponseData
                .getSuccessResult(dubboTestService.addDubboTest(dubboTest));
    }

    /**
     * 更新dubbo接口测试
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param dubboTest
     */
    @PostMapping(value = "/updateDubboTest")
    @ApiOperation(value = "修改dubbo接口测试")
    @ResponseBody
    public ResponseData updateDubboTest(@ModelAttribute DubboTest dubboTest) {
        dubboTest
                .setUpdateDate(CalendarUtil.getUsualNowDateTimeWithDateStyle());
        return ResponseData
                .getSuccessResult(dubboTestService.updateDubboTest(dubboTest));
    }

    /**
     * 删除dubbo接口测试-单个
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param id
     */
    @PostMapping(value = "/deleteDubboTest")
    @ApiOperation(value = "删除dubbo接口测试")
    @ResponseBody
    public ResponseData deleteDubboTest(@RequestParam("id") Long id) {
        logger.info("删除dubbo接口测试");
        DubboTest currentDubboTest = dubboTestService.getDubboTestById(id);
        dubboTestService.deleteDubboTest(id);
        return ResponseData.getSuccessResult(currentDubboTest);
    }

    /**
     * 批量删除dubbo接口测试
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteDubboTestInBatch")
    @ApiOperation(value = "批量删除dubbo接口测试")
    @ResponseBody
    public ResponseData deleteDubboTestInBatch(
            @RequestParam("ids[]") Long[] ids) {
        dubboTestService.deleteDubboTest(ids);
        return ResponseData.getSuccessResult(ids);
    }

    /**
     * 分页查询dubbo接口测试
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/getDubboTestListForPage")
    @ApiOperation(value = "分页查询dubbo接口测试信息")
    @ResponseBody
    public ResponseData getDubboTestListForPage(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        logger.info("分页查询dubbo接口测试,查询页码:{},每页数量:{}", page, size);
        ResponseData responseData = new ResponseData();
        Page<DubboTest> thisDubboTestPage = dubboTestService
                .findDubboTestByPage(page, size);
        responseData.setSuccess(true);
        responseData.setData(thisDubboTestPage.getContent());
        responseData.setRows(thisDubboTestPage.getContent().size());
        responseData.setMsg(null);
        return responseData;
    }

    /**
     * 执行dubbo接口测试
     * 
     * @see :
     * @param :
     * @return : ResponseData
     * @param dubboTest
     */
    @PostMapping(value = "/runDubboTest")
    @ApiOperation(value = "执行dubbo接口测试")
    @ResponseBody
    public ResponseData runDubboTest(@ModelAttribute DubboTest dubboTest) {
        logger.info("执行dubbo接口测试");
        ResponseData responseData = new ResponseData();
        Object dubboTestRunResult = dubboTestService.runDubboTest(dubboTest);
        responseData.setSuccess(true);
        responseData.setData(dubboTestRunResult);
        responseData.setMsg(null);
        return responseData;
    }
}
