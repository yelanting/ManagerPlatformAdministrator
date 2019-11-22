/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午8:58:17
 * @see:
 */
package com.administrator.platform.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.service.JavaParserService;
import com.administrator.platform.vo.JavaParserRecordDTO;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午8:58:17
 * @see :
 */
@RequestMapping("/javaParserRecord")
@Controller
public class JavaParserController {
	@Autowired
	private JavaParserService javaParserRecordService;

	/**
	 * 查询java文件解析列表，查询所有
	 * 
	 * @param :
	 * @return : ResponseData
	 */
	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询java文件解析列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		return ResponseData.getSuccessResult(
		        javaParserRecordService.findAllJavaParserRecordList());
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
	@ApiOperation(value = "根据项目名称查询java文件解析列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {

		return ResponseData.getSuccessResult(javaParserRecordService
		        .findJavaParserRecordesByProjectNameLike(searchContent));
	}

	/**
	 * 添加java文件解析
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param javaParserRecord
	 */
	@PostMapping(value = "/addJavaParserRecord")
	@ApiOperation(value = "添加java文件解析")
	@ResponseBody
	public ResponseData addJavaParserRecord(
	        @ModelAttribute JavaParserRecordDTO javaParserRecord) {
		return ResponseData.getSuccessResult(
		        javaParserRecordService.addJavaParserRecord(javaParserRecord));
	}

	/**
	 * 更新java文件解析
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param javaParserRecord
	 */
	@PostMapping(value = "/updateJavaParserRecord")
	@ApiOperation(value = "修改java文件解析")
	@ResponseBody
	public ResponseData updateJavaParserRecord(
	        @ModelAttribute JavaParserRecordDTO javaParserRecord) {
		return ResponseData.getSuccessResult(javaParserRecordService
		        .updateJavaParserRecord(javaParserRecord));
	}

	/**
	 * 获取详情
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param javaParserRecord
	 */
	@GetMapping(value = "/getDetail/{id}")
	@ApiOperation(value = "修改java文件解析")
	@ResponseBody
	public ResponseData getDetail(@PathVariable Long id) {
		return ResponseData.getSuccessResult(
		        javaParserRecordService.getJavaParserRecordById(id));
	}

	/**
	 * 删除java文件解析-单个
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param id
	 */
	@PostMapping(value = "/deleteJavaParserRecord")
	@ApiOperation(value = "删除java文件解析")
	@ResponseBody
	public ResponseData deleteJavaParserRecord(@RequestParam("id") Long id) {
		return ResponseData.getSuccessResult(
		        javaParserRecordService.deleteJavaParserRecord(id));
	}

	/**
	 * 批量删除java文件解析
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteJavaParserRecordInBatch")
	@ApiOperation(value = "批量删除java文件解析")
	@ResponseBody
	public ResponseData deleteJavaParserRecordInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		javaParserRecordService.deleteJavaParserRecord(ids);
		return ResponseData.getSuccessResult(ids);
	}

	/**
	 * 分页查询java文件解析
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping(value = "/getJavaParserRecordListForPage")
	@ApiOperation(value = "分页查询java文件解析信息")
	@ResponseBody
	public ResponseData getJavaParserRecordListForPage(
	        @RequestParam("page") Integer page,
	        @RequestParam("size") Integer size) {
		return ResponseData.getSuccessResult(
		        javaParserRecordService.findJavaParserRecordByPage(page, size));
	}

	/**
	 * 重置覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @return
	 */
	@PostMapping(value = "/resetJavaParserRecordData")
	@ApiOperation(value = "重置java文件解析数据信息")
	@ResponseBody
	public ResponseData resetJavaParserRecordData(
	        @ModelAttribute JavaParserRecordDTO javaParserRecord) {
		return ResponseData.getSuccessResult(javaParserRecordService
		        .resetJavaParserRecordData(javaParserRecord));
	}

	/**
	 * 重置覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @return
	 */
	@PostMapping(value = "/createRequestMappingSource")
	@ApiOperation(value = "重置java文件解析数据信息")
	@ResponseBody
	public ResponseData createRequestMappingSource(HttpServletRequest request,
	        @ModelAttribute JavaParserRecordDTO javaParserRecord) {
		return ResponseData.getSuccessResult(javaParserRecordService
		        .createRequestMappingSource(request, javaParserRecord));
	}

}
