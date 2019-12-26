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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.yunxiao.PipeLine;
import com.administrator.platform.service.PipeLineService;
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
// @Controller
@RequestMapping("/pipeLine")
@Api("pipeLine流水线相关API")
public class PipeLineController {
	private static final Logger logger = LoggerFactory
	        .getLogger(PipeLineController.class);
	@Autowired
	private PipeLineService pipeLineService;

	/**
	 * 查询pipeLine流水线列表，查询所有
	 * 
	 * @param :
	 * @return : ResponseData
	 */
	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询pipeLine流水线列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		logger.info("查询pipeLine流水线列表");
		List<PipeLine> pipeLinePage = pipeLineService.findAllPipeLineList();
		return ResponseData.getSuccessResult(pipeLinePage, pipeLinePage.size());
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
	@ApiOperation(value = "根据项目名称查询pipeLine流水线列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		logger.info("根据项目名称查询pipeLine流水线列表");
		List<PipeLine> pipeLinePage = pipeLineService
		        .findPipeLinesByNameLike(searchContent);
		return ResponseData.getSuccessResult(pipeLinePage, pipeLinePage.size());
	}

	/**
	 * 获取详情
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param pipeLine
	 */
	@GetMapping(value = "/getDetail/{id}")
	@ApiOperation(value = "修改pipeLine流水线")
	@ResponseBody
	public ResponseData getDetail(@PathVariable Long id) {
		return ResponseData
		        .getSuccessResult(pipeLineService.getPipeLineById(id));
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
		return ResponseData.getSuccessResult(pipeLineService
		        .configTimerTaskAndChangeStatus(cronConfig, id, enabled));
	}
}
