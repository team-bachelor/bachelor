package cn.org.bachelor.iam.acm.exception;

import cn.org.bachelor.core.exception.BaseException;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/3/21
 */
public class UserSysException extends BaseException {

    public enum Type {
        ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED");
        private String msg;

        Type(String msg) {
            this.msg = msg;
        }
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public UserSysException(Type t) {
        super(t.msg);
        this.type = t;
    }

    public UserSysException() {
        super();
    }

    public UserSysException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSysException(String message) {
        super(message);
    }

    public UserSysException(Throwable cause) {
        super(cause);
    }

    public UserSysException(String message, String... args) {
        super(message);
    }
}
