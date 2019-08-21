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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.GoodOperationRecord;
import com.administrator.platform.service.GoodOperationRecordService;
import com.administrator.platform.vo.GoodOperationRecordDTO;
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
@RequestMapping("/goodOperationRecord")
@Api("物品操作记录相关API")
public class GoodOperationRecordController {
	private static final Logger logger = LoggerFactory
	        .getLogger(GoodOperationRecordController.class);
	@Autowired
	private GoodOperationRecordService goodOperationRecordService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询物品操作记录列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		logger.info("查询物品操作记录列表");
		List<GoodOperationRecord> recordPage = goodOperationRecordService
		        .findAllGoodOperationRecordList();
		return ResponseData.getSuccessResult(recordPage, recordPage.size());
	}

	@PostMapping(value = "/addGoodOperationRecord")
	@ApiOperation(value = "添加物品操作记录")
	@ResponseBody
	public ResponseData addGoodOperationRecord(
	        @RequestParam("goodId") Long goodId,
	        @ModelAttribute GoodOperationRecordDTO goodOperationRecordDTO) {
		return ResponseData.getSuccessResult(goodOperationRecordService
		        .addGoodOperationRecord(goodOperationRecordDTO));
	}

	@GetMapping(value = "/getGoodOperationRecordByGoodId")
	@ApiOperation(value = "根据物品id查询操作信息列表")
	@ResponseBody
	public ResponseData getGoodOperationRecordByGoodId(
	        @RequestParam("goodId") Long goodId) {
		List<GoodOperationRecord> goodOperationRecordList = goodOperationRecordService
		        .findGoodOperationRecordsByGoodId(goodId);
		return ResponseData.getSuccessResult(goodOperationRecordList,
		        goodOperationRecordList.size());
	}
}
