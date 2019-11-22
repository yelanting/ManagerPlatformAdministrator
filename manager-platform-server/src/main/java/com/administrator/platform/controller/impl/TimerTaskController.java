/**
 * @author : 孙留平
 * @since : 2019年8月7日 下午8:16:05
 * @see:
 */
package com.administrator.platform.controller.impl;

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

import com.administrator.platform.model.TimerTask;
import com.administrator.platform.service.TimerTaskService;
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
@RequestMapping("/timerTask")
@Api("定时任务相关API")
public class TimerTaskController {
	private static final Logger logger = LoggerFactory
	        .getLogger(TimerTaskController.class);
	@Autowired
	private TimerTaskService timerTaskService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询定时任务列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		return ResponseData
		        .getSuccessResult(timerTaskService.findAllTimerTaskList());
	}

	@GetMapping(value = "/searchList")
	@ResponseBody
	@ApiOperation(value = "根据任务名称查询定时任务列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		return ResponseData.getSuccessResult(timerTaskService
		        .findTimerTasksByTimerTaskNameLike(searchContent));
	}

	@PostMapping(value = "/addTimerTask")
	@ApiOperation(value = "添加定时任务")
	@ResponseBody
	public ResponseData addTimerTask(@ModelAttribute TimerTask timerTask) {
		logger.info("添加定时任务");
		return ResponseData
		        .getSuccessResult(timerTaskService.addTimerTask(timerTask));
	}

	@PostMapping(value = "/updateTimerTask")
	@ApiOperation(value = "修改定时任务")
	@ResponseBody
	public ResponseData updateTimerTask(@ModelAttribute TimerTask timerTask) {
		return ResponseData
		        .getSuccessResult(timerTaskService.updateTimerTask(timerTask));
	}

	@PostMapping(value = "/deleteTimerTask")
	@ApiOperation(value = "删除定时任务")
	@ResponseBody
	public ResponseData deleteTimerTask(@RequestParam("id") Long id) {
		return ResponseData
		        .getSuccessResult(timerTaskService.deleteTimerTask(id));
	}

	@PostMapping(value = "/deleteTimerTaskInBatch")
	@ApiOperation(value = "批量删除定时任务")
	@ResponseBody
	public ResponseData deleteTimerTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData
		        .getSuccessResult(timerTaskService.deleteTimerTask(ids));
	}

	@PostMapping(value = "/getTimerTaskListForPage")
	@ApiOperation(value = "分页查询定时任务信息")
	@ResponseBody
	public ResponseData getTimerTaskListForPage(
	        @RequestParam("page") Integer page,
	        @RequestParam("size") Integer size) {
		return ResponseData.getSuccessResult(
		        timerTaskService.findTimerTaskByPage(page, size).getContent());
	}

	@PostMapping(value = "/getTimerTaskById")
	@ApiOperation(value = "分页查询定时任务信息")
	@ResponseBody
	public ResponseData getTimerTaskById(@RequestParam("id") Long id) {
		return ResponseData
		        .getSuccessResult(timerTaskService.getTimerTaskById(id));
	}

	@PostMapping(value = "/changeTimerTaskStatus")
	@ApiOperation(value = "变更状态")
	@ResponseBody
	public ResponseData changeTimerTaskStatus(
	        @RequestParam(value = "id", required = true) Long id,
	        @RequestParam(value = "operation", required = true) String operation) {
		return ResponseData.getSuccessResult(
		        timerTaskService.changeTimerTaskStatus(id, operation));
	}

	@PostMapping(value = "/openTimerTaskInBatch")
	@ApiOperation(value = "批量开启定时任务")
	@ResponseBody
	public ResponseData openTimerTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData
		        .getSuccessResult(timerTaskService.openTimerTaskInBatch(ids));
	}

	@PostMapping(value = "/openAllTimerTaskInBatch")
	@ApiOperation(value = "批量开启所有定时任务")
	@ResponseBody
	public ResponseData openAllTimerTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData
		        .getSuccessResult(timerTaskService.openAllTimerTaskInBatch());
	}

	@PostMapping(value = "/closeTimerTaskInBatch")
	@ApiOperation(value = "批量关闭定时任务")
	@ResponseBody
	public ResponseData closeTimerTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData
		        .getSuccessResult(timerTaskService.closeTimerTaskInBatch(ids));
	}

	@PostMapping(value = "/closeAllTimerTaskInBatch")
	@ApiOperation(value = "批量关闭所有定时任务")
	@ResponseBody
	public ResponseData closeAllTimerTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData
		        .getSuccessResult(timerTaskService.closeAllTimerTaskInBatch());
	}
}
