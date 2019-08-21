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

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author angrytester
 *
 */
public class ASTGeneratror {
	/**
	 * 获取AST编译单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static CompilationUnit getCompilationUnit(
			final String javaFilePath) {
		byte[] input = null;
		try {
			final BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(javaFilePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final ASTParser astParser = ASTParser.newParser(AST.JLS8);
		final Map<?, ?> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
		astParser.setCompilerOptions(options);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setResolveBindings(true);
		astParser.setBindingsRecovery(true);
		astParser.setStatementsRecovery(true);
		astParser.setSource(new String(input).toCharArray());
		final CompilationUnit compilationUnit = (CompilationUnit) astParser
				.createAST(null);
		return compilationUnit;
	}

	/**
	 * 获取包名
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static String getPackage(final String javaFilePath) {
		final CompilationUnit cu = getCompilationUnit(javaFilePath);
		return cu.getPackage().getName().toString();
	}

	/**
	 * 获取普通类单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static TypeDeclaration getClass(final String javaFilePath) {
		final CompilationUnit cu = getCompilationUnit(javaFilePath);
		final List<?> types = cu.types();
		for (final Object type : types) {
			if (type instanceof TypeDeclaration) {
				return (TypeDeclaration) type;
			}
		}
		return null;
	}

	/**
	 * 判断是否为普通类
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static boolean isTypeDeclaration(final String javaFilePath) {
		final CompilationUnit cu = getCompilationUnit(javaFilePath);
		final List<?> types = cu.types();
		for (final Object type : types) {
			if (type instanceof TypeDeclaration) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取方法
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static MethodDeclaration[] getMethods(final String javaFilePath) {
		final TypeDeclaration typeDec = getClass(javaFilePath);
		final MethodDeclaration methodDec[] = typeDec.getMethods();
		return methodDec;
	}
}
