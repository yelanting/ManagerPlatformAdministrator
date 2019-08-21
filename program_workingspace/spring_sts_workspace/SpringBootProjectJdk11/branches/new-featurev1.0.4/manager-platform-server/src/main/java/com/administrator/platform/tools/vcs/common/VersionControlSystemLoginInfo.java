/**
 * @author : 孙留平
 * @since : 2019年2月28日 下午2:35:51
 * @see:
 */
package com.administrator.platform.tools.vcs.common;

/**
 * @author : Administrator
 * @since : 2019年2月28日 下午2:35:51
 * @see :
 */
public class VersionControlSystemLoginInfo {
    /**
     * 登录密码
     */
    private String password;
    /**
     * 远程地址
     */
    private String remoteUrl;

    /**
     * 本地地址
     */
    private String localDir;

    /**
     * 较老的版本
     */
    private String olderVersion;

    /**
     * 较新的版本
     */
    private String newerVersion;

    /**
     * 最新版本
     */
    private String latestVersion;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getOlderVersion() {
        return olderVersion;
    }

    public void setOlderVersion(String olderVersion) {
        this.olderVersion = olderVersion;
    }

    public String getNewerVersion() {
        return newerVersion;
    }

    public void setNewerVersion(String newerVersion) {
        this.newerVersion = newerVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }
}
