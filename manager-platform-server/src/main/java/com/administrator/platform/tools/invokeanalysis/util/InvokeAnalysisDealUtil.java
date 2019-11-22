/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月23日 上午8:35:16
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.util;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;

public class InvokeAnalysisDealUtil {
	/**
	 * 从类完整路径中，解析
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fullClassPath
	 * @return
	 */
	public static String parsePakageNameFromFullClassPath(
	        String fullClassPath) {
		fullClassPath = dealFullClassFilePath(fullClassPath);

		if (null == fullClassPath || StringUtil.isEmpty(fullClassPath)) {
			return null;
		}

		if (fullClassPath.indexOf(ConstDefine.PACKAGE_SEP) != -1) {
			return fullClassPath.substring(0,
			        fullClassPath.lastIndexOf(ConstDefine.PACKAGE_SEP) + 1);
		}

		return null;

	}

	/**
	 * 从类完整路径中，解析类名
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fullClassPath
	 * @return
	 */
	public static String parseClassNameFromFullClassPath(String fullClassPath) {

		fullClassPath = dealFullClassFilePath(fullClassPath);
		if (null == fullClassPath || StringUtil.isEmpty(fullClassPath)) {
			return null;
		}

		if (fullClassPath.indexOf("" + ConstDefine.PACKAGE_SEP) != -1) {
			return fullClassPath.substring(
			        fullClassPath.lastIndexOf(ConstDefine.PACKAGE_SEP) + 1);
		}

		return null;

	}

	/**
	 * 处理类路径中的/
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param fullClassPath
	 * @return
	 */
	public static String dealFullClassFilePath(String fullClassPath) {
		if (StringUtil.isEmpty(fullClassPath)) {
			return null;
		}
		return fullClassPath.replace("/", "" + ConstDefine.PACKAGE_SEP);
	}

	/**
	 * 判断一个方法是不是构造方法
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param methodName
	 * @return
	 */
	public static boolean isConstructorMethod(String methodName) {
		return ConstDefine.CLIENT_INIT_METHOD_NAME.equals(methodName)
		        || ConstDefine.INIT_METHOD_NAME.equals(methodName);
	}

	/**
	 * 从方法描述中获取方法参数列表
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param desc
	 * @return
	 */
	public static String parseParametersFromDesc(String desc) {

		if (StringUtil.isEmpty(desc)) {
			return null;
		}

		if (desc.indexOf(ConstDefine.SEP_OF_PARAMETER_LIST_AND_RETURN) != -1) {
			return desc.substring(0, desc.lastIndexOf(
			        ConstDefine.SEP_OF_PARAMETER_LIST_AND_RETURN) + 1);
		}
		return null;
	}

	/**
	 * 产生get或者set方法
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param getOrSet
	 * @param fieldName
	 * @return
	 */
	public static String makeGetterAndSetterOfField(String getOrSet,
	        String fieldName) {

		if (StringUtil.isEmpty(getOrSet)) {
			getOrSet = ConstDefine.GETTER_METHOD_PREFIX;
		}

		if (StringUtil.isEmpty(fieldName)) {
			return "";
		}

		return getOrSet + StringUtil.firstCharUpCase(fieldName);
	}

	/**
	 * 从方法的参数描述里，解析出简单参数
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param desc
	 * @return
	 */
	public static String parserSimpleParametersFromParamDesc(String desc) {
		if (StringUtil.isEmpty(desc)) {
			return "";
		}

		String paramsContent = desc.substring(
		        desc.indexOf(ConstDefine.SEP_OF_PARAMETER_START),
		        desc.lastIndexOf(ConstDefine.SEP_OF_PARAMETER_END));

		if (StringUtil.isEmpty(paramsContent)) {
			return ConstDefine.SEP_OF_PARAMETER_START + " "
			        + ConstDefine.SEP_OF_PARAMETER_END;

		}

		String[] paramsContentArray = paramsContent.split(";");

		StringBuilder paramsReturnFinal = new StringBuilder(
		        ConstDefine.SEP_OF_PARAMETER_START);
		for (int i = 0; i < paramsContentArray.length; i++) {
			String eachParam = paramsContentArray[i];

			if (StringUtil.isStringAvaliable(eachParam)
			        && eachParam.contains(ConstDefine.FOLDER_SEP)) {
				paramsReturnFinal.append(eachParam.substring(
				        eachParam.lastIndexOf(ConstDefine.FOLDER_SEP) + 1));
			} else {
				paramsReturnFinal.append(eachParam);
			}
		}

		paramsReturnFinal.append(ConstDefine.SEP_OF_PARAMETER_END);
		return paramsReturnFinal.toString();
	}
}
