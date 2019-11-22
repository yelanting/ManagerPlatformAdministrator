/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月27日 下午1:53:38
 * @see:
 */
package com.administrator.platform.util;

import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.util.define.OperationSystemEnum;

public class CommonUtil {
	private CommonUtil() {

	}

	/**
	 * 获取默认存放文件夹
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	public static final String getDefaultFileSaveFolder() {
		OperationSystemEnum operationSystemEnum = Util
		        .getCurrentOperationSystem();
		if (operationSystemEnum == OperationSystemEnum.LINUX) {
			return JacocoDefine.LINUX_SELFDEFINED_BASE_FOLDER;
		}
		return JacocoDefine.WINDOWS_SELFDEFINED_BASE_FOLDER;
	}
}
