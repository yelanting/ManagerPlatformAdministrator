
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:SourceCodeDealerMaven.java
 * @author : 孙留平
 * @since : 2019年7月10日 下午2:13:25
 * @see:
 */

package com.administrator.platform.tools.codebuild.impl.codedealer;

import java.util.List;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.ExecuteCommandResult;
import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
import com.administrator.platform.tools.codebuild.intf.SourceCodeDealer;
import com.administrator.platform.tools.commandexecutor.CommandExecotor;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;
import com.administrator.platform.util.define.OperationSystemEnum;

public class SourceCodeDealerMaven implements SourceCodeDealer {

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath,
	        String jdkVersion) {

		String installSourceCommand = createInstallSourceCommand(jdkVersion);

		// 如果根目录就是maven工程了，直接执行
		boolean isMavenProject = CodeBuildMaven
		        .checkProjectMaven(sourceCodePath);

		ExecuteCommandResult executeCommandResult = null;
		if (isMavenProject) {
			return new CommandExecotor().executeCommandAndCheckResult(
			        installSourceCommand, sourceCodePath);
		}

		// 如果根目录不是MAVEN工程，则往下找，直到找到pom文件
		List<String> parentMavenFolders = CodeBuildMaven
		        .findParentMavenProjectFolderUnderCertainFolder(sourceCodePath);

		for (String string : parentMavenFolders) {
			executeCommandResult = new CommandExecotor()
			        .executeCommandAndCheckResult(installSourceCommand, string);
		}

		return executeCommandResult;
	}

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath) {
		return installSourceCode(sourceCodePath,
		        JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 编译本地代码
	 */
	@Override
	public ExecuteCommandResult compileSourceCode(String sourceCodePath) {
		return compileSourceCode(sourceCodePath,
		        JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public ExecuteCommandResult compileSourceCode(String sourceCodePath,
	        String jdkVersion) {

		String compileSourceCodeCommand = createCompileSourceCodeCommand(
		        jdkVersion);

		return new CommandExecotor().executeCommandAndCheckResult(
		        compileSourceCodeCommand, sourceCodePath);
	}

	@Override
	public String createInstallSourceCommand() {
		return createInstallSourceCommand(JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public String createInstallSourceCommand(String jdkVersion) {
		switch (jdkVersion) {
			case JacocoDefine.JDK_VERSION_EIGHT:
				return Util
				        .getCurrentOperationSystem() == OperationSystemEnum.WINDOWS
				                ? JacocoDefine.INSTALL_SOURCE_CODE_COMMAND_WINDOWS
				                : JacocoDefine.INSTALL_SOURCE_CODE_COMMAND_LINUX;
			case JacocoDefine.JDK_VERSION_SIX:
				throw new BusinessValidationException("除jdk1.8版本之外，暂不支持其他版本");
			default:
				throw new BusinessValidationException("除jdk1.8之外，暂不支持其他");
		}
	}

	@Override
	public String createCompileSourceCodeCommand() {
		return createCompileSourceCodeCommand(JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public String createCompileSourceCodeCommand(String jdkVersion) {
		switch (jdkVersion) {
			case JacocoDefine.JDK_VERSION_EIGHT:
				return Util
				        .getCurrentOperationSystem() == OperationSystemEnum.WINDOWS
				                ? JacocoDefine.COMPILE_SOURCE_CODE_COMMAND_WINDOWS
				                : JacocoDefine.COMPILE_SOURCE_CODE_COMMAND_LINUX;
			case JacocoDefine.JDK_VERSION_SIX:
				throw new BusinessValidationException("除jdk1.8之外，暂不支持其他");
			default:
				throw new BusinessValidationException("除jdk1.8之外，暂不支持其他");
		}
	}

	@Override
	public ExecuteCommandResult installSourceCode(CodeCoverage codeCoverage) {
		String sourceCodePath = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage)
		        .getAbsolutePath();
		return installSourceCode(sourceCodePath,
		        parseJdkVersionFromCodeCoverage(codeCoverage));
	}

	@Override
	public ExecuteCommandResult compileSourceCode(CodeCoverage codeCoverage) {
		String sourceCodePath = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage)
		        .getAbsolutePath();

		return compileSourceCode(sourceCodePath,
		        parseJdkVersionFromCodeCoverage(codeCoverage));
	}

	@Override
	public String createInstallSourceCommand(CodeCoverage codeCoverage) {
		return createInstallSourceCommand(
		        parseJdkVersionFromCodeCoverage(codeCoverage));
	}

	@Override
	public String createCompileSourceCodeCommand(CodeCoverage codeCoverage) {
		return createCompileSourceCodeCommand(
		        parseJdkVersionFromCodeCoverage(codeCoverage));
	}

	private String parseJdkVersionFromCodeCoverage(CodeCoverage codeCoverage) {
		return StringUtil.isStringAvaliable(codeCoverage.getJdkVersion())
		        ? codeCoverage.getJdkVersion()
		        : JacocoDefine.JDK_VERSION_EIGHT;
	}
}
