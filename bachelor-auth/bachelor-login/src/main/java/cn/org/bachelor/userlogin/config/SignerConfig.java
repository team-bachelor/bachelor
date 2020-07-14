package cn.org.bachelor.userlogin.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class SignerConfig {

  @Bean
  public RsaSigner jwtSigner() {
    String privateKey = StringUtils.EMPTY;
    try {
      InputStream ips = SignerConfig.class.getResourceAsStream("/id_rsa");
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
    RsaSigner signer = new RsaSigner(privateKey);
    return signer;
  }
}
