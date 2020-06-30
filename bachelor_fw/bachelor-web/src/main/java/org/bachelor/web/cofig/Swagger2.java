package org.bachelor.web.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2018/10/19 16:39
 * @Modified By:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Autowired
    SwaggerConfig swaggerConfig;

    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        ApiSelectorBuilder asb = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select();
        // 为当前包路径
        for (String pkg : swaggerConfig.getBasePackage()) {
            asb.apis(RequestHandlerSelectors.basePackage(pkg));
        }

        asb.paths(PathSelectors.any());
        return asb.build();
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
}
