/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 下午4:32:03
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.define;

import org.objectweb.asm.Opcodes;

public class ConstDefine {
	/**
	 * 类之间的分隔符
	 */
	public static final char PACKAGE_SEP = '.';

	/**
	 * 构造方法名称
	 */
	public static final String INIT_METHOD_NAME = "<init>";
	public static final String CLIENT_INIT_METHOD_NAME = "<init>";

	/**
	 * 方法描述中，参数列表和返回值的分隔符
	 */
	public static final String SEP_OF_PARAMETER_LIST_AND_RETURN = ")";
	/**
	 * 方法描述中，参数列表的左开始
	 */
	public static final String SEP_OF_PARAMETER_START = "(";
	public static final String SEP_OF_PARAMETER_END = SEP_OF_PARAMETER_LIST_AND_RETURN;

	/**
	 * 默认访问的ASM版本
	 */
	public static final int DEFAULT_OPCODE = Opcodes.ASM7;

	/**
	 * get
	 */
	public static final String GETTER_METHOD_PREFIX = "get";

	/**
	 * set
	 */
	public static final String SETTER_METHOD_PREFIX = "set";

	/**
	 * sep
	 */

	public static final String FOLDER_SEP = "/";
}
