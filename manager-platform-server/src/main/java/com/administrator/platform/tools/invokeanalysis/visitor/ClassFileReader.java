/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 上午9:15:59
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.visitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;
import com.administrator.platform.tools.invokeanalysis.define.GlobalInvoke;
import com.administrator.platform.tools.invokeanalysis.vo.MethodInfoVO;

public class ClassFileReader {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ClassFileReader.class);

	private SelfDefinedClassVistor classVisitor;
	private File classFile;

	/**
	 * 方法调用关系
	 */

	Map<String, List<MethodInfoVO>> methodInvokeList;

	public ClassFileReader(String classFile) {
		this(classFile, new SelfDefinedClassVistor());

	}

	public ClassFileReader(String classFile, SelfDefinedClassVistor classVisitor) {
		this.classFile = new File(classFile);
		this.classVisitor = classVisitor;
		this.methodInvokeList = new HashMap<>(16);
	}

	public void visit() {
		try {
			LOGGER.info(
			        "====================================start to read class:{}",
			        this.classFile.getAbsolutePath());
			ClassReader classReader = new ClassReader(
			        new FileInputStream(this.classFile));
			classReader.accept(this.classVisitor, 0);

			String classInfoKey = classReader.getClassName().replace("/",
			        "" + ConstDefine.PACKAGE_SEP);
			/**
			 * 全局调用关系中加入此类和此类的方法调用关系
			 */
			if (!this.classVisitor.getMethodInvokeRelationsOfEachMethod()
			        .isEmpty()) {
				GlobalInvoke.ALL_INVOKE_RELATION_SHIP.put(classInfoKey,
				        this.classVisitor
				                .getMethodInvokeRelationsOfEachMethod());
			}

			LOGGER.info(
			        "====================================read class over:{}",
			        this.classFile.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error("class文件读取异常,异常信息:{}", e.getMessage());
		}
	}
}
