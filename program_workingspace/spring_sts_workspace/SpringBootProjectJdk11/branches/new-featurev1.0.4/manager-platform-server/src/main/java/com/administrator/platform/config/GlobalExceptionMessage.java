/**
 * @author : 孙留平
 * @since : 2018年10月10日 下午6:25:22
 * @see:
 */
package com.administrator.platform.config;

/**
 * @author : Administrator
 * @since : 2018年10月10日 下午6:25:22
 * @see :
 */
public class GlobalExceptionMessage {
	private GlobalExceptionMessage() {

	}

	/**
	 * 空参数提示
	 */
	public static final String NULL_PARAMETER_MESSAGE = "参数不能为null";

	/**
	 * 数组参数是空或者是null的时候提示
	 */
	public static final String NULL_OR_EMPTY_ARRAY_MESSAGE = "数组参数不能为null且不能是空数组";

	/**
	 * 数组参数是空或者是null的时候提示
	 */
	public static final String NULL_OR_EMPTY_STRING_MESSAGE = "字符串参数不能为null且不能是空字符且不能是全空白字符";

	/**
	 * 数组参数是空或者是null的时候提示,前后有空白提示
	 */
	public static final String NULL_OR_EMPTY_OR_CANNOT_CONTAIN_BLANK_STRING_MESSAGE = "字符串左右两端不能有空白且不能是空白字符";
}
