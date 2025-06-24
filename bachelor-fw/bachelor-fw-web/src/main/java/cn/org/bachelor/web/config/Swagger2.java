package cn.org.bachelor.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 该类用于配置 Swagger2 文档生成相关信息
 *
 * @author Alexhendar
 * @since 2018/10/19
 */
@Configuration
@ConditionalOnProperty(prefix = "bachelor.swagger",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
@EnableSwagger2
public class Swagger2 {
    // 自动注入 Swagger 配置类实例
    @Autowired
    SwaggerConfig swaggerConfig;

    /**
     * 创建 REST API 的 Swagger 配置
     *
     * @return 配置好的 Docket 实例
     */
    @Bean(value = "orderApi")
    @Order(value = 1)
    public Docket createRestApi() {
        // 创建一个参数构建器，用于构建请求参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        // 创建一个参数列表，用于存储构建好的参数
        List<Parameter> pars = new ArrayList<>();
        // 构建一个名为 bachelor_authorization 的请求头参数，用于传递 jwt 信息
        tokenPar.name("bachelor_authorization").description("jwt").modelRef(new ModelRef("string"))
                .required(false).parameterType("header").required(false).build();
        // 将构建好的参数添加到参数列表中
        pars.add(tokenPar.build());
        // 创建一个 Swagger 文档构建器，指定文档类型为 SWAGGER_2
        ApiSelectorBuilder asb = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)
                .select();
        // 如果配置了扫描的基础包
        if (swaggerConfig.getBasePackages() != null) {
            // 遍历所有配置的基础包
            for (String pkg : swaggerConfig.getBasePackages()) {
                // 设置扫描指定基础包下的请求处理方法
                asb.apis(RequestHandlerSelectors.basePackage(pkg));
            }
        }
        // 设置路径选择器，匹配所有路径
        asb.paths(PathSelectors.any());
        // 构建并返回 Docket 实例
        return asb.build();
    }

    /**
     * 构建 API 文档的详细信息
     *
     * @return 包含 API 文档详细信息的 ApiInfo 实例
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题，从配置中获取
                .title(swaggerConfig.getTitle())
                // 设置版本号，从配置中获取
                .version(swaggerConfig.getVersion())
                // 设置描述信息，从配置中获取
                .description(swaggerConfig.getDescription()).build();
    }
}