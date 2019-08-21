/**
 * @author : 孙留平
 * @since : 2019年3月13日 下午2:28:50
 * @see:
 */
package com.administrator.platform.tools.codebuild.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
import com.administrator.platform.tools.jacoco.CodeCoverageFilesAndFoldersDTO;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.define.FileSuffix;

/**
 * @author : Administrator
 * @since : 2019年3月13日 下午2:28:50
 * @see :
 */
public class CodeBuildMaven implements CodeBuildIntf {
    private static final Logger logger = LoggerFactory
            .getLogger(CodeBuildMaven.class);

    public CodeBuildMaven() {

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
        codeCoverageFilesAndFoldersDTO.setSourceDirectory(
                new File(projectDir, JacocoDefine.DEFAULT_SOURCE_FOLDER));

        codeCoverageFilesAndFoldersDTO.setClassesDirectory(
                new File(projectDir, JacocoDefine.DEFAULT_CLASSES_FOLDER));

        codeCoverageFilesAndFoldersDTO.setExecutionDataFile(new File(projectDir,
                JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT));

        codeCoverageFilesAndFoldersDTO.setReportDirectory(
                new File(projectDir, JacocoDefine.CODE_COVERAGE_DATA_FOLDER));

        codeCoverageFilesAndFoldersDTO.setIncrementReportDirectory(new File(
                projectDir, JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER));

        return codeCoverageFilesAndFoldersDTO;
    }

    /**
     * 解析pom文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param pomFilePath
     */
    public static Model parsePom(String pomFilePath) {
        String finalPomFilePath = null;
        // 如果路径以pom.xml结尾，则代表是个文件
        if (pomFilePath.endsWith(JacocoDefine.DEFAULT_POM_FILE_NAME)) {
            finalPomFilePath = pomFilePath;
        } else {
            // 如果不是以pom.xml结尾
            File currentPath = new File(pomFilePath);
            // 不是以pom.xml结尾，又是个文件，则报错
            if (currentPath.isFile()) {
                throw new BusinessValidationException("路径信息不正确，非pom文件，无法解析");
            }

            if (!checkProjectMaven(pomFilePath)) {
                finalPomFilePath = null;
            } else {
                finalPomFilePath = pomFilePath + File.separator
                        + JacocoDefine.DEFAULT_POM_FILE_NAME;
            }
        }

        if (null == finalPomFilePath) {
            throw new BusinessValidationException(
                    pomFilePath + "不是一个pom文件，或者该文件夹下没有pom.xml文件");
        }

        try (FileInputStream fileInputStream = new FileInputStream(
                new File(finalPomFilePath));) {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            return reader.read(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件操作异常FileNotFoundException:{}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件操作异常IOException:{}", e.getMessage());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            logger.error("文件操作异常XmlPullParserException:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 从model中获取模块信息
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param model
     * @return
     */
    public static List<String> getModulesFromModel(Model model) {
        if (null == model) {
            return new ArrayList<>();
        }
        List<String> moduleList = model.getModules();

        List<String> finalModuleList = new ArrayList<>();
        for (String module : moduleList) {
            if (!module.startsWith("..")) {
                finalModuleList.add(module);
            }
        }
        return finalModuleList;
    }

    /**
     * 从pom文件中解析模块信息，可以是目录或者直接精确到pom文件；如果是目录则会自动加上pom.xml做为路径
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param pomFile
     * @return
     */
    public static List<String> getModulesFromPomFile(String pomFile) {
        Model model = parsePom(pomFile);
        if (null == model) {
            return new ArrayList<>();
        }
        return getModulesFromModel(model);
    }

    /**
     * @see :
     * @param :
     * @return : void
     * @param args
     */
    public static boolean checkProjectMaven(String projectDir) {

        File projectFile = new File(projectDir);

        if (!projectFile.exists()) {
            return false;
        }

        if (!projectFile.isDirectory()) {
            return false;
        }

        // 如果路径以pom.xml结尾，则代表是个文件
        // 如果不是以pom.xml结尾
        // 不是以pom.xml结尾，又是个文件，则报错
        // 如果是个文件夹，则自动加上pom.xml
        File pomFileTemp = new File(projectDir,
                JacocoDefine.DEFAULT_POM_FILE_NAME);
        // 如果pom文件不存在
        return pomFileTemp.exists();
    }

    /**
     * 获取目录下面的maven工程
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param parentFolder
     * @return
     */
    public static List<String> findMavenFoldersUnderCertainFolder(
            String parentFolder) {

        List<File> pomFileList = FileUtil.getFilesUnderFolder(parentFolder,
                FileSuffix.POM_XML_FILE);

        List<String> pomParentFolderList = new ArrayList<>();
        for (File eachPomFile : pomFileList) {
            pomParentFolderList.add(eachPomFile.getParent());
        }

        return pomParentFolderList;

    }

    /**
     * 获取文件夹下面的maven工程
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param parentFolder
     * @return
     */
    public static List<String> findMavenFoldersUnderCertainFolder(
            File parentFolder) {
        return findMavenFoldersUnderCertainFolder(
                parentFolder.getAbsolutePath());
    }

    /**
     * @see :
     * @param :
     * @return : List<String>
     * @param parentFolder：父目录
     * @return
     */

    public static List<String> findParentMavenProjectFolderUnderCertainFolder(
            String parentFolder) {
        List<File> pomFileList = FileUtil.getFilesUnderFolder(parentFolder,
                FileSuffix.POM_XML_FILE);

        List<String> parentMavenFolderList = new ArrayList<>();
        for (File file : pomFileList) {
            List<String> thisProjectModuleList = getModulesFromPomFile(
                    file.getAbsolutePath());
            if (!thisProjectModuleList.isEmpty()) {
                parentMavenFolderList.add(file.getParent());
            }
        }

        return parentMavenFolderList;
    }

    /**
     * 获取文件夹下的顶层maven目录
     * 
     * @see :
     * @param :
     * @return : String
     * @param parentFolder：父目录
     * @return
     */

    public static String findTopLevenParentMavenProjectFolderUnderCertainFolder(
            String parentFolder) {
        List<String> pomFileList = findParentMavenProjectFolderUnderCertainFolder(
                parentFolder);

        String topLevelMavenFolder = null;
        for (String string : pomFileList) {
            if (null == topLevelMavenFolder) {
                topLevelMavenFolder = string;
            } else {
                if (!string.contains(topLevelMavenFolder)) {
                    topLevelMavenFolder = string;
                }
            }
        }
        logger.debug("找到顶层maven工程:{}", topLevelMavenFolder);
        return topLevelMavenFolder;
    }

    /**
     * 获取子模块
     * 
     * @see
     *      com.administrator.platform.tools.codebuild.CodeBuild#getSubModules(java.
     *      lang.String)
     */
    @Override
    public List<String> getSubModules(String projectPath) {
        return getModulesFromPomFile(projectPath);
    }

    /**
     * 获取所有的源代码文件夹
     * 
     * @see com.administrator.platform.tools.codebuild.CodeBuild#
     *      getSubSourceFileFolders(java.lang.String)
     */
    @Override
    public List<String> getSubSourceFileFolders(String projectPath) {
        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
                projectPath);
        List<String> subSourceFileFolders = new ArrayList<>();
        for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
            subSourceFileFolders.add(codeCoverageFilesAndFoldersDTO
                    .getSourceDirectory().getAbsolutePath());
        }
        return subSourceFileFolders;
    }

    /**
     * 获取class文件夹
     * 
     * @see com.administrator.platform.tools.codebuild.CodeBuild#
     *      getSubClassesFileFolders(java.lang.String)
     */
    @Override
    public List<String> getSubClassesFileFolders(String projectPath) {

        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
                projectPath);

        List<String> subClassesFileFolders = new ArrayList<>();

        for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
            subClassesFileFolders.add(codeCoverageFilesAndFoldersDTO
                    .getClassesDirectory().getAbsolutePath());
        }
        return subClassesFileFolders;
    }

    /**
     * 获取某个文件夹下的所有MAVEN工程
     * 
     * @see com.administrator.platform.tools.codebuild.CodeBuild#
     *      getCodeCoverageFilesAndFoldersDTOs(java.lang.String)
     */
    @Override
    public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFoldersDTOs(
            String projectPath) {

        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = new ArrayList<>();

        // 如果不是多模块，直接把当前的返回
        if (!isThusProjectMulti(projectPath)) {
            CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = initDefaultCodeCoverageFilesAndFoldersDTO(
                    projectPath);
            codeCoverageFilesAndFoldersDTOs.add(codeCoverageFilesAndFoldersDTO);
            return codeCoverageFilesAndFoldersDTOs;
        }

        // 如果是多模块，则返回子模块
        List<String> modules = CodeBuildMaven
                .getModulesFromPomFile(projectPath);
        for (String eachModuleName : modules) {
            File currentModuleProject = new File(projectPath, eachModuleName);
            CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = initDefaultCodeCoverageFilesAndFoldersDTO(
                    currentModuleProject.getAbsolutePath());
            codeCoverageFilesAndFoldersDTOs.add(codeCoverageFilesAndFoldersDTO);
        }
        return codeCoverageFilesAndFoldersDTOs;
    }

    /**
     * 检查当前工程是不是一个MAVEN工程
     * 
     * @see
     *      com.administrator.platform.tools.codebuild.CodeBuild#isThusKindOfProject(
     *      java.lang.String)
     */
    @Override
    public boolean isThusKindOfProject(String projectPath) {
        return checkProjectMaven(projectPath);
    }

    /**
     * 检查当前工程是不是多模块
     * 
     * @see : 以父文件夹下的pom为解析，有module定义则代表是多模块；返回的子模块列表如果是空代表不是多模块
     * @author Administrator
     */
    @Override
    public boolean isThusProjectMulti(String projectPath) {
        boolean doesNotHaveSubModules = getModulesFromPomFile(projectPath)
                .isEmpty();
        return !doesNotHaveSubModules;
    }

    /**
     * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
     *      getCodeCoverageFilesAndFoldersDTOs(java.io.File)
     */
    @Override
    public List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFoldersDTOs(
            File projectPath) {
        return getCodeCoverageFilesAndFoldersDTOs(
                projectPath.getAbsolutePath());
    }

    // public static List<MavenProject> findDefaultMavenProjects(
    // MavenProject mavenProject) {
    // return findDependencies(mavenProject, Artifact.SCOPE_COMPILE,
    // Artifact.SCOPE_RUNTIME, Artifact.SCOPE_PROVIDED);
    //
    // }

    public static MavenProject getProject(String projectPath) {

        return new MavenProject();
    }

    // public static List<MavenProject> findDependencies(MavenProject
    // mavenProject,
    // final String... scopes) {
    // final List<MavenProject> result = new ArrayList<MavenProject>();
    // final List<String> scopeList = Arrays.asList(scopes);
    //
    // for (final Object dependencyObject : mavenProject.getDependencies()) {
    // final Dependency dependency = (Dependency) dependencyObject;
    // if (scopeList.contains(dependency.getScope())) {
    // final MavenProject project = findProjectFromMavenProjects(
    // dependency);
    // if (project != null) {
    // result.add(project);
    // }
    // }
    // }
    // return result;
    // }

    public MavenProject findProjectFromMavenProjects(final Dependency d,
            List<MavenProject> mavenProjects) {
        for (final MavenProject p : mavenProjects) {
            if (p.getGroupId().equals(d.getGroupId())
                    && p.getArtifactId().equals(d.getArtifactId())
                    && p.getVersion().equals(d.getVersion())) {
                return p;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String pomFileParent = "C:\\Users\\Administrator\\jacoco_coverage\\zhoushan_statistics_zhoushan_20190313";
        // String pomFileParent =
        // "C:\\Users\\Administrator\\jacoco_coverage\\tq-robot-1.0.0";
        getModulesFromPomFile(pomFileParent);
    }

    /**
     * @see com.administrator.platform.tools.codebuild.intf.CodeBuildIntf#
     *      getExecutionDataFiles(java.lang.String)
     */
    @Override
    public List<String> getExecutionDataFiles(String projectPath) {
        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
                projectPath);

        List<String> executionDataFiles = new ArrayList<>();

        for (CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO : codeCoverageFilesAndFoldersDTOs) {
            executionDataFiles.add(codeCoverageFilesAndFoldersDTO
                    .getExecutionDataFile().getAbsolutePath());
        }
        return executionDataFiles;
    }

    @Override
    public void cleanProject(String projectFolder) {
        logger.debug("开始清理工程");
        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
                projectFolder);

        for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
            if (null != codeCoverageFilesAndFoldersDTOs.get(i)
                    .getClassesDirectory()
                    && codeCoverageFilesAndFoldersDTOs.get(i)
                            .getClassesDirectory().exists()) {
                logger.debug("删除class文件夹:{}", codeCoverageFilesAndFoldersDTOs
                        .get(i).getClassesDirectory().getAbsolutePath());
                FileUtil.deleteFile(codeCoverageFilesAndFoldersDTOs.get(i)
                        .getClassesDirectory());
            }
        }
    }

    @Override
    public void copyClasses(String fromClassFolder, String projectFolder) {
        List<CodeCoverageFilesAndFoldersDTO> codeCoverageFilesAndFoldersDTOs = getCodeCoverageFilesAndFoldersDTOs(
                projectFolder);
        logger.debug("开始copy classes文件");
        try {
            // 定义一个字段，用来控制是不是多模块的class
            boolean everCopiedClasses = false;

            for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
                CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFoldersDTOs
                        .get(i);
                String projectName = codeCoverageFilesAndFoldersDTO
                        .getProjectDir().getName();
                File childClassFolder = new File(fromClassFolder, projectName);
                File sourceProjectClassFolder = new File(childClassFolder,
                        "classes");

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
            for (int i = 0; i < codeCoverageFilesAndFoldersDTOs.size(); i++) {
                CodeCoverageFilesAndFoldersDTO codeCoverageFilesAndFoldersDTO = codeCoverageFilesAndFoldersDTOs
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
