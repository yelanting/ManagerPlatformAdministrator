
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:CommandExecotor.java
 * @author : 孙留平
 * @since : 2019年7月10日 下午2:49:29
 * @see:
 */

package com.administrator.platform.tools.commandexecutor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.ExecuteCommandResult;
import com.administrator.platform.util.define.OperationSystemEnum;

/**
 * 执行命令的类，负责执行一些本地命令 ，并可以做检查
 * 
 * @author : Administrator
 * @since : 2019年7月10日 下午2:57:43
 * @see :
 */
public class CommandExecotor {
	private static final Logger logger = LoggerFactory
			.getLogger(CommandExecotor.class);
	/**
	 * 默认超时时间-30分钟
	 */
	private static final long EXECUTE_TIMEOUT = 30 * 60 * 1000L;

	/**
	 * 默认的前缀
	 */
	private static final String WINDOWS_COMMAND_PREFIX = "cmd.exe /c ";

	/**
	 * 默认执行本地命令
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param command
	 * @param timeout
	 * @return
	 */
	public ExecuteCommandResult executeCommand(String command,
			String executePath) {
		return executeCommand(command, executePath, EXECUTE_TIMEOUT);
	}

	/**
	 * 执行本地命令
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param command
	 * @param timeout
	 * @return
	 */
	public ExecuteCommandResult executeCommand(String command,
			String executePath, long timeout) {

		// 如果是windows的，则加上cmd命令
		if (Util.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS) {
			command = WINDOWS_COMMAND_PREFIX + command;
		}

		logger.debug("在目录:{}下,执行命令:{}", executePath, command);
		CommandLine commandLine = CommandLine.parse(command);
		ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
		Executor executor = new DefaultExecutor();
		executor.setWatchdog(watchdog);
		executor.setExitValue(0);
		if (StringUtil.isStringAvaliable(executePath)) {
			executor.setWorkingDirectory(new File(executePath));
		}
		ExecuteCommandResult executeCommandResult = null;

		String output = null;
		String errorOutput = null;
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ByteArrayOutputStream byteArrayErrorStream = new ByteArrayOutputStream();) {
			PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(
					byteArrayOutputStream, byteArrayErrorStream);
			executor.setStreamHandler(pumpStreamHandler);

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			executor.execute(commandLine, resultHandler);
			// resultHandler.waitFor(5 * 60 * 1000L);
			resultHandler.waitFor();
			int exitCodeFinal = resultHandler.getExitValue();
			// int exitCodeFinal = executor.execute(commandLine);
			executeCommandResult = new ExecuteCommandResult();
			executeCommandResult
					.setSuccessful(!executor.isFailure(exitCodeFinal));

			if (Util.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS) {
				output = byteArrayOutputStream
						.toString(GlobalDefination.CHAR_SET_GBK).trim();
				errorOutput = byteArrayErrorStream
						.toString(GlobalDefination.CHAR_SET_GBK).trim();
			} else {
				output = byteArrayOutputStream.toString().trim();
				errorOutput = byteArrayErrorStream.toString().trim();
			}

			logger.debug("输出结果:{}{}", output, errorOutput);
			executeCommandResult.setExecuteOut(output + errorOutput);
			executeCommandResult.setExitValue("" + exitCodeFinal);
			executeCommandResult.setExitCode("" + exitCodeFinal);
			logger.debug("执行结果:{}", executeCommandResult);
			return executeCommandResult;
		} catch (ExecuteException e) {
			logger.error("执行命令失败:{},输出结果:{},{}", e.getMessage(),
					null == output ? "" : output,
					null == errorOutput ? "" : errorOutput);
			throw new BusinessValidationException("执行命令失败");
		} catch (IOException e) {
			logger.error("执行命令失败，io异常:{},输出结果:{},{}", e.getMessage(),
					null == output ? "" : output,
					null == errorOutput ? "" : errorOutput);
			throw new BusinessValidationException("执行命令失败");
		} catch (InterruptedException e) {
			logger.error("执行命令失败，InterruptedException:{}", e.getMessage());
			throw new BusinessValidationException("执行命令失败");
		}
	}

	/**
	 * 检查结果是否正确
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param executeCommandResult
	 * @return
	 */
	public static boolean checkExecuteCommandResult(
			ExecuteCommandResult executeCommandResult) {

		if (null == executeCommandResult) {
			return false;
		}

		// 如果执行是失败的
		if (!executeCommandResult.isSuccessful()) {
			logger.error("命令执行失败了，输出为:{}",
					executeCommandResult.getExecuteOut());
			throw new BusinessValidationException("命令执行失败了，请排查原因");
		}

		return true;
	}

	/**
	 * 默认执行命令
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param command
	 * @return
	 */
	public ExecuteCommandResult executeCommand(String command) {
		return executeCommand(command, null, EXECUTE_TIMEOUT);
	}

	/**
	 * 安装源码并检查结果
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param toInstalledFolder
	 * @param buildType
	 * @return
	 */
	public ExecuteCommandResult executeCommandAndCheckResult(String command,
			String executePath) {
		ExecuteCommandResult executeCommandResult = executeCommand(command,
				executePath);
		checkExecuteCommandResult(executeCommandResult);
		return executeCommandResult;
	}
}
