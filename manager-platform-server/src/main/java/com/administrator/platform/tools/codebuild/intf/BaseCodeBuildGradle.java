
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:CodeBuildGradle.java
 * @author : 孙留平
 * @since : 2019年7月10日 上午10:55:12
 * @see:
 */

package com.administrator.platform.tools.codebuild.intf;

import java.io.File;

import org.gradle.tooling.GradleConnector;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.tools.jacoco.JacocoDefine;

public abstract class BaseCodeBuildGradle implements CodeBuildIntf {
	/**
	 * @see :
	 * @param :
	 * @return : void
	 * @param args
	 */
	protected boolean checkProjectGradle(String projectDir) {
		File projectFile = new File(projectDir);

		if (!projectFile.exists()) {
			return false;
		}

		if (!projectFile.isDirectory()) {
			return false;
		}

		// 如果路径以build.gradle结尾，则代表是个文件
		// 如果不是以build.gradle结尾
		// 不是以build.gradle结尾，又是个文件，则报错
		// 如果是个文件夹，则自动加上build.gradle
		File buildGradleFileTemp = new File(projectDir,
				JacocoDefine.DEFAULT_GRADLE_FILE_NAME);
		// 如果build.gradle文件不存在
		return buildGradleFileTemp.exists();
	}

	/**
	 * 获取工程连接
	 * 
	 * @see :
	 * @param :
	 * @return : ProjectConnection
	 * @param projectPath
	 * @return
	 */
	protected GradleConnector getGradleConnector(String projectPath) {
		File projectFile = new File(projectPath);
		if (!projectFile.exists()) {
			throw new BusinessValidationException(
					"Gradle" + projectPath + "工程不存在");
		}

		return GradleConnector.newConnector().forProjectDirectory(projectFile);
	}
}
