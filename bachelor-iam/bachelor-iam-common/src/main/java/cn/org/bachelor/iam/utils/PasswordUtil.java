package cn.org.bachelor.iam.utils;

import cn.org.bachelor.iam.IamConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.Resource;
import java.security.InvalidParameterException;

@Configuration
public class PasswordUtil {


    private static PasswordEncoder passwordEncoder;

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Resource
    private IamConfiguration config;

    public void setConfig(IamConfiguration config) throws Exception {
        this.config = config;
        Object o = Class.forName(config.getPasswordEncoder()).newInstance();
        if (!(o instanceof PasswordEncoder)) {
            throw new InvalidParameterException("passwordEncoderName: [" + config.getPasswordEncoder() + "] is not a PasswordEncoder.");
        }
        PasswordUtil.passwordEncoder = (PasswordEncoder) o;
    }
}
