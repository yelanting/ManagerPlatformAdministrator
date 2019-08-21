package com.administrator.platform.tools.jacoco.diff;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.jacoco.core.diff.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.define.FileSuffix;

import sun.misc.BASE64Encoder;

/**
 * AST对比
 * 
 * @author administrator
 */
public class DiffAST {
	private static final Logger logger = LoggerFactory.getLogger(DiffAST.class);
	/**
	 * 用于装载对比结果
	 */
	public static List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
	/**
	 * 文件列表
	 */
	public static List<File> fileList = new ArrayList<File>();

	/**
	 * 路径分隔符
	 */
	public static final String SEPARATOR = System.getProperty("file.separator");

	private static void initComparedInfos() {
		methodInfos.clear();
		fileList.clear();
	}

	/**
	 * @param newTag
	 *            当前tag
	 * @param oldTag
	 *            对比tag
	 * @return
	 */
	public static List<MethodInfo> diffDir(final String newTag,
			final String oldTag) {
		// src1是整个工程中有变更的文件,src2是历史版本全量文件,都是相对路径,例如在当前工作空间下生成tag1和tag2
		final String currentFolderInfo = new File(
				System.getProperty("user.dir")).getAbsolutePath();
		// 同级目录
		final String parentFolderFromCurrentPosition = new File(
				System.getProperty("user.dir")).getParent();
		final String newTagFolderPath = currentFolderInfo;
		final String oldTagFolderToCompare = parentFolderFromCurrentPosition
				+ SEPARATOR + oldTag;
		final List<File> filesUnderNewTagFolderPath = getFileList(
				newTagFolderPath);
		for (final File eachNewFile : filesUnderNewTagFolderPath) {
			// 非普通类不处理
			if (!ASTGeneratror
					.isTypeDeclaration(eachNewFile.getAbsolutePath())) {
				continue;
			}
			final File thusOldFileToCompareWith = new File(
					oldTagFolderToCompare + eachNewFile.getAbsolutePath()
							.replace(newTagFolderPath, ""));
			diffFile(eachNewFile.toString(),
					thusOldFileToCompareWith.toString());
		}
		return methodInfos;
	}

	/**
	 * @param baseDir
	 *            与当前项目空间同级的历史版本代码路径
	 * @return
	 */
	public static List<MethodInfo> diffBaseDir(final String baseDir) {
		final String pwd = new File(System.getProperty("user.dir"))
				.getAbsolutePath();// 同级目录
		final String parent = new File(System.getProperty("user.dir"))
				.getParent();
		final String tag1Path = pwd;
		final String tag2Path = parent + SEPARATOR + baseDir;
		final List<File> files1 = getFileList(tag1Path);
		for (final File f : files1) {
			// 非普通类不处理
			if (!ASTGeneratror.isTypeDeclaration(f.getAbsolutePath())) {
				continue;
			}
			final File f2 = new File(
					tag2Path + f.getAbsolutePath().replace(tag1Path, ""));
			diffFile(f.toString(), f2.toString());
		}
		return methodInfos;
	}

	/**
	 * 对比两个文件夹，需要两个文件夹的下层层次结构一致
	 * 
	 * @see :
	 * @param :
	 * @return : List<MethodInfo>
	 * @param oldDir:旧版本文件夹
	 *            是历史版本全量文件,如果oldDir是null，则代表取全量
	 * @param newDir:新文件夹
	 *            是整个工程中有变更的文件
	 * @return
	 */
	public static List<MethodInfo> diffFilesWithTwoLocalDirs(String newDir,
			String oldDir) {
		return diffFilesWithTwoLocalDirsWithJdkVersion(newDir, oldDir,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 对比两个文件夹，需要两个文件夹的下层层次结构一致
	 * 
	 * @see :
	 * @param :
	 * @return : List<MethodInfo>
	 * @param oldDir:旧版本文件夹
	 *            是历史版本全量文件,如果oldDir是null，则代表取全量
	 * @param newDir:新文件夹
	 *            是整个工程中有变更的文件
	 * @return
	 */
	public static List<MethodInfo> diffFilesWithTwoLocalDirsWithJdkVersion(
			String newDir, String oldDir, String jdkVersion) {

		methodInfos = new ArrayList<>();
		fileList = new ArrayList<>();

		File tempSrcFileOfNewFolder = new File(newDir + File.separator
				+ JacocoDefine.COMMON_SOURCE_FILES_FOLDER_START_MARK);
		File tempSrcFileOfOldFolder = null;
		if (StringUtil.isStringAvaliable(oldDir)) {
			tempSrcFileOfOldFolder = new File(oldDir + File.separator
					+ JacocoDefine.COMMON_SOURCE_FILES_FOLDER_START_MARK);
		}

		if (tempSrcFileOfNewFolder.exists()) {
			newDir = tempSrcFileOfNewFolder.getAbsolutePath();
		}

		if (null != tempSrcFileOfOldFolder && tempSrcFileOfOldFolder.exists()) {
			oldDir = tempSrcFileOfOldFolder.getAbsolutePath();
		}

		logger.debug("开始在两个目录下比对源文件的方法差异级别,只比对src下的：新：{},旧：{},编译jdk版本:{}",
				newDir, oldDir, jdkVersion);

		final List<File> filesInNewFolder = getFileList(newDir);

		// 遍历新工程中的文件列表
		for (final File eachNewFile : filesInNewFolder) {
			// 非普通类不处理
			if (!ASTGeneratror.isTypeDeclarationWithJdkVersion(
					eachNewFile.getAbsolutePath(), jdkVersion)) {
				continue;
			}

			// 这个新文件对应的旧文件
			if (!StringUtil.isEmpty(oldDir)) {
				final File thusOldFileOfThisNewFile = new File(
						eachNewFile.getAbsolutePath().replace(newDir, oldDir));
				diffFileWithJdkVersion(eachNewFile.toString(),
						thusOldFileOfThisNewFile.toString(), jdkVersion);
			} else {
				diffFileWithJdkVersion(eachNewFile.getAbsolutePath(), null,
						jdkVersion);
			}
		}

		List<MethodInfo> methodInfosFinal = new ArrayList<>();
		methodInfosFinal.addAll(methodInfos);
		return methodInfosFinal;
	}

	/**
	 * 对比两个文件夹，需要两个文件夹的下层层次结构一致
	 * 
	 * @see :
	 * @param :
	 * @return : List<MethodInfo>
	 * @param oldDir:旧版本文件夹
	 *            是历史版本全量文件,如果oldDir是null，则代表取全量
	 * @param newDir:新文件夹
	 *            是整个工程中有变更的文件
	 * @return
	 */
	public static List<MethodInfo> diffFilesWithTwoLocalDirsWithJdkSix(
			String newDir, String oldDir) {
		return diffFilesWithTwoLocalDirsWithJdkVersion(newDir, oldDir,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 对比文件
	 * 
	 * @param newFile
	 * @param oldFile
	 * @return
	 */
	public static List<MethodInfo> diffFile(final String newFile,
			final String oldFile) {
		return diffFileWithJdkVersion(newFile, oldFile,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 对比文件
	 * 
	 * @param newFile
	 * @param oldFile
	 * @return
	 */
	public static List<MethodInfo> diffFileWithJdkVersion(final String newFile,
			final String oldFile, String jdkVersion) {
		final MethodDeclaration[] methods1 = ASTGeneratror
				.getMethodsWithJdkVersion(newFile, jdkVersion);

		// 如果旧文件不存在，则认为是新文件新增
		if (StringUtil.isEmpty(oldFile) || !new File(oldFile).exists()) {
			for (final MethodDeclaration method : methods1) {
				final MethodInfo methodInfo = methodToMethodInfoWithJdkVersion(
						newFile, method, jdkVersion);
				methodInfos.add(methodInfo);
			}
		} else {
			final MethodDeclaration[] methods2 = ASTGeneratror
					.getMethodsWithJdkVersion(oldFile, jdkVersion);
			final Map<String, MethodDeclaration> methodsMap = new HashMap<String, MethodDeclaration>(
					16);
			for (int i = 0; i < methods2.length; i++) {
				methodsMap.put(
						methods2[i].getName().toString()
								+ methods2[i].parameters().toString(),
						methods2[i]);
			}
			for (final MethodDeclaration method : methods1) {
				// 如果方法名是新增的,则直接将方法加入List
				if (!isMethodExist(method, methodsMap)) {
					final MethodInfo methodInfo = methodToMethodInfoWithJdkVersion(
							newFile, method, jdkVersion);
					methodInfos.add(methodInfo);
				} else {
					// 如果两个版本都有这个方法,则根据MD5判断方法是否一致
					if (!isMethodTheSame(method,
							methodsMap.get(method.getName().toString()
									+ method.parameters().toString()))) {
						final MethodInfo methodInfo = methodToMethodInfoWithJdkVersion(
								newFile, method, jdkVersion);
						methodInfos.add(methodInfo);
					}
				}
			}
		}
		return methodInfos;
	}

	/**
	 * 判斷方法是否存在
	 * 
	 * @param method
	 * @param methodsMap
	 * @return
	 */
	public static boolean isMethodExist(final MethodDeclaration method,
			final Map<String, MethodDeclaration> methodsMap) {
		// 方法名+参数一致才一致
		return methodsMap.containsKey(
				method.getName().toString() + method.parameters().toString());

	}

	/**
	 * 判斷方法是否一致
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	public static boolean isMethodTheSame(final MethodDeclaration method1,
			final MethodDeclaration method2) {
		return makeMD5Encode(method1.toString())
				.equals(makeMD5Encode(method2.toString()));
	}

	/**
	 * @param file
	 * @param method
	 * @return
	 */
	public static MethodInfo methodToMethodInfo(final String file,
			final MethodDeclaration method) {
		return methodToMethodInfoWithJdkVersion(file, method,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * @param file
	 * @param method
	 * @return
	 */
	public static MethodInfo methodToMethodInfoWithJdkVersion(final String file,
			final MethodDeclaration method, String jdkVersion) {
		final MethodInfo methodInfo = new MethodInfo();
		methodInfo.setClassFile(file);
		methodInfo.setClassName(ASTGeneratror
				.getClassWithJdkVersion(file, jdkVersion).getName().toString());
		methodInfo.setPackages(
				ASTGeneratror.getPackageWithJdkVersion(file, jdkVersion));
		methodInfo.setMd5(makeMD5Encode(method.toString()));
		methodInfo.setMethodName(method.getName().toString());
		methodInfo.setParameters(method.parameters().toString());
		return methodInfo;
	}

	/**
	 * 根据路径获取文件列表
	 * 
	 * @param src
	 * @return
	 */
	public static List<File> getFileList(final String src) {
		final File dir = new File(src);
		final File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {

				final File currentFile = files[i];

				final String fileName = currentFile.getName();
				final String filePath = currentFile.getAbsolutePath();
				boolean isJavaFile = fileName
						.endsWith(FileSuffix.JAVA_FILE.getFileType());

				boolean isInMainSourcePath = filePath
						.contains(JacocoDefine.SRC_MAIN_SOURCES_FOLDER_PATH);

				boolean isInTestSourcePath = filePath
						.contains(JacocoDefine.TEST_FILE_SOURCE_FOLDER_PATH);

				boolean isJavaFileAndInMainSourcePathButNotInTestSourceFolder = isJavaFile
						&& isInMainSourcePath && (!isInTestSourcePath);

				if (currentFile.isDirectory()) {
					getFileList(currentFile.getAbsolutePath());
				} else if (isJavaFileAndInMainSourcePathButNotInTestSourceFolder) {
					fileList.add(currentFile);
				}
			}
		}
		return fileList;
	}

	/**
	 * MD5
	 *
	 * @param s
	 *            待MD5的字符串
	 * @return
	 */
	public static String makeMD5Encode(String s) {
		String madeMD5String = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			madeMD5String = base64en.encode(
					md5.digest(s.getBytes(GlobalDefination.CHAR_SET_DEFAULT)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return madeMD5String;
	}

	public static void main(String[] args) {
		String oldDir = "C:\\Users\\Administrator\\jacoco_coverage\\tq-robot_605098";
		String newDir = "C:\\Users\\Administrator\\jacoco_coverage\\tq-robot-1.0.0_604713";
		diffFilesWithTwoLocalDirs(newDir, oldDir);
	}

}
