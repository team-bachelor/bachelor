package cn.org.bachelor.web.exception;

import cn.org.bachelor.core.exception.BusinessException;

public class InvalidParameterException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 299005487754162152L;

	public InvalidParameterException(String paramName){
		super("INVALID_PARAMETER",paramName);
	}
	
	public InvalidParameterException(String[]... paramName){
		super("INVALID_PARAMETER", paramName.toString());
	}
}
