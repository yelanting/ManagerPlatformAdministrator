/**
 * @author : 孙留平
 * @since : 2019年5月13日 上午9:50:53
 * @see:
 */
package com.administrator.platform.constdefine;

/**
 * @author : Administrator
 * @since : 2019年5月13日 上午9:50:53
 * @see :
 */
public class JenkinsDefine {

	private JenkinsDefine() {

	}

	/**
	 * @see jenkins的url key，用于在全局参数中查找
	 */
	public static final String JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_URL = "jenkinsUrl";

	/**
	 * @see jenkins的登陆用户 key，用于在全局参数中查找
	 */
	public static final String JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_USERNAME = "jenkinsLoginUsername";
	/**
	 * @see jenkins的登陆密码 key，用于在全局参数中查找
	 */
	public static final String JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_PASSWORD = "jenkinsLoginPassword";
	/**
	 * @see jenkins使用的svn认证key，用于在全局参数中查找对应的value
	 */
	public static final String JENKINS_KEY_OF_SVN_AUTH = "jenkinsSvnAuthId";

	/**
	 * @see 默认的svn认证id
	 */
	public static final String DEFAULT_SVN_AUTH_ID = "48588540-5ff6-49be-a4ab-54e47f368166";

	/**
	 * @see 默认的任务前缀
	 */
	public static final String PREFIX_OF_AUTOCREATED_AUTOTEST_TASK_NAME = "AutoCreated_AutoTest_Task";

	/**
	 * @see python文件的后缀
	 */
	public static final String DEFAULT_PYTHON_SUFFIX = ".py";

}
