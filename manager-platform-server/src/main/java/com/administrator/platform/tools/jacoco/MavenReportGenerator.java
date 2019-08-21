/// **
// * @author : 孙留平
// * @since : 2019年3月29日 上午10:07:16
// * @see:
// */
// package com.administrator.platform.tools.jacoco;
//
// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Locale;
//
// import org.jacoco.maven.AbstractReportMojo;
// import org.jacoco.maven.FileFilter;
// import org.jacoco.maven.ReportSupport;
// import org.jacoco.report.IReportGroupVisitor;
//
// import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
// import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
//
/// **
// * @author : Administrator
// * @since : 2019年3月29日 上午10:07:16
// * @see :
// */
// public class MavenReportGenerator extends AbstractReportMojo {
// private File projectDir;
// private CodeBuildIntf codeBuildIntf;
// List<String> dataFileIncludes;
// List<String> dataFileExcludes;
// private File outputDirectory;
//
// private MavenReportGenerator() {
// }
//
// public MavenReportGenerator(String projectDir) {
// this.projectDir = new File(projectDir);
// this.codeBuildIntf = new CodeBuildMaven();
//
// initLocalFields();
// }
//
// public MavenReportGenerator(File projectDir) {
// this.projectDir = projectDir;
// this.codeBuildIntf = new CodeBuildMaven();
//
// initLocalFields();
// }
//
// private void initLocalFields() {
//
// this.dataFileExcludes = new ArrayList<>();
//
// this.dataFileIncludes = codeBuildIntf
// .getExecutionDataFiles(this.projectDir.getAbsolutePath());
//
// this.outputDirectory = new File(this.projectDir.getAbsoluteFile(),
// this.projectDir.getName());
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.apache.maven.reporting.MavenReport#getOutputName()
// */
// @Override
// public String getOutputName() {
// return this.projectDir.getName();
// }
//
// /**
// *
// * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
// */
// @Override
// public String getName(Locale locale) {
// return this.projectDir.getName();
// }
//
// /**
// *
// * @see
// * org.jacoco.maven.AbstractReportMojo#canGenerateReportRegardingDataFiles()
// */
// @Override
// boolean canGenerateReportRegardingDataFiles() {
//
// return true;
// }
//
// /**
// *
// * @see org.jacoco.maven.AbstractReportMojo#
// * canGenerateReportRegardingClassesDirectory()
// */
// @Override
// boolean canGenerateReportRegardingClassesDirectory() {
// return true;
// }
//
// /**
// *
// * @see
// * org.jacoco.maven.AbstractReportMojo#loadExecutionData(org.jacoco.maven.
// * ReportSupport)
// */
// @Override
// void loadExecutionData(ReportSupport support) throws IOException {
// if (null == dataFileIncludes) {
// dataFileIncludes = Arrays.asList("*.exec");
// }
// final FileFilter fileFilter = new FileFilter(dataFileIncludes,
// dataFileExcludes);
// loadExecutionData(support, fileFilter, this.projectDir);
//
// }
//
// private void loadExecutionData(final ReportSupport support,
// final FileFilter filter, final File basedir) throws IOException {
// for (final File execFile : filter.getFiles(basedir)) {
// support.loadExecutionData(execFile);
// }
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.jacoco.maven.AbstractReportMojo#addFormatters(org.jacoco.maven.
// * ReportSupport, java.util.Locale)
// */
// @Override
// void addFormatters(ReportSupport support, Locale locale)
// throws IOException {
// support.addAllFormatters(outputDirectory, outputEncoding, footer,
// locale);
//
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.jacoco.maven.AbstractReportMojo#createReport(org.jacoco.report.
// * IReportGroupVisitor, org.jacoco.maven.ReportSupport)
// */
// @Override
// void createReport(IReportGroupVisitor visitor, ReportSupport support)
// throws IOException {
// // TODO Auto-generated method stub
//
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
// */
// @Override
// protected String getOutputDirectory() {
// // TODO Auto-generated method stub
// return null;
// }
//
// }
