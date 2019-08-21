/**
 * @author : 孙留平
 * @since : 2019年5月9日 上午11:25:23
 * @see:
 */
package com.administrator.platform.controller.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.dto.YunXiaoConnectAutoTestTaskDTO;
import com.administrator.platform.model.yunxiao.YunXiaoResponseData;
import com.administrator.platform.service.YunXiaoConnectAutoTestTaskService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author : Administrator
 * @since : 2019年5月9日 上午11:25:23
 * @see :
 */

@Controller
@RequestMapping("/yunXiaoConnect")
@Api("云效接入相关api")
public class YunXiaoConnectAutoTestTaskControllerImpl {

	@Autowired
	private YunXiaoConnectAutoTestTaskService yunXiaoConnectAutoTestTaskService;

	@GetMapping("/executeAutoTestTask")
	@ResponseBody
	@ApiOperation(value = "云效调起执行任务", notes = "通过云效发起测试件执行，会自动在云效接入平台和jenkins平台创建或更新相关的任务")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "testSuiteRunId", value = "云效测试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "suiteId", value = "自动化测试平台试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "callBackUrl", value = "云效回调URL", required = true, dataType = "String") })
	public YunXiaoResponseData executeAutoTestTask(
	        @ModelAttribute YunXiaoConnectAutoTestTaskDTO yunXiaoConnectAutoTestTaskDto) {
		return new YunXiaoResponseData(
		        ResponseData.getSuccessResult(yunXiaoConnectAutoTestTaskService
		                .executeAutoTestTask(yunXiaoConnectAutoTestTaskDto)));
	}

	@GetMapping("/cancelAutoTestTask")
	@ResponseBody
	@ApiOperation(value = "云效取消任务")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "testSuiteRunId", value = "云效测试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "suiteId", value = "自动化测试平台试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "callBackUrl", value = "云效回调URL", required = true, dataType = "String") })
	public YunXiaoResponseData cancelAutoTestTask(
	        @ModelAttribute YunXiaoConnectAutoTestTaskDTO yunXiaoConnectAutoTestTaskDto) {
		return new YunXiaoResponseData(
		        ResponseData.getSuccessResult(yunXiaoConnectAutoTestTaskService
		                .cancelAutoTestTask(yunXiaoConnectAutoTestTaskDto)));
	}

	@GetMapping("/getAutoTestTaskProcess")
	@ResponseBody
	@ApiOperation(value = "获取当前执行进度和日志")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "testSuiteRunId", value = "云效测试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "suiteId", value = "自动化测试平台试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "callBackUrl", value = "云效回调URL", required = true, dataType = "String") })
	public YunXiaoResponseData getAutoTestTaskProcess(
	        @ModelAttribute YunXiaoConnectAutoTestTaskDTO yunXiaoConnectAutoTestTaskDto) {

		return new YunXiaoResponseData(ResponseData.getSuccessResult(
		        yunXiaoConnectAutoTestTaskService.getAutoTestTaskProcess(
		                yunXiaoConnectAutoTestTaskDto)));
	}

	@GetMapping("/getAutoTestTaskExecutionReport")
	@ApiOperation(value = "下载执行产生的报告")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "testSuiteRunId", value = "云效测试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "suiteId", value = "自动化测试平台试件ID", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "callBackUrl", value = "云效回调URL", required = true, dataType = "String") })
	public void getAutoTestTaskExecutionReport(
	        @ModelAttribute YunXiaoConnectAutoTestTaskDTO yunXiaoConnectAutoTestTaskDto,
	        HttpServletResponse httpServletResponse) {
		yunXiaoConnectAutoTestTaskService.getAutoTestTaskExecutionReport(
		        yunXiaoConnectAutoTestTaskDto, httpServletResponse);
	}
}
