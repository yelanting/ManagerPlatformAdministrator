/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:21:00
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午2:21:00
 * @see :
 */
public class StringUtil {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(StringUtil.class);
	/**
	 * 英文逗号
	 */
	public final static String COMMASYMBOL = ",";

	/**
	 * @see 空字符
	 */
	public final static char EMPTY_CHAR = ' ';

	/**
	 * @see :首字母大写
	 * @param :
	 * @return : String
	 * @param str
	 * @return
	 */

	public static String firstCharLowCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String firstCharUpCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String addPrefix(int num, String prefix, int length) {
		return String.format("%04d", num);
	}

	public static boolean isStringAvaliable(String string) {
		return (null != string && !"".equals(string.trim()));
	}

	public static Boolean notSame(String dest, String scre) {
		if (null == dest || null == scre) {
			return false;
		} else {
			return !dest.equals(scre);
		}
	}

	/**
	 * 去掉字符串前后空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		int i = s.length();
		// 字符串第一个字符
		int j = 0;
		// 中间变量
		int k = 0;

		// 将字符串转换成字符数组
		char[] arrayOfChar = s.toCharArray();
		while ((j < i) && (arrayOfChar[(k + j)] <= EMPTY_CHAR)) {
			// 确定字符串前面的空格数
			++j;
		}
		while ((j < i) && (arrayOfChar[(k + i - 1)] <= EMPTY_CHAR)) {
			// 确定字符串后面的空格数
			--i;
		}

		// 返回去除空格后的字符串
		return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);
	}

	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "")
		        .replaceAll("<[^>]*>", "");

		return str.replaceAll("[(/>)<]", "");
	}

	/**
	 * 将字符串中的英文单双引号替换是html用的
	 * 
	 * @param str
	 * @return
	 */
	public static String changeJavaToHtml(String str) {
		if (isEmpty(str)) {
			return "";
		}
		str = str.replace("'", "&#39;");
		str = str.replace("\"", "&quot;");
		return str;
	}

	public static boolean isEmpty(String str) {
		return !isStringAvaliable(str);
	}

	public static String enCodeExeclDownLoadName(String str) {
		if (isEmpty(str)) {
			return "";
		}
		try {
			return new String(str.getBytes("gbk"), "ISO8859-1");
		} catch (Exception e) {
			return "";
		}
	}

	public static String arrToString(Object[] arr) {
		return Arrays.toString(arr).replace("[", "").replace("]", "")
		        .replace(" ", "");
	}
	//
	// /***
	// * 截取orgCode 的市級別的orgCode
	// * 1.1.3.5.1.12. 返回1.1.3.
	// */
	// public static String subOrgCode(String text) {
	// if (!isStringAvaliable(text)) {
	// return "";
	// }
	//
	// String[] arr = text.split("\\.");
	// if (arr.length > 3) {
	// return arr[0] + "." + arr[1] + "." + arr[2] + ".";
	// } else {
	// return text;
	// }
	// }

	// /***
	// * 截取orgCode父级code
	// * 1.1.3.5.1.12. 返回1.1.3.5.1.
	// */
	// public static String parentOrgCode(String text) {
	// if (!isStringAvaliable(text)) {
	// return "";
	// }
	//
	// String[] arr = text.split("\\.");
	// StringBuffer sb = new StringBuffer();
	// for (int i = 0; i < arr.length - 1; i++) {
	// sb.append(arr[i]).append(".");
	// }
	// return sb.toString();
	// }

	public static String joinSortFieldOrder(String filed, String sord) {
		StringBuffer orderFiled = new StringBuffer();
		if (!StringUtil.isEmpty(filed)) {
			orderFiled.append(filed);
			if (!StringUtil.isEmpty(sord)) {
				orderFiled.append(" ").append(sord);
			}
		}
		return orderFiled.toString();
	}

	/**
	 * 把字符串类型转换为MAP
	 * 
	 * @see :
	 * @param :
	 * @return : Map<String,Object>
	 * @param params
	 * @return
	 */
	public static Map<String, Object> changeStringParamsToMap(String params) {
		if (StringUtil.isEmpty(params)) {
			return null;
		}

		String[] paramsAfterSplit = params.split("&");

		Map<String, Object> paramsMap = new HashMap<>(16);
		/**
		 * 
		 */
		for (int i = 0; i < paramsAfterSplit.length; i++) {
			String[] eachPairAfterSplit = paramsAfterSplit[i].split("=");

			if (eachPairAfterSplit.length <= 0) {
				continue;
			}

			if (eachPairAfterSplit.length == 1) {
				paramsMap.put(eachPairAfterSplit[0], null);
			} else {
				paramsMap.put("" + eachPairAfterSplit[0],
				        eachPairAfterSplit[1]);
			}
		}

		LOGGER.debug("把字符串:{},转换为MAP。结果为:{}", params, paramsMap);

		return paramsMap;
	}

	/**
	 * 把字符串数组 转换成字符串，,连接
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param stringArray
	 * @return
	 */
	public static String changeStringArrayToString(String[] stringArray) {
		if (null == stringArray || stringArray.length == 0) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (String string : stringArray) {
			stringBuilder.append(string).append(",");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}
}
