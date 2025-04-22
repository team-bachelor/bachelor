package cn.org.bachelor.iam.exception;

// 导入自定义的业务异常类
import cn.org.bachelor.exception.BusinessException;
// 导入自定义的字符串工具类
import cn.org.bachelor.iam.utils.StringUtils;
// 导入 Java 集合框架中的 HashMap 类，用于存储键值对
import java.util.HashMap;
// 导入 Java 集合框架中的 Map 接口，用于表示键值对映射
import java.util.Map;

/**
 * IamBusinessException 类继承自 BusinessException，用于表示 IAM 相关的业务异常。
 * 该类提供了一系列方法来设置和获取异常的详细信息，如错误信息、描述、URI、状态等。
 *
 * @author team bachelor
 * @since 15/5/20
 */
public class IamBusinessException extends BusinessException {
    // 异常的错误信息
    private String error;
    // 异常的详细描述
    private String description;
    // 异常相关的 URI
    private String uri;
    // 异常的状态信息
    private String state;
    // 异常的作用域信息
    private String scope;
    // 异常的重定向 URI
    private String redirectUri;
    // 异常的响应状态码
    private int responseStatus;
    // 异常的参数信息，使用 Map 存储键值对
    private Map<String, String> parameters;

    /**
     * 构造函数，接收一个 Throwable 类型的参数，用于包装其他异常。
     *
     * @param cause 引发此异常的原因
     */
    public IamBusinessException(Throwable cause) {
        // 调用父类的构造函数，传入异常原因
        super(cause);
    }

    /**
     * 构造函数，接收一个字符串类型的错误信息。
     *
     * @param error 异常的错误信息
     */
    public IamBusinessException(String error) {
        // 调用另一个构造函数，传入错误信息和空描述
        this(error, "");
    }

    /**
     * 构造函数，接收错误信息和详细描述。
     *
     * @param error       异常的错误信息
     * @param description 异常的详细描述
     */
    public IamBusinessException(String error, String description) {
        // 调用父类的构造函数，传入错误信息和描述的组合
        super(error + " " + description);
        // 初始化 parameters 为一个新的 HashMap
        this.parameters = new HashMap<>();
        // 设置异常的详细描述
        this.description = description;
        // 设置异常的错误信息
        this.error = error;
    }

    // 注释掉的静态方法，可能用于创建 OAuthBusinessException 实例
    //    public static OAuthBusinessException error(String error) {
    //        return new OAuthBusinessException(error);
    //    }
    //
    //    public static OAuthBusinessException error(String error, String description) {
    //        return new OAuthBusinessException(error, description);
    //    }

    /**
     * 设置异常的详细描述，并返回当前异常实例，支持链式调用。
     *
     * @param description 异常的详细描述
     * @return 当前异常实例
     */
    public IamBusinessException description(String description) {
        // 设置异常的详细描述
        this.description = description;
        // 返回当前异常实例
        return this;
    }

    /**
     * 设置异常相关的 URI，并返回当前异常实例，支持链式调用。
     *
     * @param uri 异常相关的 URI
     * @return 当前异常实例
     */
    public IamBusinessException uri(String uri) {
        // 设置异常相关的 URI
        this.uri = uri;
        // 返回当前异常实例
        return this;
    }

    /**
     * 设置异常的状态信息，并返回当前异常实例，支持链式调用。
     *
     * @param state 异常的状态信息
     * @return 当前异常实例
     */
    public IamBusinessException state(String state) {
        // 设置异常的状态信息
        this.state = state;
        // 返回当前异常实例
        return this;
    }

    /**
     * 设置异常的作用域信息，并返回当前异常实例，支持链式调用。
     *
     * @param scope 异常的作用域信息
     * @return 当前异常实例
     */
    public IamBusinessException scope(String scope) {
        // 设置异常的作用域信息
        this.scope = scope;
        // 返回当前异常实例
        return this;
    }

    /**
     * 设置异常的响应状态码，并返回当前异常实例，支持链式调用。
     *
     * @param responseStatus 异常的响应状态码
     * @return 当前异常实例
     */
    public IamBusinessException responseStatus(int responseStatus) {
        // 设置异常的响应状态码
        this.responseStatus = responseStatus;
        // 返回当前异常实例
        return this;
    }

    /**
     * 设置异常的参数信息，并返回当前异常实例，支持链式调用。
     *
     * @param name  参数的名称
     * @param value 参数的值
     * @return 当前异常实例
     */
    public IamBusinessException setParameter(String name, String value) {
        // 将参数的名称和值存入 parameters 中
        this.parameters.put(name, value);
        // 返回当前异常实例
        return this;
    }

    /**
     * 获取异常的错误信息。
     *
     * @return 异常的错误信息
     */
    public String getError() {
        // 返回异常的错误信息
        return this.error;
    }

    /**
     * 获取异常的详细描述。
     *
     * @return 异常的详细描述
     */
    public String getDescription() {
        // 返回异常的详细描述
        return this.description;
    }

    /**
     * 获取异常相关的 URI。
     *
     * @return 异常相关的 URI
     */
    public String getUri() {
        // 返回异常相关的 URI
        return this.uri;
    }

    /**
     * 获取异常的状态信息。
     *
     * @return 异常的状态信息
     */
    public String getState() {
        // 返回异常的状态信息
        return this.state;
    }

    /**
     * 获取异常的作用域信息。
     *
     * @return 异常的作用域信息
     */
    public String getScope() {
        // 返回异常的作用域信息
        return this.scope;
    }

    /**
     * 获取异常的响应状态码，如果未设置则默认为 400。
     *
     * @return 异常的响应状态码
     */
    public int getResponseStatus() {
        // 如果 responseStatus 为 0，则返回 400，否则返回 responseStatus
        return this.responseStatus == 0 ? 400 : this.responseStatus;
    }

    /**
     * 根据参数名称获取异常的参数值。
     *
     * @param name 参数的名称
     * @return 参数的值
     */
    public String get(String name) {
        // 从 parameters 中获取参数的值
        return (String) this.parameters.get(name);
    }

    /**
     * 获取异常的参数信息。
     *
     * @return 异常的参数信息，以 Map 形式返回
     */
    public Map<String, String> getParameters() {
        // 返回异常的参数信息
        return this.parameters;
    }

    /**
     * 获取异常的重定向 URI。
     *
     * @return 异常的重定向 URI
     */
    public String getRedirectUri() {
        // 返回异常的重定向 URI
        return this.redirectUri;
    }

    /**
     * 设置异常的重定向 URI。
     *
     * @param redirectUri 异常的重定向 URI
     */
    public void setRedirectUri(String redirectUri) {
        // 设置异常的重定向 URI
        this.redirectUri = redirectUri;
    }

    /**
     * 获取异常的消息，包含错误信息、描述、URI、状态和作用域等。
     *
     * @return 异常的消息
     */
    public String getMessage() {
        // 创建一个 StringBuilder 用于拼接消息
        StringBuilder b = new StringBuilder();
        // 如果错误信息不为空，则添加到 StringBuilder 中
        if (!StringUtils.isEmpty(this.error)) {
            b.append(this.error);
        }
        // 如果描述信息不为空，则添加到 StringBuilder 中，并在前面添加逗号和空格
        if (!StringUtils.isEmpty(this.description)) {
            b.append(", ").append(this.description);
        }
        // 如果 URI 信息不为空，则添加到 StringBuilder 中，并在前面添加逗号和空格
        if (!StringUtils.isEmpty(this.uri)){
            b.append(", ").append(this.uri);
        }

        // 检查异常的状态信息是否不为空
        if (!StringUtils.isEmpty(this.state)) {
            // 如果不为空，将状态信息添加到异常消息中，前面添加逗号和空格
            b.append(", ").append(this.state);
        }

        // 检查异常的作用域信息是否不为空
        if (!StringUtils.isEmpty(this.scope)) {
            // 如果不为空，将作用域信息添加到异常消息中，前面添加逗号和空格
            b.append(", ").append(this.scope);
        }

        // 将 StringBuilder 中的内容转换为字符串并返回
        return b.toString();
    }

    /**
     * 返回该异常对象的字符串表示形式，包含类名以及异常的详细信息。
     *
     * @return 包含类名和异常详细信息的字符串
     */
    public String toString() {
        // 拼接类名和异常的详细信息，以字符串形式返回
        return this.getClass().getName() + "{error='" + this.error + "', description='" + this.description + "', uri='" + this.uri + "', state='" + this.state + "', scope='" + this.scope + "', redirectUri='" + this.redirectUri + "', responseStatus=" + this.responseStatus + ", parameters=" + this.parameters + "}";
    }
}
