// 声明当前类所在的包，用于组织和管理代码结构
package cn.org.bachelor.cache.redis.conversion;

/**
 * InvalideMapException 类继承自 RuntimeException，用于表示在处理映射相关操作时出现的无效映射异常。
 * 当遇到不符合预期的映射结构或数据时，可抛出该异常。
 *
 * @author liuzhuo
 * @since 2015/11/11
 */
// 定义一个自定义的运行时异常类，继承自 RuntimeException
public class InvalideMapException extends RuntimeException {
    /**
     * 构造一个带有指定错误消息的 InvalideMapException 实例。
     *
     * @param message 详细的错误消息，用于描述异常发生的原因
     */
    public InvalideMapException(String message) {
        // 调用父类 RuntimeException 的构造函数，传入错误消息
        super(message);
    }

    /**
     * 构造一个带有指定错误消息和原因的 InvalideMapException 实例。
     *
     * @param message 详细的错误消息，用于描述异常发生的原因
     * @param cause   导致此异常的原因（可以是另一个异常）
     */
    public InvalideMapException(String message, Throwable cause) {
        // 调用父类 RuntimeException 的构造函数，传入错误消息和异常原因
        super(message, cause);
    }
}