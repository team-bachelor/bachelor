package cn.org.bachelor.exception;

/**
 * 系统异常
 * 该类继承自 BaseException，用于表示系统层面出现的异常情况。
 */
public class SystemException extends BaseException{

	// 序列化版本号，确保在序列化和反序列化过程中版本的一致性
	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数
	 * 调用父类的无参构造函数，创建一个无具体错误信息的系统异常实例。
	 * 注：此构造函数目前未被使用，可根据实际情况考虑是否保留。
	 */
	public SystemException() {
		super();
	}

	/**
	 * 带消息和异常原因的构造函数
	 *
	 * @param message 异常的详细描述信息
	 * @param cause 导致该异常的原始异常
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 只带消息的构造函数
	 *
	 * @param message 异常的详细描述信息
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * 只带异常原因的构造函数
	 *
	 * @param cause 导致该异常的原始异常
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}
}