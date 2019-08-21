/**
 * @author : 孙留平
 * @since : 2019年2月26日 下午6:47:46
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.io.File;

/**
 * @author : Administrator
 * @since : 2019年2月26日 下午6:47:46
 * @see :
 */
public class JacocoDefine {

	private JacocoDefine() {

	}

	/**
	 * 本应用的基础文件夹
	 */
	public static final String CURRENT_APP_BASE_FOLDER = "manager_platform";

	/**
	 * 全量覆盖率的报告目录
	 */
	public static final String CODE_COVERAGE_DATA_FOLDER = "coverageReport";

	/**
	 * 增量覆盖率报告目录
	 */
	public static final String INCREMENT_CODE_COVERAGE_DATA_FOLDER = CODE_COVERAGE_DATA_FOLDER
	        + "Increment";

	/**
	 * 默认的java源文件存放目录，基于maven项目
	 */
	public static final String DEFAULT_SOURCE_FOLDER = "/src/main/java";
	// public static final String DEFAULT_SOURCE_FOLDER = "/src";

	/**
	 * 默认的class文件存放位置
	 */
	public static final String DEFAULT_CLASSES_FOLDER = "/target/classes";

	/**
	 * jacoco的标记
	 */
	public static final String JACOCO_MARK = "jacoco";

	/**
	 * html后缀
	 */
	public static final String HTML_SUFFIX = ".html";

	/**
	 * index.html
	 */
	public static final String INDEX_HTML_NAME = "index" + HTML_SUFFIX;

	/**
	 * index.source.html
	 */
	public static final String INDEX_SOURCE_HTML_NAME = "index.source"
	        + HTML_SUFFIX;

	/**
	 * java文件后缀
	 */
	public static final String JAVA_SUFFIX = ".java";

	/**
	 * class文件后缀
	 */
	public static final String CLASS_SUFFIX = ".class";

	/**
	 * jacoco默认的资源文件
	 */
	public static final String JACOCO_RESOURCE_FOLDER_NAME = "jacoco-resources";

	/**
	 */

	public static final String CLASSPATH_FOLDER = "classpath:";
	/**
	 * jacoco相关的默认资源路径
	 */
	public static final String CLASSPATH_STATIC_JACOCO_REPORT_FOLDER_NAME = CLASSPATH_FOLDER
	        + "static/jacocoReport/";
	/**
	 * 自定义的资源文件，用于区分变更代码
	 */
	public static final String DIFF_GIF_NAME = "diff.gif";

	public static final String DIFF_GIF_CLASSPATH = CLASSPATH_STATIC_JACOCO_REPORT_FOLDER_NAME
	        + DIFF_GIF_NAME;

	/**
	 * 修改后的report样式文件，用于覆盖原有的样式
	 */
	public static final String REPORT_CSS_NAME = "report.css";

	public static final String REPORT_CSS_CLASSPATH = CLASSPATH_STATIC_JACOCO_REPORT_FOLDER_NAME
	        + REPORT_CSS_NAME;

	/**
	 * class标签
	 */
	public static final String CLASS_TAB_MARK = " class=";

	/**
	 * 
	 */
	public static final String CLASS_TAB_MARK_REGEX = CLASS_TAB_MARK
	        + "\"([^\"]+)\"";

	/**
	 * 
	 */
	public static final String JACOCO_COVERAGE_BASE_FOLDER = "jacoco_coverage";

	/**
	 * 自定义的基础文件夹-linux
	 */

	public static final String LINUX_SELFDEFINED_BASE_FOLDER = "/home/admin/"
	        + CURRENT_APP_BASE_FOLDER + "/";
	/**
	 * jacoco的基础文件夹路径
	 */

	public static final String LINUX_BASE_FOLDER = LINUX_SELFDEFINED_BASE_FOLDER
	        + JACOCO_COVERAGE_BASE_FOLDER;

	/**
	 * 自定义的基础文件夹-windows
	 */
	public static final String WINDOWS_SELFDEFINED_BASE_FOLDER = "D:"
	        + File.separator + CURRENT_APP_BASE_FOLDER + File.separator;
	// /**
	// * 自定义的基础文件夹-windows
	// */
	// public static final String WINDOWS_SELFDEFINED_BASE_FOLDER = FileUtils
	// .getUserDirectoryPath() + File.separator + CURRENT_APP_BASE_FOLDER
	// + File.separator;
	/**
	 * jacoco的基础文件夹路径WINDOWS
	 */

	public static final String WINDOWS_BASE_FOLDER = WINDOWS_SELFDEFINED_BASE_FOLDER
	        + File.separator + JACOCO_COVERAGE_BASE_FOLDER + File.separator;

	/**
	 * MAVEN的编译指令
	 */
	public static final String MVN_COMPILE_COMMAND = "mvn clean compile";
	/**
	 * 编译命令-linux
	 */
	public static final String COMPILE_SOURCE_CODE_COMMAND_LINUX = MVN_COMPILE_COMMAND;

	/**
	 * 编译命令-windows
	 */
	public static final String COMPILE_SOURCE_CODE_COMMAND_WINDOWS = MVN_COMPILE_COMMAND;

	/**
	 * maven的install指令
	 */
	public static final String MAVEN_INSTALL_COMMAND = "mvn clean install";
	public static final String IGNORE_TEST_FAULER = " -Dmaven.test.skip=true -Dmaven.test.failure.ignore=true";
	/**
	 * install-linux
	 */
	public static final String INSTALL_SOURCE_CODE_COMMAND_LINUX = MAVEN_INSTALL_COMMAND
	        + IGNORE_TEST_FAULER;

	/**
	 * install -windows
	 */
	public static final String INSTALL_SOURCE_CODE_COMMAND_WINDOWS = MAVEN_INSTALL_COMMAND
	        + IGNORE_TEST_FAULER;

	/**
	 * compile -windows - ant
	 */
	public static final String COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_ANT = "ant -f production.build.xml compile";

	/**
	 * COMPILE -LINUX - ANT
	 */
	public static final String COMPILE_SOURCE_CODE_COMMANN_LINUX_OF_ANT = COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_ANT;

	/**
	 * jacococlient.exec文件名称
	 */
	public static final String JACOCO_EXEC_FILE_NAME_DEFAULT = "jacoco.exec";

	/**
	 * ANDROID_端JACOCO覆盖率数据的文件名称
	 */
	public static final String JACOCO_EXEC_FILE_NAME_DEFAULT_ANDROID = "coverage.ec";
	// public static final String JACOCO_EXEC_FILE_NAME_DEFAULT = "target"
	// + File.separator + "jacoco.exec";

	/**
	 * jacocoexec的文件路径
	 */
	public static final String JACOCO_EXEC_FILE_CLASSPATH = CLASSPATH_FOLDER
	        + JACOCO_EXEC_FILE_NAME_DEFAULT;

	/**
	 * 默认的pom文件
	 */
	public static final String DEFAULT_POM_FILE_NAME = "pom.xml";

	/**
	 * 默认的gradle文件名称
	 */
	public static final String DEFAULT_GRADLE_FILE_NAME = "build.gradle";

	/**
	 * 默认的build文件
	 */
	public static final String DEFAULT_BUILD_XML_FILE_NAME = "build.xml";

	/**
	 * 默认的productbuild文件
	 */
	public static final String DEFAULT_PRODUCT_BUILD_FILE_NAME = "production."
	        + DEFAULT_BUILD_XML_FILE_NAME;

	/**
	 * 默认旧版本 后缀
	 */
	public static final String DEFAULT_OLDER_FOLDER_SUFFIX = "_older";

	/**
	 * 默认新版本 后缀
	 */
	public static final String DEFAULT_NEWER_FOLDER_SUFFIX = "_newer";

	/**
	 * jacocoexec备份路径
	 */
	public static final String DEFAULT_JACOCO_EXEC_BACKUP_FOLDER = "exec_data";

	/**
	 * jacocoexec客户端文件路径
	 */
	public static final String DEFAULT_JACOCO_APP_EXEC_UPLOAD_FOLDER = "app_exec_data";
	/**
	 * 上传的class文件存放路径
	 */
	public static final String DEFAULT_CLASSES_UPLOAD_FOLDER = "classes_data";

	/**
	 * 上传的classes文件名称
	 */
	public static final String DEFAULT_CLASSES_UPLOAD_FILE_NAME = "classes.zip";

	/**
	 * 上传的classes文件名称
	 */
	public static final String DEFAULT_SRC_AND_CLASSES_UPLOAD_FILE_NAME = "srcAndClasses.zip";

	/**
	 * 上传的旧源码文件名称
	 */
	public static final String DEFAULT_SRC_OLD_UPLOAD_FILE_NAME = "srcAndClassesOld.zip";

	/**
	 * jacocoexec app执行文件默认名称
	 */
	public static final String DEFAULT_JACOCO_APP_EXEC_FILE_NAME = "coverage.ec";

	/**
	 * ant型项目默认源码路径
	 */

	public static final String DEFAULT_SOURCE_FOLDER_OF_ANT = "src";

	/**
	 * gradle型项目默认源码路径
	 */

	public static final String DEFAULT_SOURCE_FOLDER_OF_GRADLE = "src";
	/**
	 * ant型项目默认class文件路径
	 */

	public static final String DEFAULT_CLASSES_FOLDER_OF_ANT = "webroot/WEB-INF/classes";

	/**
	 * 待添加的新模块
	 */

	public static final String DEFAULT_NEW_MODULE_NAME_OF_MAVEN_PROJECT = "-coverage-report";

	/**
	 * jdk6_folder on windows
	 */
	public static final String JDK6_ON_WINDOWS = "C:\\Program Files\\Java\\jdk1.6.0_45";
	public static final String JDK8_ON_WINDOWS = "C:\\Program Files\\Java\\jdk1.8.0_161";

	/**
	 * JDK6_ON_LINUX
	 */
	public static final String JDK6_ON_LINUX = "/usr/local/jdk1.6.0_45";

	/**
	 * JDK8_ON_LINUX
	 */
	public static final String JDK8_ON_LINUX = "/usr/local/java/jdk1.8.0_171";

	public static final String CLEAN_SOURCE_CODE_OF_ANT = "ant -f production.build.xml clean";

	/**
	 * jdk_6 install on windows
	 */
	public static final String INSTALL_SOURCE_WITH_JDK6_ON_WINDOWS_OF_ANT = COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_ANT;
	/**
	 * jdk_6 install on linux
	 */
	public static final String INSTALL_SOURCE_WITH_JDK6_ON_LINUX_OF_ANT = COMPILE_SOURCE_CODE_COMMANN_LINUX_OF_ANT;

	/**
	 * jdk版本1.6
	 */
	public static final String JDK_VERSION_SIX = "1.6";
	/**
	 * jdk版本1.8
	 */
	public static final String JDK_VERSION_EIGHT = "1.8";

	/**
	 * src/test
	 */
	public static final String TEST_FILE_SOURCE_FOLDER_PATH = File.separator
	        + "src" + File.separator + "test";

	/**
	 * src/main
	 */
	public static final String SRC_MAIN_SOURCES_FOLDER_PATH = File.separator
	        + "src" + File.separator;

	/**
	 * src默认源代码的起始路径
	 */
	public static final String COMMON_SOURCE_FILES_FOLDER_START_MARK = "src";

	/**
	 * 以下是gradle相关
	 */

	public static final String COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE = "gradle compilePinganzhejiangDebugJavaWithJavac";
	public static final String INSTALL_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE = COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE;
	public static final String COMPILE_SOURCE_CODE_COMMANN_LINUX_OF_GRADLE = COMPILE_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE;
	public static final String INSTALL_SOURCE_CODE_COMMAND_LINUX_OF_GRADLE = INSTALL_SOURCE_CODE_COMMAND_WINDOWS_OF_GRADLE;
}
