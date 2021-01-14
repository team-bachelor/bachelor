package cn.org.bachelor.web.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.core.exception.RemoteException;
import cn.org.bachelor.core.exception.SystemException;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import cn.org.bachelor.web.util.MessageUtil;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR;
import static cn.org.bachelor.web.json.ResponseStatus.SYS_ERR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 * 全局异常处理
 */
@RestControllerAdvice(annotations = {ExceptionHandle.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(body, status);
    }

    @ExceptionHandler(value = RemoteException.class)
    public ResponseEntity handleRemoteException(HttpServletRequest request, Exception e) throws Exception {
        logger.debug(e);
        RemoteException re = (RemoteException) e;
        // 远程异常
        Throwable cause = e.getCause();
        if (cause != null && cause instanceof BusinessException) {
            return handleBusinessException(request, (BusinessException) cause);
        } else {
            return handleSystemException(request, new SystemException(re));
        }
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity handleBusinessException(HttpServletRequest request, Exception e) throws Exception {
        logger.debug(e);
        BusinessException be = (BusinessException) e;
        return createExceptionResponseEntity(e.getMessage(), be.getArgs(), BIZ_ERR, BAD_REQUEST);
    }

    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity handleSystemException(HttpServletRequest request, Exception e) throws Exception {
        logger.error(e);
        return createExceptionResponseEntity(e.getMessage(), null, SYS_ERR, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(HttpServletRequest request, Throwable e) throws Exception {
        logger.error(e);
        return createExceptionResponseEntity("UNEXPECT_SYSTEM_EXCEPTION", null, SYS_ERR, INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity createExceptionResponseEntity(String code, String[] args, ResponseStatus rs, HttpStatus hs) {
        String msg = code;
        try {
            if (code != null && !"".equals(code)) {
                msg = MessageUtil.getMessage(code, args);
            }
        } catch (NoSuchMessageException e) {
            logger.debug(e.getMessage());
        }
        JsonResponse jr = new JsonResponse(null, code, msg, rs);
        return new ResponseEntity(jr, hs);
    }

}
