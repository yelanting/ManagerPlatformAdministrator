/**
 * @author : 孙留平
 * @since : 2019年2月28日 下午2:38:55
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

import com.administrator.platform.tools.vcs.common.VersionControlSystemLoginInfo;

/**
 * @author : Administrator
 * @since : 2019年2月28日 下午2:38:55
 * @see :
 */
public class GitLoginInfo extends VersionControlSystemLoginInfo {
    /**
     * 邮件地址
     */
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
