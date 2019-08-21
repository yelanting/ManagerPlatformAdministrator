/**
 * @author : 孙留平
 * @since : 2019年5月10日 下午5:59:08
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

import com.administrator.platform.model.GlobalParam;
import com.administrator.platform.service.GlobalParamService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2019年5月10日 下午5:59:08
 * @see :
 */
@Controller
@Api("全局参数配置相关API")
@RequestMapping("/globalParam")
public class GlobalParamConfigControllerImpl {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalParamConfigControllerImpl.class);
	@Autowired
	private GlobalParamService globalParamService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询全局参数列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
			@ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		List<GlobalParam> globalParamList = globalParamService.getList();
		return ResponseData.getSuccessResult(globalParamList,
				globalParamList.size());
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
	@ApiOperation(value = "根据key名称查询全局参数")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
			@ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
			@RequestParam("searchContent") String searchContent) {
		logger.info("根据Key名称查询全局参数列表");
		List<GlobalParam> globalParamPage = globalParamService
				.searchGlobalParamsBySearchContent(searchContent);
		return ResponseData.getSuccessResult(globalParamPage,
				globalParamPage.size());
	}

	/**
	 * 添加全局参数
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param globalParam
	 */
	@PostMapping(value = "/addGlobalParam")
	@ApiOperation(value = "添加全局参数")
	@ResponseBody
	public ResponseData addGlobalParam(
			@ModelAttribute GlobalParam globalParam) {
		return ResponseData.getSuccessResult(
				globalParamService.addGlobalParam(globalParam));
	}

	/**
	 * 更新全局参数
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param globalParam
	 */
	@PostMapping(value = "/updateGlobalParam")
	@ApiOperation(value = "修改全局参数")
	@ResponseBody
	public ResponseData updateGlobalParam(
			@ModelAttribute GlobalParam globalParam) {
		return ResponseData.getSuccessResult(
				globalParamService.updateGlobalParam(globalParam));
	}

	/**
	 * 删除全局参数-单个
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param id
	 */
	@PostMapping(value = "/deleteGlobalParam")
	@ApiOperation(value = "删除全局参数")
	@ResponseBody
	public ResponseData deleteGlobalParam(@RequestParam("id") Long id) {
		return ResponseData
				.getSuccessResult(globalParamService.deleteGlobalParam(id));
	}

	/**
	 * 批量删除全局参数
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteGlobalParamInBatch")
	@ApiOperation(value = "批量删除全局参数")
	@ResponseBody
	public ResponseData deleteGlobalParamInBatch(
			@RequestParam("ids[]") Long[] ids) {
		globalParamService.deleteGlobalParam(ids);
		return ResponseData.getSuccessResult(ids);
	}
}