/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午10:12:49
 * @see:
 */
package com.administrator.platform.tools.vcs.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jacoco.core.diff.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.tools.jacoco.JacocoOperationUtil;
import com.administrator.platform.tools.jacoco.diff.DiffAST;
import com.administrator.platform.tools.vcs.svn.SvnDiffDefine;
import com.administrator.platform.util.define.FileSuffix;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午10:12:49
 * @see :
 */
public class VcsCommonUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(VcsCommonUtil.class);

	private VcsCommonUtil() {

	}

	/**
	 * 通过远程路径获取项目名称
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param remoteUrl
	 * @return
	 */
	public static String getProjectNameFromRemoteUrl(String remoteUrl) {
		if (StringUtil.isEmpty(remoteUrl)) {
			return null;
		}

		// 如果是git路径
		if (remoteUrl.endsWith(CommonDefine.REMOTE_URL_SUFFIX_GIT)) {
			// 去最后一个/的位置
			int lastSepIndex = remoteUrl
			        .lastIndexOf(CommonDefine.REMOTE_URL_SEPERATOR);
			int gitSuffixIndex = remoteUrl
			        .lastIndexOf(CommonDefine.REMOTE_URL_SUFFIX_GIT);

			if (lastSepIndex == -1 || gitSuffixIndex == -1) {
				return null;
			}
			return remoteUrl.substring(lastSepIndex + 1, gitSuffixIndex);
		} else {
			// 否则就认为是SVN
			String[] remoteUrlArray = remoteUrl
			        .split(CommonDefine.REMOTE_URL_SEPERATOR);

			// 如果包含trunk
			if (remoteUrl.indexOf(SvnDiffDefine.REMOTE_URL_TRUNK_MARK) != -1) {
				int trunkIndex = Util.getThusItemIndexFromStringArray(
				        remoteUrlArray, SvnDiffDefine.REMOTE_URL_TRUNK_MARK);
				// 如果是以trunk结尾，则属于规范性目录,去trunk前面的就行了
				if (remoteUrl.endsWith(SvnDiffDefine.REMOTE_URL_TRUNK_MARK)) {
					if (trunkIndex == -1) {
						return null;
					}
					return remoteUrlArray[trunkIndex - 1];
				} else {
					// 如果里面有trunk但是没有以trunk结尾，则表示不规范性的url，取trunk后面的
					return remoteUrlArray[trunkIndex + 1];
				}
			} else if (remoteUrl
			        .indexOf(SvnDiffDefine.REMOTE_URL_BRANCHES_MARK) != -1) {
				// 如果没有trunk,且包含branches
				int branchesIndex = Util.getThusItemIndexFromStringArray(
				        remoteUrlArray, SvnDiffDefine.REMOTE_URL_BRANCHES_MARK);
				// 如果是以branches结尾，则属于规范性目录,去branches前面的就行了
				if (remoteUrl
				        .endsWith(SvnDiffDefine.REMOTE_URL_BRANCHES_MARK)) {
					if (branchesIndex == -1) {
						return null;
					}
					return remoteUrlArray[branchesIndex - 1];
				} else {
					// 如果里面有branches但是没有以branches结尾，则表示不规范性的url，取branches后面的
					return remoteUrlArray[branchesIndex + 1];
				}
			} else if (remoteUrl
			        .indexOf(SvnDiffDefine.REMOTE_URL_TAG_MARK) != -1) {
				// 如果没有trunk,且包含branches
				int tagsIndex = Util.getThusItemIndexFromStringArray(
				        remoteUrlArray, SvnDiffDefine.REMOTE_URL_TAG_MARK);
				// 如果是以tags结尾，则属于规范性目录,去tags前面的就行了
				if (remoteUrl.endsWith(SvnDiffDefine.REMOTE_URL_TAG_MARK)) {
					if (tagsIndex == -1) {
						return null;
					}
					return remoteUrlArray[tagsIndex - 1];
				} else {
					// 如果里面有trunk但是没有以tags结尾，则表示不规范性的url，取tags后面的
					return remoteUrlArray[tagsIndex + 1];
				}
			}
		}

		return null;
	}

	/**
	 * 解析新版本文件夹
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @param codeCoverage
	 * @return
	 */
	public static File parseNewProjectFolderFromCodeCoverage(
	        CodeCoverage codeCoverage) {
		boolean newVersionEmpty = StringUtil
		        .isEmpty(codeCoverage.getNewerVersion());
		if (StringUtil.isEmpty(codeCoverage.getNewerRemoteUrl())) {
			return null;
		}
		String suffixFolderFromNewerVersion = newVersionEmpty
		        ? JacocoDefine.DEFAULT_NEWER_FOLDER_SUFFIX
		        : "_" + codeCoverage.getNewerVersion().replace("/", "_");
		String newerProjectFolderName = getProjectNameFromRemoteUrl(
		        codeCoverage.getNewerRemoteUrl())
		        + suffixFolderFromNewerVersion;
		File newerFolder = JacocoOperationUtil
		        .getCurrentSystemFileStorePath(newerProjectFolderName);
		logger.debug("新代码的路径为:{}", newerFolder.getAbsolutePath());
		return newerFolder;
	}

	/**
	 * 解析旧版本文件夹
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @param codeCoverage
	 * @return
	 */
	public static File parseOldProjectFolderFromCodeCoverage(
	        CodeCoverage codeCoverage) {
		boolean olderVersionEmpty = StringUtil
		        .isEmpty(codeCoverage.getOlderVersion());

		if (StringUtil.isEmpty(codeCoverage.getOlderRemoteUrl())) {
			return null;
		}

		String olderProjectFolderName = getProjectNameFromRemoteUrl(
		        codeCoverage.getOlderRemoteUrl())
		        + (olderVersionEmpty ? JacocoDefine.DEFAULT_OLDER_FOLDER_SUFFIX
		                : "_" + codeCoverage.getOlderVersion().replace("/",
		                        "_"));
		return JacocoOperationUtil
		        .getCurrentSystemFileStorePath(olderProjectFolderName);
	}

	// /**
	// * 获取变更文件列表
	// *
	// * @see :
	// * @param :
	// * @return : List<ChangeFile>
	// * @param codeCoverage
	// * @return
	// */
	// public static List<ChangeFile> getChangeFileList(
	// CodeCoverage codeCoverage) {
	// logger.debug("开始获取变更文件列表");
	// List<ChangeFile> changeFiles = null;
	// 如果两个url相同
	// if (codeCoverage.getNewerRemoteUrl()
	// .equals(codeCoverage.getOlderRemoteUrl())) {
	// VCSType vcsType = codeCoverage.getVersionControlType();
	//
	// if (vcsType == VCSType.GIT) {
	// return new ArrayList<ChangeFile>();
	// } else if (vcsType == VCSType.SVN) {
	// SvnClientUtil svnClientUtilNewer = new SvnClientUtil(
	// codeCoverage.getNewerRemoteUrl(), codeCoverage);
	// SvnClientUtil svnClientUtilOlder = new SvnClientUtil(
	// codeCoverage.getOlderRemoteUrl(), codeCoverage);
	//
	// // 如果URL相同
	// if (codeCoverage.getOlderRemoteUrl()
	// .equals(codeCoverage.getNewerRemoteUrl())) {
	// if (StringUtil.isEmpty(codeCoverage.getNewerVersion())
	// && StringUtil
	// .isEmpty(codeCoverage.getOlderVersion())) {
	// codeCoverage.setNewerVersion(svnClientUtilNewer
	// .getLastCommitedRevision().toString());
	// codeCoverage.setOlderVersion(svnClientUtilNewer
	// .getFirstCommitedRevision().toString());
	// }
	// changeFiles = svnClientUtilNewer
	// .getChangeFileListFromRemoteUrl(
	// codeCoverage.getNewerRemoteUrl(),
	// Long.parseLong(
	// codeCoverage.getOlderVersion()),
	// Long.parseLong(
	// codeCoverage.getNewerVersion()));
	//
	// Util.displayListInfo(changeFiles);
	// return changeFiles;
	// } else {
	// return new ArrayList<>();
	// }
	// } else {
	// return new ArrayList<>();
	// }
	// }

	// 如果两个url不同
	// 先拿到两个文件夹

	// File newerFileFolder = VcsCommonUtil
	// .parseNewProjectFolderFromCodeCoverage(codeCoverage);
	// File olderFileFolder = VcsCommonUtil.parseOldProjectFolderFromCodeCoverage(
	// codeCoverage);logger.debug("开始比较两个不同文件夹下的源文件:{}-->{}",newerFileFolder.getAbsolutePath(),olderFileFolder.getAbsolutePath());
	// // 然后对比两个文件夹的差异文件
	// List<MethodInfo> methodInfos = DiffAST.diffFilesWithTwoLocalDirs(
	// newerFileFolder.getAbsolutePath(),
	// olderFileFolder.getAbsolutePath());
	//
	// changeFiles=new ArrayList<>();for(
	// MethodInfo methodInfo:methodInfos)
	// {
	// ChangeFile changeFile = new ChangeFile();
	// changeFile.setPackageName(methodInfo.getPackages());
	// changeFile.setFileName(
	// methodInfo.getClassName() + JacocoDefine.JAVA_SUFFIX);
	// changeFiles.add(changeFile);
	// }
	// // Util.displayListInfo(changeFiles);
	// return changeFiles;
	// }

	/**
	 * 获取变更文件列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<ChangeFile>
	 * @param codeCoverage
	 * @return
	 */
	public static List<MethodInfo> getChangeMethodsList(
	        CodeCoverage codeCoverage) {
		logger.debug("开始获取变更方法列表");
		// 先拿到两个文件夹
		File newerFileFolder = VcsCommonUtil
		        .parseNewProjectFolderFromCodeCoverage(codeCoverage);
		File olderFileFolder = VcsCommonUtil
		        .parseOldProjectFolderFromCodeCoverage(codeCoverage);
		logger.debug("开始比较两个不同文件夹下的源文件:{}-->{}",
		        newerFileFolder.getAbsolutePath(),
		        olderFileFolder.getAbsolutePath());
		// 然后对比两个文件夹的差异文件
		List<MethodInfo> changeMethods = new ArrayList<>();
		List<MethodInfo> currentChangeMethods = DiffAST
		        .diffFilesWithTwoLocalDirs(newerFileFolder.getAbsolutePath(),
		                olderFileFolder.getAbsolutePath());
		changeMethods.addAll(currentChangeMethods);
		// Util.displayListInfo(changeMethods);
		return changeMethods;
	}

	/**
	 * 解析全量和增量覆盖率报告的路径
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param args
	 */
	public static String parseWholeCodeCoverageFolderPath(File fileFolder) {
		return new File(fileFolder.getAbsolutePath(),
		        JacocoDefine.CODE_COVERAGE_DATA_FOLDER).getAbsolutePath();
	}

	/**
	 * 获取增量覆盖率报告的路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fileFolder
	 * @return
	 */
	public static String parseIncrementCodeCoverageFolderPath(File fileFolder) {
		return new File(fileFolder.getAbsolutePath(),
		        JacocoDefine.INCREMENT_CODE_COVERAGE_DATA_FOLDER)
		                .getAbsolutePath();
	}

	/**
	 * 解析全量和增量覆盖率报告的路径
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param args
	 */
	public static String parseWholeCodeCoverageFolderPathZip(File fileFolder) {
		return parseWholeCodeCoverageFolderPath(fileFolder)
		        + FileSuffix.ZIP_FILE.getFileType();
	}

	/**
	 * 获取增量覆盖率报告的路径
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fileFolder
	 * @return
	 */
	public static String parseIncrementCodeCoverageFolderPathZip(
	        File fileFolder) {
		return parseIncrementCodeCoverageFolderPath(fileFolder)
		        + FileSuffix.ZIP_FILE.getFileType();
	}

	public static void main(String[] args) {
		String svnUrlRoot = "http://sunliuping@gitlab.hztianque.com/social/zhoushan.git";

		System.out.println(getProjectNameFromRemoteUrl(svnUrlRoot));
	}
}
