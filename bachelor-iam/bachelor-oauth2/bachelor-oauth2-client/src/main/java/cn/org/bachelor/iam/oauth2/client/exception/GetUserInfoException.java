package cn.org.bachelor.iam.oauth2.client.exception;

import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;

/**
 * Created by team bachelor on 15/8/3.
 */
public class GetUserInfoException extends OAuthBusinessException {
    public GetUserInfoException(Throwable cause){
        super(cause);
    }
}
