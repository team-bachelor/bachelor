package cn.org.bachelor.up.oauth2.key;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.security.*;

import java.security.spec.X509EncodedKeySpec;

/**
 * 令牌加密解密
 *
 * @author makai
 * @version 1.0
 * @since 1.0
 */
public abstract class TokenCoder {
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 加密<br>
	 * 用公钥加密产生token
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String token(byte[] data, String publicKey)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = Base64.decode(publicKey.getBytes());

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key pubKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		// 加密时超过117字节就报错。为此采用分段加密的办法来加密
		byte[] enBytes = null;
		for (int i = 0; i < data.length; i += 64) {
		// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码

			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 64));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		//byte[] bytes=cipher.doFinal(data);
//		String token=new String(Base64.encode(enBytes));
//
//		return token;
//		解决BASE64Encoder换行问题 Made by lishihong 20170919 15:20:00
		return Base64.encodeBase64(enBytes);
	}

}
