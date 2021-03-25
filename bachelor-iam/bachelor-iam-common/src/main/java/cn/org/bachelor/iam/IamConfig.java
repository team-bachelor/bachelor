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
}
