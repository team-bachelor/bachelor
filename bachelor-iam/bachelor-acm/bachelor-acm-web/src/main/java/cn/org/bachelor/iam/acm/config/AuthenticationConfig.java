package cn.org.bachelor.iam.acm.config;

import cn.org.bachelor.iam.acm.interceptor.AuthInterceptor;
import cn.org.bachelor.iam.acm.interceptor.UserInterceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/5
 */
@Configuration
@ConfigurationProperties(prefix = "bachelor.auth")
public class AuthenticationConfig implements WebMvcConfigurer {

    private String[] excludePathPatterns;

    /**
     * 是否获取当前登录用户信息:enable_current_user
     */
    private boolean enableCurrentUser = true;

    /**
     * 是否对当前登录用户鉴权:enable_authentication
     */
    private boolean enableAuthentication = true;

    /**
     * 用于加密的私钥文件地址
     */
    private String privateKeyFile = "/id_rsa";
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/user/accesstoken",
            "/user/logout"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(enableCurrentUser) {
            registry.addInterceptor(userInterceptor()).order(-2)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
        if(enableAuthentication) {
            InterceptorRegistration ir = registry.addInterceptor(authInterceptor()).order(-1)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**");
            if (excludePathPatterns != null && excludePathPatterns.length > 0)
                ir.excludePathPatterns(excludePathPatterns);
        }
    }

    private String privateKey;
    public String getPrivateKey(){
        return privateKey;
    }

    @PostConstruct
    private void init(){
        privateKey = readPrivateKey();
    }

    public String readPrivateKey() {
        String privateKey = StringUtils.EMPTY;
        try {
            InputStream ips = this.getClass().getResourceAsStream(privateKeyFile);
            InputStreamReader reader = new InputStreamReader(ips);
            StringBuffer buffer = new StringBuffer();
            int tmp;
            while ((tmp = reader.read()) != -1) {
                buffer.append((char) tmp);
            }
            privateKey = buffer.toString();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public void setExcludePathPatterns(String[] excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    public boolean isEnableCurrentUser() {
        return enableCurrentUser;
    }

    public void setEnableCurrentUser(boolean enableCurrentUser) {
        this.enableCurrentUser = enableCurrentUser;
    }

    public boolean isEnableAuthentication() {
        return enableAuthentication;
    }

    public void setEnableAuthentication(boolean enableAuthentication) {
        this.enableAuthentication = enableAuthentication;
    }
}
