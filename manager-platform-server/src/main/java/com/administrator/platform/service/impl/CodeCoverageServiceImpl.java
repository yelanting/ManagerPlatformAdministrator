/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.TimerTaskAndRelations;
import com.administrator.platform.core.base.util.ArrayUtil;
import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.core.base.util.ZipCompress;
import com.administrator.platform.definition.form.CodeCoverageFormDefinition;
import com.administrator.platform.enums.NewSourceType;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.CodeCoverageMapper;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.ServerInfo;
import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.CodeCoverageService;
import com.administrator.platform.service.ServerInfoService;
import com.administrator.platform.service.TimerTaskPolicyService;
import com.administrator.platform.service.TimerTaskService;
import com.administrator.platform.tools.codebuild.entity.BuildType;
import com.administrator.platform.tools.codebuild.impl.CodeBuildAnt;
import com.administrator.platform.tools.codebuild.impl.CodeBuildGradleAndroid;
import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
import com.administrator.platform.tools.codebuild.impl.codedealer.SourceCodeDealerAnt;
import com.administrator.platform.tools.codebuild.impl.codedealer.SourceCodeDealerGradleAndroid;
import com.administrator.platform.tools.codebuild.impl.codedealer.SourceCodeDealerMaven;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
import com.administrator.platform.tools.codebuild.intf.SourceCodeDealer;
import com.administrator.platform.tools.jacoco.ExecutionDataClient;
import com.administrator.platform.tools.jacoco.JacocoAgentTcpServer;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.jacoco.ReportGenerator;
import com.administrator.platform.tools.schedule.TaskDefine;
import com.administrator.platform.tools.shell.ganymed.GanymedSshClient;
import com.administrator.platform.tools.vcs.common.VCSType;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;
import com.administrator.platform.tools.vcs.jgit.JgitClient;
import com.administrator.platform.tools.vcs.svn.SvnClientUtil;
import com.administrator.platform.util.TempFileUtil;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service(value = "codeCoverageService")
@Order(3)
public class CodeCoverageServiceImpl implements CodeCoverageService {
	private static final Logger logger = LoggerFactory
	        .getLogger(CodeCoverageServiceImpl.class);

	@Autowired
	@Order(2)
	private CodeCoverageMapper codeCoverageMapper;

	@Autowired
	@Order(2)
	private ServerInfoService serverInfoService;

	@Autowired
	private TimerTaskService timerTaskService;

	@Autowired
	private TimerTaskPolicyService timerTaskPilicyService;

	/**
	 * @see com.administrator.platform.service.CodeCoverageService#findAllCodeCoverageList()
	 */
	@Override
	public List<CodeCoverage> findAllCodeCoverageList() {
		logger.debug("获取所有覆盖率信息列表");
		List<CodeCoverage> allCodeCoverages = codeCoverageMapper.findAll();
		// logger.debug("当前列表:{}", allCodeCoverages);

		return allCodeCoverages;
	}

	/**
	 * 添加代码覆盖率信息实现方法
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#addCodeCoverage(com.administrator.platform.model.CodeCoverage)
	 */
	@Override
	public CodeCoverage addCodeCoverage(CodeCoverage codeCoverage) {
		ValidationUtil.validateNull(codeCoverage, null);
		validateInput(codeCoverage);
		try {
			logger.debug("添加覆盖率信息:{}", codeCoverage);
			codeCoverageMapper.insert(codeCoverage);
			return codeCoverage;
		} catch (Exception e) {
			logger.error("新增代码覆盖率失败:{},{}", codeCoverage, e.getMessage());
			throw new BusinessValidationException("新增代码覆盖率失败!");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#deleteCodeCoverage(java.lang.Long)
	 */
	@Override
	public int deleteCodeCoverage(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			logger.debug("开始删除覆盖率报告,id为:{}", id);
			codeCoverageMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException("根据ID删除失败");
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#deleteCodeCoverage(java.lang.Long[])
	 */
	@Override
	public int deleteCodeCoverage(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			logger.debug("开始批量删除覆盖率报告,id为:{}", ArrayUtil.toString(ids));
			codeCoverageMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			logger.error("批量删除失败:{},{}", ids, e.getMessage());
			throw new BusinessValidationException("根据IDS批量删除失败");
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#findEnvAddrrsByProjectName(java.lang.String)
	 */
	@Override
	public List<CodeCoverage> findCodeCoverageesByProjectName(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称搜索覆盖率信息数据:{}", searchContent);
		return codeCoverageMapper
		        .findCodeCoverageesByProjectName(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#findEnvAddrrsByProjectName(java.lang.String)
	 */
	@Override
	public List<CodeCoverage> findCodeCoverageesByProjectNameLike(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称模糊搜索覆盖率信息数据:{}", searchContent);
		return codeCoverageMapper
		        .findCodeCoverageesByProjectNameLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param codeCoverage
	 *            : 待校验的地址对象
	 */
	private void validateInput(CodeCoverage codeCoverage) {
		validateInputBase(codeCoverage);
	}

	/**
	 * 校验具体业务，生成或者下载
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void validateInputCreateAndDownload(CodeCoverage codeCoverage) {
		validateInputBase(codeCoverage);
		validateJacocoAgentTcpServer(codeCoverage);
	}

	/**
	 * 校验tcpserver的连通性
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void validateJacocoAgentTcpServer(CodeCoverage codeCoverage) {
		// 如果是MAVEN或者ANT型的，则校验tcpserver
		if (BuildType.GRADLE != codeCoverage.getBuildType()) {
			ValidationUtil.validateStringNullOrEmpty(
			        codeCoverage.getTcpServerIp(),
			        "非GRADLE项目的jacoco监听tcpServerIp不能为空");

			List<JacocoAgentTcpServer> jacocoAgentTcpServers = JacocoAgentTcpServer
			        .parseJacocoAgentTcpServersFromCodeCoverage(codeCoverage);

			for (JacocoAgentTcpServer jacocoAgentTcpServer : jacocoAgentTcpServers) {
				/**
				 * 校验tcp服务器是否可用
				 */
				ExecutionDataClient executionDataClient = new ExecutionDataClient(
				        jacocoAgentTcpServer);
				executionDataClient.checkTcpServerCanBeConnected();
			}
		}
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param codeCoverage
	 *            : 待校验的地址对象
	 */
	private void validateInputBase(CodeCoverage codeCoverage) {
		// 判空
		ValidationUtil.validateNull(codeCoverage, null);

		if (!StringUtil.isStringAvaliable(codeCoverage.getProjectName())) {
			throw new BusinessValidationException("项目名称不能为空");
		}

		ValidationUtil.validateStringNullOrEmpty(
		        codeCoverage.getNewerRemoteUrl(), "新版本代码地址不能为空");

		// 最新代码地址长度
		ValidationUtil.validateStringAndLength(codeCoverage.getNewerRemoteUrl(),
		        null, CodeCoverageFormDefinition.REMOTE_URL_LENGTH, "新版本代码地址");

		// 旧版本代码地址长度
		ValidationUtil.validateStringAndLength(codeCoverage.getOlderRemoteUrl(),
		        null, CodeCoverageFormDefinition.REMOTE_URL_LENGTH,
		        "待比较版本代码地址");

		ValidationUtil.validateStringAndLength(codeCoverage.getProjectName(),
		        null, CodeCoverageFormDefinition.COMMON_FORM_FIELD_LENGTH,
		        "项目名称");

		ValidationUtil.validateStringAndLength(codeCoverage.getUsername(), null,
		        CodeCoverageFormDefinition.COMMON_FORM_FIELD_LENGTH,
		        "版本库访问用户名");

		ValidationUtil.validateStringAndLength(codeCoverage.getPassword(), null,
		        CodeCoverageFormDefinition.COMMON_FORM_FIELD_LENGTH, "版本库访问密码");

		ValidationUtil.validateStringAndLength(codeCoverage.getDescription(),
		        null, CodeCoverageFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH,
		        "备注");

		/**
		 * 如果要从服务器下载，则不能不填写服务器路径
		 */
		if (NewSourceType.SFTP == codeCoverage.getNewSourceType()) {
			ValidationUtil.validateStringNullOrEmpty(
			        codeCoverage.getSourceCodePath(), "SFTP模式下代码路径不允许为空");

			ValidationUtil.validateNull(codeCoverage.getServerId(),
			        "SFTP模式下不允许不选择服务器");
		}
	}

	/**
	 * 修改代码覆盖率
	 * 
	 * @param codeCoverage:地址对象
	 * @see com.administrator.platform.service.CodeCoverageService#updateCodeCoverage(com.administrator.platform.model.
	 *      CodeCoverage)
	 */
	@Override
	public CodeCoverage updateCodeCoverage(CodeCoverage codeCoverage) {
		logger.debug("更新覆盖率信息:{}", codeCoverage);
		ValidationUtil.validateNull(codeCoverage, null);
		CodeCoverage currentCodeCoverage = getCodeCoverageByObject(
		        codeCoverage);

		if (null == currentCodeCoverage) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		codeCoverage.setId(currentCodeCoverage.getId());

		// 校验输入内容
		validateInput(codeCoverage);
		try {
			codeCoverageMapper.updateByPrimaryKey(codeCoverage);
			logger.debug("更新覆盖率信息完成:{}", codeCoverage);
			return codeCoverage;
		} catch (Exception e) {
			logger.error("更新代码覆盖率失败:{},{}", codeCoverage, e.getMessage());
			throw new BusinessValidationException("更新代码覆盖率失败!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.CodeCoverageService#getCodeCoverageById(java.lang.Long)
	 */
	@Override
	public CodeCoverage getCodeCoverageById(Long id) {
		ValidationUtil.validateNull(id, null);
		logger.debug("根据ID查询详情:{}", id);
		return codeCoverageMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#getCodeCoverageByObject(com.administrator.platform.model.
	 *      CodeCoverage)
	 */
	@Override
	public CodeCoverage getCodeCoverageByObject(CodeCoverage codeCoverage) {
		ValidationUtil.validateNull(codeCoverage, null);
		logger.debug("根据内容查询详情:{}", codeCoverage);
		return getCodeCoverageById(codeCoverage.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @see com.administrator.platform.service.CodeCoverageService#findCodeCoverageByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<CodeCoverage> findCodeCoverageByPage(Integer page,
	        Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);

		logger.debug("分页查询覆盖率数据:第{}页,每页{}条", page, size);
		return codeCoverageMapper.findAll(pageable);
	}

	/**
	 * 生成全量测试覆盖率信息
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#createAllCodeCoverageData(com.administrator.platform.model.CodeCoverage)
	 */
	@Override
	public CodeCoverage createAllCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request) {

		logger.debug("生成全量覆盖率数据:{}", codeCoverage);
		/**
		 * 校验输入
		 */
		validateInputCreateAndDownload(codeCoverage);

		// /**
		// * 下代码
		// */
		// File newerFileFolder = VcsCommonUtil
		// .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		/**
		 * 处理代码
		 */
		prepareNewProjectData(codeCoverage);

		/**
		 * 如果需要编译代码，则执行编译，编译一次之后，就不再编译了
		 */

		if (codeCoverage.isNeedCompile()) {
			compileProjectData(codeCoverage);
		}

		// /**
		// * dump数据
		// */
		//
		// dumpCoverageData(codeCoverage);
		//
		// /**
		// * 获取覆盖率报告
		// */
		// logger.debug("开始获取全量覆盖率执行信息");
		// ReportGenerator.createWholeCodeCoverageDataWithMulti(codeCoverage);
		//
		// /**
		// * 生成完之后，修改是否需要编译字段，改为不编译
		// */
		//
		// codeCoverage.setNeedCompile(false);
		//
		// codeCoverage.setWholeCodeCoverageDataUrl(TempFileUtil
		// .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
		// request,
		// VcsCommonUtil.parseWholeCodeCoverageFolderPathZip(
		// newerFileFolder)));
		// return updateCodeCoverage(codeCoverage);
		return fastCreateAllCodeCoverageData(codeCoverage, request);
	}

	/**
	 * 生成全量测试覆盖率信息
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#createIncrementCodeCoverageData(com.administrator.platform.model.CodeCoverage)
	 */
	@Override
	public CodeCoverage createIncrementCodeCoverageData(
	        CodeCoverage codeCoverage, HttpServletRequest request) {
		logger.debug("开始生成增量覆盖率数据:{}", codeCoverage);
		/**
		 * 校验输入
		 */
		validateInputCreateAndDownload(codeCoverage);

		// File newerFileFolder = VcsCommonUtil
		// .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		/**
		 * 处理代码
		 */
		prepareAllProjectData(codeCoverage);

		/**
		 * 如果需要编译代码，则执行编译，编译一次之后，就不再编译了
		 */

		if (codeCoverage.isNeedCompile()) {
			compileProjectData(codeCoverage);
		} else {
			dealClassesFiles(codeCoverage);
		}

		// /**
		// * dump数据
		// */
		//
		// dumpCoverageData(codeCoverage);
		//
		// /**
		// * 获取覆盖率报告
		// */
		// logger.debug("开始获取增量覆盖率执行信息");
		// ReportGenerator
		// .createIncrementCodeCoverageReportWithMulti(codeCoverage);
		//
		// /**
		// * 生成完之后，修改是否需要编译字段，改为不编译
		// */
		//
		// codeCoverage.setNeedCompile(false);
		//
		// codeCoverage.setIncrementCodeCoverageDataUrl(TempFileUtil
		// .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
		// request,
		// VcsCommonUtil.parseIncrementCodeCoverageFolderPathZip(
		// newerFileFolder)));
		// return updateCodeCoverage(codeCoverage);
		return fastCreateIncrementCodeCoverageData(codeCoverage, request);
	}

	/**
	 * 重置代码覆盖率数据
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#resetCodeCoverageData(com.administrator.platform.model.CodeCoverage)
	 */
	@Override
	public CodeCoverage resetCodeCoverageData(CodeCoverage codeCoverage) {
		logger.debug("开始重置覆盖率数据，通过更新覆盖率信息的url来操作");

		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		File wholeCodeCoverageDataFolder = new File(VcsCommonUtil
		        .parseWholeCodeCoverageFolderPath(newerFileFolder));
		File incrementCodeCoverageDataFolder = new File(VcsCommonUtil
		        .parseIncrementCodeCoverageFolderPath(newerFileFolder));

		File wholeCodeCoverageDataFolderZip = new File(VcsCommonUtil
		        .parseWholeCodeCoverageFolderPathZip(newerFileFolder));
		File incrementCodeCoverageDataFolderZip = new File(VcsCommonUtil
		        .parseIncrementCodeCoverageFolderPathZip(newerFileFolder));

		File classesFileZip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_CLASSES_UPLOAD_FILE_NAME);

		File classesFileFolderAfterUnzip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_CLASSES_UPLOAD_FILE_NAME.replace(".zip",
		                ""));
		File srcAndClassesFileZip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME);
		File srcAndClassesFolderAfterUnzip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME
		                .replace(".zip", ""));

		File srcOldFileZip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME);
		File srcOldAfterUnzip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME.replace(".zip",
		                ""));

		FileUtil.forceDeleteDirectory(wholeCodeCoverageDataFolder);
		FileUtil.forceDeleteDirectory(incrementCodeCoverageDataFolder);
		FileUtil.deleteFile(wholeCodeCoverageDataFolderZip);
		FileUtil.deleteFile(incrementCodeCoverageDataFolderZip);
		FileUtil.deleteFile(classesFileZip);
		FileUtil.deleteFile(srcAndClassesFileZip);
		FileUtil.deleteFile(classesFileFolderAfterUnzip);
		FileUtil.deleteFile(srcAndClassesFolderAfterUnzip);
		FileUtil.deleteFile(srcOldFileZip);
		FileUtil.deleteFile(srcOldAfterUnzip);
		codeCoverage.setWholeCodeCoverageDataUrl(null);
		codeCoverage.setIncrementCodeCoverageDataUrl(null);
		return updateCodeCoverage(codeCoverage);
	}

	/**
	 * 根据配置dump数据
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void dumpCoverageData(CodeCoverage codeCoverage) {

		// 如果是ant或者maven构建，则需要直接去tcp server读取
		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		// 如果是gradle则需要从上传文件夹里找，并复制
		if (BuildType.GRADLE == codeCoverage.getBuildType()) {

			File execFile = new File(
			        TempFileUtil.getExecFileSavePath(codeCoverage),
			        JacocoDefine.DEFAULT_JACOCO_APP_EXEC_FILE_NAME);

			if (!execFile.exists()) {
				throw new BusinessValidationException(
				        "还没有上传客户端的coverage.ec,先上传才能生成报告");
			}

			new CodeBuildGradleAndroid().dumpExecDataFromUploadFile(
			        codeCoverage, newerFileFolder.getAbsolutePath(), execFile);

		} else {
			List<JacocoAgentTcpServer> jacocoAgentTcpServers = JacocoAgentTcpServer
			        .parseJacocoAgentTcpServersFromCodeCoverage(codeCoverage);

			for (JacocoAgentTcpServer jacocoAgentTcpServer : jacocoAgentTcpServers) {

				/**
				 * dump数据
				 */
				ExecutionDataClient executionDataClient = new ExecutionDataClient(
				        jacocoAgentTcpServer);
				executionDataClient.dumpData(newerFileFolder.getAbsolutePath(),
				        codeCoverage.getBuildType());
			}
		}
	}

	/**
	 * 根据传入的数据进行编译
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void compileProjectData(CodeCoverage codeCoverage) {

		/**
		 * 如果是sftp，则不用编译
		 */

		if (NewSourceType.SFTP == codeCoverage.getNewSourceType()) {
			return;
		}

		/**
		 * 如果有依赖代码，需要先下载并install
		 */
		if (!StringUtil.isEmpty(codeCoverage.getDependencyProjects())) {
			logger.debug("有依赖代码，需要对依赖代码进行编译安装");
		}

		logger.debug("开始编译代码");

		SourceCodeDealer sourceCodeDealer = getSourceCodeDealer(codeCoverage);

		sourceCodeDealer.installSourceCode(codeCoverage);

		/**
		 * 生成完之后，修改是否需要编译字段，改为不编译
		 */
		codeCoverage.setNeedCompile(false);
	}

	private SourceCodeDealer getSourceCodeDealer(CodeCoverage codeCoverage) {
		SourceCodeDealer sourceCodeDealer = null;
		switch (codeCoverage.getBuildType()) {
			case ANT:
				sourceCodeDealer = new SourceCodeDealerAnt();
				break;
			case MAVEN:
				sourceCodeDealer = new SourceCodeDealerMaven();
				break;
			case GRADLE:
				sourceCodeDealer = new SourceCodeDealerGradleAndroid();
				break;
			default:
				logger.warn("目前仅支持ANT、MAVEN、Gradle，没有设置好构件类型，将被设置为默认MAVEN");
				sourceCodeDealer = new SourceCodeDealerMaven();
				break;
		}

		return sourceCodeDealer;
	}

	private CodeBuildIntf getCodeBuild(CodeCoverage codeCoverage) {
		CodeBuildIntf codeBuild = null;
		switch (codeCoverage.getBuildType()) {
			case ANT:
				codeBuild = new CodeBuildAnt();
				break;
			case MAVEN:
				codeBuild = new CodeBuildMaven();
				break;
			case GRADLE:
				codeBuild = new CodeBuildGradleAndroid();
				break;
			default:
				logger.warn("目前仅支持ANT、MAVEN、Gradle，没有设置好构件类型，将被设置为默认MAVEN");
				codeBuild = new CodeBuildMaven();
				break;
		}

		return codeBuild;
	}

	/**
	 * 预处理代码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void prepareNewProjectData(CodeCoverage codeCoverage) {
		if (NewSourceType.SFTP == codeCoverage.getNewSourceType()) {
			prepareNewProjectDataWithDownloading(codeCoverage);
		} else {
			prepareNewProjectDataWithVersionControlSystem(codeCoverage);
		}
	}

	/**
	 * 下载代码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void prepareNewProjectDataWithDownloading(
	        CodeCoverage codeCoverage) {
		if (NewSourceType.SFTP != codeCoverage.getNewSourceType()) {
			logger.error("非sftp模式，不允许sftp下载");
			return;
		}

		/**
		 * sftp模式下，从服务器上下载
		 */

		ServerInfo serverInfo = serverInfoService
		        .getServerInfoById(codeCoverage.getServerId());

		if (null == serverInfo) {
			throw new BusinessValidationException("sftp模式下，没有找到合适的服务器");
		}

		File newFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		if (newFileFolder.exists()) {
			logger.debug("新代码路径存在，删除");
			FileUtil.deleteDirectory(newFileFolder);
		}

		GanymedSshClient ganymedSshClient = new GanymedSshClient(
		        serverInfo.getServerIp(), serverInfo.getUsername(),
		        serverInfo.getPassword());
		ganymedSshClient.downloadFolder(codeCoverage.getSourceCodePath(),
		        newFileFolder.getAbsolutePath());
	}

	/**
	 * 下载代码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void prepareNewProjectDataWithVersionControlSystem(
	        CodeCoverage codeCoverage) {
		if (dealSrcAndClassesFromUploading(codeCoverage)) {
			logger.debug("已经复制了源码和class文件，不用再下载");
			return;
		}

		/**
		 * 下代码
		 */
		logger.debug("开始处理代码,因为没有上传源码和class文件，需要自己下载");
		VCSType vcsType = codeCoverage.getVersionControlType();
		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);
		SvnClientUtil svnClientUtilNewer = null;

		if (vcsType == VCSType.GIT) {
			JgitClient jgitClient = JgitClient.fromUsernameAndPassword(
			        codeCoverage.getUsername(), codeCoverage.getPassword());
			jgitClient.gitCloneAndGitPull(codeCoverage.getNewerRemoteUrl(),
			        newerFileFolder, codeCoverage.getNewerVersion());
		} else if (vcsType == VCSType.SVN) {
			svnClientUtilNewer = new SvnClientUtil(
			        codeCoverage.getNewerRemoteUrl(), codeCoverage);
			svnClientUtilNewer.checkoutDefault(codeCoverage.getNewerRemoteUrl(),
			        newerFileFolder.getAbsolutePath(),
			        codeCoverage.getNewerVersion());
		} else {
			throw new BusinessValidationException("版本控制系统不在svn和git之中，需要特殊处理哦！");
		}
	}

	/**
	 * 预处理代码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void prepareOldProjectData(CodeCoverage codeCoverage) {

		if (dealSrcFromUploading(codeCoverage)) {
			logger.debug("已经复制了旧工程文件，不用再下载");
			return;
		}

		VCSType vcsType = codeCoverage.getVersionControlType();
		File olderFileFolder = VcsCommonUtil
		        .parseOldProjectFolderFromCodeCoverage(codeCoverage);

		if (olderFileFolder.exists()) {
			FileUtil.forceDeleteDirectory(olderFileFolder);
		}

		SvnClientUtil svnClientUtilOlder = null;

		if (vcsType == VCSType.GIT) {
			JgitClient jgitClient = JgitClient.fromUsernameAndPassword(
			        codeCoverage.getUsername(), codeCoverage.getPassword());
			jgitClient.gitCloneAndGitPull(codeCoverage.getOlderRemoteUrl(),
			        olderFileFolder, codeCoverage.getOlderVersion());
		} else if (vcsType == VCSType.SVN) {
			svnClientUtilOlder = new SvnClientUtil(
			        codeCoverage.getOlderRemoteUrl(), codeCoverage);
			svnClientUtilOlder.checkoutDefault(codeCoverage.getOlderRemoteUrl(),
			        olderFileFolder.getAbsolutePath(),
			        codeCoverage.getOlderVersion());
		} else {
			throw new BusinessValidationException("版本控制系统不在svn和git之中，需要特殊处理哦！");
		}
	}

	/**
	 * 从上传的文件中，处理源码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private boolean dealSrcAndClassesFromUploading(CodeCoverage codeCoverage) {
		if (!srcAndClassesZipFileExistsFromUploading(codeCoverage)) {
			return false;
		}

		logger.debug("该项目上传了源码和class文件，直接复用");
		File srcAndClassFile = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME);
		File newerFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		File srcAndClassesFolderAfterUnzip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME
		                .replace(".zip", ""));

		if (srcAndClassesFolderAfterUnzip.exists()) {
			FileUtil.deleteDirectory(srcAndClassesFolderAfterUnzip);
		}

		try {
			ZipCompress.unzip(srcAndClassFile.getAbsolutePath(),
			        srcAndClassesFolderAfterUnzip.getAbsolutePath(), false);
		} catch (Exception e) {
			logger.error("解压文件失败,失败原因:{}", e.getMessage());
			throw new BusinessValidationException("解压文件失败");
		}

		// 解压之后拷贝

		logger.debug("解压之后，把文件复制到新的代码路径中");
		try {
			FileUtil.copyDir(
			        srcAndClassesFolderAfterUnzip.getAbsolutePath()
			                + File.separator,
			        newerFolder.getAbsolutePath() + File.separator);
			logger.debug("文件复制完成");
			return true;
		} catch (IOException e) {
			logger.error("复制文件失败，失败原因:{}", e.getMessage());
			throw new BusinessValidationException("复制文件失败");
		}
	}

	/**
	 * 从上传的文件中，处理源码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private boolean dealSrcFromUploading(CodeCoverage codeCoverage) {
		if (!srcZipFileExistsFromUploading(codeCoverage)) {
			return false;
		}

		logger.debug("该项目上传了源码文件，直接复用");
		File srcFile = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME);
		File olderFolder = VcsCommonUtil
		        .parseOldProjectFolderFromCodeCoverage(codeCoverage);

		File srcFolderAfterUnzip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME.replace(".zip",
		                ""));

		if (srcFolderAfterUnzip.exists()) {
			FileUtil.deleteDirectory(srcFolderAfterUnzip);
		}

		try {
			ZipCompress.unzip(srcFile.getAbsolutePath(),
			        srcFolderAfterUnzip.getAbsolutePath(), false);
		} catch (Exception e) {
			logger.error("解压文件失败,失败原因:{}", e.getMessage());
			throw new BusinessValidationException("解压文件失败");
		}

		// 解压之后拷贝

		logger.debug("解压之后，把文件复制到新的代码路径中");
		try {
			FileUtil.copyDir(
			        srcFolderAfterUnzip.getAbsolutePath() + File.separator,
			        olderFolder.getAbsolutePath() + File.separator);
			logger.debug("文件复制完成");
			return true;
		} catch (IOException e) {
			logger.error("复制文件失败，失败原因:{}", e.getMessage());
			throw new BusinessValidationException("复制文件失败");
		}
	}

	/**
	 * 判断是否有上传的
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param codeCoverage
	 * @return
	 */
	private boolean srcAndClassesZipFileExistsFromUploading(
	        CodeCoverage codeCoverage) {
		File srcAndClassesZip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME);

		if (!srcAndClassesZip.exists()) {
			return false;
		}

		return true;
	}

	/**
	 * 判断是否有上传的
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param codeCoverage
	 * @return
	 */
	private boolean srcZipFileExistsFromUploading(CodeCoverage codeCoverage) {
		File srcAndClassesZip = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_SRC_OLD_UPLOAD_FILE_NAME);

		if (!srcAndClassesZip.exists()) {
			return false;
		}

		return true;
	}

	/**
	 * 预处理代码
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void prepareAllProjectData(CodeCoverage codeCoverage) {
		/**
		 * 下代码
		 */
		logger.debug("开始处理所有新旧代码");

		prepareNewProjectData(codeCoverage);
		prepareOldProjectData(codeCoverage);
	}

	/**
	 * 处理class文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void dealClassesFiles(CodeCoverage codeCoverage) {

		if (NewSourceType.SFTP == codeCoverage.getNewSourceType()) {
			return;
		}

		File classZipFile = new File(
		        TempFileUtil.getClassesFileSavePath(codeCoverage),
		        JacocoDefine.DEFAULT_CLASSES_UPLOAD_FILE_NAME);

		if (BuildType.GRADLE == codeCoverage.getBuildType()) {
			return;
		}

		// 如果不存在，则不做操作，继续后续步骤
		if (!classZipFile.exists()) {
			logger.warn("没有上传classes.zip文件，将不进行class文件替换");
			return;
		}

		// 检查上传的文件中是否存在class文件，如果存在，则解压，并替换到文件夹中
		try {
			ZipCompress.unzip(classZipFile.getAbsolutePath(), null, false);
			cleanProject(codeCoverage);
			copyClasses(codeCoverage);
		} catch (Exception e) {
			logger.error("解压文件失败:{}:错误信息:{}", classZipFile.getAbsolutePath(),
			        e.getMessage());
			throw new BusinessValidationException("解压zip文件失败，检查文件是否有损坏");
		}
	}

	/**
	 * 清理工程
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void cleanProject(CodeCoverage codeCoverage) {
		CodeBuildIntf codeBuildIntf = getCodeBuild(codeCoverage);
		codeBuildIntf.cleanProject(VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage)
		        .getAbsolutePath());
	}

	/**
	 * copyclass文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	private void copyClasses(CodeCoverage codeCoverage) {
		CodeBuildIntf codeBuildIntf = getCodeBuild(codeCoverage);
		File file = new File(TempFileUtil.getClassesFileSavePath(codeCoverage),
		        "classes");
		codeBuildIntf.copyClasses(file.getAbsolutePath(),
		        VcsCommonUtil
		                .parseNewProjectFolderFromCodeCoverage(codeCoverage)
		                .getAbsolutePath());
	}

	@Override
	public void timerTaskCollectingExecDataAndBackUp(
	        CodeCoverage codeCoverage) {
		List<JacocoAgentTcpServer> jacocoAgentTcpServers = JacocoAgentTcpServer
		        .parseJacocoAgentTcpServersFromCodeCoverage(codeCoverage);

		for (int i = 0; i < jacocoAgentTcpServers.size(); i++) {
			new ExecutionDataClient(jacocoAgentTcpServers.get(0))
			        .backUpExecData();
		}

	}

	@Override
	public TimerTask configTimerTaskAndChangeStatus(String cronConfig,
	        Long codeCoverageId, boolean enabled) {
		CodeCoverage codeCoverage = getCodeCoverageById(codeCoverageId);
		validateJacocoAgentTcpServer(codeCoverage);

		TimerTask timerTask = null;

		List<TimerTask> currentTimerTasksWithThisName = timerTaskService
		        .findTimerTasksByTimerTaskName(codeCoverage.getProjectName());

		/**
		 * 如果当前没有这个任务，则创建，如果有，则更新
		 */
		if (currentTimerTasksWithThisName.isEmpty()) {
			timerTask = new TimerTask();
		} else {
			timerTask = currentTimerTasksWithThisName.get(0);
		}

		/**
		 * 定义策略
		 */

		timerTask.setTaskName(codeCoverage.getProjectName());
		timerTask.setConfig(cronConfig);
		timerTask.setClosed(!enabled);
		TimerTaskPolicy timerTaskPolicy = timerTaskPilicyService
		        .findTimerTaskPoliciesByEname(
		                TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_ENAME)
		        .get(0);

		timerTask.setPolicyId(timerTaskPolicy.getId());

		String otherParams = String.format("%s=%s",
		        TaskDefine.CODE_COVERAGER_ID_KEY, codeCoverage.getId());
		timerTask.setOtherParams(otherParams);
		timerTask.setDescription(
		        String.format("这是为覆盖率数据:%s,创建的自动采集任务。", codeCoverage.getId()));

		if (currentTimerTasksWithThisName.isEmpty()) {
			timerTask = timerTaskService.addTimerTask(timerTask);
		} else {
			timerTask = timerTaskService.updateTimerTask(timerTask);
		}

		if (enabled) {
			timerTaskService.changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.OPEN_TASK_OPERATION);
		} else {
			timerTaskService.changeTimerTaskStatus(timerTask.getId(),
			        TaskDefine.CLOSE_TASK_OPERATION);
		}

		return timerTask;
	}

	public static void main(String[] args) {
		CodeCoverage codeCoverage = new CodeCoverage();
		codeCoverage.setProjectName("浙江省平台");
		codeCoverage.setTcpServerIp(
		        "192.168.110.24:8888;192.168.110.25:8888;192.168.110.29:8888;192.168.110.30:8888;192.168.110.22:8888");
		codeCoverage.setTcpServerPort(-1);
		codeCoverage.setUsername("liqingnan@hztianque.com");
		codeCoverage.setPassword("cmm123@@@@");
		codeCoverage.setVersionControlType(VCSType.GIT);
		codeCoverage.setNewerRemoteUrl(
		        "http://liqingnan@gitlab.hztianque.com/zjgroup/newshengPingTai.git");
		codeCoverage.setNewerVersion("release/2019.11.14_release");
		codeCoverage.setOlderRemoteUrl(
		        "http://liqingnan@gitlab.hztianque.com/zjgroup/newshengPingTai.git");
		codeCoverage.setOlderVersion("release/2019.10.24_release");
		codeCoverage.setBuildType(BuildType.ANT);
		codeCoverage.setNeedCompile(false);
		codeCoverage.setJdkVersion("1.8");
		codeCoverage.setSourceCodePath("sourceCodePath");
		codeCoverage.setNewSourceType(NewSourceType.SFTP);
		CodeCoverageServiceImpl codeCoverageServiceImpl = new CodeCoverageServiceImpl();
		codeCoverageServiceImpl.dumpCoverageData(codeCoverage);
		ReportGenerator
		        .createIncrementCodeCoverageReportWithMulti(codeCoverage);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#fastCreateAllCodeCoverageData(com.administrator.platform.model.CodeCoverage,
	 *      javax.servlet.http.HttpServletRequest)
	 * @param codeCoverage
	 * @param request
	 * @return
	 */
	@Override
	public CodeCoverage fastCreateAllCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request) {
		logger.debug("开始快速生成全量覆盖率数据:{}", codeCoverage);
		// /**
		// * 校验输入
		// */
		// validateInputCreateAndDownload(codeCoverage);
		//
		// /**
		// * 下代码
		// */
		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		// /**
		// * 处理代码
		// */
		// prepareNewProjectData(codeCoverage);
		//
		// /**
		// * 如果需要编译代码，则执行编译，编译一次之后，就不再编译了
		// */
		//
		// if (codeCoverage.isNeedCompile()) {
		// compileProjectData(codeCoverage);
		// }

		/**
		 * dump数据
		 */

		dumpCoverageData(codeCoverage);

		/**
		 * 获取覆盖率报告
		 */
		logger.debug("开始获取全量覆盖率执行信息");
		ReportGenerator.createWholeCodeCoverageDataWithMulti(codeCoverage);

		/**
		 * 生成完之后，修改是否需要编译字段，改为不编译
		 */

		codeCoverage.setNeedCompile(false);

		codeCoverage.setWholeCodeCoverageDataUrl(TempFileUtil
		        .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
		                request,
		                VcsCommonUtil.parseWholeCodeCoverageFolderPathZip(
		                        newerFileFolder)));
		return updateCodeCoverage(codeCoverage);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#fastCreateIncrementCodeCoverageData(com.administrator.platform.model.CodeCoverage,
	 *      javax.servlet.http.HttpServletRequest)
	 * @param codeCoverage
	 * @param request
	 * @return
	 */
	@Override
	public CodeCoverage fastCreateIncrementCodeCoverageData(
	        CodeCoverage codeCoverage, HttpServletRequest request) {
		logger.debug("开始快速生成增量覆盖率数据:{}", codeCoverage);
		// /**
		// * 校验输入
		// */
		// validateInputCreateAndDownload(codeCoverage);
		//
		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);

		// /**
		// * 处理代码
		// */
		// prepareAllProjectData(codeCoverage);
		//
		// /**
		// * 如果需要编译代码，则执行编译，编译一次之后，就不再编译了
		// */
		//
		// if (codeCoverage.isNeedCompile()) {
		// compileProjectData(codeCoverage);
		// } else {
		// dealClassesFiles(codeCoverage);
		// }

		/**
		 * dump数据
		 */

		dumpCoverageData(codeCoverage);

		/**
		 * 获取覆盖率报告
		 */
		logger.debug("开始获取增量覆盖率执行信息");
		ReportGenerator
		        .createIncrementCodeCoverageReportWithMulti(codeCoverage);

		/**
		 * 生成完之后，修改是否需要编译字段，改为不编译
		 */

		codeCoverage.setNeedCompile(false);

		codeCoverage.setIncrementCodeCoverageDataUrl(TempFileUtil
		        .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
		                request,
		                VcsCommonUtil.parseIncrementCodeCoverageFolderPathZip(
		                        newerFileFolder)));
		return updateCodeCoverage(codeCoverage);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.CodeCoverageService#clearBackUpExecData(com.administrator.platform.model.CodeCoverage)
	 * @param codeCoverage
	 * @return
	 */
	@Override
	public CodeCoverage clearBackUpExecData(CodeCoverage codeCoverage) {
		List<JacocoAgentTcpServer> jacocoAgentTcpServers = JacocoAgentTcpServer
		        .parseJacocoAgentTcpServersFromCodeCoverage(codeCoverage);

		if (null == jacocoAgentTcpServers || jacocoAgentTcpServers.isEmpty()) {
			logger.debug("没有覆盖率服务器信息，不需要删除");
			return codeCoverage;
		}

		for (JacocoAgentTcpServer jacocoAgentTcpServer : jacocoAgentTcpServers) {
			String filePathName = ExecutionDataClient
			        .getExecFilesBackUpFolderFromJacocoAgentServer(
			                jacocoAgentTcpServer);

			File backUpExecFile = new File(filePathName,
			        JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);

			if (backUpExecFile.exists()) {
				FileUtil.forceDeleteDirectory(backUpExecFile);
			}
		}
		return codeCoverage;
	}
}
