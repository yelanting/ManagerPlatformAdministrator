/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月27日 下午4:32:27
 * @see: 从java文件中解析请求
 */
package com.administrator.platform.tools.javaparser;

public class RequestMappingInfo {
	private String packageName;
	private String className;
	private String requestMethodType;
	private String parentMapping;
	private String thisMapping;
	private String fullMapping;
	private String parameters;
	private String thisMethodName;
	private String methodSignature;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRequestMethodType() {
		return requestMethodType;
	}

	public void setRequestMethodType(String requestMethodType) {
		this.requestMethodType = requestMethodType;
	}

	public String getParentMapping() {
		return parentMapping;
	}

	public void setParentMapping(String parentMapping) {
		this.parentMapping = parentMapping;
	}

	public String getThisMapping() {
		return thisMapping;
	}

	public void setThisMapping(String thisMapping) {
		this.thisMapping = thisMapping;
	}

	public String getFullMapping() {
		return fullMapping;
	}

	public void setFullMapping(String fullMapping) {
		this.fullMapping = fullMapping;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getThisMethodName() {
		return thisMethodName;
	}

	public void setThisMethodName(String thisMethodName) {
		this.thisMethodName = thisMethodName;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	@Override
	public String toString() {
		return "RequestMappingInfo [packageName=" + packageName + ", className="
		        + className + ", requestMethodType=" + requestMethodType
		        + ", parentMapping=" + parentMapping + ", thisMapping="
		        + thisMapping + ", fullMapping=" + fullMapping + ", parameters="
		        + parameters + ", thisMethodName=" + thisMethodName
		        + ", methodSignature=" + methodSignature + "]";
	}
}
