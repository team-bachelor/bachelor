package cn.org.bachelor.iam;

import cn.org.bachelor.iam.idm.exception.ImSysException;
import cn.org.bachelor.web.json.ResponseStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.org.bachelor.web.exception.GlobalExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * @描述 全局异常处理
 * @创建时间 2018/11/9
 * @author liuzhuo
 */
@RestControllerAdvice
@Order(-1)
public class IamExceptionHandler {
    private static Log logger = LogFactory.getLog(IamExceptionHandler.class);

    @ExceptionHandler(value = ImSysException.class)
    public ResponseEntity handleImSysException(HttpServletRequest request, Exception e) {
        logger.info(e);
        if ("ACCESS_TOKEN_EXPIRED".equals(e.getMessage())) {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, ResponseStatus.BIZ_ERR, HttpStatus.LOCKED);
        } else {
            return GlobalExceptionHandler.createExceptionResponseEntity(e.getMessage(), null, ResponseStatus.BIZ_ERR, HttpStatus.UNAUTHORIZED);
        }
    }
}
