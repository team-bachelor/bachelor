package cn.org.bachelor.cache;

/**
 * 缓存异常
 * @author 刘卓
 * @since 2015/8/25
 */
public class CacheException extends RuntimeException {
    /**
     * 初始化缓存异常
     * @param t 异常原因
     */
    public CacheException(Throwable t) {
        super(t);
    }
}
