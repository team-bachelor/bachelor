package cn.org.bachelor.exception;

/**
 * 业务异常
 * 该类继承自 BaseException，用于表示业务层面出现的异常情况。
 */
public class BusinessException extends BaseException{

	// 序列化版本号，用于在序列化和反序列化过程中确保版本的兼容性
	private static final long serialVersionUID = 1L;

	// 异常参数数组，可用于传递额外的异常信息
	private String[] args = null;

	/**
	 * 无参构造函数，调用父类的无参构造函数
	 */
	public BusinessException() {
		super();
	}

	/**
	 * 带有消息和异常原因的构造函数
	 *
	 * @param message 异常消息，用于描述异常发生的具体情况
	 * @param cause 导致该异常的原始异常
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 只带有消息的构造函数
	 *
	 * @param message 异常消息，用于描述异常发生的具体情况
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * 只带有异常原因的构造函数
	 *
	 * @param cause 导致该异常的原始异常
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * 带有消息、异常原因和参数数组的构造函数
	 *
	 * @param message 异常消息，用于描述异常发生的具体情况
	 * @param cause 导致该异常的原始异常
	 * @param args 异常参数数组，可用于传递额外的异常信息
	 */
	public BusinessException(String message, Throwable cause, String... args) {
		super(message, cause);
		this.args = args;
	}

	/**
	 * 带有消息和参数数组的构造函数
	 *
	 * @param message 异常消息，用于描述异常发生的具体情况
	 * @param args 异常参数数组，可用于传递额外的异常信息
	 */
	public BusinessException(String message, String... args) {
		super(message);
		this.args = args;
	}

	/**
	 * 获取异常参数数组
	 *
	 * @return 异常参数数组
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * 设置异常参数数组
	 *
	 * @param args 要设置的异常参数数组
	 */
	protected void setArgs(String[] args) {
		this.args = args;
	}
}