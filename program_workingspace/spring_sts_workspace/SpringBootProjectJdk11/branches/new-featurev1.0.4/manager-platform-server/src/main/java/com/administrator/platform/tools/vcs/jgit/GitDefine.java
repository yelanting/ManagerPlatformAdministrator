/**
 * @author : 孙留平
 * @since : 2019年3月20日 上午8:42:17
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

/**
 * @author : Administrator
 * @since : 2019年3月20日 上午8:42:17
 * @see :
 */
public class GitDefine {
	private GitDefine() {

	}

	/**
	 * 主干代码名称
	 */
	public static final String GIT_MASTER_NAME = "master";

	/**
	 * 远程的名称origin
	 */
	public static final String GIT_ORIGIN_NAME = "origin";

	/**
	 * 本地分支的切片名称
	 */
	public static final String LOCAL_BRANCH_SLICE_NAME = "refs/heads/";

	/**
	 * tag的标签类型
	 */
	public static final String REMOTE_TAG_SLICE_NAME = "refs/tags/";

	/**
	 * 远程分支的切片名称
	 */
	public static final String REMOTE_BRANCH_SLICE_NAME = "refs/remotes/origin/";
}
