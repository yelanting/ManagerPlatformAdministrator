
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:SourceCodeDealerGradleAndroid.java
 * @author : 孙留平
 * @since : 2019年7月10日 下午2:13:41
 * @see:
 */

package com.administrator.platform.tools.codebuild.impl.codedealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.ExecuteCommandResult;
import com.administrator.platform.tools.codebuild.intf.SourceCodeDealer;
import com.administrator.platform.tools.commandexecutor.CommandExecotor;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;

public class SourceCodeDealerGradleAndroid implements SourceCodeDealer {

	private static final Logger logger = LoggerFactory
	        .getLogger(SourceCodeDealerGradleAndroid.class);
	/**
	 * 编译指令前缀
	 */
	private static final String COMPILE_PREFIX = "compile";

	/**
	 * 编译指令后缀
	 */
	private static final String COMPILE_SUFFIX = "DebugJavaWithJavac";

	/**
	 * gradle指令
	 */
	private static final String GRADLEW_COMMAND = "gradle";

	/**
	 * clean指令
	 */
	private static final String CLEAN_COMMAND = "clean";

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath,
	        String jdkVersion) {
		logger.info("在目录:{}下，安装代码", sourceCodePath);
		String installSourceCodeCommand = createInstallSourceCommand(
		        jdkVersion);

		return new CommandExecotor().executeCommandAndCheckResult(
		        installSourceCodeCommand, sourceCodePath);
	}

	@Override
	public ExecuteCommandResult installSourceCode(String sourceCodePath) {
		return installSourceCode(sourceCodePath,
		        JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public ExecuteCommandResult compileSourceCode(String sourceCodePath) {
		logger.info("在目录:{}下，编译代码", sourceCodePath);
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
		return JacocoDefine.INSTALL_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE;
	}

	@Override
	public String createCompileSourceCodeCommand() {
		return createCompileSourceCodeCommand(JacocoDefine.JDK_VERSION_EIGHT);
	}

	@Override
	public String createCompileSourceCodeCommand(String jdkVersion) {
		return JacocoDefine.COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE;
	}

	@Override
	public ExecuteCommandResult installSourceCode(CodeCoverage codeCoverage) {
		logger.info("installSourceCode(CodeCoverage codeCoverage)");
		String sourceCodePath = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage)
		        .getAbsolutePath();

		String installSourceCodeCommand = createCompileCommandWithChannelName(
		        codeCoverage.getChannelName());

		logger.debug("在目录{},下执行命令:{}", sourceCodePath,
		        installSourceCodeCommand);
		return new CommandExecotor().executeCommandAndCheckResult(
		        installSourceCodeCommand, sourceCodePath);
		// return installSourceCode(sourceCodePath,
		// parseJdkVersionFromCodeCoverage(codeCoverage));
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

	/**
	 * 从前端传输对象中解析jdk版本
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param codeCoverage
	 * @return
	 */
	private String parseJdkVersionFromCodeCoverage(CodeCoverage codeCoverage) {
		return StringUtil.isStringAvaliable(codeCoverage.getJdkVersion())
		        ? codeCoverage.getJdkVersion()
		        : JacocoDefine.JDK_VERSION_EIGHT;
	}

	/**
	 * 根据渠道名称生成编译指令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param channelName
	 * @return
	 */
	private String createCompileCommandWithChannelName(String channelName) {
		return GRADLEW_COMMAND + " " + CLEAN_COMMAND + " " + COMPILE_PREFIX
		        + (StringUtil.isEmpty(channelName) ? ""
		                : StringUtil.firstCharUpCase(channelName))
		        + COMPILE_SUFFIX;
	}

}
