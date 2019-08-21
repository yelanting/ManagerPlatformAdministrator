/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:05
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.service.CodeCoverageService;
import com.administrator.platform.tools.codebuild.entity.BuildType;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.TempFileUtil;
import com.administrator.platform.vo.CodeCoverageDTO;
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
@RequestMapping("/codeCoverage")
@Api("代码覆盖率相关API")
public class CodeCoverageController {
	private static final Logger logger = LoggerFactory
	        .getLogger(CodeCoverageController.class);
	@Autowired
	private CodeCoverageService codeCoverageService;

	/**
	 * 固定错误信息
	 */
	private static final String UPLOAD_ERROR_MESSAGE = "上传文件失败!";

	/**
	 * 查询代码覆盖率列表，查询所有
	 * 
	 * @param :
	 * @return : ResponseData
	 */
	@GetMapping(value = "/getList")
	@ResponseBody
	@ApiOperation(value = "查询代码覆盖率列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData getList() {
		logger.info("查询代码覆盖率列表");
		List<CodeCoverage> codeCoveragePage = codeCoverageService
		        .findAllCodeCoverageList();
		return ResponseData.getSuccessResult(codeCoveragePage,
		        codeCoveragePage.size());
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
	@ApiOperation(value = "根据项目名称查询代码覆盖率列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没有填好"),
	        @ApiResponse(code = 404, message = "页面查找失败，路径不对") })
	public ResponseData searchList(
	        @RequestParam("searchContent") String searchContent) {
		logger.info("根据项目名称查询代码覆盖率列表");
		List<CodeCoverage> codeCoveragePage = codeCoverageService
		        .findCodeCoverageesByProjectNameLike(searchContent);
		return ResponseData.getSuccessResult(codeCoveragePage,
		        codeCoveragePage.size());
	}

	/**
	 * 添加代码覆盖率
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param codeCoverage
	 */
	@PostMapping(value = "/addCodeCoverage")
	@ApiOperation(value = "添加代码覆盖率")
	@ResponseBody
	public ResponseData addCodeCoverage(
	        @ModelAttribute CodeCoverageDTO codeCoverage) {
		return ResponseData.getSuccessResult(
		        codeCoverageService.addCodeCoverage(codeCoverage));
	}

	/**
	 * 更新代码覆盖率
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param codeCoverage
	 */
	@PostMapping(value = "/updateCodeCoverage")
	@ApiOperation(value = "修改代码覆盖率")
	@ResponseBody
	public ResponseData updateCodeCoverage(
	        @ModelAttribute CodeCoverageDTO codeCoverage) {
		return ResponseData.getSuccessResult(
		        codeCoverageService.updateCodeCoverage(codeCoverage));
	}

	/**
	 * 获取详情
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param codeCoverage
	 */
	@GetMapping(value = "/getDetail/{id}")
	@ApiOperation(value = "修改代码覆盖率")
	@ResponseBody
	public ResponseData getDetail(@PathVariable Long id) {
		return ResponseData
		        .getSuccessResult(codeCoverageService.getCodeCoverageById(id));
	}

	/**
	 * 删除代码覆盖率-单个
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param id
	 */
	@PostMapping(value = "/deleteCodeCoverage")
	@ApiOperation(value = "删除代码覆盖率")
	@ResponseBody
	public ResponseData deleteCodeCoverage(@RequestParam("id") Long id) {
		return ResponseData
		        .getSuccessResult(codeCoverageService.deleteCodeCoverage(id));
	}

	/**
	 * 批量删除代码覆盖率
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/deleteCodeCoverageInBatch")
	@ApiOperation(value = "批量删除代码覆盖率")
	@ResponseBody
	public ResponseData deleteCodeCoverageInBatch(
	        @RequestParam("ids[]") Long[] ids) {
		codeCoverageService.deleteCodeCoverage(ids);
		return ResponseData.getSuccessResult(ids);
	}

	/**
	 * 分页查询代码覆盖率
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping(value = "/getCodeCoverageListForPage")
	@ApiOperation(value = "分页查询代码覆盖率信息")
	@ResponseBody
	public ResponseData getCodeCoverageListForPage(
	        @RequestParam("page") Integer page,
	        @RequestParam("size") Integer size) {
		Page<CodeCoverage> thisCodeCoveragePage = codeCoverageService
		        .findCodeCoverageByPage(page, size);
		return ResponseData.getSuccessResult(thisCodeCoveragePage.getContent(),
		        thisCodeCoveragePage.getContent().size());
	}

	/**
	 * 生成全量代码覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @return
	 */
	@PostMapping(value = "/createAllCodeCoverageData")
	@ApiOperation(value = "生成全量代码覆盖率信息")
	@ResponseBody
	public ResponseData createAllCodeCoverageData(
	        @ModelAttribute CodeCoverageDTO codeCoverage,
	        HttpServletRequest request) {
		return ResponseData.getSuccessResult(codeCoverageService
		        .createAllCodeCoverageData(codeCoverage, request));
	}

	/**
	 * 生成增量代码覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @return
	 */
	@PostMapping(value = "/createIncrementCodeCoverageData")
	@ApiOperation(value = "生成增量代码覆盖率信息")
	@ResponseBody
	public ResponseData createIncrementCodeCoverageData(
	        @ModelAttribute CodeCoverageDTO codeCoverage,
	        HttpServletRequest request) {
		return ResponseData.getSuccessResult(codeCoverageService
		        .createIncrementCodeCoverageData(codeCoverage, request));
	}

	/**
	 * 重置覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : ResponseData
	 * @return
	 */
	@PostMapping(value = "/resetCodeCoverageData")
	@ApiOperation(value = "重置代码覆盖率数据信息")
	@ResponseBody
	public ResponseData resetCodeCoverageData(
	        @ModelAttribute CodeCoverageDTO codeCoverage) {
		return ResponseData.getSuccessResult(
		        codeCoverageService.resetCodeCoverageData(codeCoverage));
	}

	@PostMapping("/uploadExecFile")
	@ResponseBody
	@ApiOperation("上传app客户端的覆盖率执行文件*.ec")
	public ResponseData uploadExecFile(
	        @RequestParam("uploadExecFile") MultipartFile multipartFile,
	        @ModelAttribute CodeCoverageDTO codeCoverageDTO,
	        HttpServletRequest request) {
		try {
			logger.debug("上传文件:{},Dto：{}", multipartFile, codeCoverageDTO);
			if (BuildType.GRADLE != codeCoverageDTO.getBuildType()) {
				throw new BusinessValidationException("非GRADLE项目不支持此操作");
			}

			String finalFolder = TempFileUtil
			        .getExecFileSavePath(codeCoverageDTO);

			return ResponseData.getSuccessResult(
			        TempFileUtil.multipartFileTransferAndReturnValue(request,
			                multipartFile, finalFolder, null));

		} catch (Exception e) {
			logger.error("{}:{}", UPLOAD_ERROR_MESSAGE, e.getMessage());
			throw new BusinessValidationException(UPLOAD_ERROR_MESSAGE);
		}
	}

	@PostMapping("/uploadClasses")
	@ResponseBody
	@ApiOperation("上传需要的class文件，需要压缩包")
	public ResponseData uploadClassesFile(
	        @RequestParam("uploadClasses") MultipartFile multipartFile,
	        @ModelAttribute CodeCoverageDTO codeCoverageDTO,
	        HttpServletRequest request) {
		try {
			logger.debug("上传文件:{},Dto：{}", multipartFile, codeCoverageDTO);

			if (BuildType.GRADLE == codeCoverageDTO.getBuildType()) {
				throw new BusinessValidationException("GRADLE项目不支持此操作");
			}

			String finalFolder = TempFileUtil
			        .getClassesFileSavePath(codeCoverageDTO);

			return ResponseData.getSuccessResult(
			        TempFileUtil.multipartFileTransferAndReturnValue(request,
			                multipartFile, finalFolder,
			                JacocoDefine.DEFAULT_CLASSES_UPLOAD_FILE_NAME));

		} catch (Exception e) {
			logger.error("{}:{}", UPLOAD_ERROR_MESSAGE, e.getMessage());
			throw new BusinessValidationException(UPLOAD_ERROR_MESSAGE);
		}
	}

	@PostMapping("/uploadWholeProject")
	@ResponseBody
	@ApiOperation("上传的源码和class文件，需要压缩包")
	public ResponseData uploadSrcAndClassFile(
	        @RequestParam("uploadWholeProject") MultipartFile multipartFile,
	        @ModelAttribute CodeCoverageDTO codeCoverageDTO,
	        HttpServletRequest request) {
		try {
			logger.debug("上传文件:{},Dto：{}", multipartFile, codeCoverageDTO);
			if (BuildType.GRADLE == codeCoverageDTO.getBuildType()) {
				throw new BusinessValidationException("GRADLE项目不支持此操作");
			}

			String finalFolder = TempFileUtil
			        .getClassesFileSavePath(codeCoverageDTO);

			return ResponseData.getSuccessResult(
			        TempFileUtil.multipartFileTransferAndReturnValue(request,
			                multipartFile, finalFolder,
			                JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME));

		} catch (Exception e) {
			logger.error("{}:{}", UPLOAD_ERROR_MESSAGE, e.getMessage());
			throw new BusinessValidationException(UPLOAD_ERROR_MESSAGE);
		}
	}

	@PostMapping("/uploadWholeOldProject")
	@ResponseBody
	@ApiOperation("上传的源码和class文件，需要压缩包")
	public ResponseData uploadOldSrcFile(
	        @RequestParam("uploadWholeOldProject") MultipartFile multipartFile,
	        @ModelAttribute CodeCoverageDTO codeCoverageDTO,
	        HttpServletRequest request) {
		try {
			logger.debug("上传文件:{},Dto：{}", multipartFile, codeCoverageDTO);
			if (BuildType.GRADLE == codeCoverageDTO.getBuildType()) {
				throw new BusinessValidationException("GRADLE项目不支持此操作");
			}

			String finalFolder = TempFileUtil
			        .getClassesFileSavePath(codeCoverageDTO);

			return ResponseData.getSuccessResult(
			        TempFileUtil.multipartFileTransferAndReturnValue(request,
			                multipartFile, finalFolder,
			                JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME));

		} catch (Exception e) {
			logger.error("{}:{}", UPLOAD_ERROR_MESSAGE, e.getMessage());
			throw new BusinessValidationException(UPLOAD_ERROR_MESSAGE);
		}
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
		return ResponseData.getSuccessResult(codeCoverageService
		        .configTimerTaskAndChangeStatus(cronConfig, id, enabled));
	}
}
