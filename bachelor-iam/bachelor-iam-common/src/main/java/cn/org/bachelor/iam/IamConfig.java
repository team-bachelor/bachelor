package cn.org.bachelor.iam;

import cn.org.bachelor.iam.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
public class IamConfig {

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
     * 是否以json返回认证结果（用于认证失败时，true时返回未授权的json结果，false时导向到loginURL配置的地址）
     */
    private boolean jsonResponse = true;

    /**
     * jsonResponse为false时导向到loginURL配置的地址）
     */
    private String loginURL;

    /**
     * IAM服务提供方式
     */
    private String serviceProvider = "db";
    /**
     * 用于token加密的私钥文件地址:private_key_file
     */
    private String privateKeyFile = "/id_rsa";


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

    public String[] getExcludePathPatterns() {
        return excludePathPatterns;
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

    public boolean isJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(boolean jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }
}
