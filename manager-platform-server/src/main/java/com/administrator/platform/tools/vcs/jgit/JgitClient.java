/**
 * @author : 孙留平
 * @since : 2019年2月27日 上午11:00:27
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.ListTagCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.exception.base.BusinessValidationException;

/**
 * @author : Administrator
 * @since : 2019年2月27日 上午11:00:27
 * @see :
 */
public class JgitClient {
	private UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider;
	private static final Logger logger = LoggerFactory
	        .getLogger(JgitClient.class);
	private static final String COMMON_ERROR_NOTICE = "Clone error";

	private static final String GIT_SUFFIX = "/.git";

	private static final String DEFAULT_GIT_OPERATION_EEROR = "Git操作异常，请联系管理员";
	private static final String DEFAULT_GIT_AUTH_EEROR = "Git操作异常，请联系管理员";

	private JgitClient(String username, String password) {
		this.usernamePasswordCredentialsProvider = new UsernamePasswordCredentialsProvider(
		        username, password);
	}

	private JgitClient(
	        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider) {
		this.usernamePasswordCredentialsProvider = usernamePasswordCredentialsProvider;
	}

	public static JgitClient fromUsernameAndPassword(String username,
	        String password) {
		return new JgitClient(username, password);
	}

	/**
	 * 克隆
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrl
	 * @param repoDir
	 */
	public void gitCloneCommon(String remoteUrl, File repoDir) {
		gitCloneCommon(remoteUrl, repoDir, null);
	}

	/**
	 * 克隆
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrl
	 * @param repoDir
	 */
	public void gitCloneCommon(String remoteUrl, File repoDir,
	        String branchName) {

		if (StringUtil.isEmpty(branchName)) {
			branchName = GitDefine.GIT_MASTER_NAME;
		}

		try {
			logger.debug("开始克隆代码:{},到:{}", remoteUrl,
			        repoDir.getAbsolutePath());
			CloneCommand cloneCommand = Git.cloneRepository().setURI(remoteUrl)
			        .setDirectory(repoDir);

			if (null != branchName) {
				cloneCommand.setBranch(branchName);
			}
			cloneCommand.setCredentialsProvider(
			        usernamePasswordCredentialsProvider);
			cloneCommand.call();

			validateRemoteUrl(repoDir.getAbsolutePath(), remoteUrl, branchName);

			logger.info("Cloning from:{} to {} 完成", remoteUrl, repoDir);
		} catch (InvalidRemoteException e) {
			logger.error("{}:{}", COMMON_ERROR_NOTICE, e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		} catch (TransportException e) {
			logger.error("{}:{}", COMMON_ERROR_NOTICE, e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_AUTH_EEROR);
		} catch (GitAPIException e) {
			logger.error("{}:{}", COMMON_ERROR_NOTICE, e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}

	/**
	 * 克隆
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrl
	 * @param repoDir
	 * @param branchName:分支名称
	 */
	public void gitCloneAndGitPull(String remoteUrl, File repoDir,
	        String branchName) {

		if (StringUtil.isEmpty(remoteUrl) || null == repoDir) {
			logger.error("你想下代码，但是没有给url，我们无法下载");
			return;
		}
		logger.debug("开始克隆代码:{},到:{}", remoteUrl, repoDir.getAbsolutePath());
		branchName = initDefaultBranchName(branchName);

		if (!repoDir.exists()) {
			logger.debug("当前git仓库不存在，clone");
			gitCloneCommon(remoteUrl, repoDir, branchName);
			return;
		}

		logger.debug("当前代码仓库已经存在，检查详情是否和当前远程一致");
		boolean sameRemoteUrlAndBranchName = checkLocalDirGitInfoTheSameAsCurrent(
		        repoDir.getAbsolutePath(), remoteUrl, branchName);
		// 如果url和分支都相同
		if (sameRemoteUrlAndBranchName) {
			logger.debug("当前本地git仓库和远程完全一致，直接pull");
			gitPull(repoDir);
			return;
		}

		// 存在的时候，检查本地git和远程url是否一致
		boolean sameRemoteUrl = checkLocalDirGitInfoTheSameAsCurrent(
		        repoDir.getAbsolutePath(), remoteUrl, null);
		// 如果不一致，删除目录
		if (!sameRemoteUrl) {
			logger.debug("当前本地git仓库和远程不一致。需要删除目录重新clone");
			FileUtil.forceDeleteDirectory(repoDir);
			gitCloneCommon(remoteUrl, repoDir, branchName);
		} else {
			// 如果url一致，则检出到分支
			logger.debug("当前git仓库和远程不同分支，切换分支");
			// 如果本地分支存在
			if (checkBranchNameExists(repoDir.getAbsolutePath(), branchName,
			        false)) {
				logger.debug("本地分支存在，不创建本地分支，直接切换");
				gitCheckout(repoDir, branchName, false, branchName);
			} else {
				logger.debug("本地分支不存在，创建本地分支并切换");
				gitCheckout(repoDir, branchName, true, branchName);
			}
		}
	}

	/**
	 * 克隆
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrl
	 * @param repoDir
	 * @param branchName:分支名称
	 */
	public void gitCloneAndGitPull(String remoteUrl, File repoDir) {
		gitCloneAndGitPull(remoteUrl, repoDir, GitDefine.GIT_MASTER_NAME);
	}

	/**
	 * 切分支
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param repoDir
	 * @param remoteBranchName
	 */
	public void gitCheckout(File repoDir, String remoteBranchName) {
		gitCheckout(repoDir, remoteBranchName, true, remoteBranchName);
	}

	/**
	 * 切分支
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param repoDir
	 * @param version
	 * @param createLocalBranch：是否需要创建本地分支，true是表示需要(通常用户本地没有该分支时)；false表示不需要，通常用于本地已有，直接切换
	 */
	public void gitCheckout(File repoDir, String localBranchName,
	        boolean createLocalBranch, String remoteBranchName) {
		File repoGitDir = new File(repoDir.getAbsolutePath() + GIT_SUFFIX);
		if (!repoGitDir.exists()) {
			logger.info("Error! Not Exists : {}", repoGitDir.getAbsolutePath());
			return;
		}

		try (Repository repo = new FileRepository(repoGitDir.getAbsolutePath());
		        Git git = new Git(repo);) {
			boolean remoteBranchExists = checkBranchNameExists(
			        repoDir.getAbsolutePath(), remoteBranchName, true);
			if (!remoteBranchExists) {
				logger.error("远程分支:{}不存在，无法checkout", remoteBranchName);
				throw new BusinessValidationException(
				        "远程分支:" + remoteBranchName + "不存在，无法checkout");
			}

			CheckoutCommand checkout = git.checkout();
			checkout.setName(localBranchName).setCreateBranch(createLocalBranch)
			        .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM);

			if (StringUtil.isStringAvaliable(remoteBranchName)) {
				checkout.setStartPoint(
				        GitDefine.GIT_ORIGIN_NAME + "/" + remoteBranchName);
			}

			checkout.call();
			logger.info("Checkout to:{} ", localBranchName);
			PullCommand pullCmd = git.pull();
			pullCmd.setCredentialsProvider(usernamePasswordCredentialsProvider);
			pullCmd.call();
			logger.info("Pulled from remote repository to local repository at "
			        + repo.getDirectory());
		} catch (WrongRepositoryStateException e) {
			logger.error("WrongRepositoryStateException不存在.错误信息:{}",
			        e.getMessage());
			throw new BusinessValidationException("GIT操作异常");
		} catch (InvalidConfigurationException | RefNotAdvertisedException
		        | NoHeadException | CanceledException | TransportException e) {
			logger.error("操作异常:{}", e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		} catch (InvalidRemoteException e) {
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		} catch (RefNotFoundException e) {
			logger.error("RefNotFoundException不存在.错误信息:{}", e.getMessage());
			throw new BusinessValidationException(
			        "分支名称" + remoteBranchName + "不存在！");
		} catch (GitAPIException | IOException e) {
			logger.error("GIT 或者IO 异常:{}", e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}

	/**
	 * 从某个远程分支创建本地分支，并checkout
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param repoDir
	 * @param version
	 */
	public void gitCheckoutFromRemoteBranch(File repoDir, String branchName) {
		gitCheckout(repoDir, branchName, true, branchName);
	}

	/**
	 * 获取当前本地目录的远程分支
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param localDir
	 * @param branchName
	 * @return
	 */
	private String getRemoteBranchRefOfThisLocalDirBranch(File localDir,
	        String branchName) {
		List<Ref> thisBranchRefList = getAllRemoteBranchesFromLocalDir(
		        localDir.getAbsolutePath());
		for (Ref ref : thisBranchRefList) {
			String refName = ref.getName();
			String branchNameTemp = refName.substring(refName.lastIndexOf("/"),
			        refName.length());
			if (branchNameTemp.equalsIgnoreCase(branchName)) {
				return refName;
			}
		}
		return null;
	}

	/**
	 * 更新
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param repoDir
	 */
	public void gitPull(File repoDir) {
		File repoGitDir = new File(repoDir.getAbsolutePath() + GIT_SUFFIX);
		if (!repoGitDir.exists()) {
			logger.info("Error! Not Exists : {}", repoGitDir.getAbsolutePath());
			throw new BusinessValidationException("本地路径不存在，pull代码异常");
		}

		try (Git git = Git.open(repoDir)) {
			PullCommand pullCmd = git.pull();
			pullCmd.setCredentialsProvider(usernamePasswordCredentialsProvider);

			PullResult pullResult = pullCmd.call();
			logger.info(
			        "Pulled from remote repository to local repository at :{}, pull result is :{} ",
			        repoDir.getAbsolutePath(), pullResult.isSuccessful());
		} catch (Exception e) {
			logger.error("gitPull:{}->{}", repoDir.getAbsolutePath(),
			        e.getMessage());

			throw new BusinessValidationException("Git pull操作异常！");
		}
	}

	/**
	 * 查看状态
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param repoDir
	 */
	public void gitShowStatus(File repoDir) {
		File repoGitDir = new File(repoDir.getAbsolutePath() + GIT_SUFFIX);
		if (!repoGitDir.exists()) {
			logger.info("Error! Not Exists :{} ", repoGitDir.getAbsolutePath());
			return;
		}

		try (Git git = Git.open(repoDir)) {
			Status status = git.status().call();
			logger.info("Git Change: {}", status.getChanged());
			logger.info("Git Modified:{} ", status.getModified());
			logger.info("Git UncommittedChanges:{} ",
			        status.getUncommittedChanges());
		} catch (Exception e) {
			logger.info("showStatus error:{}->{}", repoDir.getAbsolutePath(),
			        e.getMessage());
		}
	}

	/**
	 * 查看日志
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param dirName
	 */
	public void gitLog(File dirName) {
		// 提取某个作者的提交，并打印相关信息

		File gitFilePath = new File(dirName.getAbsolutePath() + GIT_SUFFIX);

		try (Git git = Git.open(gitFilePath)) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Iterable<RevCommit> results = git.log()
			        .setRevFilter(new RevFilter() {
				        @Override
				        public boolean include(RevWalk walker, RevCommit cmit)
				                throws StopWalkException,
				                MissingObjectException,
				                IncorrectObjectTypeException, IOException {
					        return true;
				        }

				        @Override
				        public RevFilter clone() {
					        return this;
				        }
			        }).call();

			results.forEach(commit -> {
				PersonIdent authoIdent = commit.getAuthorIdent();
				logger.debug("提交人：  " + authoIdent.getName() + "     <"
				        + authoIdent.getEmailAddress() + ">");
				logger.debug("提交SHA1：{}  ", commit.getId().name());
				logger.debug("提交信息：  {}", commit.getShortMessage());
				logger.debug("提交时间：  {}", format.format(authoIdent.getWhen()));
			});
		} catch (IOException | GitAPIException e) {
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}

	/**
	 * 初始化
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param dirName
	 */
	public void gitInit(File dirName) {
		logger.debug("在:{}下初始化git", dirName.getAbsolutePath());
		try {
			Git.init().setGitDir(dirName).setDirectory(dirName.getParentFile())
			        .call();
		} catch (IllegalStateException | GitAPIException e) {
			logger.error("在:{}下初始化git失败", dirName.getAbsolutePath());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}

	/**
	 * 获取本地目录下的所有分支
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param gitPath
	 * @return
	 */
	public String getGitBranches(String gitPath) {
		logger.debug("get branch:{}", gitPath);
		try {
			String wholeGitPath = gitPath;
			if (!gitPath.endsWith(GIT_SUFFIX)) {
				wholeGitPath = gitPath + GIT_SUFFIX;
			}
			Repository existingRepo = new FileRepositoryBuilder()
			        .setGitDir(new File(wholeGitPath)).build();
			logger.debug("get branch name :{}", existingRepo.getBranch());
			return existingRepo.getBranch();
		} catch (IOException e) {
			logger.error("获取分支信息失败:{}->error:{}", gitPath, e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}

	/**
	 * 检查当前文件夹是不是git仓库
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param localDir
	 * @return
	 */
	private boolean checkLocalDirIsGitRepo(String localDir) {
		return checkLocalDirIsGitRepo(new File(localDir));
	}

	/**
	 * 检查当前文件夹是不是git仓库
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param localDir
	 * @return
	 */
	private boolean checkLocalDirIsGitRepo(File localDir) {
		try (Git git = Git.open(localDir)) {
			return true;
		} catch (RepositoryNotFoundException rnf) {
			logger.error("检查本地目录是否git目录失败:{}", rnf.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("检查本地目录是否git目录失败:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 检查某个目录下面的分支是否存在
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param localRepoDir:当前的本地git仓库
	 * @param branchName:待查看的分支名称
	 * @param remoteBranch：是远程分支，还是本地分支，true代表远程分支，false代表本地分支
	 * @return
	 */
	private boolean checkBranchNameExists(String localRepoDir,
	        String branchName, boolean remoteBranch) {

		logger.info("在分支和tag列表中查找分支名称:{}", branchName);
		List<Ref> branchList = null;
		String indexName = null;
		if (remoteBranch) {
			branchList = getAllRemoteBranchesFromLocalDir(localRepoDir);
			indexName = GitDefine.REMOTE_BRANCH_SLICE_NAME;

			return checkBranchInBranchOrTagList(branchList, branchName,
			        indexName)
			        || checkBranchInBranchOrTagList(branchList, branchName,
			                GitDefine.REMOTE_TAG_SLICE_NAME);
		} else {
			branchList = getAllLocalBranchesFromLocalDir(localRepoDir);
			indexName = GitDefine.LOCAL_BRANCH_SLICE_NAME;

			return checkBranchInBranchOrTagList(branchList, branchName,
			        indexName);
		}
	}

	/**
	 * 检查分支或者tag名称是否在列表中
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param refList
	 * @param branchOrTagName
	 * @return
	 */
	private boolean checkBranchInBranchOrTagList(List<Ref> refList,
	        String branchOrTagName, String sliceName) {
		for (Ref ref : refList) {
			if ((ref.getName().indexOf(sliceName) != -1)) {
				String branchNameTemp = ref.getName()
				        .substring(sliceName.length(), ref.getName().length());
				if (branchNameTemp.equals(branchOrTagName)) {
					logger.debug("分支或者tag:{}存在", branchOrTagName);
					return true;
				}
			}
		}
		logger.debug("分支或者tag:{}不存在", branchOrTagName);
		return false;
	}

	/**
	 * 查看远程的所有分支
	 * 
	 * @see :
	 * @param :
	 * @return : List<Ref>
	 * @param remoteUrl
	 * @return
	 */
	public List<Ref> getAllGitBranchesFromRemoteUrl(String remoteUrl) {
		List<Ref> refs = new ArrayList<>();
		File destDir = new File(FileUtils.getTempDirectory().getAbsolutePath()
		        + File.separator + "test");
		if (destDir.exists()) {
			try {
				FileUtils.deleteDirectory(destDir);
			} catch (IOException e) {
				logger.error("io异常:{}", e.getMessage());
				throw new BusinessValidationException("io操作异常");
			}
		}

		CloneCommand cloneCommand = Git.cloneRepository().setURI(remoteUrl)
		        .setDirectory(destDir);

		if (null != usernamePasswordCredentialsProvider) {
			cloneCommand.setCredentialsProvider(
			        usernamePasswordCredentialsProvider);
		}

		try (Git gitClient = Git.open(destDir);) {
			cloneCommand.call();
			logger.debug("clone到:{}", destDir.getAbsolutePath());
			refs = gitClient.branchList().setListMode(ListMode.ALL).call();
			logger.debug("该仓库当前的分支版本为如下：");
			// Util.displayListInfo(refs);
			return refs;
		} catch (InvalidRemoteException e) {
			logger.error("操作异常:{}", e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		} catch (TransportException e) {
			logger.error("操作异常:{}", e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_AUTH_EEROR);
		} catch (GitAPIException | IOException e) {
			logger.error("操作异常:{}", e.getMessage());
			throw new BusinessValidationException(DEFAULT_GIT_OPERATION_EEROR);
		}
	}
	// public List<AnalyzeRequest> findDiffClasses(IcovRequest request)
	// throws GitAPIException, IOException {
	// String gitAppName = DiffService
	// .extractAppNameFrom(request.getRepoURL());
	// String gitDir = workDirFor(localRepoDir, request) + File.separator
	// + gitAppName;
	// DiffService.cloneBranch(request.getRepoURL(), gitDir, branchName);
	// String masterCommit = DiffService.getCommitId(gitDir);
	// List<DiffEntry> diffs = diffService.diffList(request.getRepoURL(),
	// gitDir, request.getNowCommit(), masterCommit);
	// List<AnalyzeRequest> diffClasses = new ArrayList<>();
	// String classPath;
	// for (DiffEntry diff : diffs) {
	// if (diff.getChangeType() == DiffEntry.ChangeType.DELETE) {
	// continue;
	// }
	// AnalyzeRequest analyzeRequest = new AnalyzeRequest();
	// if (diff.getChangeType() == DiffEntry.ChangeType.ADD) {
	// } else {
	// HashSet<String> changedMethods = MethodDiff
	// .methodDiffInClass(oldPath, newPath);
	// analyzeRequest.setMethodnames(changedMethods);
	// }
	// classPath = gitDir + File.separator
	// + diff.getNewPath()
	// .replace("src/main/java", "target/classes")
	// .replace(".java", ".class");
	// analyzeRequest.setClassesPath(classPath);
	// diffClasses.add(analyzeRequest);
	// }
	// return diffClasses;
	// }

	/**
	 * 获取diff
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param newVersion
	 * @param oldVersion
	 */
	public void gitDiff(String newVersion, String oldVersion) {
		File repoGitDir = new File("D:\\github\\jacoco-diff" + GIT_SUFFIX);
		if (!repoGitDir.exists()) {
			logger.info("Error! Not Exists :{} ", repoGitDir.getAbsolutePath());
		} else {
			Repository repo = null;
			Git git = null;
			try {
				git = Git.open(repoGitDir);
				repo = git.getRepository();
				ObjectReader reader = repo.newObjectReader();

				CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

				ObjectId oldVersionInfo = repo.resolve(oldVersion + "^{tree}");
				ObjectId newVersionInfo = repo.resolve(newVersion + "^{tree}");

				oldTreeIter.reset(reader, oldVersionInfo);

				CanonicalTreeParser newTreeIter = new CanonicalTreeParser();

				newTreeIter.reset(reader, newVersionInfo);

				List<DiffEntry> diffEntries = git.diff().setNewTree(newTreeIter)
				        .setOldTree(oldTreeIter).call();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				DiffFormatter diffFormatter = new DiffFormatter(outputStream);

				diffFormatter.setRepository(git.getRepository());

				for (DiffEntry diffEntry : diffEntries) {
					diffFormatter.format(diffEntry);
					String diffText = outputStream
					        .toString(GlobalDefination.CHAR_SET_DEFAULT);
					logger.debug(diffText);
				}
			} catch (Exception e) {
				logger.error("操作异常:{}", e.getMessage());
				throw new BusinessValidationException(
				        DEFAULT_GIT_OPERATION_EEROR);
			}
		}
	}

	public void compareTwoVersionsOfTheSameUrl(String remoteUrl) {
	}

	/**
	 * 比较两个不同url的最新版本
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrlOld
	 * @param remoteUrlNew
	 */
	public void compareTwoRemoteUrlOfBothLatestVersion(String remoteUrlOld,
	        String remoteUrlNew) {
		compareTwoRemoteUrlOfEachDifferentVersion(remoteUrlOld, "-1",
		        remoteUrlNew, "-1");
	}

	/**
	 * 比较不同远程url的不同版本
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrlOld
	 * @param versionOld
	 * @param remoteUrlNew
	 * @param versionNew
	 */
	public void compareTwoRemoteUrlOfEachDifferentVersion(String remoteUrlOld,
	        String versionOld, String remoteUrlNew, String versionNew) {
	}

	/**
	 * 获取本地目录的git信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param localPath
	 */
	public LocalGitInfo getLocalDirRepositoryInfo(String localPath) {
		logger.debug("查看本地仓库的git信息:{}", localPath);
		File localFileDir = new File(localPath);

		if (!localFileDir.exists()) {
			return null;
		}

		if (!localFileDir.isDirectory()) {
			return null;
		}

		try (Git git = Git.open(localFileDir);) {
			LocalGitInfo localGitInfo = new LocalGitInfo();
			List<RemoteConfig> remoteList = git.remoteList().call();

			if (!remoteList.isEmpty()) {
				List<URIish> urIishs = remoteList.get(0).getURIs();
				if (!urIishs.isEmpty()) {
					localGitInfo.setRemoteUrl(urIishs.get(0).toString());
				}
			}

			localGitInfo.setBranchName(git.getRepository().getBranch());
			logger.debug("本地git仓库信息：{}", localGitInfo);
			return localGitInfo;

		} catch (RepositoryNotFoundException rnfe) {
			logger.error("目录:{},不是一个git仓库", localFileDir);
			return null;
		} catch (IOException e) {
			logger.error("io操作异常,{}", e.getMessage());
			throw new BusinessValidationException("IO异常");
		} catch (GitAPIException e) {
			e.printStackTrace();
			logger.error("git操作异常:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 检查本地仓库是否和远程一致
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param localDir
	 * @param remoteUrl
	 * @param branchName
	 * @return
	 */
	public boolean checkLocalDirGitInfoTheSameAsCurrent(String localDir,
	        String remoteUrl, String branchName) {

		LocalGitInfo localGitInfo = getLocalDirRepositoryInfo(localDir);

		if (null == localGitInfo) {
			return false;
		}

		if (null != remoteUrl && null != branchName) {
			return remoteUrl.equals(localGitInfo.getRemoteUrl())
			        && branchName.equals(localGitInfo.getBranchName());
		} else {

			if (null != remoteUrl) {
				return remoteUrl.equals(localGitInfo.getRemoteUrl());
			}

			if (null != branchName) {
				return branchName.equals(localGitInfo.getBranchName());
			}
		}
		return false;
	}

	/**
	 * 列出远程仓库
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteUrl
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 */

	public void lsRemoteRepository(String remoteUrl)
	        throws InvalidRemoteException, TransportException, GitAPIException {
		logger.debug("Listing remote repository :{}", remoteUrl);
		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		if (null != usernamePasswordCredentialsProvider) {
			lsRemoteCommand.setCredentialsProvider(
			        usernamePasswordCredentialsProvider);
		}
		Collection<Ref> refs = lsRemoteCommand.setHeads(true).setTags(true)
		        .setRemote(remoteUrl).call();

		final Map<String, Ref> map = lsRemoteCommand.setHeads(true)
		        .setTags(true).setRemote(remoteUrl).callAsMap();

		logger.debug("As map");
		for (Map.Entry<String, Ref> entry : map.entrySet()) {
			logger.debug("Key: {}, Ref:{} ", entry.getKey(), entry.getValue());
		}

		refs = lsRemoteCommand.setRemote(remoteUrl).call();

		logger.debug("All refs");
		for (Ref ref : refs) {
			logger.debug("Ref: {}", ref);
		}

	}

	/**
	 * 获取本地git仓库的所有远程和本地分支
	 * 
	 * @see :
	 * @param :
	 * @return : List<Ref>
	 * @param localDir
	 * @return
	 */
	public List<Ref> getAllBranchesFromLocalDirContainsRemotesAndLocal(
	        String localDir) {
		logger.debug("获取当前仓库的所有远程和本地分支");
		try (Git git = Git.open(new File(localDir));) {
			ListBranchCommand listBranchCommand = git.branchList()
			        .setListMode(ListMode.ALL);

			ListTagCommand listTagCommand = git.tagList();

			List<Ref> tagsAndBranchList = new ArrayList<>();
			List<Ref> tagRefs = listTagCommand.call();
			List<Ref> branchRefs = listBranchCommand.call();

			tagsAndBranchList.addAll(tagRefs);
			tagsAndBranchList.addAll(branchRefs);
			logger.debug("获取到的本地和远程分支集合为:{}", tagsAndBranchList);
			// Util.displayListInfo(tagsAndBranchList);
			return tagsAndBranchList;
		} catch (IOException | GitAPIException e) {
			logger.error("io或者git操作异常:{}", e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * 获取本地仓库的所有本地分支
	 * 
	 * @see :
	 * @param :
	 * @return : List<Ref>
	 * @param localDir
	 * @return
	 */
	public List<Ref> getAllLocalBranchesFromLocalDir(String localDir) {
		logger.debug("获取git目录:{}下的所有本地分支 ", localDir);
		List<Ref> remoteAndLocalBranchesList = getAllBranchesFromLocalDirContainsRemotesAndLocal(
		        localDir);

		List<Ref> localBranchesList = new ArrayList<>();

		for (Ref ref : remoteAndLocalBranchesList) {
			int originIndex = ref.getName()
			        .indexOf(GitDefine.LOCAL_BRANCH_SLICE_NAME);
			if (originIndex != -1) {
				localBranchesList.add(ref);
			}
		}
		logger.debug("获取出来的本地分支为:{}", localBranchesList);
		// Util.displayListInfo(localBranchesList);
		return localBranchesList;
	}

	/**
	 * 获取本地仓库的远程分支
	 * 
	 * @see :
	 * @param :
	 * @return : List<Ref>
	 * @param localDir
	 * @return
	 */
	public List<Ref> getAllRemoteBranchesFromLocalDir(String localDir) {
		logger.debug("获取git目录:{}下的所有远程getAllRemoteBranchesFromLocalDir分支 ",
		        localDir);
		List<Ref> remoteAndLocalBranchesList = getAllBranchesFromLocalDirContainsRemotesAndLocal(
		        localDir);

		List<Ref> remoteBranchList = new ArrayList<>();

		for (Ref ref : remoteAndLocalBranchesList) {
			// 不是本地分支的，都是远程分支
			int originIndex = ref.getName()
			        .indexOf(GitDefine.LOCAL_BRANCH_SLICE_NAME);
			if (originIndex == -1) {
				remoteBranchList.add(ref);
			}
		}
		logger.debug("获取出来的远程分支为:{}", remoteBranchList);
		// Util.displayListInfo(remoteBranchList);
		return remoteBranchList;
	}

	public void getRemoteUrlGitInfo(String remoteUrl) {

	}

	/**
	 * 获取认证
	 * 
	 * @see :
	 * @param :
	 * @return : UsernamePasswordCredentialsProvider
	 * @return
	 */
	public UsernamePasswordCredentialsProvider getUsernamePasswordCredentialsProvider() {
		return usernamePasswordCredentialsProvider;
	}

	/**
	 * 初始化分支名称
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param branchName
	 * @return
	 */
	private static String initDefaultBranchName(String branchName) {
		return (StringUtil.isEmpty(branchName)) ? GitDefine.GIT_MASTER_NAME
		        : branchName;
	}

	/**
	 * 校验远程分支
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param localDir
	 * @param remoteUrl
	 * @param branchName
	 */
	private void validateRemoteUrl(String localDir, String remoteUrl,
	        String branchName) {
		/**
		 * 检查远程分支是否存在
		 */
		if (!checkBranchNameExists(localDir, branchName, true)) {
			logger.error("URL：{}上的远程分支：{}不存在!", remoteUrl, branchName);
			throw new BusinessValidationException("检出代码异常，分支不存在");
		}
	}
}
