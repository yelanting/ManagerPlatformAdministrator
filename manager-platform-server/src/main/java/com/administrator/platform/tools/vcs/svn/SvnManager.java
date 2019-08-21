package com.administrator.platform.tools.vcs.svn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;

/**
 * @author : Administrator
 * @since : 2019年2月28日 14:28:08
 * @see : SVN管理器
 */
public class SvnManager {
    private static final Logger logger = LoggerFactory
            .getLogger(SvnManager.class);

    private SVNRepository repository;
    private SVNClientManager sVNClientManager;

    /**
     * 初始化操作
     * 
     * @throws Exception
     */
    public void initialize() {
        FSRepositoryFactory.setup();
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
    }

    public SvnManager(SvnLoginInfo svnLoginInfo) {
        this(svnLoginInfo.getRemoteUrl(), svnLoginInfo.getUsername(),
                svnLoginInfo.getPassword());
    }

    public SvnManager(String remoteUrl, CodeCoverage codeCoverage) {
        this(remoteUrl, codeCoverage.getUsername(), codeCoverage.getPassword());
    }

    /**
     * 
     * @see :
     * @param svnRoot
     * @param username
     * @param password
     */
    public SvnManager(String svnRoot, String username, String password) {
        // 创建库连接
        try {
            this.repository = SVNRepositoryFactory
                    .create(SVNURL.parseURIEncoded(svnRoot));
            // 身份验证
            // ISVNAuthenticationManager authManager = SVNWCUtil
            // .createDefaultAuthenticationManager(svnLoginInfo.getUsername(),
            // svnLoginInfo.getPassword());

            ISVNAuthenticationManager authManager = SVNWCUtil
                    .createDefaultAuthenticationManager(username,
                            password.toCharArray());
            // 创建身份验证管理器
            this.repository.setAuthenticationManager(authManager);
            DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
            this.sVNClientManager = SVNClientManager.newInstance(options,
                    authManager);
        } catch (SVNException e) {
            logger.error(
                    "初始化认证svn失败：url:{},username:{},password:{},errorInfo:{}",
                    svnRoot, username, password, e.getErrorMessage());
            throw new BusinessValidationException("初始化认证svn失败");
        }
    }

    public SVNRepository getRepository() {
        return repository;
    }

    public SVNClientManager getsVNClientManager() {
        return sVNClientManager;
    }
}
