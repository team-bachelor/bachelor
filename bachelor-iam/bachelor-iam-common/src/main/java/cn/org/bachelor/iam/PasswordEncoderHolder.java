package cn.org.bachelor.iam;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidParameterException;

@Configuration
public class PasswordEncoderHolder {


    @Getter
    private static PasswordEncoder passwordEncoder;


    public static void setConfig(String config) throws Exception {
        try {
            Class.forName(config);
        } catch (ClassNotFoundException e) {
            return;
        }
        Object o = Class.forName(config).getConstructor().newInstance();
        if (!(o instanceof PasswordEncoder)) {
            throw new InvalidParameterException("passwordEncoderName: [" + config + "] is not a PasswordEncoder.");
        }
        PasswordEncoderHolder.passwordEncoder = (PasswordEncoder) o;
    }
}
