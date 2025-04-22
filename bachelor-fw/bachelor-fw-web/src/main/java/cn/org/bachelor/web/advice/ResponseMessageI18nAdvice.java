package cn.org.bachelor.web.advice;

import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;

/**
 * 用于返回信息国际化的通知类.<br/>
 * 如果返回的JsonResponse.msg属性所指定的信息编号在信息文件中存在,则返回与编号对应的信息,
 * 如果不存在则直接返回JsonResponse的msg属性.
 *
 * @see cn.org.bachelor.web.json.JsonResponse#getMsg()
 */
@ControllerAdvice
public class ResponseMessageI18nAdvice implements ResponseBodyAdvice {
    // 记录日志，方便调试和监控
    public static final Logger logger = LoggerFactory.getLogger(ResponseMessageI18nAdvice.class);

    // 自动注入MessageSource，用于获取国际化消息
    @Autowired
    private MessageSource messageSource;

    /**
     * 判断是否支持对返回体进行处理
     *
     * @param returnType 方法的返回类型
     * @param converterType 消息转换器类型
     * @return 如果支持处理返回 true，否则返回 false，这里始终返回 true
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 在返回体写入之前对其进行处理
     *
     * @param body 响应体对象
     * @param returnType 方法的返回类型
     * @param selectedContentType 选择的媒体类型
     * @param selectedConverterType 选择的消息转换器类型
     * @param request 服务器请求对象
     * @param response 服务器响应对象
     * @return 处理后的响应体对象
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 判断响应体是否为 JsonResponse 类型
        if(body instanceof JsonResponse) {
            // 将响应体转换为 JsonResponse 类型
            JsonResponse jsonResponse = (JsonResponse) body;
            // 如果 msg 已经有值，直接返回响应体，不进行国际化处理
            if(!StringUtils.isEmpty(jsonResponse.getMsg())) {
                return body;
            }
            try {
                // 尝试从消息源中获取与 code 对应的国际化消息
                jsonResponse.setMsg(messageSource.getMessage(jsonResponse.getCode(), null, LocaleContextHolder.getLocale()));
            } catch(NoSuchMessageException nsme) {
                // 若未找到对应的消息，记录警告日志
                logger.warn(nsme.getMessage());
            }
            // 返回处理后的 JsonResponse 对象
            return jsonResponse;
        }
        // 若响应体不是 JsonResponse 类型，直接返回原响应体
        return body;
    }
}
