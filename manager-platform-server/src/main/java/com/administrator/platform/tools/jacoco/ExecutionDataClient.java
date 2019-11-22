/**
 * @author : 孙留平
 * @since : 2019年2月26日 下午6:09:06
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jacoco.core.tools.ExecDumpClient;
import org.jacoco.core.tools.ExecFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.tools.codebuild.entity.BuildType;
import com.administrator.platform.tools.codebuild.impl.CodeBuildAnt;
import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;

/**
 * @author : Administrator
 * @since : 2019年3月8日 上午9:55:17
 * @see :
 */
public final class ExecutionDataClient {
	private static final Logger logger = LoggerFactory
	        .getLogger(ExecutionDataClient.class);
	private JacocoAgentTcpServer jacocoAgentTcpServer;

	public ExecutionDataClient(JacocoAgentTcpServer jacocoAgentTcpServer) {
		this.jacocoAgentTcpServer = jacocoAgentTcpServer;
	}

	public ExecutionDataClient(String tcpServerIp, int tcpPort) {
		this.jacocoAgentTcpServer = new JacocoAgentTcpServer(tcpServerIp,
		        tcpPort);
	}

	/**
	 * dump出信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpData(String folderPath) {
		dumpExecData(folderPath);
	}

	/**
	 * dump出信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpData(String folderPath, BuildType buildType) {
		CodeBuildIntf codeBuildIntf = null;
		if (buildType == BuildType.ANT) {
			codeBuildIntf = new CodeBuildAnt();
		} else {
			codeBuildIntf = new CodeBuildMaven();
		}

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = codeBuildIntf
		        .getCodeCoverageFilesAndFolders(folderPath);

		dumpExecData(folderPath, codeCoverageFilesAndFoldersDTOs);
	}

	/**
	 * 检查tcp服务是否可以连接
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @return
	 */
	public boolean checkTcpServerCanBeConnected() {
		try {
			ExecDumpClient dumpClient = new ExecDumpClient();
			dumpClient.dump(this.jacocoAgentTcpServer.getJacocoAgentIp(),
			        this.jacocoAgentTcpServer.getJacocoAgentPort());
			return true;
		} catch (Exception e) {
			logger.error("tcp服务连接不上，请检查配置:{}", e.getMessage());
			String errorMessage = String.format("tcp服务:%s:%s连接不上，请检查配置",
			        this.jacocoAgentTcpServer.getJacocoAgentIp(),
			        this.jacocoAgentTcpServer.getJacocoAgentPort());
			throw new BusinessValidationException(errorMessage);
		}
	}

	/**
	 * dump出信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecData(String folderPath) {
		File backUpExecFile = getBackUpExecFile();
		File parentFolderExecFile = new File(folderPath,
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);

		// 如果当前不存在，且备份文件夹中有，则先从备份文件夹中取一份，复制到当前文件夹，保持有旧数据存在
		if (!parentFolderExecFile.exists() && backUpExecFile.exists()) {
			FileUtil.copyFile(backUpExecFile, parentFolderExecFile);
		}

		backUpExecData();
		dumpExecDataToFile(parentFolderExecFile);

		List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = new CodeBuildMaven()
		        .getCodeCoverageFilesAndFolders(folderPath);
		dumpExecData(codeCoverageFilesAndFoldersDTOs);
	}

	/**
	 * dump出信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecData(String folderPath,
	        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs) {
		logger.debug("开始dump覆盖率信息:{},到:{}", this.jacocoAgentTcpServer,
		        folderPath);

		File backUpExecFile = getBackUpExecFile();
		File parentFolderExecFile = new File(folderPath,
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);

		// 如果当前不存在，且备份文件夹中有，则先从备份文件夹中取一份，复制到当前文件夹，保持有旧数据存在
		if (!parentFolderExecFile.exists() && backUpExecFile.exists()) {
			FileUtil.copyFile(backUpExecFile, parentFolderExecFile);
		}
		backUpExecData();
		dumpExecDataToFile(parentFolderExecFile);
		dumpExecData(codeCoverageFilesAndFoldersDTOs);
	}

	/**
	 * 备份覆盖率数据
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void backUpExecData() {
		logger.debug("备份覆盖率数据");
		dumpExecDataToFile(getExecFilesBackUpFolder(),
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);
		logger.debug("覆盖率数据备份完成");
	}

	/**
	 * dump出信息，保存到文件夹中
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecDataToFile(String folderPath, String fileName) {
		dumpExecDataToFile(new File(folderPath, fileName).getAbsolutePath());
	}

	/**
	 * dump出信息，保存到文件夹中
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecDataToFile(String filePath) {
		logger.debug("开始dump覆盖率信息:{},到:{}文件中", this.jacocoAgentTcpServer,
		        filePath);
		ExecDumpClient dumpClient = new ExecDumpClient();
		dumpClient.setDump(true);
		ExecFileLoader execFileLoader = null;
		try {
			execFileLoader = dumpClient.dump(
			        this.jacocoAgentTcpServer.getJacocoAgentIp(),
			        this.jacocoAgentTcpServer.getJacocoAgentPort());

			execFileLoader.save(new File(filePath), true);
		} catch (IOException e2) {
			logger.error("获取dump信息失败:{}", e2.getMessage());
			throw new BusinessValidationException("tcp服务连接失败,请查看tcp配置");
		}
	}

	/**
	 * dump出信息，保存到文件夹中
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecDataToFile(File filePath) {
		dumpExecDataToFile(filePath.getAbsolutePath());
	}

	/**
	 * dump出信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void dumpExecData(
	        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs) {
		// 先往备份文件夹中保存一份
		File backUpExecFile = getBackUpExecFile();
		for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
			File eachModuleExecFile = codeCoverageFilesAndFoldersDTO
			        .getExecutionDataFile();
			// 如果当前不存在，且备份文件夹中有，则先从备份文件夹中取一份，复制到当前文件夹，保持有旧数据存在
			if (!eachModuleExecFile.exists() && backUpExecFile.exists()) {
				FileUtil.copyFile(backUpExecFile, eachModuleExecFile);
			}

			// 保存一份到备份
			backUpExecData();
			dumpExecDataToFile(eachModuleExecFile);
		}
	}

	/**
	 * 获取当前dump的文件夹路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	private String getExecFilesBackUpFolder() {
		// return JacocoOperationUtil.getJacocoCoverageExecBackupFolder()
		// + File.separator + this.jacocoAgentTcpServer.getJacocoAgentIp()
		// + "_" + this.jacocoAgentTcpServer.getJacocoAgentPort();
		return getExecFilesBackUpFolderFromJacocoAgentServer(
		        this.jacocoAgentTcpServer);
	}

	public static String getExecFilesBackUpFolderFromJacocoAgentServer(
	        JacocoAgentTcpServer jacocoAgentTcpServer) {
		return JacocoOperationUtil.getJacocoCoverageExecBackupFolder()
		        + File.separator + jacocoAgentTcpServer.getJacocoAgentIp() + "_"
		        + jacocoAgentTcpServer.getJacocoAgentPort();
	}

	/**
	 * 判断备份文件jacoco.exec是不是存在
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @return
	 */
	private boolean backUpExecFileExists() {
		File execBackUpFile = getBackUpExecFile();
		return execBackUpFile.exists();
	}

	/**
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */

	private File getBackUpExecFile() {
		return new File(getExecFilesBackUpFolder(),
		        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);
	}

	private ExecutionDataClient() {
	}
}
