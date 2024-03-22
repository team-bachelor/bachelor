package com.huan.study.resource.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

/**
 * 资源服务器配置
 *
 * @author huan.fu 2021/7/16 - 下午5:00
 */
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 对于 userInfo 这个api 需要 s
                .antMatchers("/userInfo").access("hasAuthority('user.userInfo')")
                .and()
                // 设置session是无状态的
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt()
                // 解码jwt信息
                .decoder(jwtDecoder(restTemplateBuilder))
                // 将jwt信息转换成JwtAuthenticationToken对象
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .and()
                // 从request请求那个地方中获取 token
                .bearerTokenResolver(bearerTokenResolver())
                // 此时是认证失败
                .authenticationEntryPoint((request, response, exception) -> {
                    // oauth2 认证失败导致的，还有一种可能是非oauth2认证失败导致的，比如没有传递token，但是访问受权限保护的方法
                    if (exception instanceof OAuth2AuthenticationException) {
                        OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
                        OAuth2Error error = oAuth2AuthenticationException.getError();
                        log.info("认证失败,异常类型:[{}],异常:[{}]", exception.getClass().getName(), error);
                    }
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.setContentType(MediaType.APPLICATION_JSON.toString());
                    response.getWriter().write("{\"code\":-3,\"message\":\"您无权限访问\"}");
                })
                // 认证成功后，无权限访问
                .accessDeniedHandler((request, response, exception) -> {
                    log.info("您无权限访问,异常类型:[{}]", exception.getClass().getName());
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.setContentType(MediaType.APPLICATION_JSON.toString());
                    response.getWriter().write("{\"code\":-4,\"message\":\"您无权限访问\"}");
                })
        ;
    }

    /**
     * 从request请求中那个地方获取到token
     */
    private BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
        // 设置请求头的参数，即从这个请求头中获取到token
        bearerTokenResolver.setBearerTokenHeaderName(HttpHeaders.AUTHORIZATION);
        bearerTokenResolver.setAllowFormEncodedBodyParameter(false);
        // 是否可以从uri请求参数中获取token
        bearerTokenResolver.setAllowUriQueryParameter(false);
        return bearerTokenResolver;
    }

    /**
     * 从 JWT 的 scope 中获取的权限 取消 SCOPE_ 的前缀
     * 设置从 jwt claim 中那个字段获取权限
     * 如果需要同多个字段中获取权限或者是通过url请求获取的权限，则需要自己提供jwtAuthenticationConverter()这个方法的实现
     *
     * @return JwtAuthenticationConverter
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 去掉 SCOPE_ 的前缀
        authoritiesConverter.setAuthorityPrefix("");
        // 从jwt claim 中那个字段获取权限，模式是从 scope 或 scp 字段中获取
        authoritiesConverter.setAuthoritiesClaimName("scope");
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    /**
     * jwt 的解码器
     *
     * @return JwtDecoder
     */
    public JwtDecoder jwtDecoder(RestTemplateBuilder builder) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // 方式一：通过 jwks url 构建
        // NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder jwtDecoderBuilder = NimbusJwtDecoder.withJwkSetUri("http://qq.com:8080/oauth2/jwks");

        // 方式二：通过收取服务器的公钥创建
        Resource resource = new FileSystemResource("/Users/huan/code/study/idea/spring-cloud-parent/security/new-authoriza-server-public-key.pem");
        String publicKeyStr = String.join("", Files.readAllLines(resource.getFile().toPath()));
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        NimbusJwtDecoder.PublicKeyJwtDecoderBuilder jwtDecoderBuilder = NimbusJwtDecoder.withPublicKey(rsaPublicKey);

        // 授权服务器 jwk 的信息
        NimbusJwtDecoder decoder = jwtDecoderBuilder
                // 设置获取 jwk 信息的超时时间
                // .restOperations(
                //         builder.setReadTimeout(Duration.ofSeconds(3))
                //                 .setConnectTimeout(Duration.ofSeconds(3))
                //                 .build()
                // )
                .build();
        // 对jwt进行校验
        decoder.setJwtValidator(JwtValidators.createDefault());
        // 对 jwt 的 claim 中增加值
        decoder.setClaimSetConverter(
                MappedJwtClaimSetConverter.withDefaults(Collections.singletonMap("为claim中增加key", custom -> "值"))
        );
        return decoder;
    }
}
