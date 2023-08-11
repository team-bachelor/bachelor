package org.bachelor.auth.interceptor;

import org.bachelor.core.exception.SystemException;

public class FuncAuthFailedException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FuncAuthFailedException() {
		super();
	}

	public FuncAuthFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FuncAuthFailedException(String message) {
		super(message);
	}

	public FuncAuthFailedException(Throwable cause) {
		super(cause);
	}
}
