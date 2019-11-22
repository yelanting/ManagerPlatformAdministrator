
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:YunXiaoDefine.java
 * @author : 孙留平
 * @since : 2019年5月20日 下午4:19:32
 * @see:
 */

package com.administrator.platform.constdefine;

/**
 * @author : Administrator
 * @since : 2019年5月20日 下午4:19:32
 * @see :
 */
public class YunXiaoDefine {
	private YunXiaoDefine() {
	}

	/**
	 * 默认key值
	 */
	public static final String KEY_OF_AUTOTEST_RESULT_CALLBACK = "autotest_result_call_bak_url";

	/**
	 * 默认插入的内容
	 */
	public static final String DEFAULT_INSERTED_VALUE = CommonDefine.DEFAULT_INSERTED_VALUE;

	/**
	 * ==========================
	 * 以下内容，是管理云效流水线功能新增
	 * ==========================
	 */
	/**
	 * 云效登陆密码-全局参数用户名的键值
	 */

	public static final String DEFAULT_YUNXIAO_USERNAME_KEY = "yunXiaoLoginUsername";
	/**
	 * 云效默认用户密码-全局参数密码键值
	 */
	public static final String DEFAULT_YUNXIAO_PASSWORD_KEY = "yunXiaoLoginPassword";

	/**
	 * 云效基础URL
	 */

	public static final String YUNXIAO_BASE_HTTP_URL = "http://yunxiao.aliyun.com/";

	/**
	 * 云效登陆前基础Url
	 */

	public static final String YUNXIAO_BASE_URL_BEFORE_LOGIN = "http://uc.aliyun.com/";
	/**
	 * 登陆url
	 */

	public static final String BASE_LOGIN_URL = YUNXIAO_BASE_URL_BEFORE_LOGIN
	        + "internal/login?oauth_callback=http://uc.aliyun.com/login?from=http://yunxiao.aliyun.com:80&ctx=/torrent/management?type=PROJECT";

	/**
	 * loginAction
	 */
	public static final String LOGIN_ACTION_URL = YUNXIAO_BASE_URL_BEFORE_LOGIN
	        + "internal/doLogin?_input_charset=utf-8";

	/**
	 * 云效登陆默认用户名
	 */
	public static final String DEFAULT_LOGIN_USERNAME = "fuyidan";

	/**
	 * 云效登录默认密码
	 */
	public static final String DEFAULT_LOGIN_PASSWORD = "123456";

	/**
	 * 云效默认首页
	 */

	public static final String DEFAULT_YUNXIAO_INDEX_PAGE = YUNXIAO_BASE_URL_BEFORE_LOGIN
	        + "internal/login?oauth_callback=http://uc.aliyun.com:80/login?from=http%3A%2F%2Fuc.aliyun.com&ctx=%2Ffavicon.ico";

	/**
	 * from Index Page
	 */
	public static final String DEFAULT_YUNXIAO_FROM_INDEX_PAGE = YUNXIAO_BASE_URL_BEFORE_LOGIN
	        + "/login?from=http://yunxiao.aliyun.com:80&ctx=/torrent/management?type=PROJECT";

	/**
	 * 去intername_username的key
	 * 
	 */
	public static final String DEFAULT_INTERNAL_USERNAME_KEY = "internal_username";
	/**
	 * 取a_u的值
	 */
	public static final String DEFAULT_AU_KEY = "a_u";

	/**
	 * 获取流水线列表的请求:
	 */
	public static final String YUNXIAO_ACTION_GET_PIPELINE_LIST = "/api-torrent/api/v1/aggregate/list/PROJECT?related=true&unClosed=true";

	/**
	 * 构建流水线请求
	 */
	public static final String YUNXIAO_ACTION_BUILD_PIPELINE = "/api-torrent/api/v1/pipelineSets/project/";

	/**
	 * 获取流水线列表
	 */
	public static final String YUNXIAO_ACTION_GET_PIPELINES_BY_SET_ID = "/api-torrent/api/v1/pipelines?fetchInst=true&pipelineSetId=";
	/**
	 * 构建流水线请求
	 */
	public static final String YUNXIAO_ACTION_GET_PIPELINE_SETS = "/api-torrent/api/v1/pipelineSets/project/";
	/**
	 * RUN流水线请求
	 */

	public static final String YUNXIAO_ACTION_RUN_PIPELINE_BY_ID = "/api-torrent/api/v1/pipelines/run/";

	/**
	 * 配置管理界面相关-开始
	 */

	public static final String YUNXIAO_ACTION_CONFIG_MANAGE_GET_LIST = "/aton/api/projects/query/list";
	public static final String YUNXIAO_ACTION_RUN_CONFIG_MANAGE_PIPELINE_BY_ID = "/aton/api/projects/query/list?onlySelf=true&projectName=&projectNotStatus=true&projectStatus=all&projectType=all";
	/**
	 * 配置管理界面相关-结束
	 */

}
