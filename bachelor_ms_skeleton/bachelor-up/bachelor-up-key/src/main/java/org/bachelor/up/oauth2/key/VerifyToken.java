package cn.org.bachelor.up.oauth2.key;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

/**
 * Created by team bachelor on 2015/5/19.
 */
public class VerifyToken {
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 解密<br>
     * 用私钥解密
     *校验令牌
     * @param token
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] verification(String token, String privateKey)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Base64.decode(privateKey.getBytes());

        //对token解密
        byte[] data=Base64.decode(token.getBytes());

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }
        return enBytes;
    }

    public static void main(String[] args) throws Exception{
        //初始化秘钥
        Map<String,Object> key= TicketCoder.initKey();
        //获得私钥
        String privateKey=TicketCoder.getPrivateKey(key);
        //获得公钥
        String publicKey=TicketCoder.getPublicKey(key);

        //加密产生票据
        String ticket= TokenCoder.token("asdf".getBytes(), publicKey);
        System.out.println(ticket);
        //解密
        byte[] data= VerifyToken.verification(ticket, privateKey);
        System.out.println(new String(data));
    }

}
