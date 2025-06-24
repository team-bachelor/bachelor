package cn.org.bachelor.web.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 该类用于封装异步请求的返回值，是一个泛型类，可处理不同类型的数据内容。
 *
 * @param <T> 数据内容的类型
 * @author lz
 */
@ApiModel("异步请求返回值")
public class JsonResponse<T> {

    /**
     * 返回结果的状态
     */
    @ApiModelProperty(value = "返回结果的状态", position = 0)
    private ResponseStatus status;

    /**
     * 结果代码
     */
    @ApiModelProperty(value = "结果代码", position = 1)
    private String code;

    /**
     * 结果消息
     */
    @ApiModelProperty(value = "结果消息", position = 2)
    private String msg;

    /**
     * 结果数据
     */
    @ApiModelProperty(value = "结果数据", position = 3)
    private T data;

    /**
     * 返回值生成的时间戳，使用 final 修饰可确保其值在初始化后不可变
     */
    @ApiModelProperty(value = "返回值生成的时间戳", position = 4)
    private final Long time;

    /**
     * 无参构造函数，初始化时间戳为当前时间
     */
    public JsonResponse() {
        time = System.currentTimeMillis();
    }

    /**
     * 带数据的构造函数，调用无参构造函数初始化时间戳，并设置结果数据
     *
     * @param data 结果数据
     */
    public JsonResponse(T data) {
        this();
        this.data = data;
    }

    /**
     * 带数据和消息的构造函数，调用带数据的构造函数，并设置结果消息
     *
     * @param data 结果数据
     * @param msg 结果消息
     */
    public JsonResponse(T data, String msg) {
        this(data);
        this.msg = msg;
    }

    /**
     * 带数据、代码、消息和状态的构造函数，调用带数据和消息的构造函数，并设置结果代码和状态
     *
     * @param data 结果数据
     * @param code 结果代码
     * @param msg 结果消息
     * @param status 返回结果的状态
     */
    public JsonResponse(T data, String code, String msg, ResponseStatus status) {
        this(data, msg);
        this.setCode(code);
        this.setStatus(status);
    }

    /**
     * 获取返回结果的状态
     * 注意：此方法未被使用，可考虑移除
     *
     * @return 返回结果的状态
     */
    public ResponseStatus getStatus() {
        return status;
    }

    /**
     * 设置返回结果的状态
     *
     * @param status 返回结果的状态
     */
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    /**
     * 获取结果消息
     *
     * @return 结果消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置结果消息
     *
     * @param msg 结果消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取结果数据
     *
     * @return 结果数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置结果数据
     *
     * @param data 结果数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取返回值生成的时间戳
     * 注意：此方法未被使用，可考虑移除
     *
     * @return 返回值生成的时间戳
     */
    public Long getTime() {
        return time;
    }

    /**
     * 获取结果代码
     *
     * @return 结果代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置结果代码，若代码为 "null" 则将其置为 null
     *
     * @param code 结果代码
     */
    public void setCode(String code) {
        if ("null".equals(code)) code = null;
        this.code = code;
    }

    /**
     * 根据返回的数据创建一个包含共通字段的 ResponseEntity
     *
     * @param data 返回的数据
     * @param <K>  返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data) {
        return createHttpEntity(data, HttpStatus.OK);
    }

    /**
     * 根据返回的数据和消息创建一个包含共通字段的 ResponseEntity
     *
     * @param data 返回的数据
     * @param msg  返回的消息
     * @param <K>  返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg) {
        return createHttpEntity(data, msg, HttpStatus.OK);
    }

    /**
     * 根据返回的数据和状态创建一个包含共通字段的 ResponseEntity
     *
     * @param data   返回的数据
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, HttpStatus status) {
        return createHttpEntity(data, null, status);
    }

    /**
     * 根据返回的数据、消息和状态创建一个包含共通字段的 ResponseEntity
     *
     * @param data   返回的数据
     * @param msg    返回的消息
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntity(K data, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data, msg);
        setJsonResponseStatus(response, status);
        // 返回包含 JsonResponse 的 ResponseEntity
        return new ResponseEntity<>(response, status);
    }

    /**
     * 根据返回的数据创建一个包含共通字段的精确类型的 ResponseEntity
     * 注意：此方法未被使用，可考虑移除
     *
     * @param data 返回的数据
     * @param <K>  返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse> createHttpEntityPrecise(K data) {
        return createHttpEntityPrecise(data, HttpStatus.OK);
    }

    /**
     * 根据返回的数据和消息创建一个包含共通字段的精确类型的 ResponseEntity
     * 注意：此方法未被使用，可考虑移除
     *
     * @param data 返回的数据
     * @param msg  返回的消息
     * @param <K>  返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity createHttpEntityPrecise(K data, String msg) {
        return createHttpEntityPrecise(data, msg, HttpStatus.OK);
    }

    /**
     * 根据返回的数据和状态创建一个包含共通字段的精确类型的 ResponseEntity
     * 注意：此方法未被使用，可考虑移除
     *
     * @param data   返回的数据
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity createHttpEntityPrecise(K data, HttpStatus status) {
        return createHttpEntityPrecise(data, null, status);
    }

    /**
     * 根据返回的数据、消息和状态创建一个包含共通字段的精确类型的 ResponseEntity
     * 注意：此方法未被使用，可考虑移除
     *
     * @param data   返回的数据
     * @param msg    返回的消息
     * @param status 返回的状态
     * @param <K>    返回数据的类型
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static <K> ResponseEntity<JsonResponse>  createHttpEntityPrecise(K data, String msg, HttpStatus status) {
        JsonResponse response = new JsonResponse<K>(data, msg);
        setJsonResponseStatus(response, status);
        return new ResponseEntity(response, status);
    }

    /*****************************************************************************/

    /**
     * 根据给定的 HTTP 状态创建一个包含共通字段的 ResponseEntity
     *
     * @param status 返回的状态
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static ResponseEntity<JsonResponse> createHttpEntity(HttpStatus status) {
        // 创建一个 JsonResponse 对象
        JsonResponse response = new JsonResponse();
        // 设置 JsonResponse 的状态
        setJsonResponseStatus(response, status);
        // 返回包含 JsonResponse 的 ResponseEntity
        return new ResponseEntity<JsonResponse>(response, status);
    }

    /**
     * 指定返回编码，消息提示，状态码，创建一个包含共通字段的 ResponseEntity
     *
     * @param code   返回的代码
     * @param msg    返回的消息
     * @param status 返回的状态
     * @return 封装为 ResponseEntity，包含了共通字段的返回值
     */
    public static ResponseEntity<JsonResponse> createHttpEntity(String code, String msg, HttpStatus status) {
        // 创建一个 JsonResponse 对象
        JsonResponse response = new JsonResponse();
        // 设置结果代码
        response.code = code;
        // 设置结果消息
        response.msg = msg;
        // 设置 JsonResponse 的状态
        setJsonResponseStatus(response, status);
        // 返回包含 JsonResponse 的 ResponseEntity
        return new ResponseEntity<JsonResponse>(response, status);
    }

    /**
     * 根据 HTTP 状态设置 JsonResponse 的状态
     *
     * @param response 要设置状态的 JsonResponse 对象
     * @param status   HTTP 状态
     * @return 设置好状态的 JsonResponse 对象
     */
    private static JsonResponse setJsonResponseStatus(JsonResponse response, HttpStatus status) {
        // 如果状态码是 2xx 成功状态或者 3xx 重定向状态
        if (status.is2xxSuccessful() || status.is3xxRedirection()) {
            // 设置响应状态为 OK
            response.setStatus(ResponseStatus.OK);
            // 如果状态码是 4xx 客户端错误状态
        } else if (status.is4xxClientError()) {
            // 设置响应状态为业务错误
            response.setStatus(ResponseStatus.BIZ_ERR);
            // 如果状态码是 5xx 服务器错误状态
        } else if (status.is5xxServerError()) {
            // 设置响应状态为系统错误
            response.setStatus(ResponseStatus.SYS_ERR);
        }
        // 返回设置好状态的 JsonResponse 对象
        return response;
    }
}