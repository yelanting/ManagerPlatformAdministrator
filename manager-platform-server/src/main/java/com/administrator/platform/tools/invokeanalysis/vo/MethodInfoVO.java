/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 下午6:34:34
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.vo;

import org.jacoco.core.diff.MethodInfo;

public class MethodInfoVO extends MethodInfo {

	private String simpleParameter;
	private String fullSignature;
	private String parentRequestMapping;
	private String requestMapping;
	private String fullRequestMapping;

	public String getSimpleParameter() {
		return simpleParameter;
	}

	public void setSimpleParameter(String simpleParameter) {
		this.simpleParameter = simpleParameter;
	}

	public String getFullSignature() {
		return fullSignature;
	}

	public void setFullSignature(String fullSignature) {
		this.fullSignature = fullSignature;
	}

	@Override
	public String toString() {
		return "MethodInfoVO [simpleParameter=" + simpleParameter
		        + ", fullSignature=" + fullSignature + ", parentRequestMapping="
		        + parentRequestMapping + ", requestMapping=" + requestMapping
		        + ", fullRequestMapping=" + fullRequestMapping + ", classFile="
		        + classFile + ", className=" + className + ", packages="
		        + packages + ", md5=" + md5 + ", methodName=" + methodName
		        + ", parameters=" + parameters + "]";
	}

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	public String getParentRequestMapping() {
		return parentRequestMapping;
	}

	public void setParentRequestMapping(String parentRequestMapping) {
		this.parentRequestMapping = parentRequestMapping;
	}

	public String getFullRequestMapping() {
		return fullRequestMapping;
	}

	public void setFullRequestMapping(String fullRequestMapping) {
		this.fullRequestMapping = fullRequestMapping;
	}
}
