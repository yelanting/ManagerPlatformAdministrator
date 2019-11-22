/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月23日 下午3:46:40
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.visitor;

import org.objectweb.asm.AnnotationVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;

public class SelfDefinedAnnotationVisitor extends AnnotationVisitor {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SelfDefinedAnnotationVisitor.class);

	public SelfDefinedAnnotationVisitor(int api) {
		super(api);
	}

	public SelfDefinedAnnotationVisitor() {
		this(ConstDefine.DEFAULT_OPCODE);
	}

	public SelfDefinedAnnotationVisitor(AnnotationVisitor mv) {
		super(ConstDefine.DEFAULT_OPCODE, mv);
	}

	@Override
	public void visit(String name, Object value) {
		super.visit(name, value);

		LOGGER.info(
		        "SelfDefinedAnnotationVisitor.visit(String name,Object value) ：name:{},value:{}",
		        name, value);
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		super.visitEnum(name, descriptor, value);
		LOGGER.info(
		        "SelfDefinedAnnotationVisitor.visitEnum(String name, String descriptor, String value): name:{},descriptor:{},value:{}",
		        name, descriptor, value);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {
		LOGGER.info(
		        "SelfDefinedAnnotationVisitor.visitAnnotation( String name,  String descriptor)： name:{},descriptor:{}",
		        name, descriptor);
		return super.visitAnnotation(name, descriptor);
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		LOGGER.info(
		        "SelfDefinedAnnotationVisitor.visitArray( String name):name:{}",
		        name);
		return super.visitArray(name);
	}

	@Override
	public void visitEnd() {
		LOGGER.info("SelfDefinedAnnotationVisitor.visit Annotation End");
		super.visitEnd();
	}
}
