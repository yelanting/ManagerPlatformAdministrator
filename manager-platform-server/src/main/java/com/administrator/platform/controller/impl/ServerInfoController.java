/**
 * @author : 孙留平
 * @since : 2019年7月31日 17:40:45
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.model.ServerInfo;
import com.administrator.platform.service.ServerInfoService;
import com.administrator.platform.vo.ResponseData;
import com.administrator.platform.vo.ServerInfoDTO;
import com.administrator.platform.vo.ShellExecuteResultDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2019年7月31日 17:40:39
 * @see :
 */
@Controller
@RequestMapping("/serverInfo")
@Api("服务器相关API")
public class ServerInfoController {
	private static final Logger logger = LoggerFactory
	        .getLogger(ServerInfoController.class);
	@Autowired
	private ServerInfoService serverInfoService;

	/**
	 * 查询服务器列表，查询所有
	 * 
	 * @param :
	 * @return : ResponseData
	 */
	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询服务器列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		logger.info("查询服务器列表");
		List<ServerInfo> serverInfoPage = serverInfoService
		        .findAllServerInfoList();
		return ResponseData.getSuccessResult(serverInfoPage,
		        serverInfoPage.size());
	}

	/**
	 * 根据服务器IP查询
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param searchContent
	 * @return
	 */
	@GetMapping(value = "/searchList")
	@ResponseBody
	@ApiOperation(value = "根据服务器IP查询服务器列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		logger.info("根据服务器IP查询服务器列表");
		List<ServerInfo> serverInfoPage = serverInfoService
		        .findServerInfosByServerIpLike(searchContent);
		return ResponseData.getSuccessResult(serverInfoPage,
		        serverInfoPage.size());
	}

	/**
	 * 添加服务器
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param serverInfo
	 */
	@PostMapping(value = "/addServerInfo")
	@ApiOperation(value = "添加服务器")
	@ResponseBody
	public ResponseData addServerInfo(
	        @ModelAttribute ServerInfoDTO serverInfo) {
		return ResponseData
		        .getSuccessResult(serverInfoService.addServerInfo(serverInfo));
	}

	/**
	 * 更新服务器
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param serverInfo
	 */
	@PostMapping(value = "/updateServerInfo")
	@ApiOperation(value = "修改服务器")
	@ResponseBody
	public ResponseData updateServerInfo(
	        @ModelAttribute ServerInfoDTO serverInfo) {
		return ResponseData.getSuccessResult(
		        serverInfoService.updateServerInfo(serverInfo));
	}

	/**
	 * 获取详情
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param serverInfo
	 */
	@GetMapping(value = "/getDetail/{id}")
	@ApiOperation(value = "修改服务器")
	@ResponseBody
	public ResponseData getDetail(@PathVariable Long id) {
		return ResponseData
		        .getSuccessResult(serverInfoService.getServerInfoById(id));
	}

	/**
	 * 删除服务器-单个
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param id
	 */
	@PostMapping(value = "/deleteServerInfo")
	@ApiOperation(value = "删除服务器")
	@ResponseBody
	public ResponseData deleteServerInfo(@RequestParam("id") Long id) {
		return ResponseData
		        .getSuccessResult(serverInfoService.deleteServerInfo(id));
	}

	/**
	 * 批量删除服务器
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteServerInfoInBatch")
	@ApiOperation(value = "批量删除服务器")
	@ResponseBody
	public ResponseData deleteServerInfoInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		serverInfoService.deleteServerInfo(ids);
		return ResponseData.getSuccessResult(ids);
	}

	/**
	 * 执行shell
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/executeShell")
	@ApiOperation(value = "执行命令")
	@ResponseBody
	public ResponseData executeShell(@ModelAttribute ServerInfoDTO serverInfo,
	        @RequestParam("executeShells") String executeShell) {
		return ResponseData.getSuccessResult(
		        new ShellExecuteResultDTO[] { serverInfoService
		                .executeShellWithResponse(serverInfo, executeShell) });
	}

	/**
	 * 执行shell
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/executeShellInBatch")
	@ApiOperation(value = "批量执行命令")
	@ResponseBody
	public ResponseData executeShellInBatch(@RequestParam("ids[]") Long[] ids,
	        @RequestParam("executeShells") String executeShell) {
		return ResponseData.getSuccessResult(serverInfoService
		        .executeShellInBatchWithResponse(ids, executeShell));
	}

	/**
	 * 检测服务器是否可用
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/checkServerCanBeConnected")
	@ApiOperation(value = "检查服务器是否可用")
	@ResponseBody
	public ResponseData checkServerCanBeConnected(
	        @ModelAttribute ServerInfoDTO serverInfo) {
		return ResponseData
		        .getSuccessResult(serverInfoService.checkServerCanBeConnected(
		                new Long[] { serverInfo.getId() })[0]);
	}

	/**
	 * 执行shell
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/checkServerCanBeConnectedInBatch")
	@ApiOperation(value = "批量检测服务器是否可连接")
	@ResponseBody
	public ResponseData executeShellInBatch(@RequestParam("ids[]") Long[] ids) {
		return ResponseData.getSuccessResult(
		        serverInfoService.checkServerCanBeConnected(ids));
	}
}
