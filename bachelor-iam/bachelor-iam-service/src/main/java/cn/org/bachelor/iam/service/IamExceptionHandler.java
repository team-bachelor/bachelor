package cn.org.bachelor.iam.service;

import cn.org.bachelor.iam.exception.IamSystemException;
import cn.org.bachelor.web.exception.GlobalExceptionHandler;
import cn.org.bachelor.web.json.ResponseStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuzhuo
 * @描述 全局异常处理
 * @创建时间 2018/11/9
 */
@RestControllerAdvice
@Order(-1)
public class IamExceptionHandler {
    private static Log logger = LogFactory.getLog(IamExceptionHandler.class);

    @ExceptionHandler(value = IamSystemException.class)
    public ResponseEntity handleIamSysException(HttpServletRequest request, Exception e) {
        logger.info(e);
        if ("ACCESS_TOKEN_EXPIRED".equals(e.getMessage())) {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, ResponseStatus.BIZ_ERR, HttpStatus.LOCKED);
        } else {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, ResponseStatus.BIZ_ERR, HttpStatus.UNAUTHORIZED);
        }
    }
}
