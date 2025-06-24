package cn.org.bachelor.iam.exception;

/**
 * 该类 `IamSystemException` 继承自 `RuntimeException`，用于表示 IAM 系统相关的异常。
 * 它提供了多种构造函数，方便在不同场景下抛出异常。
 *
 * @author team bachelor
 * @since 15/5/20
 */
public class IamSystemException extends RuntimeException {
    /**
     * 无参构造函数，创建一个空的 IamSystemException 实例。
     * 此构造函数目前未被使用，若后续无使用计划，可考虑移除。
     */
    public IamSystemException() {
    }

    /**
     * 带消息的构造函数，创建一个包含指定错误消息的 IamSystemException 实例。
     *
     * @param s 异常的错误消息，用于描述异常发生的原因。
     */
    public IamSystemException(String s) {
        // 调用父类 RuntimeException 的构造函数，传递错误消息
        super(s);
    }

    /**
     * 带 Throwable 的构造函数，创建一个由另一个异常引发的 IamSystemException 实例。
     * 此构造函数目前未被使用，若后续无使用计划，可考虑移除。
     *
     * @param throwable 引发此异常的原始异常。
     */
    public IamSystemException(Throwable throwable) {
        // 调用父类 RuntimeException 的构造函数，传递原始异常
        super(throwable);
    }

    /**
     * 带消息和 Throwable 的构造函数，创建一个包含指定错误消息且由另一个异常引发的 IamSystemException 实例。
     * 此构造函数目前未被使用，若后续无使用计划，可考虑移除。
     *
     * @param s 异常的错误消息，用于描述异常发生的原因。
     * @param throwable 引发此异常的原始异常。
     */
    public IamSystemException(String s, Throwable throwable) {
        // 调用父类 RuntimeException 的构造函数，传递错误消息和原始异常
        super(s, throwable);
    }
}