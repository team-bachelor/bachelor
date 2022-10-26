package cn.org.bachelor.cache.redis.conversion;

/**
 * Created by liuzhuo on 2015/11/11.
 */
public class InvalideMapException extends RuntimeException {
    public InvalideMapException(String message) {
        super(message);
    }

    public InvalideMapException(String message, Throwable cause) {
        super(message, cause);
    }
}
