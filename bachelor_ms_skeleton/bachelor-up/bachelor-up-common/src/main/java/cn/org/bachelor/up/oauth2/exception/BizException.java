/**
 * 
 */
package cn.org.bachelor.up.oauth2.exception;


/**
 * 鎵�湁涓氬姟寮傚父鐨勫熀绫�
 * @author tangchao
 *
 */
public class BizException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String error="SYSTEM_ERROR";
	protected String errorCode="500"; //閿欒浠ｇ爜
	protected String errorDescription="";//閿欒鎻忚堪
    
    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription==null?this.getMessage():errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public BizException(){
    }
	
	public BizException(String errorDescription){
    	super(errorDescription);
        this.errorDescription = errorDescription;
    }
	
	public BizException(Throwable e){
    	super(e);
    	this.errorDescription=e.getMessage();
    }
	
	public BizException(String errorDescription,Throwable e){
    	super(errorDescription,e);
        this.errorDescription = errorDescription;
    }
    
    public BizException(String error,String errorCode,String errorDescription){
    	super(errorDescription);
        this.errorCode=errorCode;
        this.error=error;
        this.errorDescription=errorDescription;
    }
	
	/**
	 * 閿欒淇℃伅鐨刯son鏍煎紡
	 * @return
	 */
	public String json(){
		StringBuilder sb=new StringBuilder();
		sb.append("{\"errorCode\":\"").append(this.errorCode).append("\",\"error\":\"").append(error);
		sb.append("\",\"errorDescription\":\"").append(getErrorMessge(this)).append("\"}");
		return sb.toString();
	}
	
	public static String getErrorMessge(Throwable e){
		String error="";
		if(e instanceof BizException){
			error=((BizException) e).getErrorDescription();
			error=error==null?e.toString():error;
		}else{
			error=e.getMessage()==null?e.toString():e.getMessage();
		}
		error=error.replaceAll("'", "\\\\\'");
		error=error.replaceAll("\"", "\\\\\"");
		error=error.replaceAll("\r\n", "");
		error=error.replaceAll("\n", "");
		return error;
	}
}
