
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:SourceCodeDealerAnt.java
 * @author : 孙留平
 * @since : 2019年7月10日 下午2:13:14
 * @see:
 */

package com.administrator.platform.tools.codebuild.impl.codedealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.ExecuteCommandResult;
import com.administrator.platform.tools.codebuild.impl.CodeBuildAnt;
import com.administrator.platform.tools.codebuild.intf.SourceCodeDealer;
import com.administrator.platform.tools.commandexecutor.CommandExecotor;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;
import com.administrator.platform.util.define.OperationSystemEnum;

public class SourceCodeDealerAnt implements SourceCodeDealer {
	private static final Logger logger = LoggerFactory
	        .getLogger(SourceCodeDealerAnt.class);

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath,
	        String jdkVersion) {

		String installCommand = createInstallSourceCommand();

		if (isJdkSix(jdkVersion)) {
			new CodeBuildAnt()
			        .checkAndModifyConfigFileCompileWithJdkSix(sourceCodePath);
		}

		return new CommandExecotor()
		        .executeCommandAndCheckResult(installCommand, sourceCodePath);
	}

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath) {
		return installSourceCode(sourceCodePath,
		        JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public ExecuteCommandResult compileSourceCode(String sourceCodePath) {
		return compileSourceCode(sourceCodePath,
		        JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public ExecuteCommandResult compileSourceCode(String sourceCodePath,
	        String jdkVersion) {

		String compileCommand = createCompileSourceCodeCommand(jdkVersion);

		if (isJdkSix(jdkVersion)) {
			new CodeBuildAnt()
			        .checkAndModifyConfigFileCompileWithJdkSix(sourceCodePath);
		}

		return new CommandExecotor()
		        .executeCommandAndCheckResult(compileCommand, sourceCodePath);
	}

	@Override
	public String createInstallSourceCommand() {
		return createInstallSourceCommand(JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public String createInstallSourceCommand(String jdkVersion) {
		return createCompileSourceCodeCommand(jdkVersion);
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
				                ? JacocoDefine.COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_ANT
				                : JacocoDefine.COMPILE_SOURCE_CODE_COMMANN_LINUX_OF_ANT;
			case JacocoDefine.JDK_VERSION_SIX:
				boolean isWindows = Util
				        .getCurrentOperationSystem() == OperationSystemEnum.WINDOWS;
				return isWindows
				        ? JacocoDefine.INSTALL_SOURCE_WITH_JDK6_ON_WINDOWS_OF_ANT
				        : JacocoDefine.INSTALL_SOURCE_WITH_JDK6_ON_LINUX_OF_ANT;
			default:
				logger.warn("jdk版本未指定，或者不在1.6和1.8之内，将被默认设置成1.8");
				return Util
				        .getCurrentOperationSystem() == OperationSystemEnum.WINDOWS
				                ? JacocoDefine.COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_ANT
				                : JacocoDefine.COMPILE_SOURCE_CODE_COMMANN_LINUX_OF_ANT;
		}
	}

	/**
	 * 判断是不是jdk6
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param jdkVersion
	 * @return
	 */
	private boolean isJdkSix(String jdkVersion) {
		return JacocoDefine.JDK_VERSION_SIX.equalsIgnoreCase(jdkVersion);
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
