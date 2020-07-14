package cn.org.bachelor.web.exception;

import cn.org.bachelor.core.exception.BusinessException;

public class InvalidParameterException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 299005487754162152L;

	public InvalidParameterException(String paramName){
		super(msg(paramName));
	}
	
	public InvalidParameterException(String[]... paramName){
		super(msg(paramName.toString()));
	}
	
	private static String msg(String paramName) {
		return String.format("Parameter [%s] can not be empty!", paramName);
	}
	
	
}
