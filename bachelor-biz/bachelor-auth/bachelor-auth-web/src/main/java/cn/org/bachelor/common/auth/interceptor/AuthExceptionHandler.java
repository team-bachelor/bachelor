package cn.org.bachelor.common.auth.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.org.bachelor.common.auth.exception.UserSysException;
import cn.org.bachelor.web.exception.GlobalExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR;
import static org.springframework.http.HttpStatus.LOCKED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 * 全局异常处理
 */
@RestControllerAdvice
@Order(-1)
public class AuthExceptionHandler {
    private static Log logger = LogFactory.getLog(AuthExceptionHandler.class);

    @ExceptionHandler(value = UserSysException.class)
    public ResponseEntity handleUserSysException(HttpServletRequest request, Exception e) {
        logger.info(e);
        if ("ACCESS_TOKEN_EXPIRED".equals(e.getMessage())) {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, BIZ_ERR, LOCKED);
        } else {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, BIZ_ERR, UNAUTHORIZED);
        }
    }
}
