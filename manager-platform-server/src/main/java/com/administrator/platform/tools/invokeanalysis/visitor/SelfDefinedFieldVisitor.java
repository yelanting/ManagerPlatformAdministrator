/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月23日 上午9:22:55
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.visitor;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;
import com.administrator.platform.tools.invokeanalysis.vo.FieldInfo;

public class SelfDefinedFieldVisitor extends FieldVisitor {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SelfDefinedFieldVisitor.class);

	private List<FieldInfo> fieldInfos;

	public SelfDefinedFieldVisitor(int api) {
		super(api);
		this.fieldInfos = new ArrayList<>();
	}

	public SelfDefinedFieldVisitor() {
		this(ConstDefine.DEFAULT_OPCODE);
	}

	public SelfDefinedFieldVisitor(FieldVisitor fieldVisitor) {
		super(ConstDefine.DEFAULT_OPCODE, fieldVisitor);
		this.fieldInfos = new ArrayList<>();
	}

	public List<FieldInfo> getFieldInfos() {
		return fieldInfos;
	}

	public void setFieldInfos(List<FieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

		LOGGER.debug("visitAnnotation: desc:{},visible:{}", desc, visible);
		return super.visitAnnotation(desc, visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath,
	        String desc, boolean visible) {

		LOGGER.debug(
		        "visitTypeAnnotation: desc:{},visible:{},typeRef:{},typePath:{}",
		        desc, visible, typeRef, typePath);
		return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		LOGGER.debug("visitAttribute: attribute:{}", attribute);
		super.visitAttribute(attribute);
	}

	@Override
	public void visitEnd() {
		LOGGER.debug("visitField End---");
		super.visitEnd();
	}
}
