/**
 * @author : 孙留平
 * @since : 2019年5月14日 下午2:00:58
 * @see:
 */
package com.administrator.platform.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.administrator.platform.exception.base.BusinessValidationException;

/**
 * @author : Administrator
 * @since : 2019年5月14日 下午2:00:58
 * @see :
 */
public class ClasspathFilesUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(ClasspathFilesUtil.class);

	/**
	 * jenkins配置文件地址
	 */
	private static final String JENKINS_TEMPLATES_FILE_FOLDER = "jenkins_file_templates";

	/**
	 * jenkinsjob的配置文件地址
	 */
	private static final String JENKINS_JOB_TEMPLATE_FOLDER = "job_config_xmls";

	/**
	 * 默认的job配置文件
	 * 
	 * @see :
	 */
	private static final String DEFAULT_JOB_CONFIG_XML = "defaultJobConfig.xml";

	private ClasspathFilesUtil() {

	}

	/**
	 * 获取classpath下的文件
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @param relativePath
	 * @return
	 */

	private static File getClasspathFile(String relativePath) {

		ClassPathResource classPathResource = new ClassPathResource(
		        relativePath);

		File currentFile;
		try {
			currentFile = classPathResource.getFile();

			if (currentFile.exists()) {
				return currentFile;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("classpath下的文件:{},不存在，错误信息为:{}", e.getMessage(),
			        relativePath);
			throw new BusinessValidationException("classpath下的文件不存在");
		}
		return null;
	}

	/**
	 * 获取默认的jenkins配置文件
	 * 
	 * @see :
	 * @param :
	 * @return : File
	 * @return
	 */
	public static File getDefaultJenkinsConfigXmlFile() {
		String relativePath = JENKINS_TEMPLATES_FILE_FOLDER + "/"
		        + JENKINS_JOB_TEMPLATE_FOLDER + "/" + DEFAULT_JOB_CONFIG_XML;

		InputStream inputStream = new ClasspathFilesUtil().getClass()
		        .getClassLoader().getResourceAsStream(relativePath);

		File currentFile = new File("temp.xml");
		try {

			// 如果临时文件不存在，则copy
			if (!currentFile.exists()) {
				FileUtils.copyInputStreamToFile(inputStream, currentFile);
				return currentFile;
			}
			// 如果临时文件存在，则
			return currentFile;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("classpath下的文件:{},不存在，错误信息为:{}", e.getMessage(),
			        relativePath);
			throw new BusinessValidationException("classpath下的文件不存在");
		}
	}
}
