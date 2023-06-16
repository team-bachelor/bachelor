package cn.org.bachelor.iam.idm.login;

import cn.org.bachelor.exception.BusinessException;

public class LoginException extends BusinessException {
    public LoginException(String msg) {
        super(msg);
    }
    public LoginException(String msg, Throwable e) {
        super(msg, e);
    }
}
