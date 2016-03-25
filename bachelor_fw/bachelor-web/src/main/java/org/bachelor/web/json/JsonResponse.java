package org.bachelor.web.json;

import org.bachelor.dao.vo.PageVo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class JsonResponse<T> {
	
	private ResponseStatus status;
	private String code;
	private String msg;
	private T data;
	private Long time;
	private PageVo page;
	
	public JsonResponse(){
		time = new Date().getTime();
	}
	
	public JsonResponse(T data){
		this.time = new Date().getTime();
		this.data = data;
	}
	
	public JsonResponse(T data, String msg){
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
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public Long getTime() {
		return time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PageVo getPage() {
		return page;
	}

	public void setPage(PageVo page) {
		this.page = page;
	}

	public static <K> HttpEntity<JsonResponse> createHttpEntity(K data){
		return new HttpEntity<JsonResponse>(new JsonResponse<K>(data));
	}

	public static <K> HttpEntity<JsonResponse> createHttpEntity(K data, String msg){
		return new HttpEntity<JsonResponse>(new JsonResponse<K>(data, msg));
	}

	public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, HttpStatus status){
		return new ResponseEntity<JsonResponse>(new JsonResponse<K>(data), status);
	}

	public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg, HttpStatus status){
		return new ResponseEntity<JsonResponse>(new JsonResponse<K>(data, msg), status);
	}

	public static ResponseEntity<JsonResponse> createHttpEntity(HttpStatus status){
		return new ResponseEntity<JsonResponse>(new JsonResponse(), status);
	}
}
