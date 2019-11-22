/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 上午10:46:39
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;
import com.administrator.platform.tools.invokeanalysis.util.InvokeAnalysisDealUtil;
import com.administrator.platform.tools.invokeanalysis.vo.MethodInfoVO;

public class SelfDefinedMethodVisitor extends MethodVisitor {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SelfDefinedMethodVisitor.class);
	private Map<String, List<MethodInfoVO>> methodInvokeRelationsOfEachMethod;
	private List<MethodInfoVO> methodInfos;
	private String methodKey;

	public SelfDefinedMethodVisitor(MethodVisitor mv) {
		super(ConstDefine.DEFAULT_OPCODE, mv);
		LOGGER.debug("重新构造了！！！！");
		this.methodInfos = new ArrayList<>();
	}

	public SelfDefinedMethodVisitor(MethodVisitor mv, final String key,
	        final Map<String, List<MethodInfoVO>> methodInvokeRelationsOfEachMethod) {
		super(ConstDefine.DEFAULT_OPCODE, mv);
		LOGGER.debug("重新构造了！！！！");
		this.methodInfos = new ArrayList<>();
		this.methodKey = key;
		this.methodInvokeRelationsOfEachMethod = methodInvokeRelationsOfEachMethod;
	}

	@Override
	public void visitInsn(int opcode) {
		super.visitInsn(opcode);
		LOGGER.debug("SelfDefinedMethodVisitor.visitInsn:,opcode :{} ", opcode);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		super.visitIincInsn(var, increment);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitIincInsn:,var :{} , increment:{}",
		        var, increment);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitAnnotation,desc:{} ,visible:{}",
		        desc, visible);
		return super.visitAnnotation(desc, visible);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		LOGGER.debug("SelfDefinedMethodVisitor.visitAnnotationDefault");
		return super.visitAnnotationDefault();
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		super.visitAttribute(attribute);
		LOGGER.debug("SelfDefinedMethodVisitor.visitAttribute: attr:{}",
		        attribute);
	}

	@Override
	public void visitCode() {
		super.visitCode();
		LOGGER.debug("SelfDefinedMethodVisitor.visitCode");
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
	        String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitFieldInsn:,owner:{} ,name :{} ,desc:{} ,opcode:{}",
		        owner, name, desc, opcode);
	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
	        Object[] stack) {
		super.visitFrame(type, nLocal, local, nStack, stack);
		LOGGER.debug("SelfDefinedMethodVisitor.visitFrame");
	}

	@Override
	public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath,
	        String desc, boolean visible) {
		LOGGER.debug(
		        "visitInsnAnnotation:,typeRef:{},typePath:{},desc:{},visible:{}",
		        typeRef, typePath, desc, visible);
		return super.visitInsnAnnotation(typeRef, typePath, desc, visible);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		super.visitIntInsn(opcode, operand);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitIntInsn:,opcode:{},operand:{}",
		        opcode, operand);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String desc,
	        Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		LOGGER.debug("SelfDefinedMethodVisitor.visitInvokeDynamicInsn");
		super.visitInvokeDynamicInsn(name, desc, bootstrapMethodHandle,
		        bootstrapMethodArguments);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		super.visitJumpInsn(opcode, label);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitJumpInsn: opcode:{},lebel:{} ",
		        opcode, label);
	}

	@Override
	public void visitLabel(Label label) {
		super.visitLabel(label);
		LOGGER.debug("SelfDefinedMethodVisitor.visitLabel: lebel:{} ", label);
	}

	@Override
	public void visitLdcInsn(Object value) {

		super.visitLdcInsn(value);
		LOGGER.debug("SelfDefinedMethodVisitor.visitLdcInsn: value:{} ", value);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		super.visitLineNumber(line, start);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitLineNumber: line:{} ,start:{}",
		        line, start);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
	        Label start, Label end, int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
		LOGGER.debug(
		        "visitLocalVariable: name:{},desc:{},signature:{},start:{},end:{},index:{}",
		        name, desc, signature, start, end, index);
	}

	@Override
	public AnnotationVisitor visitLocalVariableAnnotation(int typeRef,
	        TypePath typePath, Label[] start, Label[] end, int[] index,
	        String desc, boolean visible) {
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitLocalVariableAnnotation: desc:{},visible:{}",
		        desc, visible);
		return super.visitLocalVariableAnnotation(typeRef, typePath, start, end,
		        index, desc, visible);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		super.visitLookupSwitchInsn(dflt, keys, labels);
		LOGGER.debug("SelfDefinedMethodVisitor.visitLookupSwitchInsn");
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(maxStack, maxLocals);
		LOGGER.debug("SelfDefinedMethodVisitor.visitMaxs");
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
	        String desc, boolean isInterface) {
		super.visitMethodInsn(opcode, owner, name, desc, isInterface);
		LOGGER.debug(
		        "visitMethodInsn：opcode:{},owner:{},name:{},desc:{},isInterface:{}",
		        opcode, owner, name, desc, isInterface);

		boolean ownerNotEmptyAndNotJavaSdkToolAndNotConstructorAndIsCompanyJavaClass = StringUtil
		        .isStringAvaliable(owner) && !owner.startsWith("java")
		        && !InvokeAnalysisDealUtil.isConstructorMethod(name)
		        && (owner.startsWith("com/tianque")
		                || owner.startsWith("com.tianque"));
		if (ownerNotEmptyAndNotJavaSdkToolAndNotConstructorAndIsCompanyJavaClass) {
			MethodInfoVO methodInfo = new MethodInfoVO();
			methodInfo.setClassName(InvokeAnalysisDealUtil
			        .parseClassNameFromFullClassPath(owner));
			methodInfo.setClassFile(
			        InvokeAnalysisDealUtil.dealFullClassFilePath(owner));
			methodInfo.setMethodName(name);
			methodInfo.setParameters(
			        InvokeAnalysisDealUtil.parseParametersFromDesc(desc));
			String simpleParameters = InvokeAnalysisDealUtil
			        .parserSimpleParametersFromParamDesc(desc);
			methodInfo.setSimpleParameter(simpleParameters);
			methodInfo.setFullSignature(name + simpleParameters);
			this.methodInfos.add(methodInfo);
		}
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		super.visitMultiANewArrayInsn(descriptor, numDimensions);
		LOGGER.debug("SelfDefinedMethodVisitor.visitMultiANewArrayInsn：desc:{}",
		        descriptor);
	}

	@Override

	public void visitParameter(String name, int access) {
		super.visitParameter(name, access);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitParameter: name:{},access:{}",
		        name, access);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
	        String desc, boolean visible) {
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitParameterAnnotation：parameter:{},desc:{},visible:{}",
		        parameter, desc, visible);
		return super.visitParameterAnnotation(parameter, desc, visible);

	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt,
	        Label... labels) {
		super.visitTableSwitchInsn(min, max, dflt, labels);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitTableSwitchInsn:min:{},max:{},dflt:{},labels:{}",
		        min, max, dflt, labels);
	}

	@Override
	public AnnotationVisitor visitTryCatchAnnotation(int typeRef,
	        TypePath typePath, String desc, boolean visible) {
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitTryCatchAnnotation: desc:{},visible:{}",
		        desc, visible);
		return super.visitTryCatchAnnotation(typeRef, typePath, desc, visible);

	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler,
	        String type) {
		super.visitTryCatchBlock(start, end, handler, type);
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitTryCatchBlock:start:{},end:{},handler:{},type:{}",
		        start, end, handler, type);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath,
	        String desc, boolean visible) {
		LOGGER.debug(
		        "SelfDefinedMethodVisitor.visitTypeAnnotation: desc:{},visible:{}",
		        desc, visible);
		return super.visitTypeAnnotation(typeRef, typePath, desc, visible);

	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		LOGGER.debug("SelfDefinedMethodVisitor.visitTypeInsn:opcode:{},type:{}",
		        opcode, type);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		LOGGER.debug("SelfDefinedMethodVisitor.visitVarInsn: opcode:{},var:{}",
		        opcode, var);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		LOGGER.debug("总共有:{}个方法调用", this.methodInfos.size());

		if (null != this.methodInvokeRelationsOfEachMethod
		        && StringUtil.isStringAvaliable(this.methodKey)) {
			this.methodInvokeRelationsOfEachMethod.put(this.methodKey,
			        this.methodInfos);
		}

		LOGGER.debug("==============visit Method End===========");
	}

	public List<MethodInfoVO> getMethodInfos() {
		return methodInfos;
	}

	public void setMethodInfos(List<MethodInfoVO> methodInfos) {
		this.methodInfos = methodInfos;
	}
}
