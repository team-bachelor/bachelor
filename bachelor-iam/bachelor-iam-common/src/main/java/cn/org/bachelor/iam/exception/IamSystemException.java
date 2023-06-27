package cn.org.bachelor.iam.exception;

/**
 * Created by team bachelor on 15/5/20.
 */
public class IamSystemException extends RuntimeException {
    public IamSystemException() {
    }

    public IamSystemException(String s) {
        super(s);
    }

    public IamSystemException(Throwable throwable) {
        super(throwable);
    }

    public IamSystemException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
