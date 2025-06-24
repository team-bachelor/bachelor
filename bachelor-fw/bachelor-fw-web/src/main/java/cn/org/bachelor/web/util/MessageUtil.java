// 声明当前类所在的包
package cn.org.bachelor.web.util;

// 引入 Spring 框架的自动注入注解，用于依赖注入
import org.springframework.beans.factory.annotation.Autowired;
// 引入 Spring 框架的限定符注解，用于指定注入的 Bean 的名称
import org.springframework.beans.factory.annotation.Qualifier;
// 引入 Spring 框架的消息源接口，用于国际化消息处理
import org.springframework.context.MessageSource;
// 引入 Spring 框架的国际化上下文持有者，用于获取当前的 Locale
import org.springframework.context.i18n.LocaleContextHolder;
// 引入 Spring 框架的组件注解，将该类标记为 Spring 组件
import org.springframework.stereotype.Component;

// 引入 Java 的 Locale 类，用于表示特定的地理、政治和文化区域
import java.util.Locale;

/**
 * MessageUtil 类用于处理国际化消息，通过 Spring 的 MessageSource 接口获取不同语言的消息。
 *
 * @author liuzhuo
 * @since 2018/11/9
 */
@Component
public class MessageUtil {
    // 静态的 MessageSource 实例，用于获取国际化消息
    private static MessageSource source;

    /**
     * 在 Spring 里，静态变量/类变量不是对象的属性，而是一个类的属性，不能用 @Autowired 一个静态变量（对象），使之成为一个 Spring Bean。<br />
     * <p>
     * 只能通过 setter 方法注入，并把类注解成为组件
     *
     * @param source 要注入的 MessageSource 实例
     */
    @Autowired
    public void init(@Qualifier("messageSource") MessageSource source) {
        // 将注入的 MessageSource 实例赋值给静态变量
        MessageUtil.source = source;
    }

    /**
     * 根据消息键和参数获取国际化消息，使用当前的 Locale。
     *
     * @param msgKey 消息键，用于从消息源中查找对应的消息
     * @param args 消息参数，用于替换消息中的占位符
     * @return 国际化后的消息字符串
     */
    public static String getMessage(String msgKey, Object... args) {
        // 消息的参数化和国际化配置，获取当前的 Locale
        Locale locale = LocaleContextHolder.getLocale();
        // 调用重载的 getMessage 方法，传入消息键、Locale 和参数
        return getMessage(msgKey, locale, args);
    }

    /**
     * 根据消息键、Locale 和参数获取国际化消息。
     *
     * @param msgKey 消息键，用于从消息源中查找对应的消息
     * @param locale 要使用的 Locale，指定语言和地区
     * @param args 消息参数，用于替换消息中的占位符
     * @return 国际化后的消息字符串
     */
    public static String getMessage(String msgKey, Locale locale, Object... args) {
        // 调用 MessageSource 的 getMessage 方法获取国际化消息
        return source.getMessage(msgKey, args, locale);
    }
}