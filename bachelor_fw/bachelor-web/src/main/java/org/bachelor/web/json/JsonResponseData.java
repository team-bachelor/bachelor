package org.bachelor.web.json;

import java.util.Date;

public class JsonResponseData {
	
	private ResponseStatus status;
	private String errCode;
	private String msg;
	private Object data;
	private Long time;
	
	public JsonResponseData(){
		time = new Date().getTime();
	}
	
	public JsonResponseData(Object data){
		this.time = new Date().getTime();
		this.data = data;
	}
	
	public JsonResponseData(Object data, String msg){
		this.time = new Date().getTime();
		this.data = data;
		this.msg = msg;
	}
	
	public ResponseStatus getStatus() {
		return status;
	}
	
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Long getTime() {
		return time;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
