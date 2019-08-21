/**
 * @author : 孙留平
 * @since : 2019年3月13日 下午2:28:50
 * @see:
 */
package com.administrator.platform.tools.codebuild.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.GradleProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.tools.codebuild.intf.BaseCodeBuildGradle;
import com.administrator.platform.tools.jacoco.CodeCoverageFilesAndFoldersDTO;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.TempFileUtil;

/**
 * @author : Administrator
 * @since : 2019年3月13日 下午2:28:50
 * @see :
 */
public class CodeBuildGradleAndroid extends BaseCodeBuildGradle {
	private static final Logger logger = LoggerFactory
	        .getLogger(CodeBuildGradleAndroid.class);

	public CodeBuildGradleAndroid() {

	}

	/**
	 * 渠道名称
	 */
	private String channelName;

	/**
	 * 默认的class文件夹
	 */
	private static final String CLASS_FOLDER = "build" + File.separator
	        + "intermediates" + File.separator + "classes";

	/**
	 * debug的默认目录名称
	 */
	private static final String DEFAULT_DEBUG_FOLDER = "debug";

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * 以一个文件夹初始化为一个gradle工程资源
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverageFilesAndFoldersDTO
	 * @param projectDir
	 * @return
	 */
	@Override
	public CodeCoverageFilesAndFoldersDTO initDefaultCodeCoverageFilesAndFoldersDTO(
	        String projectDir) {
		CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = new CodeCoverageFilesAndFoldersDTO();
		codeCoverageFilesAndFoldersDTO.setProjectDir(new File(projectDir));
		codeCoverageFilesAndFoldersDTO.setSourceDirectory(new File(projectDir,
		        JacocoDefine.DEFAULT_SOURCE_FOLDER_OF_GRADLE));

		File defaultClassFolder = new File(projectDir,
		        CLASS_FOLDER + File.separator + DEFAULT_DEBUG_FOLDER);

		if (StringUtil.isEmpty(this.getChannelName())) {
			codeCoverageFilesAndFoldersDTO
			        .setClassesDirectory(defaultClassFolder);

		} else {
			File channelFolder = new File(projectDir,
			        CLASS_FOLDER + File.separator + this.channelName
			                + File.separator + DEFAULT_DEBUG_FOLDER);

			logger.debug("channelFolder:{}", channelFolder.getAbsolutePath());
			logger.debug("defaultClassFolder:{}",
			        defaultClassFolder.getAbsolutePath());
			if (channelFolder.exists()) {
				codeCoverageFilesAndFoldersDTO
				        .setClassesDirectory(channelFolder);
			} else if (defaultClassFolder.exists()) {
				codeCoverageFilesAndFoldersDTO
				        .setClassesDirectory(defaultClassFolder);
			} else {
				logger.error("{}和{}均不存在", channelFolder.getAbsolutePath(),
				        defaultClassFolder.getAbsolutePath());
				throw new BusinessValidationException(
				        "设置子模块的class文件目录失败,有无渠道的时候均不存在");
			}
		}

		codeCoverageFilesAndFoldersDTO.setExecutionDataFile(new File(projectDir,
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT_ANDROID));

		codeCoverageFilesAndFoldersDTO.setReportDirectory(
		        new File(projectDir, JacocoDefine.CODE_COVERAGE_DATA_FOLDER));

		codeCoverageFilesAndFoldersDTO.setIncrementReportDirectory(new File(
		        projectDir, JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER));

		return codeCoverageFilesAndFoldersDTO;
	}

	/**
	 * 获取子模块
	 * 
	 * @see
	 *      com.administrator.platform.tools.codebuild.CodeBuild#getSubModules(java.
	 *      lang.String)
	 */
	@Override
	public List<String> getSubModules(String projectPath) {

		ProjectConnection projectConnection = getGradleConnector(projectPath)
		        .connect();

		try {
			GradleProject rootGradleProject = projectConnection
			        .getModel(GradleProject.class);

			DomainObjectSet<? extends GradleProject> childrenDomainObjectSet = rootGradleProject
			        .getChildren();

			List<String> subModulePathes = new ArrayList<String>();

			for (int i = 0; i < childrenDomainObjectSet.size(); i++) {
				GradleProject eachSubGradleProject = childrenDomainObjectSet
				        .getAt(i);
				subModulePathes.add(eachSubGradleProject.getName());
			}

			Util.displayListInfo(subModulePathes);
			return subModulePathes;
		} catch (Exception e) {
			logger.error("获取gradle工程的子模块失败,错误信息:{}", e.getMessage());
			throw new BusinessValidationException("获取安卓工程子模块信息失败");
		} finally {
			projectConnection.close();
		}
	}

	/**
	 * 获取所有的源代码文件夹
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getSubSourceFileFolders(java.lang.String)
	 */
	@Override
	public List<String> getSubSourceFileFolders(String projectPath) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
		        projectPath);
		List<String> subSourceFileFolders = new ArrayList<>();
		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
			subSourceFileFolders.add(codeCoverageFilesAndFoldersDTO
			        .getSourceDirectory().getAbsolutePath());
		}
		return subSourceFileFolders;
	}

	/**
	 * 获取class文件夹
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getSubClassesFileFolders(java.lang.String)
	 */
	@Override
	public List<String> getSubClassesFileFolders(String projectPath) {

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
		        projectPath);

		List<String> subClassesFileFolders = new ArrayList<>();

		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
			subClassesFileFolders.add(codeCoverageFilesAndFoldersDTO
			        .getClassesDirectory().getAbsolutePath());
		}
		return subClassesFileFolders;
	}

	/**
	 * 获取某个文件夹下的所有GRADLE工程
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getCodeCoverageFilesAndFoldersDTOs(java.lang.String)
	 */
	@Override
	public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFoldersDTOs(
	        String projectPath) {

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = new ArrayList<>();

		// 如果不是多模块，直接把当前的返回
		if (!isThusProjectMulti(projectPath)) {
			CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = initDefaultCodeCoverageFilesAndFoldersDTO(
			        projectPath);
			codeCoverageFilesAndFoldersDTOs.add(codeCoverageFilesAndFoldersDTO);
			return dealCodeCoverageFilesAndFoldersDTOsWithChannel(
			        codeCoverageFilesAndFoldersDTOs);
		}

		// 如果是多模块，则返回子模块
		List<String> modules = getSubModules(projectPath);

		for (String eachModuleName : modules) {
			File currentModuleProject = new File(projectPath, eachModuleName);
			CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = initDefaultCodeCoverageFilesAndFoldersDTO(
			        currentModuleProject.getAbsolutePath());
			codeCoverageFilesAndFoldersDTOs.add(codeCoverageFilesAndFoldersDTO);
		}
		return dealCodeCoverageFilesAndFoldersDTOsWithChannel(
		        codeCoverageFilesAndFoldersDTOs);
	}

	/**
	 * 用渠道来处理一下
	 * 
	 * @see :
	 * @param :
	 * @return : List<CodeCoverageFilesAndFoldersDTO>
	 * @param codeCoverageFilesAndFoldersDTOs
	 * @return
	 */
	private List<CodeCoverageFilesAndFoldersDTO> dealCodeCoverageFilesAndFoldersDTOsWithChannel(
	        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs) {

		if (StringUtil.isEmpty(this.getChannelName())) {
			logger.debug("没有渠道名称，原样不动");
			Util.displayListInfo(codeCoverageFilesAndFoldersDTOs);
			return codeCoverageFilesAndFoldersDTOs;
		}

		logger.debug("附带有渠道名称，做渠道适配");
		for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
			codeCoverageFilesAndFoldersDTOs.get(i);
		}

		Util.displayListInfo(codeCoverageFilesAndFoldersDTOs);
		return codeCoverageFilesAndFoldersDTOs;
	}

	/**
	 * 检查当前工程是不是一个GradleAndroid工程
	 * 
	 * @see
	 *      com.administrator.platform.tools.codebuild.CodeBuild#isThusKindOfProject(
	 *      java.lang.String)
	 */
	@Override
	public boolean isThusKindOfProject(String projectPath) {
		return checkProjectGradle(projectPath);
	}

	/**
	 * 检查当前工程是不是多模块
	 * 
	 * @see : 以父文件夹下的pom为解析，有module定义则代表是多模块；返回的子模块列表如果是空代表不是多模块
	 * @author Administrator
	 */
	@Override
	public boolean isThusProjectMulti(String projectPath) {
		boolean doesNotHaveSubModules = getSubModules(projectPath).isEmpty();
		return !doesNotHaveSubModules;
	}

	/**
	 * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
	 *      getCodeCoverageFilesAndFoldersDTOs(java.io.File)
	 */
	@Override
	public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFoldersDTOs(
	        File projectPath) {
		return getCodeCoverageFilesAndFoldersDTOs(
		        projectPath.getAbsolutePath());
	}

	/**
	 * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
	 *      getExecutionDataFiles(java.lang.String)
	 */
	@Override
	public List<String> getExecutionDataFiles(String projectPath) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
		        projectPath);

		List<String> executionDataFiles = new ArrayList<>();

		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
			executionDataFiles.add(codeCoverageFilesAndFoldersDTO
			        .getExecutionDataFile().getAbsolutePath());
		}
		return executionDataFiles;
	}

	/**
	 * 获取exec文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 * @param projectDir
	 */
	public void dumpExecDataFromUploadFile(CodeCoverage codeCoverage,
	        String projectDir) {
		File execFile = new File(TempFileUtil.getExecFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_JACOCO_APP_EXEC_FILE_NAME);
		dumpExecDataFromUploadFile(codeCoverage, projectDir, execFile);
	}

	/**
	 * 获取exec文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 * @param projectDir
	 */
	public void dumpExecDataFromUploadFile(CodeCoverage codeCoverage,
	        String projectDir, File execFile) {

		List<CodeCoverageFilesAndFoldersDTO> currentProjectChildren = getCodeCoverageFilesAndFoldersDTOs(
		        projectDir);
		CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = null;
		for (int i = 0; i < currentProjectChildren.size(); i++) {
			codeCoverageFilesAndFoldersDTO = currentProjectChildren.get(i);
			File thisExecFile = new File(
			        codeCoverageFilesAndFoldersDTO.getProjectDir(),
			        JacocoDefine.DEFAULT_JACOCO_APP_EXEC_FILE_NAME);

			if (thisExecFile.exists()) {
				FileUtil.forceDeleteDirectory(thisExecFile);
			}

			FileUtil.copyFile(execFile, thisExecFile);
		}
	}

	@Override
	public String toString() {
		return "CodeBuildGradleAndroid [channelName=" + channelName + "]";
	}

	@Override
	public void cleanProject(String projectFolder) {
		logger.debug("开始清理工程");
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
		        projectFolder);

		for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
			if (null != codeCoverageFilesAndFoldersDTOs.get(i)
			        .getClassesDirectory()
			        && codeCoverageFilesAndFoldersDTOs.get(i)
			                .getClassesDirectory().exists()) {
				logger.debug("删除class文件夹:{}", codeCoverageFilesAndFoldersDTOs
				        .get(i).getClassesDirectory().getAbsolutePath());
				FileUtil.deleteFile(codeCoverageFilesAndFoldersDTOs.get(i)
				        .getClassesDirectory());
			}
		}
	}

	@Override
	public void copyClasses(String fromClassFolder, String projectFolder) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
		        projectFolder);
		logger.debug("开始copy classes文件");
		try {
			// 定义一个字段，用来控制是不是多模块的class
			boolean everCopiedClasses = false;

			for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
				CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFoldersDTOs
				        .get(i);
				String projectName = codeCoverageFilesAndFoldersDTO
				        .getProjectDir().getName();
				File sourceProjectClassFolder = new File(fromClassFolder,
				        projectName);

				if (sourceProjectClassFolder.exists()) {
					logger.debug("拷贝class从:{}.到:{}",
					        sourceProjectClassFolder.getAbsolutePath(),
					        codeCoverageFilesAndFoldersDTO.getClassesDirectory()
					                .getAbsolutePath());
					FileUtil.copyDir(sourceProjectClassFolder.getAbsolutePath(),
					        codeCoverageFilesAndFoldersDTO.getClassesDirectory()
					                .getAbsolutePath());
					everCopiedClasses = true;
				}
			}
			if (everCopiedClasses) {
				return;
			}
			// 如果从来没有拷贝过，证明不是多模块，则直接把此处的class文件存放到
			for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
				CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFoldersDTOs
				        .get(i);
				File sourceProjectClassFolder = new File(fromClassFolder);
				if (sourceProjectClassFolder.exists()) {
					logger.debug("拷贝class从:{}.到:{}",
					        sourceProjectClassFolder.getAbsolutePath(),
					        codeCoverageFilesAndFoldersDTO.getClassesDirectory()
					                .getAbsolutePath());
					FileUtil.copyDir(sourceProjectClassFolder.getAbsolutePath(),
					        codeCoverageFilesAndFoldersDTO.getClassesDirectory()
					                .getAbsolutePath());
				}
			}
		} catch (IOException e) {
			logger.error("复制class出现异常:{}", e.getMessage());
			throw new BusinessValidationException("class文件复制过程中出现异常");
		}
	}

}
