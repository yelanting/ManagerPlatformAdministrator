/**
 * @author : 孙留平
 * @since : 2018年11月23日 上午10:59:20
 * @see:
 */
package com.administrator.platform.util.define;

/**
 * @author : Administrator
 * @since : 2018年11月23日 上午10:59:20
 * @see :
 */
public enum FileSuffix {
	/**
	 * java文件后缀
	 */
	JAVA_FILE(".java"),
	/**
	 * class文件后缀
	 */
	CLASS_FILE(".class"),

	/**
	 * python文件后缀
	 */
	PYTHON_FILE(".py"),

	/**
	 * html文件后缀
	 */
	HTML_FILE(".html"),

	/**
	 * 代码覆盖率统计的.java.html后缀
	 */
	JAVA_HTML_FILE(".java.html"),

	/**
	 * pom后缀
	 */
	POM_XML_FILE("pom.xml"),

	/**
	 * zip后缀
	 */
	ZIP_FILE(".zip"),

	/**
	 * doc
	 */
	DOC_FILE(".doc"),
	/**
	 * docx
	 */
	DOCX_FILE(".docx"),

	/**
	 * ppt
	 */
	PPT_FILE(".ppt"),
	/**
	 * pptx
	 */
	PPTX_FILE(".pptx");

	private String fileType;

	private FileSuffix() {
	}

	private FileSuffix(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
