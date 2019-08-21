/**
 * @author : 孙留平
 * @since : 2018年9月11日 下午2:59:24
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.exception.base.BusinessValidationException;

/**
 * 校验工具类
 * 
 * @author : Administrator
 * @since : 2018年9月11日 下午2:59:24
 * @see :
 */
public class ValidationUtil {

	// ------------------常量定义

	// public static final String EMAIL =
	// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";;
	/**
	 * Email正则表达式="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	 */
	public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
	/**
	 * 电话号码正则表达式=
	 * (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
	 */
	public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
	/**
	 * 手机号码正则表达式=^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$
	 */
	public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";

	/**
	 * Integer正则表达式 ^-?(([1-9]\d*$)|0)
	 */
	public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
	/**
	 * 正整数正则表达式 >=0 ^[1-9]\d*|0$
	 */
	public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
	/**
	 * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
	 */
	public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
	/**
	 * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
	 */
	public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
	/**
	 * 正Double正则表达式 >=0 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
	 */
	public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
	/**
	 * 负Double正则表达式 <= 0 ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
	 */
	public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
	/**
	 * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
	 */
	public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
	/**
	 * 邮编正则表达式 [0-9]\d{5}(?!\d) 国内6位邮编
	 */
	public static final String CODE = "[0-9]\\d{5}(?!\\d)";
	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
	 */
	public static final String STR_ENG_NUM_ = "^\\w+$";
	/**
	 * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
	 */
	public static final String STR_ENG_NUM = "^[A-Za-z0-9]+";
	/**
	 * 匹配由26个英文字母组成的字符串 ^[A-Za-z]+$
	 */
	public static final String STR_ENG = "^[A-Za-z]+$";

	/**
	 * 匹配由26个英文字母组成的字符串与下划线 ^[A-Z_a-z]+$
	 */
	public static final String STR_ENG_LINE = "^[A-Z_a-z]+$";

	/**
	 * 过滤特殊字符串正则
	 * regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	 */
	public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	/***
	 * 日期正则 支持：
	 * YYYY-MM-DD
	 * YYYY/MM/DD
	 * YYYY_MM_DD
	 * YYYYMMDD
	 * YYYY.MM.DD的形式
	 */
	public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)"
	        + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)"
	        + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)"
	        + "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)"
	        + "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)"
	        + "(0?2)([-\\/\\._]?)(29)$)"
	        + "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)"
	        + "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|"
	        + "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
	/***
	 * 日期正则 支持：
	 * YYYY-MM-DD
	 */
	public static final String DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	/**
	 * URL正则表达式
	 * 匹配 http www ftp
	 */
	public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
	        + "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
	        + "(\\w*:)*(\\w*\\+)*(\\w*\\.)*"
	        + "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

	/**
	 * 身份证正则表达式
	 */
	public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})"
	        + "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
	        + "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";

	/**
	 * 机构代码
	 */
	public static final String JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";

	/**
	 * 匹配数字组成的字符串 ^[0-9]+$
	 */
	public static final String STR_NUM = "^[0-9]+$";
	/**
	 * 匹配汉字、数字、字母、下划线，下划线位置不限
	 */
	public static final String STR_CNZ_ = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
	/**
	 * 匹配汉字、字母、下划线，下划线位置不限
	 */
	public static final String STR_CZ_ = "^[a-zA-Z_\u4e00-\u9fa5]+$";

	public static final String STR_CZ_NUM_ = "^[_0-9\u4e00-\u9fa5]+$";

	public static final String STR_ZNT = "(?![^a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{0,1000}$";

	public static final String CHINESE = "^[\u4e00-\u9fa5]+$";

	/**
	 * 身份证号码长度15
	 */
	public static final int ID_CARD_LENGTH_FIFTEEN = 15;
	/**
	 * 身份证号码长度18
	 */
	public static final int ID_CARD_LENGTH_EIGHTEEN = 18;

	/**
	 * 匹配英文、字母、特殊符号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strZnt(String str) {
		return regular(str, STR_ZNT);
	}

	/**
	 * 匹配汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return regular(str, CHINESE);
	}

	/**
	 * 匹配英文+下划线
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrAndUnderline(String str) {
		return regular(str, STR_ENG_LINE);
	}

	/**
	 * 匹配汉字、数字、字母、下划线，下划线位置不限
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strCnz(String str) {
		return regular(str, STR_CNZ_);
	}

	/**
	 * 匹配汉字、字母、下划线，下划线位置不限
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strCz(String str) {
		return regular(str, STR_CZ_);
	}

	/**
	 * 匹配汉字、数字、下划线，下划线位置不限
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strCzNumUnderLine(String str) {
		return regular(str, STR_CZ_NUM_);
	}

	//// ------------------验证方法
	/**
	 * 判断字段是否为空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static synchronized boolean strIsNull(String str) {
		return null == str || str.trim().length() <= 0 ? true : false;
	}

	/**
	 * 判断字段是非空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean strNotNull(String str) {
		return !strIsNull(str);
	}

	/**
	 * 字符串null转空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String nullToStr(String str) {
		return strIsNull(str) ? "" : str;
	}

	/**
	 * 字符串null赋值默认值
	 * 
	 * @param str
	 *            目标字符串
	 * @param defaut
	 *            默认值
	 * @return String
	 */
	public static String nullToStr(String str, String defaut) {
		return strIsNull(str) ? defaut : str;
	}

	/**
	 * 判断字段是否为Email 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		return regular(str, EMAIL);
	}

	/**
	 * 判断是否为电话号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isPhone(String str) {
		return regular(str, PHONE);
	}

	/**
	 * 判断是否为手机号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		return regular(str, MOBILE);
	}

	/**
	 * 判断是否为Url 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isUrl(String str) {
		return regular(str, URL);
	}

	/**
	 * 判断字段是否为INTEGER 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		return regular(str, INTEGER);
	}

	/**
	 * 判断字段是否为正整数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIntegerUnderLineNegative(String str) {
		return regular(str, INTEGER_NEGATIVE);
	}

	/**
	 * 判断字段是否为负整数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIntegerUnderLinePositive(String str) {
		return regular(str, INTEGER_POSITIVE);
	}

	/**
	 * 判断字段是否为DOUBLE 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDouble(String str) {
		return regular(str, DOUBLE);
	}

	/**
	 * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDoubleUnderLineNegative(String str) {
		return regular(str, DOUBLE_NEGATIVE);
	}

	/**
	 * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDoublePositive(String str) {
		return regular(str, DOUBLE_POSITIVE);
	}

	/**
	 * 判断字段是否为日期 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDate(String str) {
		return regular(str, DATE_ALL);
	}

	/**
	 * 验证2010-12-10
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate1(String str) {
		return regular(str, DATE_FORMAT1);
	}

	/**
	 * 判断字段是否为年龄 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isAge(String str) {
		return regular(str, AGE);
	}

	/**
	 * 判断字段是否超长
	 * 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
	 * 
	 * @param str
	 * @param leng
	 * @return boolean
	 */
	public static boolean isLengOut(String str, int leng) {
		return strIsNull(str) ? false : str.trim().length() > leng;
	}

	/**
	 * 判断字段是否在范围内
	 * 字串为空返回fasle, 不在范围内返回false,在范围内返回true
	 * 
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isLengthIn(String str, int min, int max) {
		if (str == null) {
			return false;
		}
		if (str.trim().length() < min || str.trim().length() > max) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字段是否为身份证 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIdCard(String str) {
		if (strIsNull(str)) {
			return false;
		}
		if (str.trim().length() == ID_CARD_LENGTH_FIFTEEN
		        || str.trim().length() == ID_CARD_LENGTH_EIGHTEEN) {
			return regular(str, IDCARD);
		} else {
			return false;
		}

	}

	/**
	 * 判断字段是否为邮编 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isCode(String str) {
		return regular(str, CODE);
	}

	/**
	 * 判断字符串是不是全部是英文字母
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEnglish(String str) {
		return regular(str, STR_ENG);
	}

	/**
	 * 判断字符串是不是全部是英文字母与下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEnglishAndLine(String str) {
		return regular(str, STR_ENG_LINE);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEngNum(String str) {
		return regular(str, STR_ENG_NUM);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字+下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEngUnderLineNum(String str) {
		return regular(str, STR_ENG_NUM_);
	}

	/**
	 * 过滤特殊字符串 返回过滤后的字符串
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String filterStr(String str) {
		Pattern p = Pattern.compile(STR_SPECIAL);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 校验机构代码格式
	 * 
	 * @return
	 */
	public static boolean isJigouCode(String str) {
		return regular(str, JIGOU_CODE);
	}

	/**
	 * 判断字符串是不是数字组成
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isStrUnderLineNum(String str) {
		return regular(str, STR_NUM);
	}

	/**
	 * 匹配是否符合正则表达式pattern 匹配返回true
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param pattern
	 *            匹配模式
	 * @return boolean
	 */
	private static boolean regular(String str, String pattern) {
		if (null == str || str.trim().length() <= 0) {
			return false;
		}
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断长度大小，边界不包含
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param toCheckString
	 * @param minLength
	 * @param maxLength
	 * @param noticeName
	 * @return
	 */
	public static boolean validateStringAndLength(String toCheckString,
	        Integer minLength, Integer maxLength, String noticeName) {

		int thusStringLength = 0;

		if (StringUtil.isEmpty(toCheckString)) {
			// throw new BusinessValidationException(
			// GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
			thusStringLength = 0;
		} else {
			thusStringLength = toCheckString.length();
		}

		if (null != minLength && null != maxLength && minLength > maxLength) {
			throw new BusinessValidationException("参数错误，最小长度不能大于最大长度!");
		}

		// 不能少于最小长度
		if (null != minLength && thusStringLength < minLength) {
			if (StringUtil.isStringAvaliable(noticeName)) {
				throw new BusinessValidationException(
				        noticeName + "长度不能小于" + minLength + "!(需大于或等于)");
			} else {
				throw new BusinessValidationException(
				        "长度不能小于" + minLength + "!(需大于或等于)");
			}

		}

		// 不能大于最大长度
		if (null != maxLength && thusStringLength > maxLength) {
			if (StringUtil.isStringAvaliable(noticeName)) {
				throw new BusinessValidationException(
				        noticeName + "长度不能大于" + maxLength + "!(需小于或等于)");
			} else {
				throw new BusinessValidationException(
				        "长度不能大于" + maxLength + "!(需小于或等于)");
			}
		}
		return true;
	}

	/**
	 * 判断是否是null。是null时，抛异常
	 * 
	 * @see :判断是否是null。是null时，抛异常
	 * @param object:
	 *            待判断的参数
	 * @return : void
	 * @param message
	 *            ： 是null的时候抛出的异常信息
	 */
	public static void validateNull(Object object, String message) {
		if (null == object) {

			if (!StringUtil.isStringAvaliable(message)) {
				message = GlobalExceptionMessage.NULL_PARAMETER_MESSAGE;
			}

			throw new BusinessValidationException(message);
		}
	}

	/**
	 * 判断是否是null。是null时，抛异常
	 * 且判断数组是不是空，是空时抛异常
	 * 
	 * @see :判断是否是null。是null时，抛异常
	 * @param Object:
	 *            待判断的参数
	 * @return : void
	 * @param message
	 *            ： 是null的时候抛出的异常信息
	 */
	public static void validateArrayNullOrEmpty(Object[] object,
	        String message) {
		if (null == object || object.length == 0) {
			if (!StringUtil.isStringAvaliable(message)) {
				message = GlobalExceptionMessage.NULL_OR_EMPTY_ARRAY_MESSAGE;
			}

			throw new BusinessValidationException(message);
		}
	}

	/**
	 * 判断字符串是null或者是，或者是空字符
	 * 
	 * @see :判断是否是null。是null时，抛异常
	 * @param Object:
	 *            待判断的参数
	 * @return : void
	 * @param message
	 *            ： 是null的时候抛出的异常信息
	 */
	public static void validateStringNullOrEmpty(String sourceString,
	        String message) {
		if (!StringUtil.isStringAvaliable(sourceString)) {
			if (!StringUtil.isStringAvaliable(message)) {
				message = GlobalExceptionMessage.NULL_OR_EMPTY_STRING_MESSAGE;
			}

			throw new BusinessValidationException(message);
		}
	}

	/**
	 * 检查两端是不是有空白
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param sourceString
	 * @param message
	 */
	public static void validateStringContainBlackInStartOrEnd(
	        String sourceString, String message) {
		if (StringUtil.isEmpty(sourceString)
		        || (sourceString.length() > sourceString.trim().length())) {
			if (StringUtil.isEmpty(message)) {
				message = GlobalExceptionMessage.NULL_OR_EMPTY_OR_CANNOT_CONTAIN_BLANK_STRING_MESSAGE;
			}

			throw new BusinessValidationException(message);
		}
	}
}
