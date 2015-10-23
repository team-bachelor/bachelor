package org.bachelor.core.exception;

public class BusinessException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] args = null;
	
	
	
	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause, String... args) {
		super(message, cause);
		this.args = args;
	}

	public BusinessException(String message, String... args) {
		super(message);
		this.args = args;
	}
	
	public String[] getArgs() {
		return args;
	}

	protected void setArgs(String[] args) {
		this.args = args;
	}


}
