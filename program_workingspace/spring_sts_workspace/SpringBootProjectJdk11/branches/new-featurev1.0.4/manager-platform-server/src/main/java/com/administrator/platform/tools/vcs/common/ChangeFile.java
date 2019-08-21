/**
 * @author : 孙留平
 * @since : 2019年2月28日 下午9:31:24
 * @see:
 */
/*
 * package com.administrator.platform.tools.vcs.common;
 * 
 * import java.util.List;
 * 
 * import org.tmatesoft.svn.core.wc.SVNStatusType;
 * 
 * import com.administrator.platform.tools.vcs.svn.FileChangedInfo;
 * 
 *//**
    * @author : Administrator
    * @since : 2019年2月28日 下午9:31:24
    * @see :
    */
/*
 * public class ChangeFile {
 *//**
    * 文件名
    */
/*
 * private String fileName;
 * 
 *//**
    * 文件名
    */
/*
 * private String packageName;
 * 
 *//**
    * 相对路径，相对于项目根目录的相对路径
    */
/*
 * private String relativePath;
 * 
 *//**
    * 是不是接口文件
    */
/*
 * private boolean interfaceFile;
 * 
 *//**
    * 在版本库中的url路径，全路径
    */
/*
 * private String filePath;
 * 
 *//**
    * 文件类型
    */
/*
 * private String fileType;
 * 
 *//**
   * 
   */
/*
 * private SVNStatusType changeType;
 * 
 *//**
    * 文件内容
    */
/*
 * private String fileContent;
 * 
 *//**
    * 新增行
    */
/*
 * private List<Integer> addLines;
 * 
 *//**
    * 变更行号
    */
/*
 * private List<Integer> modifiedLines;
 * 
 *//**
    * 删除行号
    */
/*
 * private List<Integer> deletedLines;
 * 
 *//**
    * 
    * @see :
    *//*
       * private List<FileChangedInfo> fileChangedInfo;
       * 
       * public ChangeFile() {
       * }
       * 
       * public ChangeFile(String filePath) {
       * this.filePath = filePath;
       * this.fileType = getFileTypeFromPath(filePath);
       * }
       * 
       * public String getFilePath() {
       * return filePath;
       * }
       * 
       * public void setFilePath(String filePath) {
       * this.filePath = filePath;
       * }
       * 
       * public String getFileType() {
       * return fileType;
       * }
       * 
       * public void setFileType(String fileType) {
       * this.fileType = fileType;
       * }
       * 
       * public String getFileContent() {
       * return fileContent;
       * }
       * 
       * public void setFileContent(String fileContent) {
       * this.fileContent = fileContent;
       * }
       * 
       * private static String getFileTypeFromPath(String path) {
       * String fileType = "";
       * int idx = path.lastIndexOf(".");
       * if (idx > -1) {
       * fileType = path.substring(idx + 1).trim().toLowerCase();
       * }
       * return fileType;
       * }
       * 
       * public List<Integer> getAddLines() {
       * return addLines;
       * }
       * 
       * public void setAddLines(List<Integer> addLines) {
       * this.addLines = addLines;
       * }
       * 
       * public List<Integer> getModifiedLines() {
       * return modifiedLines;
       * }
       * 
       * public void setModifiedLines(List<Integer> modifiedLines) {
       * this.modifiedLines = modifiedLines;
       * }
       * 
       * public List<Integer> getDeletedLines() {
       * return deletedLines;
       * }
       * 
       * public void setDeletedLines(List<Integer> deletedLines) {
       * this.deletedLines = deletedLines;
       * }
       * 
       * public String getFileName() {
       * return fileName;
       * }
       * 
       * public void setFileName(String fileName) {
       * this.fileName = fileName;
       * }
       * 
       * public String getPackageName() {
       * return packageName;
       * }
       * 
       * public void setPackageName(String packageName) {
       * this.packageName = packageName;
       * }
       * 
       * public boolean isInterfaceFile() {
       * return interfaceFile;
       * }
       * 
       * public void setInterfaceFile(boolean interfaceFile) {
       * this.interfaceFile = interfaceFile;
       * }
       * 
       * public SVNStatusType getChangeType() {
       * return changeType;
       * }
       * 
       * public void setChangeType(SVNStatusType changeType) {
       * this.changeType = changeType;
       * }
       * 
       * public List<FileChangedInfo> getFileChangedInfo() {
       * return fileChangedInfo;
       * }
       * 
       * public void setFileChangedInfo(List<FileChangedInfo> fileChangedInfo) {
       * this.fileChangedInfo = fileChangedInfo;
       * }
       * 
       * public String getRelativePath() {
       * return relativePath;
       * }
       * 
       * public void setRelativePath(String relativePath) {
       * this.relativePath = relativePath;
       * }
       * 
       * @Override
       * public String toString() {
       * return "ChangeFile [fileName=" + fileName + ", packageName="
       * + packageName + ", relativePath=" + relativePath
       * + ", interfaceFile=" + interfaceFile + ", filePath=" + filePath
       * + ", fileType=" + fileType + ", changeType=" + changeType
       * + ", fileContent=" + fileContent + ", addLines=" + addLines
       * + ", modifiedLines=" + modifiedLines + ", deletedLines="
       * + deletedLines + ", fileChangedInfo=" + fileChangedInfo + "]";
       * }
       * }
       */