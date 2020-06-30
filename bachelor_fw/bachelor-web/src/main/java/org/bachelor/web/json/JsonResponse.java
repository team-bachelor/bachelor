package org.bachelor.web.json;

import org.bachelor.context.vo.PageVo;
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

    public JsonResponse() {
        time = new Date().getTime();
    }

    public JsonResponse(T data) {
        this();
        this.data = data;
    }

    public JsonResponse(T data, String msg) {
        this(data);
        this.msg = msg;
    }
    public JsonResponse(T data, String code, String msg, ResponseStatus status){
        this(data, msg);
        this.setCode(code);
        this.setStatus(status);
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

    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data) {
        return createHttpEntity(data,HttpStatus.OK);
    }

    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg) {
        return createHttpEntity(data,msg,HttpStatus.OK);
    }

    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data);
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data, msg);
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    public static ResponseEntity<JsonResponse> createHttpEntity(HttpStatus status) {
        JsonResponse response = new JsonResponse();
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    /**
     * 指定返回编码，消息提示，状态码
     * @param code
     * @param msg
     * @param status
     * @return
     */
    public static ResponseEntity<JsonResponse> createHttpEntity(String code, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse();
        response.code = code;
        response.msg = msg;
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    private static JsonResponse setJsonResponseStatus(JsonResponse response, HttpStatus status) {
        int s = status.value();
        if (s < 300){
            response.setStatus(ResponseStatus.OK);
        }else if(s < 500){
            response.setStatus(ResponseStatus.BIZ_ERR);
        }else if(s < 600){
            response.setStatus(ResponseStatus.SYS_ERR);
        }
        return response;
    }
}
