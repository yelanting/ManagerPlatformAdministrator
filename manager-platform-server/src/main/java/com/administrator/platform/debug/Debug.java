
/***
 * @see:Project Name:manager-platform-server*@see:File Name:Debug.java*@author:孙留平*@since:2019 年7月9日 下午4:01:59*@see:
 */

package com.administrator.platform.debug;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;

public class Debug {
	private static final Logger LOGGER = LoggerFactory.getLogger(Debug.class);

	public static void main(String[] args) throws Exception {
		File file = new File(
		        "D:\\manager_platform\\uploadFile\\classes_data\\newshengPingTai_release_2019.07.29_release\\classes.zip");

		File file2 = new File(
		        "D:\\manager_platform\\uploadFile\\classes_data\\test2\\");

		FileUtil.copyDir(
		        "D:\\manager_platform\\uploadFile\\classes_data\\newshengPingTai_release_2019.07.29_release\\classes\\",
		        "D:\\manager_platform\\uploadFile\\classes_data\\test2\\");
	}
}
