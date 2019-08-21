/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:03:25
 * @see:
 */
package com.administrator.platform.exception.constant;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:03:25
 * @see :异常码类
 */
public interface ExceptionCode {
	/**
	 * 业务规则校验异常码,异常信息不被记录
	 */
	String BUSINESS_VALIDATION = "BE100-01";
	/**
	 * 业务规则校验异常码,异常信息被记录
	 */
	String SERVICE_VALIDATION = "SE100-01";
	/**
	 * 非法操作异常码
	 */
	String ILLEGAL_OPERATION = "IOE100-01";
	/**
	 * 参数不合法异常码
	 */
	String PARAMETER_ILLEGAL = "PIE100-01";
	/**
	 * 系统工具类异常码
	 */
	String SYSTEM_UTIL = "SUE100-01";

	/**
	 * 操作失败异常
	 */
	String OPERATION_FAILED = "OFE100-01";

	/**
	 * 未知异常码
	 */
	String UNKNOWN_CODE = "UN100-001";

	/**
	 * 错误码
	 */
	String ERROR_CODE = "ERROR100-001";

	/**
	 * 云效的操作异常代码
	 */
	String YUNXIAO_BUSINESS_VALIDATION = "YX_BE100-01";
}
