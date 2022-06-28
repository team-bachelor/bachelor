package cn.org.bachelor.web.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2018/10/19 16:39
 * @Modified By:
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "bachelor.swagger",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class Swagger2 {
    @Autowired
    SwaggerConfig swaggerConfig;

    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean(value = "orderApi")
    @Order(value = 1)
    public Docket createRestApi() {
        ApiSelectorBuilder asb = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select();
        // 为当前包路径
        if (swaggerConfig.getBasePackages() != null && swaggerConfig.getBasePackages().length != 0)
            for (String pkg : swaggerConfig.getBasePackages()) {
                asb.apis(RequestHandlerSelectors.basePackage(pkg));
            }

        asb.paths(PathSelectors.any());
        return asb.build().securityContexts(newArrayList(securityContext(),securityContext1())).securitySchemes(newArrayList(apiKey(),apiKey1()));
    }

    /**
     * <p> :构建 api文档的详细信息函数,注意这里的注解引用的是哪个 </p>
     *
     * @param
     * @return
     * @Auther: Alexhendar
     * @Date: 2018/10/19 16:42
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title(swaggerConfig.getTitle())
                // 版本号
                .version(swaggerConfig.getVersion())
                // 描述
                .description(swaggerConfig.getDescription()).build();
    }




    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }
    private ApiKey apiKey1() {
        return new ApiKey("BearerToken1", "Authorization-x", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }
    private SecurityContext securityContext1() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth1())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
    List<SecurityReference> defaultAuth1() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("BearerToken1", authorizationScopes));
    }

    public static <T> List<T> newArrayList(T... ts) {
        List<T> list = new ArrayList();
        if (ts != null && ts.length > 0) {
            list.addAll(Arrays.asList(ts));
        }

        return list;
    }
}
