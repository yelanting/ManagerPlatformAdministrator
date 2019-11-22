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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.yunxiao.ConfigManage;
import com.administrator.platform.service.ConfigManageService;
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
@RequestMapping("/configManage")
@Api("配置管理相关API")
public class ConfigManageController {
	private static final Logger logger = LoggerFactory
	        .getLogger(ConfigManageController.class);
	@Autowired
	private ConfigManageService configManageService;

	/**
	 * 查询配置管理列表，查询所有
	 * 
	 * @param :
	 * @return : ResponseData
	 */
	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询配置管理列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		logger.info("查询配置管理列表");
		List<ConfigManage> configManagePage = configManageService
		        .findAllConfigManageList();
		return ResponseData.getSuccessResult(configManagePage,
		        configManagePage.size());
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
	@ApiOperation(value = "根据项目名称查询配置管理列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		logger.info("根据项目名称查询配置管理列表");
		List<ConfigManage> configManagePage = configManageService
		        .findConfigManagesByNameLike(searchContent);
		return ResponseData.getSuccessResult(configManagePage,
		        configManagePage.size());
	}

	/**
	 * 获取详情
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param configManage
	 */
	@GetMapping(value = "/getDetail/{id}")
	@ApiOperation(value = "修改配置管理")
	@ResponseBody
	public ResponseData getDetail(@PathVariable Long id) {
		return ResponseData
		        .getSuccessResult(configManageService.getConfigManageById(id));
	}

	/**
	 * 配置定时任务，改变定时任务状态
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	@PostMapping("/configTimerTaskAndChangeStatus")
	@ResponseBody
	@ApiOperation("配置定时任务，并尝试开启或者关闭")
	public ResponseData configTimerTaskAndChangeStatus(
	        @RequestParam("scheduledConfigExpress") String cronConfig,
	        @RequestParam("id") Long id,
	        @RequestParam("enabled") boolean enabled) {
		return ResponseData.getSuccessResult(configManageService
		        .configTimerTaskAndChangeStatus(cronConfig, id, enabled));
	}
}
