/**
 * @author : 孙留平
 * @since : 2019年3月13日 下午10:01:57
 * @see:
 */
package com.administrator.platform.tools.vcs.svn;

/**
 * @author : Administrator
 * @since : 2019年3月13日 下午10:01:57
 * @see :
 */
public class SvnDirectoryInfo {

    /**
     * 远程路径
     */
    private String svnUrl;

    /**
     * 当前的revision
     */
    private String svnRevision;

    /**
     * 提交的revision
     */
    private String commitedRevision;

    public String getSvnUrl() {
        return svnUrl;
    }

    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    public String getSvnRevision() {
        return svnRevision;
    }

    public void setSvnRevision(String svnRevision) {
        this.svnRevision = svnRevision;
    }

    public String getCommitedRevision() {
        return commitedRevision;
    }

    public void setCommitedRevision(String commitedRevision) {
        this.commitedRevision = commitedRevision;
    }

    @Override
    public String toString() {
        return "SvnDirectoryInfo [svnUrl=" + svnUrl + ", svnRevision="
                + svnRevision + ", commitedRevision=" + commitedRevision + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commitedRevision == null) ? 0
                : commitedRevision.hashCode());
        result = prime * result
                + ((svnRevision == null) ? 0 : svnRevision.hashCode());
        result = prime * result + ((svnUrl == null) ? 0 : svnUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SvnDirectoryInfo other = (SvnDirectoryInfo) obj;
        if (commitedRevision == null) {
            if (other.commitedRevision != null) {
                return false;
            }
        } else if (!commitedRevision.equals(other.commitedRevision)) {
            return false;
        }
        if (svnRevision == null) {
            if (other.svnRevision != null) {
                return false;
            }
        } else if (!svnRevision.equals(other.svnRevision)) {
            return false;
        }
        if (svnUrl == null) {
            if (other.svnUrl != null) {
                return false;
            }
        } else if (!svnUrl.equals(other.svnUrl)) {
            return false;
        }
        return true;
    }

}
