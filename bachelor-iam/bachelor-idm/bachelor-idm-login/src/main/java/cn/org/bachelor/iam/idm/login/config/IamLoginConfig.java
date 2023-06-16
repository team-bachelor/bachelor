package cn.org.bachelor.iam.idm.login.config;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.idm.login.filter.UserIdentifyFilter;
import cn.org.bachelor.iam.idm.login.service.LoginAuthenticationProvider;
import cn.org.bachelor.iam.idm.login.service.UserDetailsService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lz
 */
@Configuration
@ConfigurationProperties(
        prefix = "bachelor.iam.login"
)
public class IamLoginConfig extends WebSecurityConfigurerAdapter {

    /**
     * 用户拦截器和访问控制拦截器要拦截的地址
     */
    private String[] excludePathPatterns;

    private boolean enableGateway = true;

    @Resource
    private IamConfiguration config;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Resource
    private UserIdentifyFilter userIdentifyFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (excludePathPatterns != null) {
            List<String> list = Arrays.asList(excludePathPatterns);
            list.add("/local/login");
            list.add("/captcha.png");
            excludePathPatterns = list.toArray(new String[list.size()]);
        } else {
            excludePathPatterns = new String[]{"/local/login", "/captcha.png", "/verCaptcha"};
        }
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问s

                .antMatchers(excludePathPatterns).anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //把token校验过滤器添加到过滤器链中

//        http.addFilterBefore(userIdentifyFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(userIdentifyFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder
                .authenticationProvider(loginAuthenticationProvider)
                .userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    public String getPrivateKey() {
        return config.getPrivateKey();
    }


    public void setEnableGateway(boolean enableGateway) {
        this.enableGateway = enableGateway;
    }

    public boolean isEnableGateway() {
        return enableGateway;
    }
}


