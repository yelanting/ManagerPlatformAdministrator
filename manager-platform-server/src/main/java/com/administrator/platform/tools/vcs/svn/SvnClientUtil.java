/**
 * @author : 孙留平
 * @since : 2019年2月27日 下午3:44:07
 * @see:
 */
package com.administrator.platform.tools.vcs.svn;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.tools.vcs.common.CommonDefine;
import com.administrator.platform.util.define.FileSuffix;

/**
 * @author : Administrator
 * @since : 2019年2月27日 下午3:44:07
 * @see : svn操作工具类
 */
public class SvnClientUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(SvnClientUtil.class);

    /**
     * svn管理员
     */
    private SvnManager svnManager;

    /**
     * logs的显示最大记录
     */
    private static long maxShowLogCount = 2000L;

    /**
     * 构造函数
     * 
     * @see :
     * @param svnLoginInfo
     */
    public SvnClientUtil(SvnLoginInfo svnLoginInfo) {
        this.svnManager = new SvnManager(svnLoginInfo);
    }

    /**
     * 构造函数
     * 
     * @see :
     * @param remoteUrl
     *            :远程库地址
     * @param username
     *            : 用户名
     * @param password
     *            :密码
     */
    public SvnClientUtil(String remoteUrl, String username, String password) {
        this.svnManager = new SvnManager(remoteUrl, username, password);
    }

    public SvnClientUtil(String remoteUrl, CodeCoverage codeCoverage) {
        this(remoteUrl, codeCoverage.getUsername(), codeCoverage.getPassword());
    }

    /**
     * 构造函数
     * 
     * @see :
     * @param svnManager
     *            : svn管理员
     */
    public SvnClientUtil(SvnManager svnManager) {
        this.svnManager = svnManager;
    }

    /**
     * 创建文件夹
     * 
     * @see :
     * @param :
     * @return : SVNCommitInfo
     * @param url
     * @param commitMessage
     * @return
     */
    public SVNCommitInfo makeDirectory(SVNURL url, String commitMessage) {
        try {
            return this.svnManager.getsVNClientManager().getCommitClient()
                    .doMkDir(new SVNURL[] { url }, commitMessage);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 导入文件夹
     * 
     * @Param localPath:本地路径
     * @Param dstURL:目标地址
     */
    public SVNCommitInfo importDirectory(File localPath, SVNURL dstUrl,
            String commitMessage, boolean isRecursive) {
        try {
            return this.svnManager.getsVNClientManager().getCommitClient()
                    .doImport(localPath, dstUrl, commitMessage, null, true,
                            true, SVNDepth.fromRecurse(isRecursive));
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 添加入口
     * 
     * @see :
     * @param :
     * @return : void
     * @param wcPath
     */
    public void addEntry(File wcPath) {
        try {
            this.svnManager.getsVNClientManager().getWCClient().doAdd(
                    new File[] { wcPath }, true, false, false,
                    SVNDepth.INFINITY, false, false, true);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
    }

    /**
     * 显示状态
     * 
     * @see :
     * @param :
     * @return : SVNStatus
     * @param wcPath
     * @param remote
     * @return
     */
    public SVNStatus showStatus(File wcPath, boolean remote) {
        SVNStatus status = null;
        try {
            status = this.svnManager.getsVNClientManager().getStatusClient()
                    .doStatus(wcPath, remote);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return status;
    }

    /**
     * 提交代码
     * 
     * @see :
     * @param :
     * @return : SVNCommitInfo
     * @param wcPath
     * @param keepLocks
     * @param commitMessage
     * @return
     */
    public SVNCommitInfo commit(File wcPath, boolean keepLocks,
            String commitMessage) {
        try {
            return this.svnManager.getsVNClientManager().getCommitClient()
                    .doCommit(new File[] { wcPath }, keepLocks, commitMessage,
                            null, null, false, false, SVNDepth.INFINITY);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 更新代码
     * 
     * @see :
     * @param :
     * @return : long
     * @param wcPath
     * @param updateToRevision
     * @param depth
     * @return
     */
    public long update(File wcPath, SVNRevision updateToRevision,
            SVNDepth depth) {

        cleanUpLocalPath(wcPath);
        SVNUpdateClient updateClient = this.svnManager.getsVNClientManager()
                .getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            if (null == updateToRevision) {
                updateToRevision = SVNRevision.HEAD;
            }
            return updateClient.doUpdate(wcPath, updateToRevision, depth, false,
                    false);
        } catch (SVNAuthenticationException e) {
            logger.error("更新失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return 0;
    }

    /**
     * 更新代码到最新版本
     * 
     * @see :
     * @param :
     * @return : long
     * @param wcPath
     * @param updateToRevision
     * @param depth
     * @return
     */
    public long update(File wcPath) {
        /**
         * 先clean up
         */
        cleanUpLocalPath(wcPath);
        SVNUpdateClient updateClient = this.svnManager.getsVNClientManager()
                .getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            long updateToVersion = updateClient.doUpdate(wcPath,
                    SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
            logger.debug("更新本地目录:{},到版本:{}", wcPath.getAbsolutePath(),
                    updateToVersion);
            return updateToVersion;
        } catch (SVNAuthenticationException e) {
            logger.error("更新失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
            return -1;
        }
    }

    /**
     * 更新代码到最新版本
     * 
     * @see :
     * @param :
     * @return : long
     * @param wcPath
     * @param updateToRevision
     * @param depth
     * @return
     */
    public long update(String wcPath) {
        File localPath = new File(wcPath);
        return update(localPath);
    }

    /**
     * @see :
     * @param :
     * @return : void
     * @param path
     */
    public void cleanUpLocalPath(String path) {
        File localDirPath = new File(path);
        cleanUpLocalPath(localDirPath);
    }

    /**
     * clean up目录
     * 
     * @see :
     * @param :
     * @return : void
     * @param localFileDir
     */
    public void cleanUpLocalPath(File localFileDir) {
        if (!localFileDir.exists()) {
            return;
        }

        if (!isWorkingCopy(localFileDir)) {
            return;
        }

        try {
            logger.info("更新之前，对文件夹做cleanup操作:{}",
                    localFileDir.getAbsolutePath());
            this.svnManager.getsVNClientManager().getWCClient()
                    .doCleanup(localFileDir);

            logger.info("更新之前，对文件夹:{}做cleanup操作成功",
                    localFileDir.getAbsolutePath());
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("对:{}执行cleanup失败,失败原因：{}", localFileDir,
                    e.getMessage());
        }
    }

    /**
     * 从SVN导出项目到本地
     * 
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public long checkout(SVNURL url, SVNRevision revision, File destPath,
            SVNDepth depth) {
        logger.debug("开始检出代码:{},版本:{},到:{}", url, revision, destPath);
        SVNUpdateClient updateClient = this.svnManager.getsVNClientManager()
                .getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            return updateClient.doCheckout(url, destPath, revision, revision,
                    depth, false);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return 0;
    }

    /**
     * 从SVN导出项目到本地
     * 
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public long checkoutDefault(String destPath) {
        logger.debug("开始检出代码到:{}", destPath);
        File destFile = new File(destPath);
        // 如果已经存在
        if (destFile.exists()) {
            // 首先检查是否是svn文件夹，如果不是则删除
            if (!isWorkingCopy(destFile)) {
                try {
                    FileUtils.forceDelete(destFile);
                } catch (IOException e) {
                    logger.debug("删除文件夹失败:{}", e.getMessage());
                }
            } else {
                // 如果是svn文件夹，再判断svn信息
                if (checkLocalDirIsTheSameWithCurrentAuth(destFile)) {
                    logger.debug("当前文件夹已存在且svn信息和远程库一模一样，不需要检出");
                    return getLatestVersionOfRepository(null);
                } else {
                    // 如果url一样，直接更新
                    if (checkLocalDirIsTheSameUrlWithCurrentAuth(destFile)) {
                        logger.debug("当前文件夹已存在，且url相同，直接更新即可");
                        return update(destFile);
                    } else {
                        try {
                            FileUtils.forceDelete(destFile);
                        } catch (IOException e) {
                            logger.debug("删除文件夹失败:{}", e.getMessage());
                        }
                    }
                }
            }
        }

        SVNUpdateClient updateClient = this.svnManager.getsVNClientManager()
                .getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            SVNRepository repository = this.svnManager.getRepository();
            SVNURL svnUrl = repository.getLocation();
            return updateClient.doCheckout(svnUrl, destFile, SVNRevision.HEAD,
                    SVNRevision.HEAD, SVNDepth.INFINITY, false);
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
            return 0;
        }
    }

    /**
     * 从SVN导出项目到本地
     * 
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public long checkoutDefault(String remoteUrl, String destPath,
            String checkOutVersion) {
        logger.debug("开始在:{}检出代码remoteUrl:{}到版本:{}", destPath, remoteUrl,
                checkOutVersion);
        File destFile = new File(destPath);
        // 如果已经存在
        if (destFile.exists()) {
            // 首先检查是否是svn文件夹，如果不是则删除
            if (!isWorkingCopy(destFile)) {
                try {
                    FileUtils.forceDelete(destFile);
                } catch (IOException e) {
                    logger.debug("删除文件夹失败:{}", e.getMessage());
                }
            } else {
                // 如果是svn文件夹，再判断svn信息
                if (checkLocalDirIsTheSameWithCurrentAuth(destFile)) {
                    logger.debug("当前文件夹已存在且svn信息和远程库一模一样，不需要检出");
                    return getLatestVersionOfRepository(null);
                } else {
                    // 如果url一样，直接更新
                    if (checkLocalDirIsTheSameUrlWithCurrentAuth(destFile)) {
                        logger.debug("当前文件夹已存在，且url相同，直接更新即可");
                        return update(destFile,
                                SVNRevision.parse(checkOutVersion),
                                SVNDepth.INFINITY);
                    } else {
                        try {
                            FileUtils.forceDelete(destFile);
                        } catch (IOException e) {
                            logger.debug("删除文件夹失败:{}", e.getMessage());
                        }
                    }
                }
            }
        }

        SVNUpdateClient updateClient = this.svnManager.getsVNClientManager()
                .getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            SVNRepository repository = this.svnManager.getRepository();
            SVNURL svnUrl = repository.getLocation();
            return updateClient.doCheckout(svnUrl, destFile, SVNRevision.HEAD,
                    SVNRevision.parse(checkOutVersion), SVNDepth.INFINITY,
                    false);
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
            throw new BusinessValidationException(
                    "svn操作异常，请检查url是否正确或者账号密码权限是否正确");
        }
    }

    /**
     * 确定path是否是一个工作空间
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param path
     * @return
     */
    public static boolean isWorkingCopy(File path) {
        if (!path.exists()) {
            logger.warn("{} not exist!", path);
            return false;
        }
        try {
            if (null == SVNWCUtil.getWorkingCopyRoot(path, false)) {
                return false;
            }
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return true;
    }

    /**
     * 确定path是否是一个工作空间
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param path
     * @return
     */
    public static boolean isWorkingCopy(String localPath) {
        File path = new File(localPath);
        return isWorkingCopy(path);
    }

    /**
     * 确定一个URL在SVN上是否存在
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param url
     * @param username
     * @param password
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isUrlExists(SVNURL url, String username,
            String password) {
        try {
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            ISVNAuthenticationManager authManager = SVNWCUtil
                    .createDefaultAuthenticationManager(username,
                            password.toCharArray());
            svnRepository.setAuthenticationManager(authManager);
            // 遍历SVN,获取结点。
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1);
            return nodeKind == SVNNodeKind.NONE ? false : true;
        } catch (SVNException e) {
            logger.error("{},{}", e.getErrorMessage(), e);
        }
        return false;
    }

    /**
     * 确认一个URL地址和当前认证过的仓库是不是同一个地址
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param remoteUrl
     * @return
     */
    private boolean checkRemoteUrlTheSameWithCurrentUrl(String remoteUrl) {
        String currentUrl = this.svnManager.getRepository().getLocation()
                .toString();

        // 判断是不是同一个url
        boolean isSameUrl = remoteUrl.equalsIgnoreCase(currentUrl);

        return isSameUrl;
    }

    /**
     * 根据当前已经认证的信息创建仓库
     * 
     * @see :
     * @param :
     * @return : SVNRepository
     * @param remoteUrl
     * @return
     */
    private SVNRepository createRepositoryWithCurrentAuthenticationManager(
            String remoteUrl) {
        SVNRepository svnRepository = null;
        if (StringUtil.isEmpty(remoteUrl)) {
            return this.svnManager.getRepository();
        }

        boolean isSameUrl = checkRemoteUrlTheSameWithCurrentUrl(remoteUrl);

        // 如果不是同一个URL
        try {
            // 如果是另一个url
            if (isSameUrl) {
                return this.svnManager.getRepository();
            }

            svnRepository = SVNRepositoryFactory
                    .create(SVNURL.parseURIEncoded(remoteUrl));

            svnRepository.setAuthenticationManager(
                    this.svnManager.getRepository().getAuthenticationManager());
            return svnRepository;
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("svn操作失败,失败信息{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取远程仓库的最新版本
     * 
     * @see :
     * @param :
     * @return : long
     * @param remoteUrl
     * @return
     */
    public long getLatestVersionOfRepository(String remoteUrl) {
        long lastVersion = -1;
        SVNRepository svnRepository = createRepositoryWithCurrentAuthenticationManager(
                remoteUrl);

        if (null == svnRepository) {
            return -1;
        }

        try {
            lastVersion = svnRepository.getLatestRevision();
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
        }

        String toRecordUrl = null == remoteUrl
                ? "当前已认证的URL的" + svnRepository.getLocation().toString()
                : remoteUrl;
        logger.debug("获取URL:{}最新版本号为:{}", toRecordUrl, lastVersion);
        return lastVersion;
    }

    /**
     * 获取默认的配置
     * 
     * @see :
     * @param :
     * @return : DefaultSVNOptions
     * @return
     */
    private DefaultSVNOptions getDefaultSvnOptions() {
        boolean readonly = true;
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(readonly);
        options.setDiffCommand("-x -w");
        return options;
    }

    /**
     * 获取一段时间内，所有的commit记录
     * 
     * @param st
     *            开始时间
     * @param et
     *            结束时间
     * @return
     * @throws SVNException
     */
    public SVNLogEntry[] getLogByTime(Date st, Date et) throws SVNException {

        SVNRepository repos = this.svnManager.getRepository();
        long startRevision = repos.getDatedRevision(st);
        long endRevision = repos.getDatedRevision(et);

        @SuppressWarnings("unchecked")
        Collection<SVNLogEntry> logEntries = repos.log(new String[] { "" },
                null, startRevision, endRevision, true, true);
        SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
        return svnLogEntries;
    }

    /**
     * 创建临时文件
     * 
     * @see :
     * @param :
     * @return : String
     * @param oldVersion
     * @param newVersion
     * @return
     */
    private String createTempFilePath(long oldVersion, long newVersion) {
        String tempDir = System.getProperty("java.io.tmpdir");
        Random random = new Random();
        return tempDir + "/svn_diff_file_" + oldVersion + "_" + newVersion + "_"
                + random.nextInt(10000) + ".txt";

    }

    /**
     * 比对同一个URL的两个不同版本
     * 
     * @see :
     * @param :
     * @return : String
     * @param remoteUrl
     * @param oldVersion
     * @param newVersion
     * @return
     */
    public String doDiffCompareBetweenTwoVersionsWithSameRemoteUrl(
            String remoteUrl, long oldVersion, long newVersion) {
        // logger.info("开始比较url:{},在版本:{},到版本:{}的改动信息.", remoteUrl, oldVersion,
        // newVersion);
        SVNRepository svnRepository = createRepositoryWithCurrentAuthenticationManager(
                remoteUrl);

        DefaultSVNOptions options = getDefaultSvnOptions();

        if (null == svnRepository) {
            throw new BusinessValidationException("创建版本库" + remoteUrl + "失败:");
        }
        SVNDiffClient diffClient = new SVNDiffClient(
                svnRepository.getAuthenticationManager(), options);
        diffClient.setGitDiffFormat(true);
        File tempLogFile = null;
        String svnDiffFile = null;
        do {
            svnDiffFile = createTempFilePath(oldVersion, newVersion);
            // logger.debug("临时文件路径为：{}", svnDiffFile);
            tempLogFile = new File(svnDiffFile);
        } while (tempLogFile != null && tempLogFile.exists());

        try (OutputStream outputStream = new FileOutputStream(svnDiffFile);) {
            tempLogFile.createNewFile();
            diffClient.doDiff(SVNURL.parseURIEncoded(remoteUrl),
                    SVNRevision.create(oldVersion),
                    SVNURL.parseURIEncoded(remoteUrl),
                    SVNRevision.create(newVersion),
                    org.tmatesoft.svn.core.SVNDepth.UNKNOWN, true,
                    outputStream);

            return svnDiffFile;
        } catch (FileNotFoundException e) {
            logger.error("创建文件失败:{},失败原因:{}", svnDiffFile, e.getMessage());
        } catch (IOException e) {
            logger.error("文件操作异常:{}", e.getMessage());
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("svn操作异常:{}", e.getMessage());
        }

        return svnDiffFile;
    }

    /**
     * 从SVN服务器获取文件
     * 
     * @param filePath
     *            相对于仓库根目录的路径
     * @param outputStream
     *            要输出的目标流，可以是文件流 FileOutputStream
     * @param version
     *            要checkout的版本号
     * @return 返回checkout文件的版本号
     * @throws Exception
     *             可以自定义Exception
     */
    public long getFileFromSvn(String filePath, OutputStream outputStream,
            long version) {
        SVNNodeKind node = null;
        SVNRepository repository = null;
        try {
            repository = this.svnManager.getRepository();
            node = repository.checkPath(filePath, version);
        } catch (SVNException e) {
            logger.error("SVN检测不到该文件:{},错误信息:{}", filePath, e);
        }
        if (node != SVNNodeKind.FILE) {
            logger.error("路径:{}不是文件", node);
        }
        SVNProperties properties = new SVNProperties();
        try {
            repository.getFile(filePath, version, properties, outputStream);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            throw new BusinessValidationException(
                    "获取SVN服务器中的" + filePath + "文件失败" + e.getMessage());
        }
        return Long.parseLong(properties.getStringValue("svn:entry:revision"));
    }

    /**
     * 判断文件是否存在
     * 
     * @param entry
     *            要判断的节点.参加{@link SVNDirEntry}
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private boolean containsSpecialFile(SVNDirEntry entry) throws Exception {
        if (entry.getKind() == SVNNodeKind.FILE) {
            return true;
        } else if (entry.getKind() == SVNNodeKind.DIR) {
            Collection<SVNDirEntry> entries;
            String path = entry.getURL().getPath();
            try {
                entries = this.svnManager.getRepository().getDir(path, -1, null,
                        (Collection) null);
            } catch (SVNAuthenticationException e) {
                logger.error("操作失败，错误信息:{}", e.getMessage());
                throw new BusinessValidationException(
                        "仓库授权失败，请检查用户名密码是否对该仓库有权限");
            } catch (SVNException e) {
                throw new Exception("获得:" + path + "下级目录失败", e);
            }
            for (SVNDirEntry unit : entries) {
                if (containsSpecialFile(unit)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 此函数递归的获取版本库中某一目录下的所有条目。
     * 
     * @see :
     * @param :
     * @return : void
     * @param repository
     * @param path
     * @throws SVNException
     */
    public static void listEntries(SVNRepository repository, String path)
            throws SVNException {
        // 获取版本库的path目录下的所有条目。参数－1表示是最新版本。
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            /*
             * 检查此条目是否为目录，如果为目录递归执行
             */
            if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(repository, ("".equals(path)) ? entry.getName()
                        : path + "/" + entry.getName());
            }
        }
    }

    public void displayFiles() {
        try {
            listFileEntriesWithFileSuffix(this.svnManager.getRepository(), "",
                    FileSuffix.JAVA_FILE);
        } catch (SVNException e) {
            logger.error("svn操作失败，失败原因:{}", e.getMessage());
        }
    }

    public void displayDocFiles() {

        FileSuffix[] docFileSuffixes = new FileSuffix[] { FileSuffix.DOC_FILE,
                FileSuffix.DOCX_FILE, FileSuffix.PPT_FILE,
                FileSuffix.PPTX_FILE };

        try {
            listFileEntriesWithFileSuffix(this.svnManager.getRepository(),
                    "08.规范总结类", docFileSuffixes);
        } catch (SVNException e) {
            logger.error("获取文档失败，失败原因:{}", e.getMessage());
            throw new BusinessValidationException("从svn获取文档列表失败");

        }
    }

    /**
     * 此函数递归的获取版本库中某一目录下的所有条目。
     * 
     * @see :
     * @param :
     * @return : void
     * @param repository
     * @param path
     * @throws SVNException
     */
    public void listFileEntriesWithFileSuffix(SVNRepository repository,
            String path, FileSuffix[] fileSuffixes) throws SVNException {
        // 获取版本库的path目录下的所有条目。参数－1表示是最新版本。
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            /*
             * 检查此条目是否为目录，如果为目录递归执行
             */
            if (entry.getKind() == SVNNodeKind.DIR) {
                listFileEntriesWithFileSuffix(repository,
                        ("".equals(path)) ? entry.getName()
                                : path + "/" + entry.getName(),
                        fileSuffixes);
            } else if (entry.getKind() == SVNNodeKind.FILE) {
                for (int i = 0; i < fileSuffixes.length; i++) {
                    if (entry.getName()
                            .endsWith(fileSuffixes[i].getFileType())) {
                        try {
                            logger.debug("url:{}",
                                    URLDecoder.decode(entry.getURL().toString(),
                                            GlobalDefination.CHAR_SET_DEFAULT));
                        } catch (UnsupportedEncodingException e) {
                            logger.error("异常了：{}", e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 此函数递归的获取版本库中某一目录下的所有条目。
     * 
     * @see :
     * @param :
     * @return : void
     * @param repository
     * @param path
     * @throws SVNException
     */
    public void listFileEntriesWithFileSuffix(SVNRepository repository,
            String path, FileSuffix fileSuffix) throws SVNException {
        // 获取版本库的path目录下的所有条目。参数－1表示是最新版本。
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            /*
             * 检查此条目是否为目录，如果为目录递归执行
             */
            if (entry.getKind() == SVNNodeKind.DIR) {
                listFileEntriesWithFileSuffix(repository,
                        ("".equals(path)) ? entry.getName()
                                : path + "/" + entry.getName(),
                        fileSuffix);
            } else if (entry.getKind() == SVNNodeKind.FILE) {

                if (entry.getName().endsWith(fileSuffix.getFileType())) {
                    String thisFileInfo = "/"
                            + ("".equals(path) ? "" : path + "/")
                            + entry.getName();
                    logger.debug("path:{}", entry.getPath());
                    logger.debug("url:{}", entry.getURL());
                    logger.debug(thisFileInfo);
                }
            }
        }
    }

    /**
     * 根据文件后缀，展示文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param fileSuffix
     */
    public void displayFilesWithSuffix(FileSuffix fileSuffix) {
        /*
         * 上面的代码基本上是固定的操作。
         * 下面的部分根据任务不同，执行不同的操作。
         */
        SVNRepository repository = null;
        try {
            repository = this.svnManager.getRepository();
            // 打印版本库的根
            logger.debug("Repository Root: {}",
                    repository.getRepositoryRoot(true));
            // 打印出版本库的UUID
            logger.debug("Repository UUID: {}",
                    repository.getRepositoryUUID(true));
            // 打印版本库的目录树结构
            listEntries(repository, "");
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("打印版本树时发生错误: {}", svne.getMessage());
            System.exit(1);
        }
    }

    /**
     * 显示当前库的目录树
     * 
     * @see :
     * @param :
     * @return : void
     */
    public void displayRepositoryTree() {
        /*
         * 上面的代码基本上是固定的操作。
         * 下面的部分根据任务不同，执行不同的操作。
         */
        SVNRepository repository = null;
        try {
            repository = this.svnManager.getRepository();
            // 打印版本库的根
            logger.debug("Repository Root: {}",
                    repository.getRepositoryRoot(true));
            // 打印出版本库的UUID
            logger.debug("Repository UUID: {}",
                    repository.getRepositoryUUID(true));
            logger.debug("");
            // 打印版本库的目录树结构
            listEntries(repository, "");
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("打印版本树时发生错误: {}", svne.getMessage());
            System.exit(1);
        }
        /*
         * 获得版本库的最新版本树
         */
        long latestRevision = -1;
        try {
            latestRevision = repository.getLatestRevision();
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("获取最新版本号时出错: {}", svne.getMessage());
            System.exit(1);
        }
        logger.debug("---------------------------------------------");
        logger.debug("版本库的最新版本是: {}", latestRevision);
        System.exit(0);
    }

    /**
     * 显示文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param filePath
     */
    public void displayFile(String filePath) {
        // 此变量用来存放要查看的文件的属性名/属性值列表。
        SVNProperties fileProperties = new SVNProperties();
        // 此输出流用来存放要查看的文件的内容。
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        SVNRepository repository = this.svnManager.getRepository();
        try {
            // 获得版本库中文件的类型状态（是否存在、是目录还是文件），参数-1表示是版本库中的最新版本。
            SVNNodeKind nodeKind = repository.checkPath(filePath, -1);

            if (nodeKind == SVNNodeKind.NONE) {
                logger.error("要查看的文件在 不存在.");
                System.exit(1);
            } else if (nodeKind == SVNNodeKind.DIR) {
                logger.error("要查看对应版本的条目是一个目录.");
                System.exit(1);
            }
            // 获取要查看文件的内容和属性，结果保存在baos和fileProperties变量中。
            repository.getFile(filePath, -1, fileProperties, baos);

        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("在获取文件内容和属性时发生错误: {}", svne.getMessage());
            System.exit(1);
        }

        // 获取文件的mime-type
        String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
        // 判断此文件是否是文本文件
        boolean isTextType = SVNProperty.isTextMimeType(mimeType);
        /*
         * 显示文件的所有属性
         */
        Iterator iterator = fileProperties.nameSet().iterator();
        while (iterator.hasNext()) {
            String propertyName = (String) iterator.next();
            String propertyValue = fileProperties.getStringValue(propertyName);
            logger.debug("文件的属性: {}={}", propertyName, propertyValue);
        }
        /*
         * 如果文件是文本类型，则把文件的内容显示到控制台。
         */
        if (isTextType) {
            logger.debug("File contents:");
            try {
                baos.writeTo(System.out);
            } catch (IOException ioe) {
                logger.error("io异常,{}", ioe.getMessage());
            }
        } else {
            logger.debug("因为文件不是文本文件，无法显示！");
        }
        logger.debug("");
        /*
         * 获得版本库的最新版本号。
         */
        long latestRevision = -1;
        try {
            latestRevision = repository.getLatestRevision();

            List<SVNLogEntry> entries = new ArrayList<SVNLogEntry>();
            try {
                // 为过滤的文件路径前缀，为空表示不进行过滤
                // -1代表最新的版本号，初始版本号为0
                repository.log(new String[] { "" }, entries, -1, -1, true,
                        true);
            } catch (SVNAuthenticationException e) {
                logger.error("操作失败，错误信息:{}", e.getMessage());
                throw new BusinessValidationException(
                        "仓库授权失败，请检查用户名密码是否对该仓库有权限");
            } catch (SVNException e) {
                logger.error("svn操作异常，信息:{}", e.getMessage());
            }
            logger.debug("当前log信息数量:{}", entries.size());
            String message = entries.get(0).getMessage();
            logger.debug("提交的message信息：{}", message);

        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("获取最新版本号时出错: {}", svne.getMessage());
            System.exit(1);
        }
        logger.debug("");
        logger.debug("---------------------------------------------");
        logger.debug("版本库的最新版本号: {}", latestRevision);
        System.exit(0);

    }

    /**
     * 通过比较日志，统计以+号开头的非空行
     * 
     * @param content
     * @return
     */
    public int countAddLine(String content) {
        int sum = 0;
        if (content != null) {
            content = '\n' + content + '\n';
            char[] chars = content.toCharArray();
            int len = chars.length;
            // 判断当前行是否以+号开头
            boolean startPlus = false;
            // 判断当前行，是否为空行（忽略第一个字符为加号）
            boolean notSpace = false;

            for (int i = 0; i < len; i++) {
                char ch = chars[i];
                if (ch == '\n') {
                    // 当当前行是+号开头，同时其它字符都不为空，则行数+1
                    if (startPlus && notSpace) {
                        sum++;
                        notSpace = false;
                    }
                    // 为下一行做准备，判断下一行是否以+头
                    if (i < len - 1 && chars[i + 1] == '+') {
                        startPlus = true;
                        // 跳过下一个字符判断，因为已经判断了
                        i++;
                    } else {
                        startPlus = false;
                    }
                } else if (startPlus && ch > ' ') {
                    // 如果当前行以+开头才进行非空行判断
                    notSpace = true;
                }
            }
        }

        return sum;
    }

    // /**
    // * 统计一段时间内代码增加量
    // *
    // * @param st
    // * @param et
    // * @return
    // * @throws Exception
    // */
    // public int staticticsCodeAddByTime(Date st, Date et) throws Exception {
    // int sum = 0;
    // SVNLogEntry[] logs = getLogByTime(st, et);
    // if (logs.length > 0) {
    // long lastVersion = logs[0].getRevision() - 1;
    // for (SVNLogEntry log : logs) {
    // File logFile = getChangeLog(lastVersion, log.getRevision());
    // int addSize = staticticsCodeAdd(logFile);
    // sum += addSize;
    // lastVersion = log.getRevision();
    // }
    // }
    // return sum;
    // }

    /**
     * 获取某一版本有变动的文件路径
     * 
     * @param version
     * @return
     * @throws SVNException
     */
    public List<SVNLogEntryPath> getChangeFileList(long version)
            throws SVNException {
        List<SVNLogEntryPath> result = new ArrayList<>();

        ISVNAuthenticationManager authManager = this.svnManager.getRepository()
                .getAuthenticationManager();

        DefaultSVNOptions options = getDefaultSvnOptions();
        SVNLogClient logClient = new SVNLogClient(authManager, options);

        SVNURL url = this.svnManager.getRepository().getLocation();

        String[] paths = { "." };
        SVNRevision pegRevision = SVNRevision.create(version);
        SVNRevision startRevision = SVNRevision.create(version);
        SVNRevision endRevision = SVNRevision.create(version);
        boolean stopOnCopy = false;
        boolean discoverChangedPaths = true;
        long limit = 9999L;

        ISVNLogEntryHandler handler = new ISVNLogEntryHandler() {

            /**
             * This method will process when doLog() is done
             */
            @Override
            public void handleLogEntry(SVNLogEntry logEntry)
                    throws SVNException {
                // logger.debug("Author: {}", logEntry.getAuthor());
                // logger.debug("Date: {}", logEntry.getDate());
                // logger.debug("Message: {}", logEntry.getMessage());
                // logger.debug("Revision: {}", logEntry.getRevision());
                Map<String, SVNLogEntryPath> maps = logEntry.getChangedPaths();
                Set<Map.Entry<String, SVNLogEntryPath>> entries = maps
                        .entrySet();
                for (Map.Entry<String, SVNLogEntryPath> entry : entries) {
                    SVNLogEntryPath entryPath = entry.getValue();
                    result.add(entryPath);
                    // logger.debug("类型：{},路径：{}", entryPath.getType(),
                    // entryPath.getPath());
                }
            }
        };
        // Do log
        try {
            logClient.doLog(url, paths, pegRevision, startRevision, endRevision,
                    stopOnCopy, discoverChangedPaths, limit, handler);
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.debug("Error in doLog() ");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取指定文件内容
     * 
     * @param url
     *            svn地址
     * @return
     */
    public String checkoutFileToString(String url) {
        SVNRepository repos = createRepositoryWithCurrentAuthenticationManager(
                url);
        try {
            SVNDirEntry entry = repos.getDir("", -1, false, null);
            int size = (int) entry.getSize();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
                    size);
            SVNProperties properties = new SVNProperties();
            repos.getFile("", -1, properties, outputStream);
            return new String(outputStream.toByteArray(),
                    Charset.forName(GlobalDefination.CHAR_SET_DEFAULT));
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查路径是否存在
     * 
     * @param url
     * @return 1：存在 0：不存在 -1：出错
     */
    public int checkPath(String url) {

        SVNRepository repos = createRepositoryWithCurrentAuthenticationManager(
                url);
        SVNNodeKind nodeKind;
        try {
            nodeKind = repos.checkPath("", -1);
            boolean result = nodeKind == SVNNodeKind.NONE ? false : true;
            if (result) {
                return 1;
            }

        } catch (SVNException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    //
    // /**
    // * 从变更信息中解析变更文件详情
    // *
    // * @see :
    // * @param :
    // * @return : List<ChangeFile>
    // * @param svnDiffStatusList
    // * @param oldVersion
    // * @param newVersion
    // * @return
    // */
    // public List<ChangeFile> getDiffFileDetailsAccordingToDiffStatus(
    // List<SVNDiffStatus> svnDiffStatusList, long oldVersion,
    // long newVersion) {
    //
    // List<ChangeFile> changedFileList = new ArrayList<>();
    //
    // for (SVNDiffStatus svnDiffStatus : svnDiffStatusList) {
    // ChangeFile changeFile = getDiffFileDetail(svnDiffStatus, oldVersion,
    // newVersion);
    // if (null != changeFile) {
    // changedFileList.add(changeFile);
    // }
    // }
    //
    // // logger.debug("得到所有的文件变更信息为:{}", changedFileList);
    // // Util.displayListInfo(changedFileList);
    // return changedFileList;
    // }

    public void getFileInfo(SVNDiffStatus svnDiffStatus, long oldVersion,
            long newVersion) {
        SVNRepository repository = this.svnManager.getRepository();
        // 进行path的处理
        String path = svnDiffStatus.getPath();
        String url = svnDiffStatus.getURL().toString();
        BufferedOutputStream outputStream = null;
        SVNProperties svnProperties = new SVNProperties();
        try {
            // 获得版本库中文件的类型状态（是否存在、是目录还是文件），参数-1表示是版本库中的最新版本。
            SVNNodeKind nodeKind = repository.checkPath(path, newVersion);

            if (nodeKind == SVNNodeKind.NONE) {
                throw new BusinessValidationException(
                        "要查看的文件在 '" + url + "'中不存在");
            } else if (nodeKind == SVNNodeKind.DIR) {
                throw new BusinessValidationException(
                        "要查看对应版本的条目在 '" + url + "'中是一个目录.");
            }

            outputStream = new BufferedOutputStream(
                    new FileOutputStream(new File("D:/check.txt")));
            // 获取要查看文件的内容和属性，结果保存在baos和fileProperties变量中。
            repository.getFile(path, -1, svnProperties, outputStream);
            logger.debug("=~~~svnProperties：{}", svnProperties.toString());

        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            throw new BusinessValidationException(svne.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // /**
    // * 从差异信息中解析差异信息详情
    // *
    // * @see :
    // * @param :
    // * @return : ChangeFile
    // * @param svnDiffStatus
    // * @param oldVersion
    // * @param newVersion
    // * @return
    // */
    // public ChangeFile getDiffFileDetail(SVNDiffStatus svnDiffStatus,
    // long oldVersion, long newVersion) {
    // SVNURL path = svnDiffStatus.getURL();
    // // logger.debug("查看单个文件的差异信息:====={}", path);
    // String svnDiffTempFile = doDiffCompareBetweenTwoVersionsWithSameRemoteUrl(
    // path.toString(), oldVersion, newVersion);
    //
    // ChangeFile changeFile = new ChangeFile();
    // changeFile = parseChangeFile(svnDiffTempFile, changeFile);
    // changeFile.setRelativePath(svnDiffStatus.getPath());
    // changeFile.setChangeType(svnDiffStatus.getModificationType());
    // changeFile.setFilePath(svnDiffStatus.getURL().toString());
    // if (StringUtil.isEmpty(changeFile.getPackageName())) {
    // changeFile.setPackageName(
    // parsePackageNameFromPath(svnDiffStatus.getPath()));
    // }
    //
    // return changeFile;
    // }

    /**
     * 从变更标记中，解析变更信息
     * 
     * @see :
     * @param changedMark:
     *            需要以@@开始，以及@@结束的字符串
     * @return : FileChangedInfo
     * @return
     */
    private static FileChangedInfo parseFileChangedInfoFromChangedMark(
            String changedMark) {
        FileChangedInfo fileChangedInfo = null;
        // 如果不是以@@开始或者以@@结束，则返回
        if (!changedMark.startsWith(SvnDiffDefine.DOUBLE_AITE_CHARS)
                || !changedMark.endsWith(SvnDiffDefine.DOUBLE_AITE_CHARS)) {
            return null;
        }

        String middleInfo = changedMark
                .replace(SvnDiffDefine.DOUBLE_AITE_CHARS, "").trim();

        String[] middleInfoList = middleInfo.split(" ");

        if (middleInfoList.length < 2) {
            return null;
        }

        String deletedInfo = middleInfoList[0];
        String addedInfo = middleInfoList[1];

        String delimeter = ",";
        String[] deletedInfoList = deletedInfo.split(delimeter);
        String[] addedInfoList = addedInfo.split(delimeter);

        int deletedStartLine = Integer.parseInt(deletedInfoList[0]
                .replaceAll(SvnDiffDefine.REG_DELETE_MARK, ""));

        int deletedStartLineCount = Integer.parseInt(deletedInfoList[1]
                .replaceAll(SvnDiffDefine.REG_DELETE_MARK, ""));

        int addedStartLine = Integer.parseInt(
                addedInfoList[0].replaceAll(SvnDiffDefine.REG_ADD_MARK, ""));

        int addedStartLineCount = Integer.parseInt(
                addedInfoList[1].replaceAll(SvnDiffDefine.REG_DELETE_MARK, ""));

        fileChangedInfo = new FileChangedInfo();

        fileChangedInfo.setAddedStartLine(addedStartLine);
        fileChangedInfo.setAddedStartLineCount(addedStartLineCount);
        fileChangedInfo.setDeletedStartLine(deletedStartLine);
        fileChangedInfo.setDeletedStartLineCount(deletedStartLineCount);
        // logger.debug("file changed info:{}", fileChangedInfo);
        return fileChangedInfo;
    }

    public static String parseFileNameFromIndexLine(String indexContent) {

        if (StringUtil.isEmpty(indexContent)) {
            return null;
        }

        int indexIndex = indexContent
                .indexOf(SvnDiffDefine.FILE_START_MARK_IN_SVN_DIFF_RESULT);
        if (indexIndex == -1) {
            return null;
        }

        indexContent = indexContent.trim();

        return indexContent.substring(
                SvnDiffDefine.FILE_START_MARK_IN_SVN_DIFF_RESULT.length());
    }

    /**
     * 从相对路径地址中解析包名
     * 
     * @see :
     * @param :
     * @return : String
     * @param relativePath
     * @return
     */
    public static String parsePackageNameFromPath(String relativePath) {

        String filePathSeperator = null;

        if (relativePath.indexOf('\\') != -1) {
            filePathSeperator = "\\";
        } else if (relativePath.indexOf("/") != -1) {
            filePathSeperator = "/";
        } else {
            filePathSeperator = "/";
        }

        String[] filePathList = relativePath.split(filePathSeperator);

        int companyMarkIndex = -1;
        for (int i = 0; i < filePathList.length; i++) {
            if (CommonDefine.COMPANY_MARK_LIST.contains(filePathList[i])) {
                companyMarkIndex = i;
                break;
            }
        }
        StringBuilder packageName = new StringBuilder();
        for (int j = companyMarkIndex; j < filePathList.length - 1; j++) {
            packageName.append(filePathList[j]).append(".");
        }
        return packageName.substring(0, packageName.length() - 1);

    }

    /**
     * 检查本地的目录是否和当前认证的是否同一个库
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param localSvnDir
     * @param svnRevision：
     *            当前的url的版本
     * @return
     */
    public boolean checkLocalDirIsTheSameWithCurrentAuth(File localSvnDir,
            SVNRevision svnRevision) {
        if (!checkLocalDirIsTheSameUrlWithCurrentAuth(localSvnDir)) {
            return false;
        }

        SvnDirectoryInfo svnDirectoryInfoRemote = getRemoteSvnInfo();
        SvnDirectoryInfo svnDirectoryInfoLocal = getLocalDirectoryInfo(
                localSvnDir);
        if (null != svnRevision) {
            svnDirectoryInfoRemote.setSvnRevision(svnRevision.toString());
        }

        if (null == svnDirectoryInfoRemote) {
            return false;
        }

        return svnDirectoryInfoRemote.equals(svnDirectoryInfoLocal);
    }

    /**
     * 检查本地的目录是否和当前认证信息是一个URL
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param localSvnDir
     * @return
     */
    public boolean checkLocalDirIsTheSameUrlWithCurrentAuth(File localSvnDir) {
        // 如果本地目录不存在，返回false
        if (!localSvnDir.exists()) {
            return false;
        }
        // 如果不是个svn工程，返回false
        if (!isWorkingCopy(localSvnDir)) {
            return false;
        }

        // 获取本地版本
        SvnDirectoryInfo svnDirectoryInfoLocal = getLocalDirectoryInfo(
                localSvnDir);

        if (null == svnDirectoryInfoLocal) {
            return false;
        }

        // 如果路径不同
        return checkRemoteUrlTheSameWithCurrentUrl(
                svnDirectoryInfoLocal.getSvnUrl());
    }

    /**
     * 检查本地的目录是否和当前认证的是否同一个库
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param localSvnDir:文件
     * @return
     */
    public boolean checkLocalDirIsTheSameWithCurrentAuth(File localSvnDir) {
        return checkLocalDirIsTheSameWithCurrentAuth(localSvnDir, null);
    }

    /**
     * @see :
     * @param :
     * @return : boolean
     * @param localSvnDir：路径文件夹
     * @return
     */
    public boolean checkLocalDirIsTheSameWithCurrentAuth(String localSvnDir) {
        return checkLocalDirIsTheSameWithCurrentAuth(new File(localSvnDir),
                null);
    }

    /**
     * 检查本地版本库是否和远程库同一个
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param localSvnDir
     * @param svnRevision
     * @return
     */
    public boolean checkLocalDirIsTheSameWithCurrentAuth(String localSvnDir,
            SVNRevision svnRevision) {
        return checkLocalDirIsTheSameWithCurrentAuth(new File(localSvnDir),
                svnRevision);
    }

    /**
     * 获取本地路径的svn信息
     * 
     * @see :
     * @param :
     * @return : SvnDirectoryInfo
     * @param localPath
     * @return
     */
    public SvnDirectoryInfo getLocalDirectoryInfo(File localPathFile) {
        // 如果不存在
        if (!localPathFile.exists()) {

            return null;
        }

        // 如果不是目录
        if (!localPathFile.isDirectory()) {
            return null;
        }

        SVNInfo svnInfo;
        SvnDirectoryInfo svnDirectoryInfo = null;
        try {
            svnInfo = this.svnManager.getsVNClientManager().getWCClient()
                    .doInfo(localPathFile, SVNRevision.WORKING);
            svnDirectoryInfo = new SvnDirectoryInfo();
            svnDirectoryInfo.setCommitedRevision(
                    svnInfo.getCommittedRevision().toString());
            svnDirectoryInfo.setSvnRevision(svnInfo.getRevision().toString());
            svnDirectoryInfo.setSvnUrl(svnInfo.getURL().toString());
            logger.debug("获取本地svn目录:{}的信息:{}", localPathFile.getAbsolutePath(),
                    svnDirectoryInfo);
            return svnDirectoryInfo;
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("svn操作失败:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取远程仓库的信息
     * 
     * @see :
     * @param :
     * @return : void
     * @param svnRepository
     */
    public SvnDirectoryInfo getRemoteSvnInfo(String remoteUrl) {
        if (StringUtil.isEmpty(remoteUrl)) {
            remoteUrl = this.svnManager.getRepository().getLocation()
                    .toString();
        }

        Map<String, SVNLogEntry> svnLogsEntry = getHistory(remoteUrl);
        if (svnLogsEntry.isEmpty()) {
            return null;
        }

        Set<String> revisionList = svnLogsEntry.keySet();
        Object[] revisionListArray = revisionList.toArray();
        SvnDirectoryInfo svnDirectoryInfo = new SvnDirectoryInfo();
        svnDirectoryInfo.setSvnUrl(remoteUrl);
        svnDirectoryInfo.setCommitedRevision((String) revisionListArray[0]);
        svnDirectoryInfo.setSvnRevision(
                String.valueOf(getLatestVersionOfRepository(remoteUrl)));

        logger.debug("获取远程目录:{}的版本信息:{}", remoteUrl, svnDirectoryInfo);
        return svnDirectoryInfo;
    }

    /**
     * @see :
     * @param :
     * @return : Object[]
     * @param remoteUrl
     * @return
     */
    public Object[] getCommitedRevisionArray(String remoteUrl) {
        if (StringUtil.isEmpty(remoteUrl)) {
            remoteUrl = this.svnManager.getRepository().getLocation()
                    .toString();
        }

        Map<String, SVNLogEntry> svnLogsEntry = getHistory(remoteUrl);
        if (svnLogsEntry.isEmpty()) {
            return new Object[] {};
        }

        Set<String> revisionList = svnLogsEntry.keySet();
        return revisionList.toArray();
    }

    /**
     * 获取url的第一个版本号
     * 
     * @see :
     * @param :
     * @return : SVNRevision
     * @param remoteUrl
     * @return
     */
    public SVNRevision getFirstCommitedRevision(String remoteUrl) {
        Object[] revisionListArray = getCommitedRevisionArray(remoteUrl);
        return SVNRevision.parse(
                (String) revisionListArray[revisionListArray.length - 1]);
    }

    /**
     * 获取当前认证库的第一个版本号
     * 
     * @see :
     * @param :
     * @return
     */
    public SVNRevision getFirstCommitedRevision() {
        return getFirstCommitedRevision(
                this.svnManager.getRepository().getLocation().toString());
    }

    /**
     * 获取url的第一个版本号
     * 
     * @see :
     * @param :
     * @return : SVNRevision
     * @param remoteUrl
     * @return
     */
    public SVNRevision getLastCommitedRevision(String remoteUrl) {
        Object[] revisionListArray = getCommitedRevisionArray(remoteUrl);
        return SVNRevision.parse((String) revisionListArray[0]);
    }

    /**
     * 获取当前认证库的第一个版本号
     * 
     * @see :
     * @param :
     * @return : SVNRevision
     * @param remoteUrl
     * @return
     */
    public SVNRevision getLastCommitedRevision() {
        return getLastCommitedRevision(
                this.svnManager.getRepository().getLocation().toString());
    }

    /**
     * 获取远程仓库的信息
     * 
     * @see :
     * @param :
     * @return : void
     * @param svnRepository
     */
    public SvnDirectoryInfo getRemoteSvnInfo() {
        String remoteUrl = this.svnManager.getRepository().getLocation()
                .toString();
        return getRemoteSvnInfo(remoteUrl);
    }

    /**
     * 获取最近的日志
     * 
     * @see :
     * @param :
     * @return : Map<String,SVNLogEntry>
     * @param remoteUrl
     * @return
     */
    public Map<String, SVNLogEntry> getHistory(String remoteUrl) {
        SVNRepository repository = null;
        try {
            // 根据URL实例化SVN版本库。
            repository = createRepositoryWithCurrentAuthenticationManager(
                    remoteUrl);

            if (null == repository) {
                return new HashMap<String, SVNLogEntry>(16);
            }

            @SuppressWarnings({ "unchecked", "rawtypes" })
            final Map<String, SVNLogEntry> history = new TreeMap<String, SVNLogEntry>(
                    new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            if (o1 == null || o2 == null) {
                                return 0;
                            } else if (Integer.parseInt(o1 + "") > Integer
                                    .parseInt(o2 + "")) {
                                return -1;
                            } else if (Integer.parseInt(o1 + "") < Integer
                                    .parseInt(o2 + "")) {
                                return 1;
                            } else if (new Integer(o1 + "")
                                    .equals(new Integer(o2 + ""))) {
                                return 0;
                            }
                            return 0;
                        }
                    });
            long startRevision = 0;
            repository.log(new String[] { "" }, startRevision, -1, true, true,
                    new ISVNLogEntryHandler() {
                        @Override
                        public void handleLogEntry(SVNLogEntry svnlogentry)
                                throws SVNException {
                            fillResult(svnlogentry);
                        }

                        public void fillResult(SVNLogEntry svnlogentry) {
                            history.put(svnlogentry.getRevision() + "",
                                    svnlogentry);
                        }
                    });
            return history;
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException svne) {
            logger.error("在获取文件内容和属性时发生错误:{} ", svne.getMessage());
            return new HashMap<>(16);
        }
    }

    /**
     * 获取本地目录的svn信息
     * 
     * @see :
     * @param :
     * @return : SvnDirectoryInfo
     * @param localPath
     * @return
     */
    public SvnDirectoryInfo getLocalDirectoryInfo(String localPath) {
        return getLocalDirectoryInfo(new File(localPath));
    }

    /**
     * @see :
     * @param :
     * @return : void
     * @param remoteUrl
     * @param logCount
     */
    public void getLogs(String remoteUrl, long logCount) {
        SVNRepository svnRepository = createRepositoryWithCurrentAuthenticationManager(
                remoteUrl);

        if (null == svnRepository) {
            return;
        }

        final List<String> historyList = new ArrayList<>();

        try {
            svnRepository.log(new String[] { "" }, 0, -1, true, true,
                    new ISVNLogEntryHandler() {
                        @Override
                        public void handleLogEntry(SVNLogEntry svnLogEntry)
                                throws SVNException {
                            fillResult(svnLogEntry);
                        }

                        public void fillResult(SVNLogEntry svnLogEntry) {
                            historyList.add("" + svnLogEntry.getRevision());
                        }
                    });

            for (String string : historyList) {
                logger.debug(string);
            }
        } catch (SVNAuthenticationException e) {
            logger.error("操作失败，错误信息:{}", e.getMessage());
            throw new BusinessValidationException("仓库授权失败，请检查用户名密码是否对该仓库有权限");
        } catch (SVNException e) {
            logger.error("svn操作异常，失败原因:{}", e.getMessage());
        }
    }

    /**
     * @see :
     * @param :
     * @return : void
     * @param args
     * @throws SVNException
     */
    public void getLogs(String remoteUrl) {
        getLogs(remoteUrl, maxShowLogCount);
    }

    public static void main(String[] args) throws SVNException {
        String svnUrlRoot = "http://redmine.hztianque.com:9080/repos/TQProduct/tq-robot/branches/tq-robot-1.0.0/";
        SvnLoginInfo svnLoginInfo = new SvnLoginInfo();
        svnLoginInfo.setUsername("sunliuping");
        svnLoginInfo.setPassword("Admin@1234");
        svnLoginInfo.setRemoteUrl(svnUrlRoot);
        SvnClientUtil svnClientUtil2 = new SvnClientUtil(svnLoginInfo);
        String localPath = "D:\\TianQue\\tq-robot\\branches\\tq-robot-1.0.0";
        svnClientUtil2.checkoutDefault(
                "C:\\Users\\Administrator\\jacoco_coverage\\tq-robot-1.0.0");
    }
}
