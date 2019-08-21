package com.administrator.platform.core.base.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.jacoco.JacocoDefine;
import com.administrator.platform.uiautotest.config.BrowserDefine;
import com.administrator.platform.uiautotest.config.BrowserDefine.BrowserTypes;
import com.administrator.platform.util.define.OperationSystemEnum;
import com.administrator.platform.util.define.OperationSystemMark;

/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:21:00
 * @see:
 */
public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	public static String getCurrentProjectRootPath() {
		String path = System.getProperty("user.dir");
		logger.debug("当前项目根路径为:{}", path);
		return path;
	}

	/**
	 * @see 获取driver路径
	 * @since 2018年6月9日 23:18:24
	 */
	public static String getDriverPath(BrowserTypes browserType) {
		// 默认是chrome
		String driverName = null;

		switch (browserType) {
			case CHROME:
				driverName = "chromedriver";
				break;
			case IE:
				driverName = "IEDriverServer";
				break;
			case FIREFOX:
				driverName = "geckodriver";
				break;
			default:
				logger.error("浏览器类型不是常用的，所以我默认设置成chrome");
				driverName = "chromedriver";
				break;
		}

		String wholeDriverFilePath = Util.getCurrentProjectRootPath()
				+ BrowserDefine.CURRENT_PROJECT_NEEDED_SOURCE_FILE_PATH
				+ driverName + ".exe";
		logger.debug(wholeDriverFilePath);
		return wholeDriverFilePath;
	}

	/**
	 * @see 获取driver的配置属性
	 * @since 2018年6月9日 23:18:24
	 */
	public static String getDriverProperty(BrowserTypes browserType) {
		// 默认是chrome
		String driverProperty = null;

		switch (browserType) {
			case CHROME:
				driverProperty = "chrome";
				break;
			case IE:
				driverProperty = "IEDriverServer";
				break;
			case FIREFOX:
				driverProperty = "gecko";
				break;
			default:
				logger.error("浏览器类型不是常用的，所以我默认设置成chrome");
				driverProperty = "chrome";
				break;
		}

		String driverPropertyName = "webdriver." + driverProperty + ".driver";
		logger.debug(driverPropertyName);
		return driverPropertyName;
	}

	/**
	 * @see 获取driver实例
	 * @since 2018年6月9日 23:18:24
	 */
	public static WebDriver getDriverInstance(BrowserTypes browserType) {
		initDriverProperty(browserType);

		// 默认是chrome
		switch (browserType) {
			case CHROME:
				return new ChromeDriver();
			case IE:
				return new InternetExplorerDriver();
			case FIREFOX:
				return new FirefoxDriver();
			default:
				return new ChromeDriver();
		}
	}

	/**
	 * @see 等待时间
	 * @param args
	 * @throws InterruptedException
	 */
	public static void waitForTime(Long seconds) throws InterruptedException {
		logger.debug("等待时间:{}秒", seconds);
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			logger.error("等待时间失败，异常:{}", e.getMessage());
			throw e;
		}
	}

	/**
	 * @see 设置property
	 * @param args
	 */

	public static void initDriverProperty(BrowserTypes browserType) {
		System.setProperty(Util.getDriverProperty(browserType),
				Util.getDriverPath(browserType));
	}

	/**
	 * 打印list
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param args
	 */
	public static void displayListInfo(List<? extends Object> objectList) {
		if (objectList.isEmpty()) {
			logger.info("objectList是空的");
		}
		for (Object object : objectList) {
			logger.debug(object.toString());
		}
	}

	/**
	 * 获取当前操作系统
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @return
	 */
	public static String getCurrentPlatform() {
		Properties properties = System.getProperties();
		return properties.getProperty("os.name");
	}

	/**
	 * 获取当前操作系统类型
	 * 
	 * @see :
	 * @param :
	 * @return : OperationSystemEnum
	 * @return
	 */
	public static OperationSystemEnum getCurrentOperationSystem() {
		String currentOs = getCurrentPlatform();
		if ((currentOs.toLowerCase()
				.indexOf(OperationSystemMark.WINDOWS_SYSTEM)) != -1) {
			return OperationSystemEnum.WINDOWS;
		} else if ((currentOs.toLowerCase()
				.indexOf(OperationSystemMark.LINUX_SYSTEM) != -1)) {
			return OperationSystemEnum.LINUX;
		} else {
			return OperationSystemEnum.LINUX;
		}
	}

	/**
	 * 从数组中找出对应 元素的索引
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param stringArray
	 * @param toFindItem
	 * @return
	 */
	public static int getThusItemIndexFromStringArray(String[] stringArray,
			String toFindItem) {
		int arrayLength = stringArray.length;
		if (arrayLength == 0) {
			return -1;
		}

		if (null == toFindItem) {
			return -1;
		}

		for (int i = 0; i < arrayLength; i++) {
			if (stringArray[i].equals(toFindItem)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 重置jdk
	 */
	public static void resetJdkVersionAndPath() {

		boolean isWindows = Util
				.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS;
		String jdkEightHomePath = isWindows ? JacocoDefine.JDK8_ON_WINDOWS
				: JacocoDefine.JDK8_ON_LINUX;
	}

	/**
	 * 修改jdk版本到1.6
	 */
	public static void setJdkVersionToSix() {
		boolean isWindows = Util
				.getCurrentOperationSystem() == OperationSystemEnum.WINDOWS;

		String jdkSixHomePath = isWindows ? JacocoDefine.JDK6_ON_WINDOWS
				: JacocoDefine.JDK6_ON_LINUX;

		String path = getPathVariable();

		String pathOnWindows = jdkSixHomePath + File.separator + "bin;" + path;
		String pathOnLinux = jdkSixHomePath + File.separator + "bin:" + path;
		String setPathVariable = isWindows ? pathOnWindows : pathOnLinux;
		System.setProperty("java.home", jdkSixHomePath);
		System.setProperty("Path", setPathVariable);
	}

	/**
	 * 获取path
	 */
	public static String getPathVariable() {
		return System.getenv("Path");
	}
}
