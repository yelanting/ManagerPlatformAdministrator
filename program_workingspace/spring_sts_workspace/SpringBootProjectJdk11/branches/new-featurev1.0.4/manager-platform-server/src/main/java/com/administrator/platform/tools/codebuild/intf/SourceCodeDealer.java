
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:SourceCodeDealer.java
 * @author : 孙留平
 * @since : 2019年7月10日 下午2:10:33
 * @see:
 */

package com.administrator.platform.tools.codebuild.intf;

import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.ExecuteCommandResult;

/**
 * 处理源码的接口
 * 
 * @author : Administrator
 * @since : 2019年7月10日 下午2:10:38
 * @see :
 */
public interface SourceCodeDealer {

	/**
	 * 在某个工程下面执行install 操作，以某个版本jdk为准
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param sourceCodePath
	 * @param jdkVersion
	 * @return
	 */
	ExecuteCommandResult installSourceCode(String sourceCodePath,
	        String jdkVersion);

	/**
	 * 在某个工程下，执行install操作，默认是jdk8
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param sourceCodePath
	 * @return
	 */
	ExecuteCommandResult installSourceCode(String sourceCodePath);

	/**
	 * 在某个工程下面执行compile操作，默认jdk8
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param sourceCodePath
	 * @return
	 */
	ExecuteCommandResult compileSourceCode(String sourceCodePath);

	/**
	 * 在某个工程下面执行compile操作，可选择jdk版本，主要用于ant构建
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param sourceCodePath
	 * @param jdkVersion
	 * @return
	 */
	ExecuteCommandResult compileSourceCode(String sourceCodePath,
	        String jdkVersion);

	/**
	 * 以前端表单传进来的CodeCoverage对象来进行安装
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param codeCoverage
	 * @return
	 */
	ExecuteCommandResult installSourceCode(CodeCoverage codeCoverage);

	/**
	 * 以前端表单传进来的CodeCoverage对象来进行compile
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param codeCoverage
	 * @return
	 */

	ExecuteCommandResult compileSourceCode(CodeCoverage codeCoverage);

	/**
	 * 生成默认的install指令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	String createInstallSourceCommand();

	/**
	 * create默认的install指令，可选jdk版本
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param jdkVersion
	 * @return
	 */
	String createInstallSourceCommand(String jdkVersion);

	/**
	 * create根据前端的传输对象生成install指令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param codeCoverage
	 * @return
	 */
	String createInstallSourceCommand(CodeCoverage codeCoverage);

	/**
	 * create生成默认的编译指令，jdk8
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	String createCompileSourceCodeCommand();

	/**
	 * create生成编译指令，可选择jdk版本
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param jdkVersion
	 * @return
	 */
	String createCompileSourceCodeCommand(String jdkVersion);

	/**
	 * create根据前端的传输对象生成对应的编译指令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param codeCoverage
	 * @return
	 */
	String createCompileSourceCodeCommand(CodeCoverage codeCoverage);

}
