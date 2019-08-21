/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:47:53
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.controller.BaseController;
import com.administrator.platform.model.AutoTestTask;
import com.administrator.platform.service.TaskService;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:47:53
 * @see :
 */
@Controller
@RequestMapping("/task")
@Api("任务相关API")
public class TaskControllerImpl implements BaseController {
	@Autowired
	private TaskService autoTestTaskService;

	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询任务列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		List<AutoTestTask> autoTestAutoTestTaskList = autoTestTaskService
		        .getList();
		return ResponseData.getSuccessResult(autoTestAutoTestTaskList,
		        autoTestAutoTestTaskList.size());
	}

	/**
	 * 根据任务名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param searchContent
	 * @return
	 */
	@GetMapping(value = "/searchList")
	@ResponseBody
	@ApiOperation(value = "根据key名称查询任务")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		List<AutoTestTask> autoTestTaskPage = autoTestTaskService
		        .searchAutoTestTasksBySearchContent(searchContent);
		return ResponseData.getSuccessResult(autoTestTaskPage,
		        autoTestTaskPage.size());
	}

	/**
	 * 添加任务
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param autoTestTask
	 */
	@PostMapping(value = "/addTask")
	@ApiOperation(value = "添加任务")
	@ResponseBody
	public ResponseData addAutoTestTask(
	        @ModelAttribute AutoTestTask autoTestTask) {
		return ResponseData.getSuccessResult(
		        autoTestTaskService.addAutoTestTask(autoTestTask));
	}

	/**
	 * 更新任务
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param autoTestTask
	 */
	@PostMapping(value = "/updateTask")
	@ApiOperation(value = "修改任务")
	@ResponseBody
	public ResponseData updateAutoTestTask(
	        @ModelAttribute AutoTestTask autoTestTask) {
		return ResponseData.getSuccessResult(
		        autoTestTaskService.updateAutoTestTask(autoTestTask));
	}

	/**
	 * 删除任务-单个
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param id
	 */
	@PostMapping(value = "/deleteTask")
	@ApiOperation(value = "删除任务")
	@ResponseBody
	public ResponseData deleteAutoTestTask(@RequestParam("id") Long id) {
		return ResponseData
		        .getSuccessResult(autoTestTaskService.deleteAutoTestTask(id));
	}

	/**
	 * 批量删除任务
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteTaskInBatch")
	@ApiOperation(value = "批量删除任务")
	@ResponseBody
	public ResponseData deleteAutoTestTaskInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		autoTestTaskService.deleteAutoTestTask(ids);
		return ResponseData.getSuccessResult(ids);
	}

	/**
	 * 执行任务
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/executeTask")
	@ApiOperation(value = "执行任务")
	@ResponseBody
	public ResponseData executeAutoTestTask(
	        @ModelAttribute AutoTestTask autoTestTask) {
		return ResponseData.getSuccessResult(
		        autoTestTaskService.executeTask(autoTestTask));
	}

	/**
	 * 取消执行任务
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/cancelTask")
	@ApiOperation(value = "取消任务")
	@ResponseBody
	public ResponseData cancelAutoTestTask(
	        @ModelAttribute AutoTestTask autoTestTask) {
		return ResponseData
		        .getSuccessResult(autoTestTaskService.cancelTask(autoTestTask));
	}

	/**
	 * 获取执行进度
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/getExecutionProcess")
	@ApiOperation(value = "获取任务执行信息")
	@ResponseBody
	public ResponseData getExecutionProcess(
	        @ModelAttribute AutoTestTask autoTestTask) {
		return ResponseData.getSuccessResult(
		        autoTestTaskService.getExecutionProcess(autoTestTask));
	}

	/**
	 * 下载测试报告
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/downloadExecutionReport")
	@ApiOperation(value = "下载结果报告")
	public void downloadExecutionReport(
	        @ModelAttribute AutoTestTask autoTestTask,
	        HttpServletRequest httpServletRequest,
	        HttpServletResponse httpServletResponse) {
		autoTestTaskService.downloadExecutionReport(autoTestTask,
		        httpServletResponse);
	}

}
