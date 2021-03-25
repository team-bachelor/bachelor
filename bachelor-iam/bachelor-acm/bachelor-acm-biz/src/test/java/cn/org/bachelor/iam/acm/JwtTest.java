package cn.org.bachelor.iam.acm;

import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.token.RSAKeyCreator;
import cn.org.bachelor.iam.token.RSAKeyPair;
import org.junit.Test;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/15
 */
public class JwtTest {

    @Test
    public void decode() {
        String payload = "{\n" +
                "  \"sub\": \"liuzhuo\",\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"user_code\":\"liuzhuo\",\n" +
                "  \"iat\": 1516239022\n" +
                "}";
        try {
            RSAKeyPair kp = RSAKeyCreator.initKey();

            String ssoAppPrivateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                    RSAKeyCreator.getPrivateKey(kp) +
                    "\n-----END RSA PRIVATE KEY-----";
            System.out.println(ssoAppPrivateKey);
            String token = JwtToken.create(payload, ssoAppPrivateKey);
            System.out.println(token);
            String ssoAppPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                    RSAKeyCreator.getPublicKey(kp) +
                    "\n-----END PUBLIC KEY-----";
            JwtToken jwt = JwtToken.decodeAndVerify(token, kp.getPublic());
            System.out.println(ssoAppPublicKey);
            System.out.println(jwt.getClaims());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
