package com.administrator.platform.tools.jacoco.diff;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.administrator.platform.tools.jacoco.JacocoDefine;

/**
 * @author : Administrator
 * @since : 2019年5月5日 上午9:53:46
 * @see :
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
		return getCompilationUnitWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 获取AST编译单元1.6
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static CompilationUnit getCompilationUnitWithJdkSix(
			final String javaFilePath) {
		return getCompilationUnitWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 获取AST编译单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static CompilationUnit getCompilationUnitWithJdkVersion(
			final String javaFilePath, String jdkVersion) {
		byte[] input = null;
		try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(javaFilePath));) {
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final ASTParser astParser = ASTParser.newParser(AST.JLS8);
		final Map<?, ?> options = JavaCore.getOptions();

		if (jdkVersion.equals(JacocoDefine.JDK_VERSION_SIX)) {
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
		} else {
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
		}
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
		return getPackageWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 获取包名
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static String getPackageWithJdkSix(final String javaFilePath) {
		return getPackageWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 获取包名
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static String getPackageWithJdkVersion(final String javaFilePath,
			String jdkVersion) {
		final CompilationUnit cu = getCompilationUnitWithJdkVersion(
				javaFilePath, jdkVersion);
		return cu.getPackage().getName().toString();
	}

	/**
	 * 获取普通类单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static TypeDeclaration getClass(final String javaFilePath) {
		return getClassWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 获取普通类单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static TypeDeclaration getClassWithJdkVersion(
			final String javaFilePath, String jdkVersion) {
		final CompilationUnit compilationUnit = getCompilationUnitWithJdkVersion(
				javaFilePath, jdkVersion);
		final List<?> types = compilationUnit.types();
		for (final Object type : types) {
			if (type instanceof TypeDeclaration) {
				return (TypeDeclaration) type;
			}
		}
		return null;
	}

	/**
	 * 获取普通类单元
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static TypeDeclaration getClassWithJdkSix(
			final String javaFilePath) {
		return getClassWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 判断是否为普通类
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static boolean isTypeDeclaration(final String javaFilePath) {
		return isTypeDeclarationWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 判断是否为普通类1.6
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static boolean isTypeDeclarationWithJdkSix(
			final String javaFilePath) {
		return isTypeDeclarationWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 判断是否为普通类
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static boolean isTypeDeclarationWithJdkVersion(
			final String javaFilePath, String jdkVersion) {
		final CompilationUnit cu = getCompilationUnitWithJdkVersion(
				javaFilePath, jdkVersion);
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
		return getMethodsWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_EIGHT);
	}

	/**
	 * 获取方法
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static MethodDeclaration[] getMethodsWithJdkSix(
			final String javaFilePath) {
		return getMethodsWithJdkVersion(javaFilePath,
				JacocoDefine.JDK_VERSION_SIX);
	}

	/**
	 * 获取方法
	 * 
	 * @param javaFilePath
	 * @return
	 */
	public static MethodDeclaration[] getMethodsWithJdkVersion(
			final String javaFilePath, String jdkVersion) {
		final TypeDeclaration typeDec = getClassWithJdkVersion(javaFilePath,
				jdkVersion);

		if (null == typeDec) {
			return new MethodDeclaration[] {};
		}

		return typeDec.getMethods();
	}
}
