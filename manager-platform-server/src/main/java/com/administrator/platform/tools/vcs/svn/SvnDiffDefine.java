/**
 * @author : 孙留平
 * @since : 2019年3月1日 上午10:05:39
 * @see:
 */
package com.administrator.platform.tools.vcs.svn;

/**
 * @author : Administrator
 * @since : 2019年3月1日 上午10:05:39
 * @see :
 */
public class SvnDiffDefine {
    /**
     * JAVA文件后缀
     * 
     */
    public static final String JAVA_FILE_SUFFIX = ".java";

    /**
     * diff的结果中的分隔符
     */
    public static final String DIFF_FILE_SEP_IN_DIFF_RESULT = "===================================================================";

    /**
     * @@变更符号
     * 
     */
    public static final String DOUBLE_AITE_CHARS = "@@";

    /**
     * @see 旧版本未存在
     */
    public static final String NO_EXISTS_FILE_NOTICE = "(nonexistent)";

    /**
     * @see 新增标记
     */
    public static final String ADD_MARK = "+";

    /**
     * @see 剔除的+++标记
     */
    public static final String EXCLUDED_ADD_MARK = "+++";

    /**
     * @see 删除标记
     */

    public static final String DELETE_MARK = "-";

    /**
     * @see +的正则表达
     */
    public static final String REG_ADD_MARK = "[+]";

    /**
     * @see -的正则表达删除标记
     */

    public static final String REG_DELETE_MARK = "[-]";

    /**
     * @see 包的标记
     */
    public static final String PACKAGE_MARK = "package ";

    /**
     * 行尾分号标记
     */

    public static final String LINE_TAIL_MARK = ";";

    /**
     * 文件名的开头
     */
    public static final String FILE_START_MARK_IN_SVN_DIFF_RESULT = "Index: ";

    /**
     * trunk标记
     */
    public static final String REMOTE_URL_TRUNK_MARK = "trunk";

    /**
     * branches标记
     */

    public static final String REMOTE_URL_BRANCHES_MARK = "branches";

    /**
     * release标记
     */
    public static final String REMOTE_URL_RELEASE_MARK = "release";

    /**
     * tag标记
     */
    public static final String REMOTE_URL_TAG_MARK = "tags";
}
