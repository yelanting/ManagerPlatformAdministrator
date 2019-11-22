/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月23日 上午9:20:26
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.vo;

public class FieldInfo {
	private String fieldName;
	private String fieldType;
	private int fieldAccess;
	private String desc;
	private String signature;
	private Object value;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getFieldAccess() {
		return fieldAccess;
	}

	public void setFieldAccess(int fieldAccess) {
		this.fieldAccess = fieldAccess;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FieldInfo [fieldName=" + fieldName + ", fieldType=" + fieldType
		        + ", fieldAccess=" + fieldAccess + ", desc=" + desc
		        + ", signature=" + signature + ", value=" + value + "]";
	}

	public FieldInfo(String fieldName, String fieldType, int fieldAccess,
	        String desc, String signature, Object value) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldAccess = fieldAccess;
		this.desc = desc;
		this.signature = signature;
		this.value = value;
	}

	public FieldInfo() {
		super();
	}
}
