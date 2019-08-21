/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:05
 * @see:
 */
package com.administrator.platform.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.TimerTaskPolicyService;
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
@RequestMapping("/timerTaskPolicy")
@Api("策略相关API")
public class TimerTaskPolicyController {
	@Autowired
	private TimerTaskPolicyService timerTaskPolicyService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询策略列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.findAllTimerTaskPolicyList());
	}

	@GetMapping(value = "/searchList")
	@ResponseBody
	@ApiOperation(value = "根据种类名称查询策略列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		return ResponseData.getSuccessResult(timerTaskPolicyService
		        .findTimerTaskPoliciesByCnameLike(searchContent));
	}

	@PostMapping(value = "/addTimerTaskPolicy")
	@ApiOperation(value = "添加策略")
	@ResponseBody
	public ResponseData addTimerTaskPolicy(
	        @ModelAttribute TimerTaskPolicy timerTaskPolicy) {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.addTimerTaskPolicy(timerTaskPolicy));
	}

	@PostMapping(value = "/updateTimerTaskPolicy")
	@ApiOperation(value = "修改策略")
	@ResponseBody
	public ResponseData updateTimerTaskPolicy(
	        @ModelAttribute TimerTaskPolicy timerTaskPolicy) {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.updateTimerTaskPolicy(timerTaskPolicy));
	}

	@PostMapping(value = "/deleteTimerTaskPolicy")
	@ApiOperation(value = "删除策略")
	@ResponseBody
	public ResponseData deleteTimerTaskPolicy(@RequestParam("id") Long id) {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.deleteTimerTaskPolicy(id));
	}

	@PostMapping(value = "/deleteTimerTaskPolicyInBatch")
	@ApiOperation(value = "批量删除策略")
	@ResponseBody
	public ResponseData deleteTimerTaskPolicyInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.deleteTimerTaskPolicy(ids));
	}

	@PostMapping(value = "/getTimerTaskPolicyListForPage")
	@ApiOperation(value = "分页查询策略信息")
	@ResponseBody
	public ResponseData getTimerTaskPolicyListForPage(
	        @RequestParam("page") Integer page,
	        @RequestParam("size") Integer size) {
		return ResponseData.getSuccessResult(timerTaskPolicyService
		        .findTimerTaskPolicyByPage(page, size).getContent());
	}

	@PostMapping(value = "/getTimerTaskPolicyById")
	@ApiOperation(value = "查询策略信息详情")
	@ResponseBody
	public ResponseData getTimerTaskPolicyById(@RequestParam("id") Long id) {
		return ResponseData.getSuccessResult(
		        timerTaskPolicyService.getTimerTaskPolicyById(id));
	}
}
