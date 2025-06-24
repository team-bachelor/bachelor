package cn.org.bachelor.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 消息配置类，用于配置Spring的消息源，支持消息的国际化。
 *
 * @author liuzhuo
 * @since 2018/11/9
 */
@Configuration
@ConditionalOnResource(resources= {"${spring.messages.basename}"})
public class MessageConfig {

    /**
     * 国际化文件路径，从配置文件中读取，默认配置在spring.messages.basename属性中。
     */
    @Value("${spring.messages.basename}")
    public String basename;

    /**
     * 创建并配置一个MessageSource Bean，用于解析消息，支持参数化和国际化。
     *
     * @return 配置好的MessageSource实例
     */
    @Bean
    public MessageSource messageSource() {
        // 创建一个基于资源束的消息源实例
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 设置消息源使用的资源束基础名称，即国际化文件路径
        messageSource.setBasename(basename);
        // 返回配置好的消息源实例
        return messageSource;
    }

}