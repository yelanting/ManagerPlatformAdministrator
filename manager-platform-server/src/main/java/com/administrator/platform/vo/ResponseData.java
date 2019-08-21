package com.administrator.platform.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @see 响应实体
 * @author Administrator
 * @since 2018年8月8日 12:53:36
 */
@ApiModel("返回响应数据")
public class ResponseData {
	@ApiModelProperty(value = "操作成功与否")
	private Boolean success = true;
	@ApiModelProperty(value = "响应状态码，0表示成功")
	private int code = 0;
	@ApiModelProperty(value = "返回响应消息内容")
	private String msg;
	@ApiModelProperty(value = "返回的响应数据")
	private Object data;

	@ApiModelProperty(value = "返回的数据数量")
	private int rows = 0;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseData [success=" + success + ", code=" + code + ", msg="
		        + msg + ", data=" + data + "]";
	}

	public ResponseData() {

	}

	private ResponseData(Boolean success, int code, String msg, Object data,
	        int rows) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.rows = rows;
	}

	public static ResponseData getSuccessResult() {
		return new ResponseData(Boolean.TRUE, 0, "", null, 0);
	}

	public static ResponseData getSuccessResult(Object data) {
		return new ResponseData(Boolean.TRUE, 0, "", data, 0);
	}

	public static ResponseData getSuccessResult(Object data, String message) {
		return new ResponseData(Boolean.TRUE, 0, message, data, 0);
	}

	public static ResponseData getSuccessResult(String message) {
		return new ResponseData(Boolean.TRUE, 0, message, null, 0);
	}

	public static ResponseData getSuccessResult(Object data, int size) {
		return new ResponseData(Boolean.TRUE, 0, "", data, size);
	}

	public static ResponseData getSuccessResult(List data) {
		return new ResponseData(Boolean.TRUE, 0, "", data, data.size());
	}
}
