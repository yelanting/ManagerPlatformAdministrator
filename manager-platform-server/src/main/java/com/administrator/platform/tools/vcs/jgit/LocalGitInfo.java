/**
 * @author : 孙留平
 * @since : 2019年3月19日 下午9:39:43
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

/**
 * @author : Administrator
 * @since : 2019年3月19日 下午9:39:43
 * @see :
 */
public class LocalGitInfo {
    private String remoteUrl;
    private String branchName;

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "LocalGitInfo [remoteUrl=" + remoteUrl + ", branchName="
                + branchName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((branchName == null) ? 0 : branchName.hashCode());
        result = prime * result
                + ((remoteUrl == null) ? 0 : remoteUrl.hashCode());
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
        LocalGitInfo other = (LocalGitInfo) obj;
        if (branchName == null) {
            if (other.branchName != null) {
                return false;
            }
        } else if (!branchName.equals(other.branchName)) {
            return false;
        }
        if (remoteUrl == null) {
            if (other.remoteUrl != null) {
                return false;
            }
        } else if (!remoteUrl.equals(other.remoteUrl)) {
            return false;
        }
        return true;
    }
}
