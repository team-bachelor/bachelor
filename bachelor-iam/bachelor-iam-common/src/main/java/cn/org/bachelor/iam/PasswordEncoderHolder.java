package cn.org.bachelor.iam;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidParameterException;

@Configuration
public class PasswordEncoderHolder {


    private static PasswordEncoder passwordEncoder;

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    public static void setConfig(String config) throws Exception {
        Object o = Class.forName(config).newInstance();
        if (!(o instanceof PasswordEncoder)) {
            throw new InvalidParameterException("passwordEncoderName: [" + config + "] is not a PasswordEncoder.");
        }
        PasswordEncoderHolder.passwordEncoder = (PasswordEncoder) o;
    }
}
