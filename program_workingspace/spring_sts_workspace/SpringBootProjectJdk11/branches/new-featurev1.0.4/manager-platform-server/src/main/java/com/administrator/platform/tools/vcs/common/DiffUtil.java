/// **
// * @author : 孙留平
// * @since : 2019年3月1日 上午10:07:04
// * @see:
// */
// package com.administrator.platform.tools.vcs.common;
//
// import java.io.File;
//
// import com.administrator.platform.tools.jacoco.JacocoDefine;
// import com.administrator.platform.tools.vcs.svn.SvnDiffDefine;
//
/// **
// * @author : Administrator
// * @since : 2019年3月1日 上午10:07:04
// * @see :
// */
// public class DiffUtil {
//
// private DiffUtil() {
//
// }
//
// /**
// * 判断是不是java文件
// *
// * @see :
// * @param :
// * @return : boolean
// * @param path
// * @return
// */
// public static boolean isJavaFile(String path) {
// return path.endsWith(SvnDiffDefine.JAVA_FILE_SUFFIX);
// }
//
// /**
// * 是不是接口文件
// *
// * @see :
// * @param :
// * @return : boolean
// * @param path
// * @return
// */
// public static boolean isInterface(String path) {
// return false;
// }
//
// /**
// * 检查一个字符串是否以公司的标志开始
// *
// * @see :
// * @param :
// * @return : boolean
// * @param toCheckString
// * @return
// */
// public static boolean checkStartsWithCompanyMark(String toCheckString) {
// for (String currentMark : CommonDefine.COMPANY_MARK_LIST) {
// if (toCheckString.startsWith(currentMark)) {
// return true;
// }
// }
//
// return false;
// }
//
// public static NeededInfoFromChangeFile getNeededInfoFromChangeFile(
// ChangeFile changeFile) {
//
// if (null == changeFile) {
// return null;
// }
//
// NeededInfoFromChangeFile neededInfoFromChangeFile = new NeededInfoFromChangeFile();
//
// String packageName = changeFile.getPackageName();
// String fileName = changeFile.getFileName();
//
// StringBuilder folderName = new StringBuilder();
//
// String[] foldersFromPackage = packageName.split("\\.");
//
// for (String string : foldersFromPackage) {
// folderName.append(string).append(File.separator);
// }
//
// neededInfoFromChangeFile.setClassFolder(folderName.toString());
// neededInfoFromChangeFile.setClassName(fileName
// .replace(JacocoDefine.JAVA_SUFFIX, JacocoDefine.CLASS_SUFFIX));
// neededInfoFromChangeFile.setSourceFolder(folderName.toString());
// return neededInfoFromChangeFile;
// }
// }
