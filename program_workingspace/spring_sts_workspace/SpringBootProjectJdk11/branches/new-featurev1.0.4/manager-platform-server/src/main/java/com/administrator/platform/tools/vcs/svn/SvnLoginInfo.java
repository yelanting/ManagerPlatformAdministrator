/**
 * @author : 孙留平
 * @since : 2019年2月28日 下午2:39:50
 * @see:
 */
package com.administrator.platform.tools.vcs.svn;

import com.administrator.platform.tools.vcs.common.VersionControlSystemLoginInfo;

/**
 * @author : Administrator
 * @since : 2019年2月28日 下午2:39:50
 * @see :
 */
public class SvnLoginInfo extends VersionControlSystemLoginInfo {
    /**
     * 登录用户名
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
