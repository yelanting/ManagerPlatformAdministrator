/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 下午6:43:49
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis;

import java.io.File;
import java.util.List;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.tools.invokeanalysis.visitor.ClassFileReader;
import com.administrator.platform.util.define.FileSuffix;

public class ClassFileDealRunner {
	private ClassFileDealRunner() {

	}

	public static void analysisClassFolder(String folder) {
		List<File> files = FileUtil.getFilesUnderFolder(folder,
		        FileSuffix.CLASS_FILE);

		for (File file : files) {
			new ClassFileReader(file.getAbsolutePath()).visit();
		}
	}

	public static void analysisClassFolder(File folderFile) {
		analysisClassFolder(folderFile.getAbsolutePath());
	}
}
