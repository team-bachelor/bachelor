package cn.org.bachelor.iam.idm.exception;

import cn.org.bachelor.exception.BaseException;

/**
 * @描述 IM系统引发的错误
 * @创建时间 2019/3/21
 * @author liuzhuo
 */
public class ImSysException extends BaseException {

    /**
     * @描述 错误类型
     */
    public enum Type {
        /**
         * token过期
         */
        ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED");
        private String msg;

        Type(String msg) {
            this.msg = msg;
        }
    }

    private Type type;

    /**
     * @return 错误类型
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @param t 错误类型
     */
    public ImSysException(Type t) {
        super(t.msg);
        this.type = t;
    }

    /**
     *
     */
    public ImSysException() {
        super();
    }

    /**
     * @param message 消息或消息代码
     * @param cause 错误原因
     */
    public ImSysException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message 消息或消息代码
     */
    public ImSysException(String message) {
        super(message);
    }

    /**
     * @param cause 错误原因
     */
    public ImSysException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message 消息或消息代码
     * @param args 消息参数
     */
    public ImSysException(String message, String... args) {
        super(message);
    }
}
