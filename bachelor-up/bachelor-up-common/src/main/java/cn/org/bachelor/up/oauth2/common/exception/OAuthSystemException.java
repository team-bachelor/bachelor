package cn.org.bachelor.up.oauth2.common.exception;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthSystemException extends Exception {
    public OAuthSystemException() {
    }

    public OAuthSystemException(String s) {
        super(s);
    }

    public OAuthSystemException(Throwable throwable) {
        super(throwable);
    }

    public OAuthSystemException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
