package cn.org.bachelor.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2018/11/5 13:58
 * @Modified By:
 */
@Configuration
@ConfigurationProperties(prefix = "bachelor.swagger")
@ConditionalOnProperty(prefix = "bachelor.swagger",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    /**
     * swagger扫描的基础包
     */
    String[] basePackages;

    /**
     * swagger页面标题
     */
    String title;

    /**
     * API版本
     */
    String version;

    /**
     * 模块描述
     */
    String description;

    /**
     * 是否通过网关公示
     */
    Boolean hidden;

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
