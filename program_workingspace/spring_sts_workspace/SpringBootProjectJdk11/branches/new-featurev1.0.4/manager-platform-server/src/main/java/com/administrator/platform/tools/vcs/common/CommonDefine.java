/**
 * @author : 孙留平
 * @since : 2019年3月5日 下午4:37:02
 * @see:
 */
package com.administrator.platform.tools.vcs.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Administrator
 * @since : 2019年3月5日 下午4:37:02
 * @see :
 */
public class CommonDefine {
    private CommonDefine() {

    }

    public static final List<String> COMPANY_MARK_LIST = new ArrayList<>();
    static {
        COMPANY_MARK_LIST.add("com");
        COMPANY_MARK_LIST.add("org");
    }

    /**
     * git路径后缀
     */
    public static final String REMOTE_URL_SUFFIX_GIT = ".git";

    /**
     * 远程url的分割符
     */
    public static final String REMOTE_URL_SEPERATOR = "/";
}
