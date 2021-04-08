package com.fl.wdl.vo;

import com.fl.wdl.constant.ResponseStatus;

public class CommonResult {
	private boolean success;
	private Integer code;
	private Object message;
	private Object data;
	
	
	private CommonResult() {}
	
	public static CommonResult buildSuccess(Object data) {
		return buildSuccess(ResponseStatus.SUCCESS.code(), ResponseStatus.SUCCESS.message(), data);
	}
	
	public static CommonResult buildSuccess(Integer code, Object message, Object data) {
		CommonResult jsonData = new CommonResult();
		jsonData.setSuccess(true);
		jsonData.setCode(code);
		jsonData.setMessage(message);
		jsonData.setData(data);
		return jsonData;
	}
	
	public static CommonResult buildError() {
		return buildError(ResponseStatus.DEFAULT_ERROR.code(), ResponseStatus.DEFAULT_ERROR.message());
	}
	
	public static CommonResult buildError(Integer code, Object message) {
		CommonResult jsonData = new CommonResult();
		jsonData.setSuccess(false);
		jsonData.setCode(code);
		jsonData.setMessage(message);
		return jsonData;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
