package cn.org.bachelor.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
// 此导入未被使用，后续可考虑移除
import org.springframework.stereotype.Component;

/**
 * 该类用于配置 Swagger 的相关属性。
 * 通过 @ConfigurationProperties 注解将配置文件中以 "bachelor.swagger" 为前缀的属性绑定到该类的字段上。
 * 同时，使用 @ConditionalOnProperty 注解控制该配置类是否生效，当配置文件中 "bachelor.swagger.enabled" 为 true 时生效，若未配置则默认生效。
 *
 * @author Alexhendar
 * @since 2018/11/5
 */
@Configuration
@ConfigurationProperties(prefix = "bachelor.swagger")
@ConditionalOnProperty(prefix = "bachelor.swagger",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    /**
     * swagger 扫描的基础包
     * 用于指定 Swagger 扫描 API 接口的基础包路径。
     */
    String[] basePackages;

    /**
     * swagger 页面标题
     * 显示在 Swagger UI 页面上的标题。
     */
    String title;

    /**
     * API 版本
     * 标识当前 API 的版本号。
     */
    String version;

    /**
     * 模块描述
     * 对当前 Swagger 配置所涉及模块的描述信息。
     */
    String description;

    /**
     * 是否通过网关公示
     * 用于控制该 Swagger 配置的 API 是否通过网关进行公示。
     */
    Boolean hidden;

    /**
     * 获取 swagger 扫描的基础包。
     * 此方法可能可以使用 Lombok 的 @Getter 注解替代，以简化代码。
     *
     * @return swagger 扫描的基础包数组
     */
    public String[] getBasePackages() {
        return basePackages;
    }

    /**
     * 设置 swagger 扫描的基础包。
     *
     * @param basePackages swagger 扫描的基础包数组
     */
    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    /**
     * 获取 swagger 页面标题。
     * 此方法可能可以使用 Lombok 的 @Getter 注解替代，以简化代码。
     *
     * @return swagger 页面标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置 swagger 页面标题。
     *
     * @param title swagger 页面标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取 API 版本。
     * 此方法可能可以使用 Lombok 的 @Getter 注解替代，以简化代码。
     *
     * @return API 版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置 API 版本。
     *
     * @param version API 版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取模块描述。
     * 此方法可能可以使用 Lombok 的 @Getter 注解替代，以简化代码。
     *
     * @return 模块描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置模块描述。
     *
     * @param description 模块描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 判断是否通过网关公示。
     *
     * @return true 表示通过网关公示，false 表示不通过网关公示
     */
    public Boolean isHidden() {
        return hidden;
    }

    /**
     * 设置是否通过网关公示。
     *
     * @param hidden 是否通过网关公示的布尔值
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}