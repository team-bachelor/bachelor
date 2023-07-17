package cn.org.bachelor.web.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param <T> 数据内容的类型
 * @author lz
 */
@ApiModel("异步请求返回值")
public class JsonResponse<T> {

    @ApiModelProperty(value = "返回结果的状态", position = 0)
    private ResponseStatus status;
    @ApiModelProperty(value = "结果代码", position = 1)
    private String code;
    @ApiModelProperty(value = "结果消息", position = 2)
    private String msg;
    @ApiModelProperty(value = "结果数据", position = 3)
    private T data;
    @ApiModelProperty(value = "返回值生成的时间戳", position = 4)
    private Long time;

    public JsonResponse() {
        time = System.currentTimeMillis();
    }

    public JsonResponse(T data) {
        this();
        this.data = data;
    }

    public JsonResponse(T data, String msg) {
        this(data);
        this.msg = msg;
    }

    public JsonResponse(T data, String code, String msg, ResponseStatus status) {
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
        if ("null".equals(code)) code = null;
        this.code = code;

    }

    /**
     * @param data 返回的数据
     * @param <K>  返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data) {
        return createHttpEntity(data, HttpStatus.OK);
    }

    /**
     * @param data 返回的数据
     * @param msg  返回的消息
     * @param <K>  返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg) {
        return createHttpEntity(data, msg, HttpStatus.OK);
    }

    /**
     * @param data   返回的数据
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, HttpStatus status) {
        return createHttpEntity(data, null, status);
    }

    /**
     * @param data   返回的数据
     * @param msg    返回的消息
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data, msg);
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    /*****************************************************************************/
    /**
     * @param data 返回的数据
     * @param <K>  返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse<K>> createHttpEntityPrecise(K data) {
        return createHttpEntityPrecise(data, HttpStatus.OK);
    }

    /**
     * @param data 返回的数据
     * @param msg  返回的消息
     * @param <K>  返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse<K>> createHttpEntityPrecise(K data, String msg) {
        return createHttpEntityPrecise(data, msg, HttpStatus.OK);
    }

    /**
     * @param data   返回的数据
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse<K>> createHttpEntityPrecise(K data, HttpStatus status) {
        return createHttpEntityPrecise(data, null, status);
    }

    /**
     * @param data   返回的数据
     * @param msg    返回的消息
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse<K>> createHttpEntityPrecise(K data, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data, msg);
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse<K>>(response, status);
    }

    /*****************************************************************************/

    /**
     * @param status 返回的状态
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static ResponseEntity<JsonResponse> createHttpEntity(HttpStatus status) {
        JsonResponse response = new JsonResponse();
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    /**
     * 指定返回编码，消息提示，状态码
     *
     * @param code   返回的代码
     * @param msg    返回的消息
     * @param status 返回的状态
     * @return 封装为ResponseEntity，包含了共通字段的返回值
     */
    public static ResponseEntity<JsonResponse> createHttpEntity(String code, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse();
        response.code = code;
        response.msg = msg;
        setJsonResponseStatus(response, status);
        return new ResponseEntity<JsonResponse>(response, status);
    }

    private static JsonResponse setJsonResponseStatus(JsonResponse response, HttpStatus status) {
        if (status.is2xxSuccessful() || status.is3xxRedirection()) {
            response.setStatus(ResponseStatus.OK);
        } else if (status.is4xxClientError()) {
            response.setStatus(ResponseStatus.BIZ_ERR);
        } else if (status.is5xxServerError()) {
            response.setStatus(ResponseStatus.SYS_ERR);
        }
        return response;
    }
}
