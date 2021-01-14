package cn.org.bachelor.architecture.ms.console;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import cn.org.bachelor.web.util.MessageUtil;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;

import static cn.org.bachelor.web.exception.GlobalExceptionHandler.createExceptionResponseEntity;
import static cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR;
import static cn.org.bachelor.web.json.ResponseStatus.SYS_ERR;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 * 全局异常处理
 */
@RestControllerAdvice
public class WebConsoleExceptionHandler {
    private Log logger = LogFactory.getLog(WebConsoleExceptionHandler.class);

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity handleHttpClientException(HttpClientErrorException e) {
        HttpStatus status = e.getStatusCode();
        logger.error(e);
        if (status.is4xxClientError()) {
            return createExceptionResponseEntity(status.toString(), null, BIZ_ERR, status);
        } else {
            return createExceptionResponseEntity(status.toString(), null, SYS_ERR, status);
        }
    }

}
