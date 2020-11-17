package cn.org.bachelor.up.oauth2.exception;

/**
 * Created by team bachelor on 15/8/3.
 */
public class GetAccessTokenException extends RuntimeException {

    public GetAccessTokenException(Throwable cause) {
        super(cause);
    }

	public GetAccessTokenException(String msg) {
		super(msg);
	}
}
