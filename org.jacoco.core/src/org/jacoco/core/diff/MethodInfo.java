/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Evgeny Mandrikov - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.core.diff;

/**
 * @author agrytester
 *
 */
public class MethodInfo {
	/**
	 * java文件
	 */
	public String classFile;
	/**
	 * 类名
	 */
	public String className;
	/**
	 * 包名
	 */
	public String packages;
	/**
	 * 方法的md5
	 */
	public String md5;
	/**
	 * 方法名
	 */
	public String methodName;
	/**
	 * 方法参数
	 */
	public String parameters;

	/**
	 * @return
	 */
	public String getClassFile() {
		return classFile;
	}

	/**
	 * @param classFile
	 */
	public void setClassFile(final String classFile) {
		this.classFile = classFile;
	}

	/**
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 */
	public void setClassName(final String className) {
		this.className = className;
	}

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public String getPackages() {
		return packages;
	}

	/**
	 * @param packages
	 */
	public void setPackages(final String packages) {
		this.packages = packages;
	}

	/**
	 * @return
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * @param md5
	 */
	public void setMd5(final String md5) {
		this.md5 = md5;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 */
	public void setMethodName(final String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 */
	public void setParameters(final String parameters) {
		this.parameters = parameters;
	}

}