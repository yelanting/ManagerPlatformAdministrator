/**
 * @author : 孙留平
 * @since : 2019年8月7日 下午8:16:05
 * @see:
 */
package com.administrator.platform.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.service.TimerTaskMonitorService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2019年8月7日 下午8:16:05
 * @see :
 */
@Controller
@RequestMapping("/timerTaskMonitor")
@Api("定时任务监控相关API")
public class TimerTaskMonitorController {
	@Autowired
	private TimerTaskMonitorService timerTaskMonitorService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询定时任务监控列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		return ResponseData.getSuccessResult(
		        timerTaskMonitorService.findAllTimerTaskMonitorList());
	}

	@GetMapping(value = "/searchList")
	@ResponseBody
	@ApiOperation(value = "根据任务名称查询定时任务监控列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		return ResponseData.getSuccessResult(timerTaskMonitorService
		        .findTimerTaskMonitorsByTimerTaskName(searchContent));
	}

	@GetMapping(value = "/deleteAll")
	@ResponseBody
	@ApiOperation(value = "一键清除所有任务监控")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData deleteAll() {
		return ResponseData.getSuccessResult(
		        timerTaskMonitorService.deleteAllTimerTaskMonitors());
	}
}
