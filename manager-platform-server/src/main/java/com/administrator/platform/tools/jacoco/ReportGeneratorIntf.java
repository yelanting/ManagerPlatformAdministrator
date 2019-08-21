/// **
// * @author : 孙留平
// * @since : 2019年2月20日 下午4:36:25
// * @see:
// */
// package com.administrator.platform.tools.jacoco;
//
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.zip.ZipOutputStream;
//
// import org.apache.commons.io.FileUtils;
// import org.jacoco.core.analysis.Analyzer;
// import org.jacoco.core.analysis.CoverageBuilder;
// import org.jacoco.core.analysis.IBundleCoverage;
// import org.jacoco.core.diff.MethodInfo;
// import org.jacoco.core.tools.ExecFileLoader;
// import org.jacoco.report.DirectorySourceFileLocator;
// import org.jacoco.report.FileMultiReportOutput;
// import org.jacoco.report.IReportVisitor;
// import org.jacoco.report.ZipMultiReportOutput;
// import org.jacoco.report.html.HTMLFormatter;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.core.io.DefaultResourceLoader;
// import org.springframework.core.io.Resource;
//
// import com.administrator.platform.core.base.util.FileUtil;
// import com.administrator.platform.core.base.util.StringUtil;
// import com.administrator.platform.definition.form.GlobalDefination;
// import com.administrator.platform.exception.base.BusinessValidationException;
// import com.administrator.platform.model.CodeCoverage;
// import com.administrator.platform.tools.codebuild.entity.BuildType;
// import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
// import com.administrator.platform.tools.jacoco.diff.DiffAST;
// import com.administrator.platform.tools.vcs.common.VcsCommonUtil;
//
/// **
// * @author : Administrator
// * @since : 2019年2月20日 下午4:36:25
// * @see :
// */
// public class ReportGeneratorIntf {
// private static final Logger logger = LoggerFactory
// .getLogger(ReportGeneratorIntf.class);
//
// public ReportGeneratorIntf() {
//
// }
//
// /**
// * 初始化
// *
// * @see :
// * @param :
// * @return : CodeCoverageFilesAndFoldersDTO
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// */
// private static CodeCoverageFilesAndFoldersDTO
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// return initDefaultCodeCoverageFilesAndFoldersDTO(
// codeCoverageFilesAndFoldersDTO.getProjectDir());
// }
//
// /**
// * 初始化
// *
// * @see :
// * @param :
// * @return : CodeCoverageFilesAndFoldersDTO
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// */
// private static CodeCoverageFilesAndFoldersDTO
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// File projectFolder) {
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp = new
/// CodeCoverageFilesAndFoldersDTO();
// codeCoverageFilesAndFoldersDTOTemp.setProjectDir(projectFolder);
// if (null == codeCoverageFilesAndFoldersDTOTemp.getClassesDirectory()) {
//
// codeCoverageFilesAndFoldersDTOTemp.setClassesDirectory(
// new File(projectFolder.getAbsolutePath() + File.separator
// + JacocoDefine.DEFAULT_CLASSES_FOLDER));
// }
//
// if (null == codeCoverageFilesAndFoldersDTOTemp.getSourceDirectory()) {
// codeCoverageFilesAndFoldersDTOTemp.setSourceDirectory(
// new File(projectFolder.getAbsolutePath() + File.separator
// + JacocoDefine.DEFAULT_SOURCE_FOLDER));
// }
//
// if (null == codeCoverageFilesAndFoldersDTOTemp.getExecutionDataFile()) {
// codeCoverageFilesAndFoldersDTOTemp.setExecutionDataFile(
// new File(projectFolder.getAbsolutePath() + File.separator
// + JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT));
// }
//
// if (null == codeCoverageFilesAndFoldersDTOTemp.getReportDirectory()) {
// codeCoverageFilesAndFoldersDTOTemp.setReportDirectory(
// new File(projectFolder.getAbsolutePath() + File.separator
// + JacocoDefine.CODE_COVERAGE_DATA_FOLDER));
// }
// if (null == codeCoverageFilesAndFoldersDTOTemp
// .getIncrementReportDirectory()) {
// codeCoverageFilesAndFoldersDTOTemp.setIncrementReportDirectory(
// new File(projectFolder.getAbsolutePath() + File.separator
// + JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER));
// }
// logger.debug("初始化后的codeCoverageFilesAndFoldersDTO:{}",
// codeCoverageFilesAndFoldersDTOTemp);
// return codeCoverageFilesAndFoldersDTOTemp;
// }
//
// /**
// * 初始化
// *
// * @see :
// * @param :
// * @return : CodeCoverageFilesAndFoldersDTO
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// */
// private static CodeCoverageFilesAndFoldersDTO
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// String projectDir) {
// return initDefaultCodeCoverageFilesAndFoldersDTO(new File(projectDir));
// }
// // public ReportGenerator(final File projectDirectory) {
// // this.titleLocal = projectDirectory.getName();
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = new
// // CodeCoverageFilesAndFoldersDTO();
// // codeCoverageFilesAndFoldersDTO.setExecutionDataFile(new File(
// // projectDirectory, JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT));
// // codeCoverageFilesAndFoldersDTO.setClassesDirectory(new File(
// // projectDirectory, JacocoDefine.DEFAULT_CLASSES_FOLDER));
// //
// // codeCoverageFilesAndFoldersDTO.setSourceDirectory(new File(
// // projectDirectory.getAbsolutePath() + File.separator + "\\",
// // JacocoDefine.DEFAULT_SOURCE_FOLDER));
// //
// // // 要保存报告的地址
// // codeCoverageFilesAndFoldersDTO.setReportDirectory(new
// // File(projectDirectory,
// // JacocoDefine.CODE_COVERAGE_DATA_FOLDER));
// // codeCoverageFilesAndFoldersDTO
// // .setIncrementReportDirectory(new File(projectDirectory,
// // JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER));
// //
// // this.codeCoverageFilesAndFoldersDTOLocal =
// // codeCoverageFilesAndFoldersDTO;
// // }
//
// // /**
// // * 生成全量报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @throws IOException
// // */
// // public void create() throws IOException {
// // loadExecutionData();
// // final IBundleCoverage bundleCoverage = analyzeStructure();
// // createReport(bundleCoverage);
// //
// // }
//
// /**
// * 生成全量报告
// *
// * @see :
// * @param :
// * @return : void
// * @throws IOException
// */
// public static void createWholeCodeCoverageData(CodeCoverage codeCoverage)
// throws IOException {
// File currentProjectDir = VcsCommonUtil
// .parseNewProjectFolderFromCodeCoverage(codeCoverage);
//
// List<String> moduleList = CodeBuildMaven
// .getModulesFromPomFile(currentProjectDir.getAbsolutePath());
// for (String eachModule : moduleList) {
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp = new
/// CodeCoverageFilesAndFoldersDTO();
// codeCoverageFilesAndFoldersDTOTemp.setProjectDir(
// new File(currentProjectDir.getAbsolutePath(), eachModule));
// codeCoverageFilesAndFoldersDTOTemp =
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// codeCoverageFilesAndFoldersDTOTemp);
// final IBundleCoverage bundleCoverage = analyzeStructure(
// codeCoverageFilesAndFoldersDTOTemp);
// createReport(bundleCoverage, codeCoverageFilesAndFoldersDTOTemp);
// }
// }
//
// /**
// * 生成全量报告
// *
// * @see :
// * @param :
// * @return : void
// * @throws IOException
// */
// public static void createWholeCodeCoverageData(
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// File currentProjectDir = codeCoverageFilesAndFoldersDTO.getProjectDir();
//
// List<String> moduleList = CodeBuildMaven
// .getModulesFromPomFile(currentProjectDir.getAbsolutePath());
// for (String eachModule : moduleList) {
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp = new
/// CodeCoverageFilesAndFoldersDTO();
// codeCoverageFilesAndFoldersDTOTemp.setProjectDir(
// new File(currentProjectDir.getAbsolutePath(), eachModule));
// codeCoverageFilesAndFoldersDTOTemp =
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// codeCoverageFilesAndFoldersDTOTemp);
// final IBundleCoverage bundleCoverage = analyzeStructure(
// codeCoverageFilesAndFoldersDTOTemp);
// createReport(bundleCoverage, codeCoverageFilesAndFoldersDTOTemp);
// }
// }
//
// // /**
// // * 根据变更文件列表生成报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // */
// // public void create(List<ChangeFile> changeFiles) {
// // loadExecutionData();
// // final IBundleCoverage bundleCoverage = analyzeStructure(changeFiles,
// // this.codeCoverageFilesAndFoldersDTOLocal.getSourceDirectory()
// // .getAbsolutePath(),
// // this.codeCoverageFilesAndFoldersDTOLocal.getClassesDirectory()
// // .getAbsolutePath());
// // try {
// // createReport(bundleCoverage);
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
//
// // /**
// // * 在某个目录生成覆盖率报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // * @param reportDir
// // */
// // public static void createWithChangeFiles(List<ChangeFile> changeFiles,
// // String reportDir, CodeCoverage codeCoverage) {
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = new
// // CodeCoverageFilesAndFoldersDTO();
// // codeCoverageFilesAndFoldersDTO.setProjectDir(VcsCommonUtil
// // .parseNewProjectFolderFromCodeCoverage(codeCoverage));
// // createWithChangeFiles(changeFiles, codeCoverageFilesAndFoldersDTO);
// // }
//
// // /**
// // * 在某个目录生成覆盖率报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // * @param reportDir
// // */
// // public static void createWithChangeFiles(List<ChangeFile> changeFiles,
// // String reportDir,
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// // logger.debug("开始在:{}下生成变更报告:{}", reportDir,
// // codeCoverageFilesAndFoldersDTO);
// // final IBundleCoverage bundleCoverage = analyzeStructure(changeFiles,
// // codeCoverageFilesAndFoldersDTO.getSourceDirectory()
// // .getAbsolutePath(),
// // codeCoverageFilesAndFoldersDTO.getClassesDirectory()
// // .getAbsolutePath(),
// // codeCoverageFilesAndFoldersDTO);
// // try {
// // if (null == bundleCoverage) {
// // logger.debug("在:{}下未生成变更报告:{}，因为没有变更信息", reportDir,
// // codeCoverageFilesAndFoldersDTO);
// // return;
// // }
// // createReport(bundleCoverage, reportDir, codeCoverageFilesAndFoldersDTO);
// // copyDiffFilesToReportSource(reportDir);
// // modifyReportAccordingToChangedFiles(changeFiles, reportDir);
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
//
// // /**
// // * 在某个目录生成覆盖率报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // * @param reportDir
// // */
// // public static void createWithChangeFiles(List<ChangeFile> changeFiles,
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// // logger.debug("开始生成变更报告:{}", codeCoverageFilesAndFoldersDTO);
// // try {
// // final IBundleCoverage bundleCoverage = analyzeStructureWithChangeFiles(
// // changeFiles, codeCoverageFilesAndFoldersDTO);
// // if (null == bundleCoverage) {
// // logger.debug("未生成变更报告:{}，因为没有变更信息", codeCoverageFilesAndFoldersDTO);
// // return;
// // }
// //
// // String reportDir = codeCoverageFilesAndFoldersDTO
// // .getIncrementReportDirectory().getAbsolutePath();
// // createReport(bundleCoverage, reportDir, codeCoverageFilesAndFoldersDTO);
// // copyDiffFilesToReportSource(reportDir);
// // modifyReportAccordingToChangedFiles(changeFiles, reportDir);
// // FileUtil.zipCompressFile(reportDir, null);
// // } catch (IOException e) {
// // e.printStackTrace();
// // throw new BusinessValidationException("生成覆盖率报告失败！");
// // }
// // }
//
// /**
// * 在某个目录生成覆盖率报告
// *
// * @see :
// * @param :
// * @return : void
// * @param changeMethods
// * @param reportDir
// */
// public static void createWithChangeMethods(List<MethodInfo> changeMethods,
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// logger.debug("开始生成变更报告:{}", codeCoverageFilesAndFoldersDTO);
// try {
// final IBundleCoverage bundleCoverage = analyzeStructureWithChangeMethods(
// changeMethods, codeCoverageFilesAndFoldersDTO);
// if (null == bundleCoverage) {
// logger.debug("未生成变更报告:{}，因为没有变更信息",
// codeCoverageFilesAndFoldersDTO);
// return;
// }
//
// String reportDir = codeCoverageFilesAndFoldersDTO
// .getIncrementReportDirectory().getAbsolutePath();
// createReport(bundleCoverage, reportDir,
// codeCoverageFilesAndFoldersDTO);
// // copyDiffFilesToReportSource(reportDir);
// // modifyReportAccordingToChangedFiles(changeFiles, reportDir);
// // FileUtil.zipCompressFile(reportDir, null);
// } catch (IOException e) {
// e.printStackTrace();
// throw new BusinessValidationException("生成覆盖率报告失败！");
// }
// }
//
// /**
// * 把样式文件和diff的标记文件拷贝到覆盖率目录中
// *
// * @see :
// * @param :
// * @return : void
// * @param reportDir
// */
// private static void copyDiffFilesToReportSource(String reportDir) {
// File reportDirFile = new File(reportDir);
//
// if (!reportDirFile.exists()) {
// return;
// }
//
// if (!reportDirFile.isDirectory()) {
// return;
// }
//
// File[] children = reportDirFile.listFiles();
//
// Resource resourceDiff = new DefaultResourceLoader()
// .getResource(JacocoDefine.DIFF_GIF_CLASSPATH);
//
// Resource resourceReportCss = new DefaultResourceLoader()
// .getResource(JacocoDefine.REPORT_CSS_CLASSPATH);
//
// try {
// for (File file : children) {
//
// if (!file.isDirectory()) {
// continue;
// }
// if (file.getName()
// .equals(JacocoDefine.JACOCO_RESOURCE_FOLDER_NAME)) {
// FileUtils.copyFileToDirectory(resourceDiff.getFile(), file);
// FileUtils.copyFileToDirectory(resourceReportCss.getFile(),
// file);
// copyDiffFilesToReportSource(file.getAbsolutePath());
// }
// }
// } catch (Exception e) {
// logger.error("复制样式文件失败:{}", e.getMessage());
// }
// }
//
// // /**
// // * 生成增量覆盖率报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // */
// // public static void createIncrementCodeCoverageReportWithChangeFiles(
// // List<ChangeFile> changeFiles, CodeCoverage codeCoverage) {
// // File currentProjectDir = VcsCommonUtil
// // .parseNewProjectFolderFromCodeCoverage(codeCoverage);
// //
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO =
// // initDefaultCodeCoverageFilesAndFoldersDTO(
// // currentProjectDir);
// // List<String> moduleList = MavenProjectUtil
// // .getModulesFromPomFile(currentProjectDir.getAbsolutePath());
// //
// // if (moduleList.isEmpty()) {
// // logger.debug("currentProjectDir:{}没有子模块，生成单独工程的覆盖率报告",
// // currentProjectDir.getAbsolutePath());
// // createWithChangeFiles(changeFiles, codeCoverageFilesAndFoldersDTO);
// // } else {
// // logger.debug("开始生成多模块覆盖率报告");
// // for (String eachModule : moduleList) {
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp =
// // initDefaultCodeCoverageFilesAndFoldersDTO(
// // new File(currentProjectDir.getAbsolutePath(),
// // eachModule));
// // createWithChangeFiles(changeFiles,
// // codeCoverageFilesAndFoldersDTOTemp);
// // }
// // }
// // }
//
// /**
// * 生成增量覆盖率报告
// *
// * @see :
// * @param :
// * @return : void
// * @param changeFiles
// */
// public static void createIncrementCodeCoverageReportWithChangeMethods(
// List<MethodInfo> changeMethods, CodeCoverage codeCoverage) {
// File currentProjectDir = VcsCommonUtil
// .parseNewProjectFolderFromCodeCoverage(codeCoverage);
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO =
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// currentProjectDir);
// List<String> moduleList = CodeBuildMaven
// .getModulesFromPomFile(currentProjectDir.getAbsolutePath());
//
// if (moduleList.isEmpty()) {
// logger.debug("currentProjectDir:{}没有子模块，生成单独工程的覆盖率报告",
// currentProjectDir.getAbsolutePath());
// createWithChangeMethods(changeMethods,
// codeCoverageFilesAndFoldersDTO);
// } else {
// logger.debug("开始生成多模块覆盖率报告");
// for (String eachModule : moduleList) {
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp =
/// initDefaultCodeCoverageFilesAndFoldersDTO(
// new File(currentProjectDir.getAbsolutePath(),
// eachModule));
// createWithChangeMethods(changeMethods,
// codeCoverageFilesAndFoldersDTOTemp);
// }
// }
// }
//
// /**
// * 生成增量覆盖率报告
// *
// * @see :
// * @param :
// * @return : void
// * @param changeFiles
// */
// public static void createIncrementCodeCoverageReport(
// CodeCoverage codeCoverage) {
// /**
// * 生成增量报告,有对比版本信息的时候，才生成增量报告
// */
// if (StringUtil.isEmpty(codeCoverage.getOlderRemoteUrl())) {
// return;
// }
//
// List<MethodInfo> changeMethods = VcsCommonUtil
// .getChangeMethodsList(codeCoverage);
// createIncrementCodeCoverageReportWithChangeMethods(changeMethods,
// codeCoverage);
// }
//
// // /**
// // * 处理增量报告中的文件夹
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param folder
// // */
// // private void dealWithIncrementReportDir(File folder) {
// // // 找到子目录
// // logger.debug("开始处理增量报告中的空文件夹");
// // File[] currentFiles = folder.listFiles();
// // File currentFile = null;
// // for (int i = 0; i < currentFiles.length; i++) {
// // // 如果是个目录
// // currentFile = currentFiles[i];
// //
// // // 如果是以jacoco开始,跳过
// // if (currentFile.getName().startsWith(JacocoDefine.JACOCO_MARK)) {
// // continue;
// // }
// //
// // if (currentFile.isDirectory()) {
// // List<File> currentJavaHtmlFileList = FileUtil
// // .getFilesUnderFolderNew(currentFile.getAbsolutePath(),
// // FileSuffix.JAVA_HTML_FILE);
// //
// // // logger.debug("{}下的java.html文件有{}个",
// // // currentFile.getAbsolutePath(),
// // // currentJavaHtmlFileList.size());
// //
// // // 下面没有想要的数据，且目录名称为公司标志
// // if (currentJavaHtmlFileList.isEmpty()) {
// // // logger.debug("删除文件夹:{}", currentFile.getAbsolutePath());
// // FileUtil.deleteDirectory(currentFile);
// // } else {
// // dealWithIncrementReportDir(currentFile);
// // }
// // } else {
// // // 如果是文件,则处理index
// // boolean ifFileIsIndexFile = currentFile.getName()
// // .equals(JacocoDefine.INDEX_HTML_NAME)
// // || currentFile.getName()
// // .equals(JacocoDefine.INDEX_SOURCE_HTML_NAME);
// //
// // // 如果是
// // if (ifFileIsIndexFile) {
// // List<File> javaSourceFiles = FileUtil
// // .getFilesUnderFolderNew(currentFile.getParent(),
// // FileSuffix.JAVA_HTML_FILE);
// // modifyHtml(currentFile.getAbsolutePath(), javaSourceFiles);
// // }
// // }
// // }
// // }
//
// // /**
// // * 处理增量报告中的文件夹
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param folder
// // */
// // private void modifyHtmlUnderIncrementReportDir(File folder) {
// // // 找到子目录
// // logger.debug("开始处理增量报告中的空文件夹");
// // File[] currentFiles = folder.listFiles();
// // File currentFile = null;
// // for (int i = 0; i < currentFiles.length; i++) {
// // // 如果是个目录
// // currentFile = currentFiles[i];
// //
// // // 如果是以jacoco开始,跳过
// // if (currentFile.getName().startsWith(JacocoDefine.JACOCO_MARK)) {
// // continue;
// // }
// //
// // if (currentFile.isDirectory()) {
// // List<File> currentJavaHtmlFileList = FileUtil
// // .getFilesUnderFolderNew(currentFile.getAbsolutePath(),
// // FileSuffix.JAVA_HTML_FILE);
// //
// // logger.debug("{}下的java.html文件有{}个",
// // currentFile.getAbsolutePath(),
// // currentJavaHtmlFileList.size());
// //
// // // 下面没有想要的数据，且目录名称为公司标志
// // if (currentJavaHtmlFileList.isEmpty()) {
// // // logger.debug("删除文件夹:{}", currentFile.getAbsolutePath());
// // FileUtil.deleteDirectory(currentFile);
// // }
// // } else {
// // // 如果是文件则跳过
// // logger.debug("{}非目录", currentFile.getAbsolutePath());
// // }
// //
// // }
// // }
//
// // /**
// // * 根据变更文件修改覆盖率报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param changeFiles
// // * @param reportDir
// // */
// // private static void modifyReportAccordingToChangedFiles(
// // List<ChangeFile> changeFiles, String reportDir) {
// //
// // Pattern pattern = Pattern.compile(JacocoDefine.CLASS_TAB_MARK_REGEX);
// // Matcher matcher = null;
// // for (ChangeFile changeFile : changeFiles) {
// // String packageName = changeFile.getPackageName();
// // String fileName = changeFile.getFileName();
// //
// // File htmlFile = new File(reportDir, packageName + File.separator
// // + fileName + JacocoDefine.HTML_SUFFIX);
// //
// // if (!htmlFile.exists()) {
// // continue;
// // }
// //
// // File tempDestFile = new File(
// // htmlFile.getAbsolutePath().replace(".java", ".tempJava"));
// //
// // try {
// // FileUtils.copyFile(htmlFile, tempDestFile);
// // } catch (IOException e1) {
// // e1.printStackTrace();
// // }
// //
// // // 读取临时文件中的数据，写入源文件
// // try (BufferedReader br = new BufferedReader(
// // new InputStreamReader(new FileInputStream(tempDestFile)));
// // BufferedWriter bw = new BufferedWriter(
// // new OutputStreamWriter(
// // new FileOutputStream(htmlFile)));) {
// // String line = null;
// // // 逐行读取html文件
// // int currentLine = 0;
// // while ((line = br.readLine()) != null) {
// // currentLine++;
// // if (checkCurrentLineInChangedLine(currentLine,
// // changeFile)) {
// // // 出现了class标签
// // if (line.indexOf(JacocoDefine.CLASS_TAB_MARK) != -1) {
// // matcher = pattern.matcher(line);
// // if (matcher.find()) {
// // String currentClassStyle = matcher.group(1);
// //
// // line = line.replace(
// // JacocoDefine.CLASS_TAB_MARK + "\""
// // + currentClassStyle + "\"",
// // JacocoDefine.CLASS_TAB_MARK + "\""
// // + currentClassStyle + "-diff"
// // + "\"");
// // }
// // }
// // }
// // bw.write(line + "\n");
// // }
// // bw.flush();
// // br.close();
// // bw.close();
// // FileUtils.forceDelete(tempDestFile);
// // } catch (FileNotFoundException e) {
// // e.printStackTrace();
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// // }
//
// // /**
// // * 检查当前行是不是在该文件的变更行中
// // *
// // * @see :
// // * @param :
// // * @return : boolean
// // * @param currentLine
// // * @param changeFile
// // * @return
// // */
// // private static boolean checkCurrentLineInChangedLine(int currentLine,
// // ChangeFile changeFile) {
// // List<Integer> changedLines = changeFile.getAddLines();
// //
// // if (null == changedLines || changedLines.isEmpty()) {
// // return false;
// // }
// //
// // return changedLines.contains(currentLine);
// // }
//
// // /**
// // * 检查java.html文件是否在变更列表中
// // *
// // * @see :
// // * @param :
// // * @return : boolean
// // * @param file
// // * @param changeFiles
// // * @return
// // */
// // private boolean checkJavaHtmlFileInChangedList(File file,
// // List<ChangeFile> changeFiles) {
// // String pathDetail = file.getAbsolutePath();
// // for (ChangeFile changeFile : changeFiles) {
// // int packageNameIndex = pathDetail
// // .indexOf(changeFile.getPackageName());
// // int fileNameIndex = pathDetail.indexOf(changeFile.getFileName());
// // // 包名在路径中，。且文件名相同
// // if (packageNameIndex != -1 && fileNameIndex != -1) {
// // return true;
// // }
// // }
// // return false;
// // }
//
// /**
// * 生成报告
// *
// * @see :
// * @param :
// * @return : void
// * @param bundleCoverage
// * @throws IOException
// */
// private static void createReport(final IBundleCoverage bundleCoverage,
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// logger.debug("生成报告:{}", codeCoverageFilesAndFoldersDTO);
// // final HTMLFormatter htmlFormatter = new HTMLFormatter();
// // final HTMLFormatter htmlFormatterZip = new HTMLFormatter();
// //
// // File coverageFolderFile = codeCoverageFilesAndFoldersDTO
// // .getReportDirectory();
// //
// // if (coverageFolderFile.exists()) {
// // FileUtil.forceDeleteDirectory(coverageFolderFile);
// // }
// //
// // File coverageZipFile = new File(codeCoverageFilesAndFoldersDTO
// // .getReportDirectory().getAbsolutePath() + ".zip");
// // if (coverageZipFile.exists()) {
// // FileUtil.forceDeleteDirectory(coverageZipFile);
// // }
// //
// // final IReportVisitor visitor = htmlFormatter
// // .createVisitor(new FileMultiReportOutput(coverageFolderFile));
// //
// // final IReportVisitor visitorZip = htmlFormatterZip
// // .createVisitor(new ZipMultiReportOutput(new ZipOutputStream(
// // new FileOutputStream(coverageZipFile))));
// //
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTO);
// //
// // if (null == execFileLoaderTemp) {
// // return;
// // }
// //
// // visitor.visitInfo(execFileLoaderTemp.getSessionInfoStore().getInfos(),
// // execFileLoaderTemp.getExecutionDataStore().getContents());
// // visitor.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitor.visitEnd();
// //
// // visitorZip.visitInfo(
// // execFileLoaderTemp.getSessionInfoStore().getInfos(),
// // execFileLoaderTemp.getExecutionDataStore().getContents());
// // visitorZip.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitorZip.visitEnd();
// createReport(
// bundleCoverage, codeCoverageFilesAndFoldersDTO
// .getReportDirectory().getAbsolutePath(),
// codeCoverageFilesAndFoldersDTO);
// }
// //
// // /**
// // * 在某个目录下生成报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param bundleCoverage
// // * @param reportDir
// // * @throws IOException
// // */
// // private static void createReport(final IBundleCoverage bundleCoverage,
// // String reportDir) throws IOException {
// //
// // File currentProjectDir = this.codeCoverageFilesAndFoldersDTOLocal
// // .getProjectDir();
// // List<String> moduleList = MavenProjectUtil
// // .getModulesFromPomFile(currentProjectDir.getAbsolutePath());
// //
// // if (moduleList.isEmpty()) {
// // final HTMLFormatter htmlFormatter = new HTMLFormatter();
// // final IReportVisitor visitor = htmlFormatter.createVisitor(
// // new FileMultiReportOutput(new File(reportDir)));
// //
// // visitor.visitInfo(
// // execFileLoaderLocal.getSessionInfoStore().getInfos(),
// // execFileLoaderLocal.getExecutionDataStore().getContents());
// // visitor.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // this.codeCoverageFilesAndFoldersDTOLocal
// // .getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitor.visitEnd();
// // } else {
// // for (String eachModule : moduleList) {
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTOTemp = new
// // CodeCoverageFilesAndFoldersDTO();
// // codeCoverageFilesAndFoldersDTOTemp.setProjectDir(new File(
// // currentProjectDir.getAbsolutePath(), eachModule));
// // codeCoverageFilesAndFoldersDTOTemp =
// // initDefaultCodeCoverageFilesAndFoldersDTO(
// // codeCoverageFilesAndFoldersDTOTemp);
// //
// // final HTMLFormatter htmlFormatter = new HTMLFormatter();
// // final IReportVisitor visitor = htmlFormatter.createVisitor(
// // new FileMultiReportOutput(new File(reportDir)));
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTOTemp);
// // visitor.visitInfo(
// // execFileLoaderTemp.getSessionInfoStore().getInfos(),
// // execFileLoaderTemp.getExecutionDataStore()
// // .getContents());
// // visitor.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // codeCoverageFilesAndFoldersDTOTemp
// // .getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitor.visitEnd();
// // }
// // }
// // }
//
// /**
// * 在某个目录下生成报告
// *
// * @see :
// * @param :
// * @return : void
// * @param bundleCoverage
// * @param reportDir
// * @throws IOException
// */
// private static void createReport(final IBundleCoverage bundleCoverage,
// String reportDir,
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// final HTMLFormatter htmlFormatter = new HTMLFormatter();
// final HTMLFormatter htmlFormatterZip = new HTMLFormatter();
//
// File coverageFolderFile = new File(reportDir);
// if (coverageFolderFile.exists()) {
// FileUtil.forceDeleteDirectory(coverageFolderFile);
// }
//
// File coverageZipFile = new File(reportDir + ".zip");
// if (coverageZipFile.exists()) {
// FileUtil.forceDeleteDirectory(coverageZipFile);
// }
//
// final IReportVisitor visitor = htmlFormatter
// .createVisitor(new FileMultiReportOutput(coverageFolderFile));
//
// final IReportVisitor visitorZip = htmlFormatterZip
// .createVisitor(new ZipMultiReportOutput(new ZipOutputStream(
// new FileOutputStream(coverageZipFile))));
//
// ExecFileLoader execFileLoader = getExecFileLoader(
// codeCoverageFilesAndFoldersDTO);
//
// if (null == execFileLoader) {
// return;
// }
//
// visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
// execFileLoader.getExecutionDataStore().getContents());
// visitor.visitBundle(bundleCoverage,
// new DirectorySourceFileLocator(
// codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// GlobalDefination.CHAR_SET_DEFAULT, 4));
// visitor.visitEnd();
//
// visitorZip.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
// execFileLoader.getExecutionDataStore().getContents());
// visitorZip.visitBundle(bundleCoverage,
// new DirectorySourceFileLocator(
// codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// GlobalDefination.CHAR_SET_DEFAULT, 4));
// visitorZip.visitEnd();
// }
//
// /**
// * 在某个目录下生成报告
// *
// * @see :
// * @param :
// * @return : void
// * @param bundleCoverage
// * @param reportDir
// * @throws IOException
// */
// private static void createReportWithMulti(
// final IBundleCoverage bundleCoverage, String reportDir,
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// final HTMLFormatter htmlFormatter = new HTMLFormatter();
//
// ExecFileLoader execFileLoader = getExecFileLoader(
// codeCoverageFilesAndFoldersDTO);
//
// // visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
// // execFileLoader.getExecutionDataStore().getContents());
// // visitor.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitor.visitEnd();
// //
// // visitorZip.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
// // execFileLoader.getExecutionDataStore().getContents());
// // visitorZip.visitBundle(bundleCoverage,
// // new DirectorySourceFileLocator(
// // codeCoverageFilesAndFoldersDTO.getSourceDirectory(),
// // GlobalDefination.CHAR_SET_DEFAULT, 4));
// // visitorZip.visitEnd();
// }
//
// // private static IBundleCoverage analyzeStructure() throws IOException {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderLocal.getExecutionDataStore(), coverageBuilder);
// // analyzer.analyzeAll(
// // this.codeCoverageFilesAndFoldersDTOLocal.getClassesDirectory());
// // return coverageBuilder.getBundle(titleLocal);
// // }
//
// /**
// *
// * @see :
// * @param :
// * @return : ExecFileLoader
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// */
// public static ExecFileLoader getExecFileLoader(
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// ExecFileLoader execFileLoaderTemp = new ExecFileLoader();
// try {
// execFileLoaderTemp.load(
// codeCoverageFilesAndFoldersDTO.getExecutionDataFile());
// return execFileLoaderTemp;
// } catch (IOException e) {
// e.printStackTrace();
// return null;
// }
// }
//
// /**
// * 分析全量数据
// *
// * @see :
// * @param :
// * @return : IBundleCoverage
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// * @throws IOException
// */
// private static IBundleCoverage analyzeStructure(
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// List<MethodInfo> methodInfos = DiffAST
// .diffFilesWithTwoLocalDirs(codeCoverageFilesAndFoldersDTO
// .getProjectDir().getAbsolutePath(), null);
//
// return analyzeStructureWithChangeMethods(methodInfos,
// codeCoverageFilesAndFoldersDTO);
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTO);
// //
// // if (null == execFileLoaderTemp) {
// // return coverageBuilder.getBundle(
// // codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
// // }
// //
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
// // analyzer.analyzeAll(codeCoverageFilesAndFoldersDTO.getClassesDirectory());
// // return coverageBuilder.getBundle(
// // codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
// }
//
// /**
// * 根据变更方法解析数据
// *
// * @see :
// * @param :
// * @return : IBundleCoverage
// * @param methodInfos
// * @param codeCoverageFilesAndFoldersDTO
// * @return
// * @throws IOException
// */
// private static IBundleCoverage analyzeStructureWithChangeMethods(
// List<MethodInfo> methodInfos,
// CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
// throws IOException {
// final CoverageBuilder coverageBuilder = new CoverageBuilder();
// CoverageBuilder.methodInfos = null;
// List<MethodInfo> currentMethodsInfo = new ArrayList<>();
// currentMethodsInfo.addAll(methodInfos);
// CoverageBuilder.methodInfos = currentMethodsInfo;
//
// ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// codeCoverageFilesAndFoldersDTO);
//
// if (null == execFileLoaderTemp) {
// return coverageBuilder.getBundle(
// codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
// }
//
// final Analyzer analyzer = new Analyzer(
// execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
// analyzer.analyzeAll(
// codeCoverageFilesAndFoldersDTO.getClassesDirectory());
// return coverageBuilder.getBundle(
// codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
// }
//
// // /**
// // * 根据变更文件，解析执行信息
// // *
// // * @see :
// // * @param :
// // * @return : IBundleCoverage
// // * @param changeFiles
// // * @param sourceDirectory
// // * @param classDirectory
// // * @return
// // */
// // private static IBundleCoverage analyzeStructure(
// // List<ChangeFile> changeFiles, String sourceDirectory,
// // String classDirectory) {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// // for (ChangeFile changeFile : changeFiles) {
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderLocal.getExecutionDataStore(),
// // coverageBuilder);
// // // 需要根据变更文件的信息，找到变更文件的源码和class文件
// //
// // NeededInfoFromChangeFile neededInfoFromChangeFile = DiffUtil
// // .getNeededInfoFromChangeFile(changeFile);
// // File file = new File(classDirectory,
// // neededInfoFromChangeFile.getSourceFolder()
// // + neededInfoFromChangeFile.getClassName());
// //
// // if (!file.exists()) {
// // continue;
// // }
// //
// // InputStream inputStream;
// // try {
// // inputStream = new FileInputStream(file);
// // analyzer.analyzeClass(inputStream, classDirectory);
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// //
// // return coverageBuilder.getBundle(titleLocal);
// // }
//
// // /**
// // * 根据变更文件，解析执行信息
// // *
// // * @see :
// // * @param :
// // * @return : IBundleCoverage
// // * @param changeFiles
// // * @param sourceDirectory
// // * @param classDirectory
// // * @return
// // */
// // private static IBundleCoverage analyzeStructureWithChangeFiles(
// // List<ChangeFile> changeFiles, String sourceDirectory,
// // String classDirectory,
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// // String currentTitle = codeCoverageFilesAndFoldersDTO.getProjectDir()
// // .getName();
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTO);
// //
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
// //
// // for (ChangeFile changeFile : changeFiles) {
// // // 需要根据变更文件的信息，找到变更文件的源码和class文件
// // NeededInfoFromChangeFile neededInfoFromChangeFile = DiffUtil
// // .getNeededInfoFromChangeFile(changeFile);
// // File file = new File(classDirectory,
// // neededInfoFromChangeFile.getSourceFolder()
// // + neededInfoFromChangeFile.getClassName());
// //
// // if (!file.exists()) {
// // continue;
// // }
// //
// // InputStream inputStream;
// // try {
// // inputStream = new FileInputStream(file);
// // analyzer.analyzeClass(inputStream, classDirectory);
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
//
// // for(final IClassCoverage cc:coverageBuilder.getClasses()) {
// // totalCoveredCount = totalCoveredCount +
// // cc.getLineCounter().getCoveredCount();
// //
// // totalCount = totalCount + cc.getLineCounter().getTotalCount();
// // }
// //
// // coverRatio = totalCoveredCount * 100 / totalCount;
// //
// // if (reportResDAO.getInfoByPrjName(prjName).size() >=1) {
// // reportResDAO.updateTotalCov(prjName,appName,coveredRatio , new
// // Date());
// // }else {
// // reportResDAO.insertReportInfo(reportResDO);
// // }
// // return coverageBuilder.getBundle(currentTitle);
// //
// // }
//
// /**
// * 根据变更文件，解析执行信息
// *
// * @see :
// * @param :
// * @return : IBundleCoverage
// * @param changeFiles
// * @param sourceDirectory
// * @param classDirectory
// * @return
// */
// // private static IBundleCoverage analyzeStructureWithChangeFiles(
// // List<ChangeFile> changeFiles,
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// // String currentTitle = codeCoverageFilesAndFoldersDTO.getProjectDir()
// // .getName();
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTO);
// //
// // if (null == execFileLoaderTemp) {
// // return null;
// // }
// //
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
// //
// // for (ChangeFile changeFile : changeFiles) {
// // // 需要根据变更文件的信息，找到变更文件的源码和class文件
// // NeededInfoFromChangeFile neededInfoFromChangeFile = DiffUtil
// // .getNeededInfoFromChangeFile(changeFile);
// // logger.debug("neededInfoFromChangeFile:{}",
// // neededInfoFromChangeFile);
// // File file = new File(
// // codeCoverageFilesAndFoldersDTO.getClassesDirectory()
// // .getAbsolutePath(),
// // neededInfoFromChangeFile.getSourceFolder()
// // + neededInfoFromChangeFile.getClassName());
// // logger.debug("对应的class文件为:{}", file.getAbsolutePath());
// // if (!file.exists()) {
// // continue;
// // }
// //
// // InputStream inputStream;
// // try {
// // inputStream = new FileInputStream(file);
// // analyzer.analyzeClass(inputStream, codeCoverageFilesAndFoldersDTO
// // .getClassesDirectory().getAbsolutePath());
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// // return coverageBuilder.getBundle(currentTitle);
// // }
//
// // /**
// // * 根据变更文件，解析执行信息
// // *
// // * @see :
// // * @param :
// // * @return : IBundleCoverage
// // * @param changeFiles
// // * @param sourceDirectory
// // * @param classDirectory
// // * @return
// // */
// // private static IBundleCoverage analyzeStructure(
// // List<MethodInfo> methodInfos,
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
// // final CoverageBuilder coverageBuilder = new CoverageBuilder();
// // String currentTitle = codeCoverageFilesAndFoldersDTO.getProjectDir()
// // .getName();
// // ExecFileLoader execFileLoaderTemp = getExecFileLoader(
// // codeCoverageFilesAndFoldersDTO);
// //
// // if (null == execFileLoaderTemp) {
// // return null;
// // }
// //
// // final Analyzer analyzer = new Analyzer(
// // execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
// //
// // for (ChangeFile changeFile : changeFiles) {
// // // 需要根据变更文件的信息，找到变更文件的源码和class文件
// // NeededInfoFromChangeFile neededInfoFromChangeFile = DiffUtil
// // .getNeededInfoFromChangeFile(changeFile);
// // logger.debug("neededInfoFromChangeFile:{}",
// // neededInfoFromChangeFile);
// // File file = new File(
// // codeCoverageFilesAndFoldersDTO.getClassesDirectory()
// // .getAbsolutePath(),
// // neededInfoFromChangeFile.getSourceFolder()
// // + neededInfoFromChangeFile.getClassName());
// // logger.debug("对应的class文件为:{}", file.getAbsolutePath());
// // if (!file.exists()) {
// // continue;
// // }
// //
// // InputStream inputStream;
// // try {
// // inputStream = new FileInputStream(file);
// // analyzer.analyzeClass(inputStream, codeCoverageFilesAndFoldersDTO
// // .getClassesDirectory().getAbsolutePath());
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// // return coverageBuilder.getBundle(currentTitle);
// // }
//
// // /**
// // * 修改html
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param htmlFileName
// // * @param changeFile
// // */
// // private static void modifyHtml(String htmlFileName, ChangeFile
// // changeFile) {
// // int newLineCount = 0;
// // int coverLineCount = 0;
// //
// // List<String> fileContent = new ArrayList<>();
// //
// // List<Integer> addedLines = changeFile.getAddLines();
// //
// // String line = null;
// // try (BufferedReader br = new BufferedReader(new InputStreamReader(
// // new FileInputStream(new File(htmlFileName))))) {
// // while ((line = br.readLine()) != null) {
// // fileContent.add(line);
// // }
// //
// // int fileSize = fileContent.size();
// //
// // for (int i = 0; i < fileSize; i++) {
// // // 如果当前行是变更行
// // if (addedLines.contains(i + 1)) {
// //
// // }
// // }
// // } catch (FileNotFoundException e) {
// // e.printStackTrace();
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
//
// // private static void modifyHtml(String htmlFileName, List<File> files) {
// // logger.debug("修改html:{},依据的文件列表为:{}", htmlFileName, files);
// //
// // try (BufferedReader br = new BufferedReader(new InputStreamReader(
// // new FileInputStream(new File(htmlFileName))))) {
// // String line = null;
// // while ((line = br.readLine()) != null) {
// // System.out.println("eachLine:" + line);
// // }
// //
// // } catch (FileNotFoundException e) {
// // e.printStackTrace();
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
//
// // /**
// // * 根据CodeCoverage生成全量报告
// // *
// // * @see :
// // * @param :
// // * @return : void
// // * @param codeCoverage
// // */
// // public static void createWholeCodeCoverageDataNew(
// // CodeCoverage codeCoverage) {
// // File newerFileFolder = VcsCommonUtil
// // .parseNewProjectFolderFromCodeCoverage(codeCoverage);
// //
// // if (!CodeBuildMaven
// // .checkProjectMaven(newerFileFolder.getAbsolutePath())) {
// // newerFileFolder = new File(CodeBuildMaven
// // .findTopLevenParentMavenProjectFolderUnderCertainFolder(
// // newerFileFolder.getAbsolutePath()));
// // }
// //
// // JacocoAgentTcpServer jacocoAgentTcpServer = JacocoAgentTcpServer
// // .parseJacocoAgentTcpServer(codeCoverage);
// //
// // ExecutionDataClient executionDataClient = new ExecutionDataClient(
// // jacocoAgentTcpServer);
// // executionDataClient.dumpData(newerFileFolder.getAbsolutePath());
// // /**
// // * 生成全量报告
// // */
// // CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = new
// // CodeCoverageFilesAndFoldersDTO();
// // codeCoverageFilesAndFoldersDTO.setProjectDir(newerFileFolder);
// // codeCoverageFilesAndFoldersDTO =
// // initDefaultCodeCoverageFilesAndFoldersDTO(
// // codeCoverageFilesAndFoldersDTO);
// //
// // if (StringUtil.isStringAvaliable(codeCoverage.getNewerRemoteUrl())) {
// // logger.debug("开始生成全量报告");
// // try {
// // createWholeCodeCoverageData(codeCoverageFilesAndFoldersDTO);
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// // }
//
// /**
// * 根据CodeCoverage生成全量报告
// *
// * @see :
// * @param :
// * @return : void
// * @param codeCoverage
// */
// public static void createWholeCodeCoverageDataWithMulti(
// CodeCoverage codeCoverage) {
// File newerFileFolder = VcsCommonUtil
// .parseNewProjectFolderFromCodeCoverage(codeCoverage);
//
// if (!CodeBuildMaven
// .checkProjectMaven(newerFileFolder.getAbsolutePath())) {
// newerFileFolder = new File(CodeBuildMaven
// .findTopLevenParentMavenProjectFolderUnderCertainFolder(
// newerFileFolder.getAbsolutePath()));
// }
//
// JacocoAgentTcpServer jacocoAgentTcpServer = JacocoAgentTcpServer
// .parseJacocoAgentTcpServer(codeCoverage);
//
// List<String> currentProjectModules = CodeBuildMaven
// .getModulesFromPomFile(newerFileFolder.getAbsolutePath());
//
// ExecutionDataClient executionDataClient = new ExecutionDataClient(
// jacocoAgentTcpServer);
// executionDataClient.dumpData(newerFileFolder.getAbsolutePath());
//
// for (String eachModule : currentProjectModules) {
// executionDataClient.dumpData(newerFileFolder.getAbsolutePath()
// + File.separator + eachModule);
// }
// }
//
// public List<String> loadExecutionDataFiles(String parentProjectDir) {
// List<String> execFiles = new ArrayList<>();
//
// execFiles.add(parentProjectDir + File.separator
// + JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);
// List<String> currentProjectModules = CodeBuildMaven
// .getModulesFromPomFile(parentProjectDir);
// for (String eachModule : currentProjectModules) {
// execFiles.add(parentProjectDir + File.separator + eachModule
// + File.separator
// + JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT);
// }
// return execFiles;
// }
//
// public static void main(String[] args) {
// CodeCoverage codeCoverage = new CodeCoverage();
// codeCoverage.setProjectName("AI机器人");
// codeCoverage.setBuildType(BuildType.MAVEN);
//
// codeCoverage.setNewerRemoteUrl(
// "http://redmine.hztianque.com:9080/repos/TQProduct/tq-robot/branches/tq-robot-1.0.0");
// codeCoverage.setNewerVersion("609899");
// codeCoverage.setTcpServerIp("192.168.110.36");
// codeCoverage.setTcpServerPort(2018);
// codeCoverage.setUsername("sunliuping");
// codeCoverage.setPassword("Admin@1234");
// createWholeCodeCoverageDataWithMulti(codeCoverage);
// }
//
// }
