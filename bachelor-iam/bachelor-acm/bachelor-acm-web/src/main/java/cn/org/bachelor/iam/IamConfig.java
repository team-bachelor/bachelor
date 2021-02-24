package cn.org.bachelor.iam;

import cn.org.bachelor.iam.acm.interceptor.UserAccessControlInterceptor;
import cn.org.bachelor.iam.idm.interceptor.UserIdentifyInterceptor;
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
 * @描述
 * @创建时间 2018/11/5
 * @author liuzhuo
 */
@Configuration
@ConfigurationProperties(prefix = "bachelor.iam")
public class IamConfig implements WebMvcConfigurer {

    /**
     * 用户拦截器和访问控制拦截器要拦截的地址
     */
    private String[] excludePathPatterns;

    /**
     * 是否获取当前用户信息
     */
    private boolean enableUserIdentify = true;

    /**
     * 是否对当前登录用户鉴权
     */
    private boolean enableUserAccessControl = true;

    /**
     * 用于token加密的私钥文件地址:private_key_file
     */
    private String privateKeyFile = "/id_rsa";


    /**
     * @return 访问控制拦截器
     */
    @Bean
    public UserAccessControlInterceptor authInterceptor() {
        return new UserAccessControlInterceptor();
    }

    /**
     * @return 用户识别拦截器
     */
    @Bean
    public UserIdentifyInterceptor userInterceptor() {
        return new UserIdentifyInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",//与idmascontroller相关
            "/idm/as/logout"//与idmascontroller相关
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(enableUserIdentify) {
            registry.addInterceptor(userInterceptor()).order(-2)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
        if(enableUserAccessControl) {
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

    public boolean isEnableUserIdentify() {
        return enableUserIdentify;
    }

    public void setEnableUserIdentify(boolean enableUserIdentify) {
        this.enableUserIdentify = enableUserIdentify;
    }

    public boolean isEnableUserAccessControl() {
        return enableUserAccessControl;
    }

    public void setEnableUserAccessControl(boolean enableUserAccessControl) {
        this.enableUserAccessControl = enableUserAccessControl;
    }
}
