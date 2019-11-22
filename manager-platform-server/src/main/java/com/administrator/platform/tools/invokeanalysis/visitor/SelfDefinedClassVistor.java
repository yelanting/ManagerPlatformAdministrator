/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 上午8:40:36
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.tools.invokeanalysis.define.ConstDefine;
import com.administrator.platform.tools.invokeanalysis.util.InvokeAnalysisDealUtil;
import com.administrator.platform.tools.invokeanalysis.vo.ClassFileInfo;
import com.administrator.platform.tools.invokeanalysis.vo.FieldInfo;
import com.administrator.platform.tools.invokeanalysis.vo.MethodInfoVO;

public class SelfDefinedClassVistor extends ClassVisitor {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SelfDefinedClassVistor.class);
	private Map<String, List<MethodInfoVO>> methodInvokeRelationsOfEachMethod;
	private boolean isInterface;
	private SelfDefinedMethodVisitor thisSelfDefinedMethodVisitor;
	private List<FieldInfo> thisFieldInfos;
	private ClassFileInfo classFileInfo;

	public SelfDefinedClassVistor(int api) {
		super(api);
		this.methodInvokeRelationsOfEachMethod = new HashMap<>(16);
		this.thisFieldInfos = new ArrayList<>();
	}

	public SelfDefinedClassVistor() {
		this(ConstDefine.DEFAULT_OPCODE);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
	        String superName, String[] interfaces) {
		LOGGER.debug(
		        "====================================visit start====================================");
		LOGGER.debug("{} extends {} implements {} {", name, superName,
		        StringUtil.changeStringArrayToString(interfaces));
		LOGGER.debug(
		        "version:{},access:{},name:{},signature:{},supername:{},interfaces:{}",
		        version, access, name, signature, superName,
		        StringUtil.changeStringArrayToString(interfaces));
		this.classFileInfo = new ClassFileInfo();

		classFileInfo.setName(
		        InvokeAnalysisDealUtil.parseClassNameFromFullClassPath(name));
		classFileInfo.setPackname(
		        InvokeAnalysisDealUtil.parsePakageNameFromFullClassPath(name));
		classFileInfo.setFullName(
		        InvokeAnalysisDealUtil.dealFullClassFilePath(name));
		isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
	}

	@Override
	public void visitSource(String source, String debug) {
		LOGGER.debug("SelfDefinedClassVistor.visitSource：{} {}", source, debug);

	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		LOGGER.debug("SelfDefinedClassVistor.visitOuterClass：{} {} {}", owner,
		        name, desc);

	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		LOGGER.debug("SelfDefinedClassVistor.visitAnnotation：{} {} ", desc,
		        visible);
		AnnotationVisitor annotationVisitor = super.visitAnnotation(desc,
		        visible);
		return new SelfDefinedAnnotationVisitor(annotationVisitor);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName,
	        int access) {
		LOGGER.debug("SelfDefinedClassVistor.visitInnerClass：{} {} {} {}", name,
		        outerName, innerName, access);

	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
	        String signature, Object value) {
		LOGGER.info(
		        "======================begin to visitFeild======================");
		LOGGER.debug(
		        "visitField: access:{} name:{},desc:{},signature:{},Value:{}",
		        access, name, desc, signature, value);

		FieldVisitor fieldVisitor = super.visitField(access, name, desc,
		        signature, value);

		FieldInfo fieldInfo = new FieldInfo(name, desc, access, desc, signature,
		        value);
		thisFieldInfos.add(fieldInfo);
		LOGGER.debug(
		        "======================end to visitFeild======================");
		return new SelfDefinedFieldVisitor(fieldVisitor);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
	        String signature, String[] exceptions) {
		LOGGER.debug("******visitMethod start ******");
		LOGGER.debug("SelfDefinedClassVistor.visitMethod: {} {} {} {}  ", name,
		        desc, access, signature);

		MethodVisitor methodVisitor = super.visitMethod(access, name, desc,
		        signature, exceptions);
		SelfDefinedMethodVisitor selfDefinedMethodVisitor = null;

		String key = this.classFileInfo.getFullName() + ConstDefine.PACKAGE_SEP
		        + name + InvokeAnalysisDealUtil
		                .parserSimpleParametersFromParamDesc(desc);

		if (!isInterface && !"<init>".equals(name) && !"<clinit>".equals(name)
		        && !isGetterAndSetterMethod(name)) {
			selfDefinedMethodVisitor = new SelfDefinedMethodVisitor(
			        methodVisitor, key, this.methodInvokeRelationsOfEachMethod);

			this.thisSelfDefinedMethodVisitor = selfDefinedMethodVisitor;
		} else {
			LOGGER.debug("当前方法是构造方法，或者该类是接口，不计入");
			this.thisSelfDefinedMethodVisitor = (SelfDefinedMethodVisitor) methodVisitor;
		}
		return thisSelfDefinedMethodVisitor;
	}

	@Override
	public void visitEnd() {
		LOGGER.debug(
		        "}\n====================================visitEnd====================================");
	}

	/**
	 * 判断一个方法是不是set和get方法
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param methodName
	 * @return
	 */
	private boolean isGetterAndSetterMethod(String methodName) {

		if (thisFieldInfos.isEmpty()) {
			return false;
		}

		boolean isGetter = false;
		boolean isSetter = false;
		for (FieldInfo fieldInfo : thisFieldInfos) {
			isSetter = InvokeAnalysisDealUtil.makeGetterAndSetterOfField(
			        ConstDefine.SETTER_METHOD_PREFIX, fieldInfo.getFieldName())
			        .equals(methodName);
			isGetter = InvokeAnalysisDealUtil.makeGetterAndSetterOfField(
			        ConstDefine.GETTER_METHOD_PREFIX, fieldInfo.getFieldName())
			        .equals(methodName);
			if (isGetter || isSetter) {
				LOGGER.debug("方法:{}，是get或者set方法", methodName);
				return true;
			}
		}

		LOGGER.debug("方法:{}，不是get或者set方法", methodName);
		return false;
	}

	public Map<String, List<MethodInfoVO>> getMethodInvokeRelationsOfEachMethod() {
		return methodInvokeRelationsOfEachMethod;
	}

	public void setMethodInvokeRelationsOfEachMethod(
	        Map<String, List<MethodInfoVO>> methodInvokeRelationsOfEachMethod) {
		this.methodInvokeRelationsOfEachMethod = methodInvokeRelationsOfEachMethod;
	}

	public ClassFileInfo getClassFileInfo() {
		return classFileInfo;
	}

	public void setClassFileInfo(ClassFileInfo classFileInfo) {
		this.classFileInfo = classFileInfo;
	}

	public SelfDefinedMethodVisitor getThisSelfDefinedMethodVisitor() {
		return thisSelfDefinedMethodVisitor;
	}

	public void setThisSelfDefinedMethodVisitor(
	        SelfDefinedMethodVisitor thisSelfDefinedMethodVisitor) {
		this.thisSelfDefinedMethodVisitor = thisSelfDefinedMethodVisitor;
	}

	public List<FieldInfo> getThisFieldInfos() {
		return thisFieldInfos;
	}

	public void setThisFieldInfos(List<FieldInfo> thisFieldInfos) {
		this.thisFieldInfos = thisFieldInfos;
	}

}
