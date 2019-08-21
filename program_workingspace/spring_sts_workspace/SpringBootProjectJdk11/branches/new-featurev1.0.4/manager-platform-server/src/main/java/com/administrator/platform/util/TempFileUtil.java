/**
 * @author : 孙留平
 * @since : 2019年1月19日 下午4:36:54
 * @see:
 */
package com.administrator.platform.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;
import com.administrator.platform.util.define.OperationSystemEnum;
import com.alibaba.fastjson.JSONObject;

/**
 * @author : Administrator
 * @since : 2019年1月19日 下午4:36:54
 * @see : 临时文件工具类
 */
public class TempFileUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(TempFileUtil.class);

	/**
	 * 上传文件路径名称
	 */
	public static final String COMMON_UPLOAD_FILE_FOLDER_NAME = "uploadFile";

	/**
	 * 上传文件的路径-跟系统挂钩
	 */
	public static final String COMMON_UPLOAD_FILE_PATH = getDefaultCommonUploadFilePath()
	        + File.separator + COMMON_UPLOAD_FILE_FOLDER_NAME + File.separator;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
	        "yyyy/MM/dd/");

	/**
	 * 
	 */
	private static final String SOURCE_URL_KEY = "sourceFileUrl";

	private static final String TARGET_URL_KEY = "targetFileUrl";

	private TempFileUtil() {

	}

	/**
	 * 生成临时文件名称
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fileName
	 * @return
	 */
	public static String createTempFileName(String fileName) {
		String newName = UUID.randomUUID().toString() + "-" + fileName;
		logger.debug("当前文件的临时存放文件:{}", newName);
		return newName;
	}

	/**
	 * 从请求中获取上传文件路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param request
	 * @return
	 */
	public static String getRequestUploadFilePath(HttpServletRequest request) {
		String realPath = COMMON_UPLOAD_FILE_PATH;
		logger.debug("上传文件存放路径为:{}", realPath);
		return realPath;
	}

	/**
	 * 生成标准化目录
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	public static String createFormatString() {
		String format = simpleDateFormat.format(new Date());
		return format;
	}

	/**
	 * 检查并生成临时文件
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @param request
	 * @return
	 */
	public static File checkAndCreateTempFilePath(HttpServletRequest request) {
		String realPath = getRequestUploadFilePath(request);
		File folder = new File(realPath, createFormatString());
		logger.debug("当前存放临文件时路径为:{},{}", folder.getName(),
		        folder.getAbsolutePath());
		return checkAndCreateTempFilePath(folder.getAbsolutePath());
	}

	/**
	 * 检查并生成临时文件
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @param request
	 * @return
	 */
	public static File checkAndCreateTempFilePath(String filePath) {
		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdir();
		}

		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
		return folder;
	}

	/**
	 * 转储文件
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param multipartFile
	 * @param request
	 * @return
	 */
	public static String multipartFileTransfer(MultipartFile multipartFile,
	        HttpServletRequest request) {
		File folder = checkAndCreateTempFilePath(request);
		String newName = createTempFileName(
		        multipartFile.getOriginalFilename());

		return multipartFileTransfer(multipartFile, folder.getAbsolutePath(),
		        newName);
	}

	/**
	 * 转储文件
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param multipartFile
	 * @param request
	 * @return
	 */
	public static String multipartFileTransfer(MultipartFile multipartFile,
	        String foldePath, String saveFileName) {
		File folder = checkAndCreateTempFilePath(foldePath);

		if (StringUtil.isEmpty(saveFileName)) {
			saveFileName = multipartFile.getOriginalFilename();
		}

		File finalSavePath = new File(folder, saveFileName);
		try {

			if (finalSavePath.exists()) {
				logger.debug("文件已经存在，删除旧文件");
				FileUtil.forceDeleteDirectory(finalSavePath);
			}

			multipartFile.transferTo(finalSavePath);

			return finalSavePath.getAbsolutePath();
		} catch (IllegalStateException e) {
			logger.error("文件转换异常:{}", e.getMessage());
			throw new BusinessValidationException("上传文件转储失败");
		} catch (IOException e) {
			logger.error("上传文件转储失败，出现io异常:{}", e.getMessage());
			throw new BusinessValidationException("上传文件转储失败，出现io异常");
		}
	}

	/**
	 * 转储之后返回存储信息
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param multipartFile
	 * @param request
	 * @return
	 */
	public static JSONObject multipartFileTransferAndReturnValue(
	        HttpServletRequest request, MultipartFile multipartFile,
	        String folderPath, String saveFileName) {
		String finalSourceFilePath = null;
		// *如果这两个都是空的，则使用默认传输方式
		if (StringUtil.isEmpty(folderPath)
		        && StringUtil.isEmpty(saveFileName)) {
			finalSourceFilePath = multipartFileTransfer(multipartFile, request);
		} else {
			finalSourceFilePath = multipartFileTransfer(multipartFile,
			        folderPath, saveFileName);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SOURCE_URL_KEY, finalSourceFilePath);
		jsonObject.put(TARGET_URL_KEY,
		        getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
		                request, finalSourceFilePath));
		return jsonObject;
	}

	/**
	 * 获取文件访问url
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param request
	 * @return
	 */
	public static String getFormattedRequestUrl(HttpServletRequest request,
	        String newName) {
		String finalAccessUrl = request.getScheme() + "://"
		        + request.getServerName() + ":" + request.getServerPort()
		        + COMMON_UPLOAD_FILE_PATH + createFormatString() + newName;

		return finalAccessUrl;
	}

	/**
	 * 把当前本地路径，转换成可以访问的url路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param request
	 * @param fullPath
	 * @return
	 */
	public static String getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
	        HttpServletRequest request, String fullPath) {

		String defaultTempBaseFolder = getDefaultTempBaseFolder();
		String fullPathAfterReplace = fullPath.replace(defaultTempBaseFolder,
		        "");
		logger.debug("把本地全路径:{},去掉固定路径:{},换为相对路径结果为:{},", fullPath,
		        defaultTempBaseFolder, fullPathAfterReplace);
		String finalAccessUrl = request.getScheme() + "://"
		        + request.getServerName() + ":" + request.getServerPort() + "/"
		        + fullPathAfterReplace;
		logger.debug("最终生成的可访问的url为:{}", finalAccessUrl);
		return finalAccessUrl;
	}

	/**
	 * 根据当前操作系统获取自定义的基础文件夹
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	public static String getDefaultCommonUploadFilePath() {
		if (Util.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS) {
			return JacocoDefine.WINDOWS_SELFDEFINED_BASE_FOLDER;
		} else {
			return JacocoDefine.LINUX_SELFDEFINED_BASE_FOLDER;
		}
	}

	/**
	 * 获取当前工程启动时的临时文件夹路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	public static String getDefaultTempBaseFolder() {
		if (Util.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS) {
			return JacocoDefine.WINDOWS_SELFDEFINED_BASE_FOLDER;
		} else {
			return JacocoDefine.LINUX_SELFDEFINED_BASE_FOLDER;
		}
	}

	/**
	 * 获取EXEC,存放路径,
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param codeCoverageDTO
	 * @return
	 */
	public static String getExecFileSavePath(CodeCoverage codeCoverage) {
		String folderName = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage).getName();

		File execParentFolder = new File(COMMON_UPLOAD_FILE_PATH,
		        JacocoDefine.DEFAULT_JACOCO_APP_EXEC_UPLOAD_FOLDER);

		String finalFolder = new File(execParentFolder, folderName)
		        .getAbsolutePath();

		logger.debug("exec存放位置为:{}", finalFolder);
		return finalFolder;
	}

	/**
	 * 获取Class文件,存放路径,
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param codeCoverageDTO
	 * @return
	 */
	public static String getClassesFileSavePath(CodeCoverage codeCoverage) {
		String folderName = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage).getName();

		File execParentFolder = new File(COMMON_UPLOAD_FILE_PATH,
		        JacocoDefine.DEFAULT_CLASSES_UPLOAD_FOLDER);

		String finalFolder = new File(execParentFolder, folderName)
		        .getAbsolutePath();

		logger.debug("classes存放位置为:{}", finalFolder);
		return finalFolder;
	}
}
