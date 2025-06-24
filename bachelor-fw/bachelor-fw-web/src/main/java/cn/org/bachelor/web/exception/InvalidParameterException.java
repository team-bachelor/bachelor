package cn.org.bachelor.web.exception;

import cn.org.bachelor.exception.BusinessException;

/**
 * InvalidParameterException 类用于表示参数无效的业务异常。
 * 该类继承自 BusinessException，用于在参数不符合预期时抛出异常。
 *
 * @author liuzhuo
 * @since 2018/11/5
 */
public class InvalidParameterException extends BusinessException {

    /**
     * 序列化版本号，用于在反序列化时确保类的版本兼容性。
     */
    private static final long serialVersionUID = 299005487754162152L;

    /**
     * 构造一个包含指定参数名称的无效参数异常。
     *
     * @param paramName 无效参数的名称
     */
    public InvalidParameterException(String paramName) {
        // 调用父类构造函数，传入异常代码和参数名称
        super("INVALID_PARAMETER", paramName);
    }

    /**
     * 构造一个包含多个参数名称数组的无效参数异常。
     *
     * @param paramName 包含多个参数名称的可变参数数组
     */
    public InvalidParameterException(String[]... paramName) {
        // 调用父类构造函数，传入异常代码和参数名称数组的字符串表示
        // 这里原代码存在问题，使用 paramName.toString() 会导致输出数组的引用信息，而不是数组元素内容
        // 可以考虑使用 Arrays.deepToString(paramName) 来正确输出数组元素内容
        super("INVALID_PARAMETER", paramName == null ? "" : paramName.toString());
    }
}