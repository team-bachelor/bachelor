package cn.org.bachelor.cache;

/**
 * 缓存异常
 * @author 刘卓
 * @since 2015/8/25
 */
public class NotSupportInClusterException extends RuntimeException {
    /**
     * 初始化缓存异常
     * @param msg 异常原因
     */
    public NotSupportInClusterException(String msg) {
        super(msg);
    }
}
