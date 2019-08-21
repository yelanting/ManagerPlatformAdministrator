/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午7:53:02
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.io.File;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午7:53:02
 * @see :
 */
public class CodeCoverageFilesAndFoldersDTO {
    private File projectDir;

    /**
     * 覆盖率的exec文件地址
     */
    private File executionDataFile;

    /**
     * 目录下必须包含源码编译过的class文件,用来统计覆盖率。所以这里用server打出的jar包地址即可
     */
    private File classesDirectory;

    /**
     * 源码的/src/main/java,只有写了源码地址覆盖率报告才能打开到代码层。使用jar只有数据结果
     */
    private File sourceDirectory;
    private File reportDirectory;
    private File incrementReportDirectory;

    public File getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(File projectDir) {
        this.projectDir = projectDir;
    }

    public File getExecutionDataFile() {
        return executionDataFile;
    }

    public void setExecutionDataFile(File executionDataFile) {
        this.executionDataFile = executionDataFile;
    }

    public File getClassesDirectory() {
        return classesDirectory;
    }

    public void setClassesDirectory(File classesDirectory) {
        this.classesDirectory = classesDirectory;
    }

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public File getReportDirectory() {
        return reportDirectory;
    }

    public void setReportDirectory(File reportDirectory) {
        this.reportDirectory = reportDirectory;
    }

    public File getIncrementReportDirectory() {
        return incrementReportDirectory;
    }

    public void setIncrementReportDirectory(File incrementReportDirectory) {
        this.incrementReportDirectory = incrementReportDirectory;
    }

    @Override
    public String toString() {
        return "CodeCoverageFilesAndFoldersDTO [projectDir=" + projectDir
                + ", executionDataFile=" + executionDataFile
                + ", classesDirectory=" + classesDirectory
                + ", sourceDirectory=" + sourceDirectory + ", reportDirectory="
                + reportDirectory + ", incrementReportDirectory="
                + incrementReportDirectory + "]";
    }
}
