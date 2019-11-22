/**
 * @author : 孙留平
 * @since : 2019年3月21日 上午11:00:28
 * @see:
 */
package com.administrator.platform.tools.codebuild.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.tools.codebuild.CodeBuildDefaultDefines;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
import com.administrator.platform.tools.jacoco.CodeCoverageFilesAndFoldersDTO;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.define.OperationSystemEnum;

/**
 * @author : Administrator
 * @since : 2019年3月21日 上午11:00:28
 * @see :
 */
public class CodeBuildAnt implements CodeBuildIntf {

	private static final Logger logger = LoggerFactory
	        .getLogger(CodeBuildAnt.class);

	public CodeBuildAnt() {

	}

	/**
	 * 以一个文件夹初始化为一个MAVEN工程资源
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
		        JacocoDefine.DEFAULT_SOURCE_FOLDER_OF_ANT));

		codeCoverageFilesAndFoldersDTO.setClassesDirectory(new File(projectDir,
		        JacocoDefine.DEFAULT_CLASSES_FOLDER_OF_ANT));

		codeCoverageFilesAndFoldersDTO.setExecutionDataFile(new File(projectDir,
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT));

		codeCoverageFilesAndFoldersDTO.setReportDirectory(
		        new File(projectDir, JacocoDefine.CODE_COVERAGE_DATA_FOLDER));

		codeCoverageFilesAndFoldersDTO.setIncrementReportDirectory(new File(
		        projectDir, JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER));
		return codeCoverageFilesAndFoldersDTO;
	}

	/**
	 * 获取子模块，默认没有子模块，ANT构建型，默认只有一个模块
	 * 
	 * @see
	 *      com.administrator.platform.tools.codebuild.CodeBuild#getSubModules(java.
	 *      lang.String)
	 */
	@Override
	public List<String> getSubModules(String projectPath) {
		// 默认没有子模块
		return new ArrayList<>();
	}

	/**
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getSubSourceFileFolders(java.lang.String)
	 */
	@Override
	public List<String> getSubSourceFileFolders(String projectPath) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = getCodeCoverageFilesAndFolders(
		        projectPath);

		List<String> subSourceFileFoders = new ArrayList<>();
		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFolders) {
			subSourceFileFoders.add(codeCoverageFilesAndFoldersDTO
			        .getSourceDirectory().getAbsolutePath());
		}
		return subSourceFileFoders;
	}

	/**
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getSubClassesFileFolders(java.lang.String)
	 */
	@Override
	public List<String> getSubClassesFileFolders(String projectPath) {

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = getCodeCoverageFilesAndFolders(
		        projectPath);

		List<String> subClassesFileFolders = new ArrayList<>();
		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFolders) {
			subClassesFileFolders.add(codeCoverageFilesAndFoldersDTO
			        .getClassesDirectory().getAbsolutePath());
		}
		return subClassesFileFolders;
	}

	/**
	 * 
	 * @see com.administrator.platform.tools.codebuild.CodeBuild#
	 *      getCodeCoverageFilesAndFolders(java.lang.String)
	 */
	@Override
	public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFolders(
	        String projectPath) {

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = new ArrayList<>();
		codeCoverageFilesAndFolders
		        .add(initDefaultCodeCoverageFilesAndFoldersDTO(projectPath));
		return codeCoverageFilesAndFolders;
	}

	/**
	 * 判断当前文件夹是否ant工程
	 * 
	 * @see
	 *      com.administrator.platform.tools.codebuild.CodeBuild#isThusKindOfProject(
	 *      java.lang.String)
	 */
	@Override
	public boolean isThusKindOfProject(String projectPath) {
		File projectFile = new File(projectPath);

		if (!projectFile.exists()) {
			return false;
		}

		if (!projectFile.isDirectory()) {
			return false;
		}

		// 如果路径以build.xml结尾，则代表是个文件
		// 如果不是以build.xml结尾
		// 不是以build.xml结尾，又是个文件，则报错
		// 如果是个文件夹，则自动加上build.xml
		File antFileTemp = new File(projectPath,
		        JacocoDefine.DEFAULT_PRODUCT_BUILD_FILE_NAME);
		// 如果build文件不存在
		if (antFileTemp.exists()) {
			logger.debug("配置文件:{}存在", antFileTemp.getAbsolutePath());
			return true;
		}

		return false;
	}

	/**
	 * 获取某个文件夹下的ant工程
	 * 
	 * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
	 *      getCodeCoverageFilesAndFolders(java.io.File)
	 */
	@Override
	public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFolders(
	        File projectPath) {
		return getCodeCoverageFilesAndFolders(projectPath.getAbsolutePath());
	}

	/**
	 * 默认ANT型都是非多模块
	 * 
	 * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
	 *      isThusProjectMulti(java.lang.String)
	 */
	@Override
	public boolean isThusProjectMulti(String projectPath) {
		return false;
	}

	/**
	 * 获取执行文件信息
	 * 
	 * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
	 *      getExecutionDataFiles(java.lang.String)
	 */
	@Override
	public List<String> getExecutionDataFiles(String projectPath) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = getCodeCoverageFilesAndFolders(
		        projectPath);

		List<String> executionDataFiles = new ArrayList<>();
		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFolders) {
			executionDataFiles.add(codeCoverageFilesAndFoldersDTO
			        .getExecutionDataFile().getAbsolutePath());
		}
		return executionDataFiles;
	}

	/**
	 * 检查并修改jdk版本
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void checkAndModifyConfigFileCompileWithJdkSix(
	        String projectPathOrBuildXmlPath) {
		logger.debug("开始检查并尝试修改构建配置文件:{}", projectPathOrBuildXmlPath);
		if (!isThusKindOfProject(projectPathOrBuildXmlPath)) {
			return;
		}

		File projectPathFile = new File(projectPathOrBuildXmlPath);

		// 如果是个文件夹，则找到对应的文件进行修改
		if (projectPathFile.isDirectory()) {
			projectPathFile = new File(projectPathOrBuildXmlPath,
			        JacocoDefine.DEFAULT_PRODUCT_BUILD_FILE_NAME);

			if (!projectPathFile.exists()) {
				return;
			}
		}

		checkBuildXmlReplacedAndDoActionIfNot(
		        projectPathFile.getAbsolutePath());

	}

	/**
	 * 检查配置文件是否已经被修改，如果被修改了则跳过，如果没有被修改则修改文件内容
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param buildXmlPath
	 */
	private void checkBuildXmlReplacedAndDoActionIfNot(String buildXmlPath) {
		// 找到文件之后，读文件
		logger.debug("配置文件路径为:{}", buildXmlPath);
		String fileContentAfterReplaced = getBuildXmlContentAfterModified(
		        buildXmlPath);
		if (StringUtil.isEmpty(fileContentAfterReplaced)) {
			return;
		}

		FileUtil.writeToFile(buildXmlPath, fileContentAfterReplaced, false);
	}

	/**
	 * 获取修改后的配置文件
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param buildXmlPath
	 * @return
	 */
	private static String getBuildXmlContentAfterModified(String buildXmlPath) {
		final StringBuilder sBuilder = new StringBuilder();
		String line = null;
		File file = new File(buildXmlPath);

		if (!file.exists()) {
			return "";
		}

		boolean isWindows = Util
		        .getCurrentOperationSystem() == OperationSystemEnum.WINDOWS;
		String jdkPath = isWindows ? JacocoDefine.JDK6_ON_WINDOWS
		        : JacocoDefine.JDK6_ON_LINUX;

		String executableContent = "  executable=\"" + jdkPath + File.separator
		        + "bin" + File.separator + "javac" + "\"";

		String debugAttribute = " debug=\"true\"";

		try (BufferedReader inputStreamReader = new BufferedReader(
		        new InputStreamReader(new FileInputStream(file)));) {
			while (null != (line = inputStreamReader.readLine())) {
				// 是不是javac标签
				boolean isJavacTag = line.contains(
				        CodeBuildDefaultDefines.CODE_BUILD_COMPILE_COMMAND_START_TAG);
				boolean containsDebug = line.contains(debugAttribute);
				boolean containsExecutable = line.contains(executableContent);
				String toRelaceContent = CodeBuildDefaultDefines.CODE_BUILD_COMPILE_COMMAND_START_TAG;
				// 如果没有executable内容
				if (!containsExecutable) {
					toRelaceContent += executableContent;
				}
				// 如果没有debug属性
				if (!containsDebug) {
					toRelaceContent += debugAttribute;
				}

				if (isJavacTag) {
					line = line.replace(
					        CodeBuildDefaultDefines.CODE_BUILD_COMPILE_COMMAND_START_TAG,
					        toRelaceContent);
				}

				sBuilder.append(line + "\n");
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
		logger.debug("替换后的配置文件为:{}", sBuilder.toString());
		return sBuilder.toString();
	}

	@Override
	public void cleanProject(String projectFolder) {
		logger.debug("开始清理工程");
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = getCodeCoverageFilesAndFolders(
		        projectFolder);

		for (int i = 0; i < codeCoverageFilesAndFolders.size(); i++) {
			if (null != codeCoverageFilesAndFolders.get(i)
			        .getClassesDirectory()
			        && codeCoverageFilesAndFolders.get(i)
			                .getClassesDirectory().exists()) {
				logger.debug("删除class文件夹:{}", codeCoverageFilesAndFolders
				        .get(i).getClassesDirectory().getAbsolutePath());
				FileUtil.deleteFile(codeCoverageFilesAndFolders.get(i)
				        .getClassesDirectory());
			}
		}
	}

	@Override
	public void copyClasses(String fromClassFolder, String projectFolder) {
		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFolders = getCodeCoverageFilesAndFolders(
		        projectFolder);
		logger.debug("开始copy classes文件");
		try {
			// 定义一个字段，用来控制是不是多模块的class
			boolean everCopiedClasses = false;

			for (int i = 0; i < codeCoverageFilesAndFolders.size(); i++) {
				CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFolders
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
			for (int i = 0; i < codeCoverageFilesAndFolders.size(); i++) {
				CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFolders
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
