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

import org.eclipse.jdt.core.dom.MethodDeclaration;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AST对比
 * 
 * @author angrytester
 *
 */
public class DiffAST {
	/**
	 * 用于装载对比结果
	 */
	public static List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
	/**
	 * 文件列表
	 */
	public static List<File> list = new ArrayList<File>();
	/**
	 * 路径分隔符
	 */
	public static final String SEPARATOR = System.getProperty("file.separator");

	/**
	 * @param ntag
	 *            当前tag
	 * @param otag
	 *            对比tag
	 * 
	 * @return
	 */
	public static List<MethodInfo> diffDir(final String ntag,
			final String otag) {// src1是整个工程中有变更的文件,src2是历史版本全量文件,都是相对路径,例如在当前工作空间下生成tag1和tag2
		final String pwd = new File(System.getProperty("user.dir"))
				.getAbsolutePath();// 同级目录
		final String parent = new File(System.getProperty("user.dir")).getParent();
		final String tag1Path = pwd;
		final String tag2Path = parent + SEPARATOR + otag;
		final List<File> files1 = getFileList(tag1Path);
		for (final File f : files1) {
			// 非普通类不处理
			if (!ASTGeneratror.isTypeDeclaration(f.getAbsolutePath())) {
				continue;
			}
			final File f2 = new File(
					tag2Path + f.getAbsolutePath().replace(tag1Path, ""));
			diffFile(f.toString(), f2.toString());
		}
		return methodInfos;
	}

	/**
	 * @param baseDir 与当前项目空间同级的历史版本代码路径
	 * @return
	 */
	public static List<MethodInfo> diffBaseDir(final String baseDir) {
		final String pwd = new File(System.getProperty("user.dir"))
				.getAbsolutePath();// 同级目录
		final String parent = new File(System.getProperty("user.dir")).getParent();
		final String tag1Path = pwd;
		final String tag2Path = parent + SEPARATOR + baseDir;
		final List<File> files1 = getFileList(tag1Path);
		for (final File f : files1) {
			// 非普通类不处理
			if (!ASTGeneratror.isTypeDeclaration(f.getAbsolutePath())) {
				continue;
			}
			final File f2 = new File(
					tag2Path + f.getAbsolutePath().replace(tag1Path, ""));
			diffFile(f.toString(), f2.toString());
		}
		return methodInfos;
	}

	/**
	 * 对比文件
	 * 
	 * @param nfile
	 * @param ofile
	 * @return
	 */
	public static List<MethodInfo> diffFile(final String nfile,
			final String ofile) {
		final MethodDeclaration[] methods1 = ASTGeneratror.getMethods(nfile);
		if (!new File(ofile).exists()) {
			for (final MethodDeclaration method : methods1) {
				final MethodInfo methodInfo = methodToMethodInfo(nfile, method);
				methodInfos.add(methodInfo);
			}
		} else {
			final MethodDeclaration[] methods2 = ASTGeneratror
					.getMethods(ofile);
			final Map<String, MethodDeclaration> methodsMap = new HashMap<String, MethodDeclaration>();
			for (int i = 0; i < methods2.length; i++) {
				methodsMap.put(
						methods2[i].getName().toString()
								+ methods2[i].parameters().toString(),
						methods2[i]);
			}
			for (final MethodDeclaration method : methods1) {
				// 如果方法名是新增的,则直接将方法加入List
				if (!isMethodExist(method, methodsMap)) {
					final MethodInfo methodInfo = methodToMethodInfo(nfile,
							method);
					methodInfos.add(methodInfo);
				} else {
					// 如果两个版本都有这个方法,则根据MD5判断方法是否一致
					if (!isMethodTheSame(method,
							methodsMap.get(method.getName().toString()
									+ method.parameters().toString()))) {
						final MethodInfo methodInfo = methodToMethodInfo(nfile,
								method);
						methodInfos.add(methodInfo);
					}
				}
			}
		}
		return methodInfos;
	}

	/**
	 * 判斷方法是否存在
	 * 
	 * @param method
	 * @param methodsMap
	 * @return
	 */
	public static boolean isMethodExist(final MethodDeclaration method,
			final Map<String, MethodDeclaration> methodsMap) {
		// 方法名+参数一致才一致
		if (!methodsMap.containsKey(
				method.getName().toString() + method.parameters().toString())) {
			return false;
		}
		return true;
	}

	/**
	 * 判斷方法是否一致
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	public static boolean isMethodTheSame(final MethodDeclaration method1,
			final MethodDeclaration method2) {
		if (MD5Encode(method1.toString())
				.equals(MD5Encode(method2.toString()))) {
			return true;
		}
		return false;
	}

	/**
	 * @param file
	 * @param method
	 * @return
	 */
	public static MethodInfo methodToMethodInfo(final String file,
			final MethodDeclaration method) {
		final MethodInfo methodInfo = new MethodInfo();
		methodInfo.setClassFile(file);
		methodInfo.setClassName(
				ASTGeneratror.getClass(file).getName().toString());
		methodInfo.setPackages(ASTGeneratror.getPackage(file));
		methodInfo.setMd5(MD5Encode(method.toString()));
		methodInfo.setMethodName(method.getName().toString());
		methodInfo.setParameters(method.parameters().toString());
		return methodInfo;
	}

	/**
	 * 根据路径获取文件列表
	 * 
	 * @param src
	 * @return
	 */
	public static List<File> getFileList(final String src) {
		final File dir = new File(src);
		final File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				final String fileName = files[i].getName();
				final String filePath = files[i].getAbsolutePath();
				final String mainPath = "src" + SEPARATOR + "main" + SEPARATOR
						+ "java";
				if (files[i].isDirectory()) {
					getFileList(files[i].getAbsolutePath());
				} else if (fileName.endsWith("java")
						&& filePath.contains(mainPath)) {
					list.add(files[i]);
				} else {
					continue;
				}
			}
		}
		return list;
	}


	/**
	 * MD5
	 *
	 * @param s 待MD5的字符串
	 * @return
	 */
	public static String MD5Encode(String s) {
		String MD5String = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			MD5String = base64en.encode(md5.digest(s.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return MD5String;
	}

}
