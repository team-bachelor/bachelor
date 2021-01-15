package cn.org.bachelor.usermanager.utils;

 
public class ResponseEntiy<T> {

	public static final String SUCCESS = "200";
	 
	String result = SUCCESS;

	 
	String message;

	 
	long total;

	 
	T rows;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T data) {
		this.rows = data;
	}

}
