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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.ExecutorAgent;
import com.administrator.platform.service.ExecutorAgentService;
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
@Api("执行机相关API")
@RequestMapping("/executorAgent")
public class ExecutorAgentControllerImpl {

	private static final Logger logger = LoggerFactory
	        .getLogger(ExecutorAgentControllerImpl.class);
	@Autowired
	private ExecutorAgentService executorAgentService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询执行机列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		List<ExecutorAgent> executorAgentList = executorAgentService.getList();
		return ResponseData.getSuccessResult(executorAgentList,
		        executorAgentList.size());
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
		List<ExecutorAgent> executorAgentPage = executorAgentService
		        .searchExecutorAgentsBySearchContent(searchContent);
		return ResponseData.getSuccessResult(executorAgentPage,
		        executorAgentPage.size());
	}
}
