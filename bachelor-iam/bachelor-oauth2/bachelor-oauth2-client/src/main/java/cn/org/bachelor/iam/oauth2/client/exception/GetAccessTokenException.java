package cn.org.bachelor.iam.oauth2.client.exception;

import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;

/**
 * Created by team bachelor on 15/8/3.
 */
public class GetAccessTokenException extends OAuthBusinessException {

    public GetAccessTokenException(Throwable cause) {
        super(cause);
    }

	public GetAccessTokenException(String msg) {
		super(msg);
	}
}
