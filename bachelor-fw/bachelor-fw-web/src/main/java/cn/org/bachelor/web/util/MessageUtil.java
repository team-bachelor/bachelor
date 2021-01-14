package cn.org.bachelor.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
@Component
public class MessageUtil {
    private static MessageSource source;

    /**
     * 在Spring里，静态变量/类变量不是对象的属性，而是一个类的属性，不能用@Autowired一个静态变量（对象），使之成为一个SpringBean。<br />
     * <p>
     * 只能通过setter方法注入，并把类注解成为组件
     *
     * @param source
     */
    @Autowired
    public void init(@Qualifier("messageSource") MessageSource source) {
        MessageUtil.source = source;
    }

    /**
     * 抛出校验错误异常
     *
     * @param msgKey
     * @param args
     */
    public static String getMessage(String msgKey, Object... args) {
        // 消息的参数化和国际化配置
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(msgKey, locale, args);
    }

    public static String getMessage(String msgKey, Locale locale, Object... args) {
        return source.getMessage(msgKey, args, locale);
    }
}
