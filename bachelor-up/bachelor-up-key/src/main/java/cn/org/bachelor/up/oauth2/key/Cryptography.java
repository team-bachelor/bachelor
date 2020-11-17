package cn.org.bachelor.up.oauth2.key;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Cryptography {
	/**
	 * 生成访问代码
	 * @return 访问代码的字符串
	 */
	public static String generateAccessCode() {
		Random random = new Random();
		byte[] bytes = new byte[24];
		random.nextBytes(bytes);
		byte[] bs = Base64.encode(bytes);
		return new String(bs);
	}

	/**
	 * 生成openid
	 * @param appid
	 * @param userid
	 * @return
	 */
	public static String generateOpenId(String appid, String userid) {
		return Base64.encode(userid + "@" + appid);
	}

	// 方形矩阵的大小
	private static final int matrixSide = 16;
	// 获得矩阵数量
	private static final int matrixItemCount = 256;

	/**
	 * 生成访问令牌
	 * @param request 访问令牌请求
	 * @return
	 */
//	public static AsToken generateAccessToken(GenAsTokenRequest request) {
//		// 获取sr和cr的byte数组
//		byte[] sr1 = Base64.decode(request.getAsCode().getBytes());
//		byte[] cr1 = Base64.decode(request.getStateCode().getBytes());
//		// 将sr和cr混合
//		byte[] s = new byte[32];
//		for (int i = 0, ci = 0, si = 0; i < 32; i++) {
//			if (i % 4 == 0) {
//				s[i] = cr1[ci++];
//			} else {
//				s[i] = sr1[si++];
//			}
//		}
//		// 准备key和iv
//		byte[] key = new byte[24];
//		byte[] iv = new byte[8];
//		// 根据上面混合的结果，生成key和iv
//		for (int i = 0; i < 32; i++) {
//			if (i < 24) {
//				key[i] = s[i];
//			} else {
//				iv[i - 24] = s[i];
//			}
//		}
//		// 拼接token源串
//		String src = new StringBuilder(request.getUserid()).append("@")
//				.append(request.getAppid()).append(":").append(request.getScope())
//				.toString();
//		// 获取token1
//		byte[] token1 = TripleDES.encrypt(key, iv, src.getBytes());
//		// 取各种长度
//		byte keyLength = int2Byte(key.length);
//		byte ivLength = int2Byte(iv.length);
//		byte matrixLength = int2Byte(matrixSide);
//		// 计算token2的长度
//		// 数据长度
//		int dataLength = key.length + iv.length + token1.length + 5;// 5 = 前置数据的个数
//		// 补位长度
//		int paddingLength = matrixItemCount - dataLength % matrixItemCount;
//		if (paddingLength == 0) {
//			paddingLength = matrixItemCount;
//		}
//		// 生成要补位的数组
//		byte[] padding = new byte[paddingLength];
//		// 补位数组的元素内容
//		byte paddingCell = int2Byte(paddingLength);
//		// 初始化数组
//		for (int i = 0; i < paddingLength; i++) {
//			padding[i] = paddingCell;
//		}
//		// 生成token2
//		byte[] token2 = new byte[dataLength + paddingLength];
//		// 获取token1的长度byte
//		byte[] tokenLength = short2Bytes((short) token1.length);
//		// 按元素填充
//		token2[0] = tokenLength[0];
//		token2[1] = tokenLength[1];
//		token2[2] = keyLength;
//		token2[3] = ivLength;
//		token2[4] = matrixLength;
//		// 将其他内容填充近数组
//		int start = 5;
//		// 加入key
//		appendByte(token2, key, start);
//		start += key.length;
//		// 加入token1
//		appendByte(token2, token1, start);
//		start += token1.length;
//		// 加入iv
//		appendByte(token2, iv, start);
//		start += iv.length;
//		// 加入补位
//		appendByte(token2, padding, start);
//
//		// 开始转置
//		turnToken(true, token2, cr1);
//		String dk = new String(Base64.encode(token2));
//		// 加入key2
//		byte[] key2 = Base64.decode(request.getRandomKey()).getBytes();
//		byte[] shaToken = new byte[token2.length + key2.length];
//		appendByte(shaToken, token2, 0);
//		appendByte(shaToken, key2, token2.length);
//		// SHA-256
//		shaToken = encryptSHA(shaToken);
//		String shak = new String(Base64.encode(shaToken));
//		return new AsToken(shak, dk);
//	}

	/**
	 * 解析访问令牌
	 * @param decryptableToken
	 * @param stateCode
	 * @return
	 */
//	public static AsTokenSrc decryptToken(String decryptableToken, String stateCode) {
//		byte[] token2 = Base64.decode(decryptableToken).getBytes();
//		byte[] cr1 = Base64.decode(stateCode).getBytes();
//		// 反转置
//		turnToken(false, token2, cr1);
//		//
//		int token1Length = token2[0] & 0xFF;
//		token1Length |= ((token2[1] << 8) & 0xFF00);
//		int keyLength = token2[2] & 0xFF;
//		int ivLength = token2[3] & 0xFF;
////		int matrixLength = token2[4] & 0xFF;
//		int start = 6;
//		byte[] key = readBytes(token2, start, keyLength);
//		start += keyLength;
//		byte[] token1 = readBytes(token2, start, token1Length);
//		start += token1Length;
//		byte[] iv = readBytes(token2, start, ivLength);
//		byte[] srcByte = TripleDES.decrypt(key, iv, token1);
//		String src = new String(srcByte);
//		String[] srcArray = src.split("@|:");
//		String userId = srcArray[0];
//		String appId = srcArray[1];
//		String scope = srcArray[2];
//		AsTokenSrc at = new AsTokenSrc();
//		at.setUserid(userId);
//		at.setAppid(appId);
//		at.setScope(scope);
//		return at;
//	}

	/**
	 * 读取byte数组中的指定段
	 * @param src 源
	 * @param start 开始位置
	 * @param offset 读取偏移量
	 * @return 包含读取内容的新数组
	 */
	private static byte[] readBytes(byte[] src, int start, int offset){
		byte[] ret = new byte[(start + offset) <= src.length ? offset : src.length - start];
		for(int i = 0;i < ret.length; i++){
			ret[i] = src[i+start];
		}
		return ret;
	}

	/**
	 * 翻转token矩阵
	 * @param encrypt 是否为加密状态
	 * @param token2 要转置的token
	 * @param cr1 客户端随机值
	 */
	private static void turnToken(boolean encrypt, byte[] token2, byte[] cr1) {
		int start = 0;
		int count = (int) Math.ceil((double)token2.length / (double)matrixItemCount);
		for (int i = 0; i < count; i++) {
			// 获取cr1中的byte用来计算转置方式
			byte cr = cr1[i % cr1.length];
			// 计算转置的次数
			int times = (int) (cr >> 4);
			times = times % 4;
			// 计算转置的方向
			boolean forward = (((int) cr & 0x0f) % 2 == 0) == encrypt;
			// 生成转置用正方形数组
			byte[][] matrix = new byte[matrixSide][matrixSide];
			start = i * matrixItemCount;
			// 将token2中的一段，转换成二维数组
			for (int j = start; j < start + matrixItemCount; j++) {
				matrix[Math.round((j - start) / matrixSide)][j % matrixSide] = token2[j];
			}
			// 转置数组
			matrix = turn(forward, times, matrix);
			// 将转置完的数组放回token2
			for (int j = start; j < start + matrixItemCount; j++) {
				token2[j] = matrix[Math.round((j - start) / matrixSide)][j
						% matrixSide];
			}
		}
	}

	/**
	 * MD5加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) {

		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(data);
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * SHA-256加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) {
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			sha.update(data);
			return sha.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return data;

	}

	/**
	 * 向数组中追加指定的数组
	 * @param target 追加的目标
	 * @param forappend 要追加的数组
	 * @param start 开始位置
	 */
	private static void appendByte(byte[] target, byte[] forappend, int start) {
		for (int i = start; i < forappend.length + start; i++) {
			target[i] = forappend[i - start];
		}
	}

	/**
	 * short转byte数组
	 * @param num
	 * @return
	 */
	private static byte[] short2Bytes(short num) {
		byte[] byteNum = new byte[2];
		for (int ix = 0; ix < 2; ++ix) {
			int offset = 16 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	/**
	 * int转1位数组
	 * @param num
	 * @return
	 */
	private static byte int2Byte(int num) {
		return (byte) (num & 0x000000ff);
	}

	/**
	 * 矩阵转置，要求必须是正方形矩阵
	 *
	 * @param forward
	 *            方向是否顺时针
	 * @param times
	 *            转置次数
	 * @param src
	 *            源矩阵
	 * @return
	 */
	private static byte[][] turn(boolean forward, int times, byte[][] src) {
		times = times % 4;
		if (times == 0) {
			return src;
		}
		if (!forward) {
			times = 4 - times;
		}
		int colcount = src[0].length;
		int rowcount = src.length;
		for (int time = 0; time < times; time++) {
			byte[][] b = new byte[colcount][rowcount];
			for (int i = 0; i < src.length; i++) {
				for (int j = 0; j < src[0].length; j++) {
					b[j][colcount - i - 1] = src[i][j];
				}
			}
			src = b;
		}
		return src;
	}

	public static void main(String[] arg) {

		// byte[] sr1 = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
		// 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
		// byte[] cr1 = { 0x1, 0x1, 0x1, 0x1, 0x1, 0x1, 0x1, 0x1, };
		//
		// byte[] s = new byte[32];
		// for (int i = 0, ci = 0, si = 0; i < 32; i++) {
		// if (i % 4 == 0) {
		// s[i] = cr1[ci++];
		// } else {
		// s[i] = sr1[si++];
		// }
		// }
		// System.out.println(s);
		// byte[] key = new byte[24];
		// byte[] iv = new byte[8];
		// for (int i = 0; i < 32; i++) {
		// if (i < 24) {
		// key[i] = s[i];
		// } else {
		// iv[i - 24] = s[i];
		// }
		// }
		// System.out.println(key);
		// System.out.println(iv);
		// String src =
		// "123456789012345678901234567890123456@123456789012345678901234567890123456:123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456123456789012345678901234567890123456";
		// //src = Base64.encode(src);
		// byte[] token1 = TripleDES.encrypt(key, iv, src.getBytes());
		// System.out.println(token1);

		// 矩阵转置
		byte[][] a = { { 0x1, 0x2, 0x3 }, { 0x1, 0x2, 0x3 }, { 0x1, 0x2, 0x3} };
		byte[][] b = turn(true, 1, a);
		turn(false, 1, b);
//		byte[] token2 = { 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				// 16
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
//				0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf 
//				};
//		byte[] cr1 = { 0x11, 0x12 };
//		turnToken(true, token2, cr1);
//		System.out.println(token2);
//		turnToken(false, token2, cr1);
//		System.out.println(token2);
//		String[] s = "1@2:3".split("@|:");
//		System.out.println(s);
//		System.out.println(int2Byte(256));
	}

}
