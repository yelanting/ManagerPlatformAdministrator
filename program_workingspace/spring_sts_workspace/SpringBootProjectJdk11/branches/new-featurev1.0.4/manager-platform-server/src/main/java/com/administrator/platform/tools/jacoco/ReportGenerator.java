/**
 * @author : 孙留平
 * @since : 2019年2月20日 下午4:36:25
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.diff.MethodInfo;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.ISourceFileLocator;
import org.jacoco.report.MultiSourceFileLocator;
import org.jacoco.report.html.HTMLFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.core.base.util.ZipCompress;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.tools.codebuild.entity.BuildType;
import com.administrator.platform.tools.codebuild.impl.CodeBuildGradleAndroid;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
import com.administrator.platform.tools.jacoco.diff.DiffAST;
import com.administrator.platform.tools.vcs.common.CommonDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;

/**
 * @author : Administrator
 * @since : 2019年2月20日 下午4:36:25
 * @see :
 */
public class ReportGenerator {
    private static final Logger logger = LoggerFactory
            .getLogger(ReportGenerator.class);

    public ReportGenerator() {

    }

    /**
     * 生成增量覆盖率报告
     * 
     * @see :
     * @param :
     * @return : void
     * @param changeFiles
     */
    public static void createIncrementCodeCoverageReportWithMulti(
            CodeCoverage codeCoverage) {
        /**
         * 生成增量报告,有对比版本信息的时候，才生成增量报告
         */
        logger.info("开始生成增量覆盖率报告");
        if (StringUtil.isEmpty(codeCoverage.getOlderRemoteUrl())) {
            return;
        }

        File newerFolder = VcsCommonUtil
                .parseNewProjectFolderFromCodeCoverage(codeCoverage);

        File olderFolder = VcsCommonUtil
                .parseOldProjectFolderFromCodeCoverage(codeCoverage);

        /*
         * 如果是1.6版本，则根据1.6的编译单元来获取方法
         */
        List<MethodInfo> changeMethods = null;
        changeMethods = DiffAST.diffFilesWithTwoLocalDirs(
                newerFolder.getAbsolutePath(), olderFolder.getAbsolutePath());

        initCoverageBuilderMethodsInfo(changeMethods);
        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = null;

        if (isGradle(codeCoverage)) {
            CodeBuildGradleAndroid codeBuildGradleAndroid = new CodeBuildGradleAndroid();
            codeBuildGradleAndroid
                    .setChannelName(codeCoverage.getChannelName());
            codeCoverageFilesAndFoldersDTOs = codeBuildGradleAndroid
                    .getCodeCoverageFilesAndFoldersDTOs(
                            newerFolder.getAbsolutePath());
        } else {
            CodeBuildIntf codeBuildIntf = codeCoverage
                    .parseCodeBuildFromThisObject();

            codeCoverageFilesAndFoldersDTOs = codeBuildIntf
                    .getCodeCoverageFilesAndFoldersDTOs(
                            newerFolder.getAbsolutePath());
        }

        createIncrementCodeCoverageReportWithChangeMethodsWithMulti(newerFolder,
                codeCoverageFilesAndFoldersDTOs);

        logger.info("生成增量覆盖率报告完成");
    }

    /**
     * 在某个目录下生成报告
     *
     * @see :
     * @param :
     * @return : void
     * @param bundleCoverage
     * @param reportDir
     * @throws IOException
     */
    private static void createReportWithMultiProjects(File reportDir,
            List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs)
            throws IOException {
        logger.debug("开始在:{}下生成覆盖率报告", reportDir);
        File coverageFolderFile = reportDir;
        if (coverageFolderFile.exists()) {
            FileUtil.forceDeleteDirectory(coverageFolderFile);
        }

        HTMLFormatter htmlFormatter = new HTMLFormatter();
        IReportVisitor iReportVisitor = null;

        boolean everCreatedReport = false;

        for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
            // class文件为空或者不存在
            boolean classDirNotExists = (null == codeCoverageFilesAndFoldersDTO
                    .getClassesDirectory())
                    || (!(codeCoverageFilesAndFoldersDTO.getClassesDirectory()
                            .exists()));

            // class文件目录不存在
            boolean needNotToCreateReport = classDirNotExists;
            if (needNotToCreateReport) {
                logger.debug("目录:{}没有class文件，不生成报告",
                        codeCoverageFilesAndFoldersDTO.getProjectDir()
                                .getAbsolutePath());
                continue;
            }

            // 修改标志位
            everCreatedReport = true;
            logger.debug("正在为:{}生成报告", codeCoverageFilesAndFoldersDTO
                    .getProjectDir().getAbsolutePath());
            IBundleCoverage bundleCoverage = analyzeStructureWithOutChangeMethods(
                    codeCoverageFilesAndFoldersDTO);
            ExecFileLoader execFileLoader = getExecFileLoader(
                    codeCoverageFilesAndFoldersDTO);
            iReportVisitor = htmlFormatter
                    .createVisitor(new FileMultiReportOutput(
                            new File(coverageFolderFile.getAbsolutePath(),
                                    codeCoverageFilesAndFoldersDTO
                                            .getProjectDir().getName())));

            if (null != execFileLoader) {
                iReportVisitor.visitInfo(
                        execFileLoader.getSessionInfoStore().getInfos(),
                        execFileLoader.getExecutionDataStore().getContents());
            }

            ISourceFileLocator iSourceFileLocator = getSourceFileLocatorsUnderThis(
                    codeCoverageFilesAndFoldersDTO.getSourceDirectory());
            iReportVisitor.visitBundle(bundleCoverage, iSourceFileLocator);
            iReportVisitor.visitEnd();
        }

        if (!everCreatedReport) {
            throw new BusinessValidationException("从未生成报告，检查下工程是否未编译或者是否都是空工程");
        }
    }

    /**
     * 获取当前目录下的所有目录，用作
     * 
     * @see :
     * @param :
     * @return : ISourceFileLocator
     * @param topLevelSourceFileFolder
     * @return
     */
    private static ISourceFileLocator getSourceFileLocatorsUnderThis(
            File topLevelSourceFileFolder) {
        MultiSourceFileLocator iSourceFileLocator = new MultiSourceFileLocator(
                4);

        List<File> sourceFileFolders = getSourceFileFoldersUnderThis(
                topLevelSourceFileFolder);

        for (File eachSourceFileFolder : sourceFileFolders) {
            iSourceFileLocator
                    .add(new DirectorySourceFileLocator(eachSourceFileFolder,
                            GlobalDefination.CHAR_SET_DEFAULT, 4));
        }
        return iSourceFileLocator;
    }

    /**
     * 获取顶层目录下的所有源码文件夹
     * 
     * @see :
     * @param :
     * @return : String[]
     * @param topLevelSourceFileFolder
     * @return
     */

    private static List<File> getSourceFileFoldersUnderThis(
            File topLevelSourceFileFolder) {

        List<File> sourceFileFolders = new ArrayList<>();
        logger.debug("获取当前源码目录:{}下的所有源码相关文件夹顶层目录为:",
                topLevelSourceFileFolder.getAbsolutePath());

        FileUtil.onlyFolders = new ArrayList<>();
        FileUtil.walkFolder(topLevelSourceFileFolder);

        List<File> foldersUnderThis = new ArrayList<>();
        foldersUnderThis.addAll(FileUtil.onlyFolders);
        for (File eachFile : foldersUnderThis) {
            // 如果他的祖先层级已经被加入过
            if (!isAnyAncestorsEverJoinedSourceFolder(sourceFileFolders,
                    eachFile)) {
                boolean isSourceFileFolder = isThisFolderSourceFileFolder(
                        eachFile);
                if (isSourceFileFolder) {
                    sourceFileFolders.add(eachFile.getParentFile());
                }
            }

        }

        Util.displayListInfo(sourceFileFolders);
        return sourceFileFolders;
    }

    /**
     * 判断当前文件夹是否有祖先被加入，如果被加入，就不在继续判断
     * 
     * @see :
     * @param :
     * @return : void
     * @param sourceFileFolders
     * @param thisFolder
     */
    private static boolean isAnyAncestorsEverJoinedSourceFolder(
            List<File> sourceFileFolders, File thisFolder) {
        String thisFolderString = thisFolder.getAbsolutePath();
        for (File file : sourceFileFolders) {
            if (thisFolderString.startsWith(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * 
     * @see : 断这个文件是不是源码文件夹
     * @param :
     * @return : boolean
     * @param fileFolder
     * @return
     */
    private static boolean isThisFolderSourceFileFolder(File fileFolder) {
        if (!fileFolder.exists()) {
            return false;
        }

        // 如果是文件夹
        if (fileFolder.isDirectory()) {
            String name = fileFolder.getName();
            if (CommonDefine.COMPANY_MARK_LIST.contains(name)) {
                logger.debug("名称为:{},的目录{}是源码目录", name,
                        fileFolder.getAbsolutePath());
                return true;
            }
        }
        logger.debug("名称为:{},的目录{}不是源码目录", fileFolder.getName(),
                fileFolder.getAbsolutePath());
        return false;
    }

    /**
     * @see :
     * @param :
     * @return : ExecFileLoader
     * @param codeCoverageFilesAndFoldersDTO
     * @return
     */
    public static ExecFileLoader getExecFileLoader(
            CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO) {
        ExecFileLoader execFileLoaderTemp = new ExecFileLoader();
        try {
            execFileLoaderTemp.load(
                    codeCoverageFilesAndFoldersDTO.getExecutionDataFile());
            return execFileLoaderTemp;
        } catch (IOException e) {
            logger.error("IO异常,{}", e.getMessage());
            return null;
        }
    }

    /**
     * 无参解析，前提是要先初始化CoverageBuilder中的methods，不然可能出不来数据
     * 
     * @see :
     * @param :
     * @return : IBundleCoverage
     * @param methodInfos
     * @param codeCoverageFilesAndFoldersDTO
     * @return
     * @throws IOException
     */
    private static IBundleCoverage analyzeStructureWithOutChangeMethods(
            CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO)
            throws IOException {
        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        ExecFileLoader execFileLoaderTemp = getExecFileLoader(
                codeCoverageFilesAndFoldersDTO);
        if (null == execFileLoaderTemp) {
            return coverageBuilder.getBundle(
                    codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
        }
        final Analyzer analyzer = new Analyzer(
                execFileLoaderTemp.getExecutionDataStore(), coverageBuilder);
        analyzer.analyzeAll(
                codeCoverageFilesAndFoldersDTO.getClassesDirectory());
        return coverageBuilder.getBundle(
                codeCoverageFilesAndFoldersDTO.getProjectDir().getName());
    }

    private static void initCoverageBuilderMethodsInfo(
            List<MethodInfo> methodInfos) {
        CoverageBuilder.methodInfos = null;
        List<MethodInfo> currentMethodsInfo = new ArrayList<>();
        currentMethodsInfo.addAll(methodInfos);
        CoverageBuilder.methodInfos = currentMethodsInfo;
    }

    /**
     * 根据CodeCoverage生成全量报告
     * 
     * @see :
     * @param :
     * @return : void
     * @param codeCoverage
     */
    public static void createWholeCodeCoverageDataWithMulti(
            CodeCoverage codeCoverage) {
        logger.debug("生成全量覆盖率报告开始");
        File newerFolder = VcsCommonUtil
                .parseNewProjectFolderFromCodeCoverage(codeCoverage);

        List<MethodInfo> changeMethods = null;
        if (codeCoverage.getJdkVersion().equals(JacocoDefine.JDK_VERSION_SIX)) {
            changeMethods = DiffAST.diffFilesWithTwoLocalDirsWithJdkSix(
                    newerFolder.getAbsolutePath(), null);
        } else {
            changeMethods = DiffAST.diffFilesWithTwoLocalDirs(
                    newerFolder.getAbsolutePath(), null);
        }

        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = null;

        if (isGradle(codeCoverage)) {
            logger.debug("是gradle项目");
            CodeBuildGradleAndroid codeBuildGradleAndroid = new CodeBuildGradleAndroid();

            logger.debug("123：{}", codeBuildGradleAndroid);
            codeBuildGradleAndroid
                    .setChannelName(codeCoverage.getChannelName());
            codeCoverageFilesAndFoldersDTOs = codeBuildGradleAndroid
                    .getCodeCoverageFilesAndFoldersDTOs(
                            newerFolder.getAbsolutePath());
        } else {
            CodeBuildIntf codeBuildIntf = codeCoverage
                    .parseCodeBuildFromThisObject();

            codeCoverageFilesAndFoldersDTOs = codeBuildIntf
                    .getCodeCoverageFilesAndFoldersDTOs(
                            newerFolder.getAbsolutePath());
        }

        // CodeBuildIntf codeBuildIntf = codeCoverage
        // .parseCodeBuildFromThisObject();
        //
        // List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs
        // = codeBuildIntf
        // .getCodeCoverageFilesAndFoldersDTOs(newerFileFolder);

        initCoverageBuilderMethodsInfo(changeMethods);

        createWholeCodeCoverageDataWithMulti(newerFolder,
                codeCoverageFilesAndFoldersDTOs);

        logger.debug("生成全量覆盖率报告完成");
    }

    /**
     * 生成增量覆盖率报告
     * 
     * @see :
     * @param :
     * @return : void
     * @param fileFolder
     * @param codeCoverageFilesAndFoldersDTOs
     */
    public static void createIncrementCodeCoverageReportWithChangeMethodsWithMulti(
            File fileFolder,
            List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs) {
        try {

            File incrementCodeCoverageDataFolder = new File(
                    fileFolder.getAbsolutePath(),
                    JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER);
            createReportWithMultiProjects(incrementCodeCoverageDataFolder,
                    codeCoverageFilesAndFoldersDTOs);

            File incrementCodeCoverageDataZip = new File(
                    fileFolder.getAbsolutePath(),
                    JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER + ".zip");

            ZipCompress.zip(incrementCodeCoverageDataFolder.getAbsolutePath(),
                    incrementCodeCoverageDataZip.getAbsolutePath());
        } catch (IOException e) {
            logger.error("io异常:{}", e.getMessage());
        }
    }

    /**
     * 生成全量覆盖率报告
     * 
     * @see :
     * @param :
     * @return : void
     * @param fileFolder
     * @param codeCoverageFilesAndFoldersDTOs
     */
    public static void createWholeCodeCoverageDataWithMulti(File fileFolder,
            List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs) {
        try {

            File wholeCodeCoverageDataFolder = new File(
                    fileFolder.getAbsolutePath(),
                    JacocoDefine.CODE_COVERAGE_DATA_FOLDER);
            createReportWithMultiProjects(wholeCodeCoverageDataFolder,
                    codeCoverageFilesAndFoldersDTOs);

            File wholeZipFile = new File(fileFolder.getAbsolutePath(),
                    JacocoDefine.CODE_COVERAGE_DATA_FOLDER + ".zip");

            ZipCompress.zip(wholeCodeCoverageDataFolder.getAbsolutePath(),
                    wholeZipFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("IO操作异常:{}", e.getMessage());
        }

    }

    private static boolean isGradle(CodeCoverage codeCoverage) {
        return BuildType.GRADLE == codeCoverage.getBuildType();
    }

    public static void main(String[] args) {
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
        File topLevelSourceFileFolder = new File(
                "D:\\manager_platform\\jacoco_coverage\\newshengPingTai__dataAnalysis\\src");
        ReportGenerator.getSourceFileFoldersUnderThis(topLevelSourceFileFolder);
    }

}
