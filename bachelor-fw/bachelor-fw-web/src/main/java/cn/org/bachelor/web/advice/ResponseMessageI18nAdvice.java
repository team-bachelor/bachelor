package cn.org.bachelor.web.advice;

import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;

@ControllerAdvice
public class ResponseMessageI18nAdvice implements ResponseBodyAdvice {

  public static final Logger logger = LoggerFactory.getLogger(ResponseMessageI18nAdvice.class);

  @Autowired
  private MessageSource messageSource;

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    if(body instanceof JsonResponse){
        JsonResponse jsonResponse = (JsonResponse)body;
        // 如果msg已经有值，直接返回;如果正常处理只设置了code，再处理
        if(!StringUtils.isEmpty(jsonResponse.getMsg())){
          return body;
        }
        try{
          jsonResponse.setMsg(messageSource.getMessage(jsonResponse.getCode(),null, Locale.CHINA));
        }catch(NoSuchMessageException nsme){
          logger.warn(nsme.getMessage());
        }

        return jsonResponse;
    }
    return body;
  }
}
