package cn.org.bachelor.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
@Configuration
@ConditionalOnResource(resources= {"${spring.messages.basename}"})
public class MessageConfig {

    /** 国际化文件路径 */
    @Value("${spring.messages.basename}")
    public String basename;

    /**
     * 用于解析消息的策略接口，支持这些消息的参数化和国际化。
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        return messageSource;
    }

}
