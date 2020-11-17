package cn.org.bachelor.up.oauth2.client.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class MD5 {
  private static byte[] a = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

  private InputStream b = null;
  private boolean c = false;
  private int[] d = null;
  private long e = 0L;
  private byte[] f = null;
  private byte[] g = null;

  private static String a(byte[] buf) {
    StringBuffer sb = new StringBuffer(2 * buf.length);
    for (int i = 0; i < buf.length; i++) {
      int h = (buf[i] & 0xF0) >> 4;
      int l = buf[i] & 0xF;
      sb.append(new Character((char) (h > 9 ? 97 + h - 10 : 48 + h)));
      sb.append(new Character((char) (l > 9 ? 97 + l - 10 : 48 + l)));
    }
    return sb.toString();
  }

  private final int a(int x, int y, int z) {
    return x & y | (x ^ 0xFFFFFFFF) & z;
  }

  private final int b(int x, int y, int z) {
    return x & z | y & (z ^ 0xFFFFFFFF);
  }

  private final int c(int x, int y, int z) {
    return x ^ y ^ z;
  }

  private final int d(int x, int y, int z) {
    return y ^ (x | z ^ 0xFFFFFFFF);
  }

  private final int a(int x, int n) {
    return x << n | x >>> 32 - n;
  }

  private final int a(int a, int b, int c, int d, int x, int s, int ac) {
    a += a(b, c, d) + x + ac;
    a = a(a, s);
    a += b;
    return a;
  }

  private final int b(int a, int b, int c, int d, int x, int s, int ac) {
    a += b(b, c, d) + x + ac;
    a = a(a, s);
    a += b;
    return a;
  }

  private final int c(int a, int b, int c, int d, int x, int s, int ac) {
    a += c(b, c, d) + x + ac;
    a = a(a, s);
    a += b;
    return a;
  }

  private final int d(int a, int b, int c, int d, int x, int s, int ac) {
    a += d(b, c, d) + x + ac;
    a = a(a, s);
    a += b;
    return a;
  }

  private final void a(int[] output, byte[] input, int off, int len) {
    int i = 0;
    int j = 0;
    for (; j < len; j += 4) {
      output[i] = (input[(off + j)] & 0xFF | (input[(off + j + 1)] & 0xFF) << 8
          | (input[(off + j + 2)] & 0xFF) << 16 | (input[(off + j + 3)] & 0xFF) << 24);

      i++;
    }
  }

  private final void a(byte[] block, int offset) {
    int a = this.d[0];
    int b = this.d[1];
    int c = this.d[2];
    int d = this.d[3];
    int[] x = new int[16];
    a(x, block, offset, 64);

    a = a(a, b, c, d, x[0], 7, -680876936);

    d = a(d, a, b, c, x[1], 12, -389564586);

    c = a(c, d, a, b, x[2], 17, 606105819);

    b = a(b, c, d, a, x[3], 22, -1044525330);

    a = a(a, b, c, d, x[4], 7, -176418897);

    d = a(d, a, b, c, x[5], 12, 1200080426);

    c = a(c, d, a, b, x[6], 17, -1473231341);

    b = a(b, c, d, a, x[7], 22, -45705983);

    a = a(a, b, c, d, x[8], 7, 1770035416);

    d = a(d, a, b, c, x[9], 12, -1958414417);

    c = a(c, d, a, b, x[10], 17, -42063);

    b = a(b, c, d, a, x[11], 22, -1990404162);

    a = a(a, b, c, d, x[12], 7, 1804603682);

    d = a(d, a, b, c, x[13], 12, -40341101);

    c = a(c, d, a, b, x[14], 17, -1502002290);

    b = a(b, c, d, a, x[15], 22, 1236535329);

    a = b(a, b, c, d, x[1], 5, -165796510);

    d = b(d, a, b, c, x[6], 9, -1069501632);

    c = b(c, d, a, b, x[11], 14, 643717713);

    b = b(b, c, d, a, x[0], 20, -373897302);

    a = b(a, b, c, d, x[5], 5, -701558691);

    d = b(d, a, b, c, x[10], 9, 38016083);

    c = b(c, d, a, b, x[15], 14, -660478335);

    b = b(b, c, d, a, x[4], 20, -405537848);

    a = b(a, b, c, d, x[9], 5, 568446438);

    d = b(d, a, b, c, x[14], 9, -1019803690);

    c = b(c, d, a, b, x[3], 14, -187363961);

    b = b(b, c, d, a, x[8], 20, 1163531501);

    a = b(a, b, c, d, x[13], 5, -1444681467);

    d = b(d, a, b, c, x[2], 9, -51403784);

    c = b(c, d, a, b, x[7], 14, 1735328473);

    b = b(b, c, d, a, x[12], 20, -1926607734);

    a = c(a, b, c, d, x[5], 4, -378558);

    d = c(d, a, b, c, x[8], 11, -2022574463);

    c = c(c, d, a, b, x[11], 16, 1839030562);

    b = c(b, c, d, a, x[14], 23, -35309556);

    a = c(a, b, c, d, x[1], 4, -1530992060);

    d = c(d, a, b, c, x[4], 11, 1272893353);

    c = c(c, d, a, b, x[7], 16, -155497632);

    b = c(b, c, d, a, x[10], 23, -1094730640);

    a = c(a, b, c, d, x[13], 4, 681279174);

    d = c(d, a, b, c, x[0], 11, -358537222);

    c = c(c, d, a, b, x[3], 16, -722521979);

    b = c(b, c, d, a, x[6], 23, 76029189);

    a = c(a, b, c, d, x[9], 4, -640364487);

    d = c(d, a, b, c, x[12], 11, -421815835);

    c = c(c, d, a, b, x[15], 16, 530742520);

    b = c(b, c, d, a, x[2], 23, -995338651);

    a = d(a, b, c, d, x[0], 6, -198630844);

    d = d(d, a, b, c, x[7], 10, 1126891415);

    c = d(c, d, a, b, x[14], 15, -1416354905);

    b = d(b, c, d, a, x[5], 21, -57434055);

    a = d(a, b, c, d, x[12], 6, 1700485571);

    d = d(d, a, b, c, x[3], 10, -1894986606);

    c = d(c, d, a, b, x[10], 15, -1051523);

    b = d(b, c, d, a, x[1], 21, -2054922799);

    a = d(a, b, c, d, x[8], 6, 1873313359);

    d = d(d, a, b, c, x[15], 10, -30611744);

    c = d(c, d, a, b, x[6], 15, -1560198380);

    b = d(b, c, d, a, x[13], 21, 1309151649);

    a = d(a, b, c, d, x[4], 6, -145523070);

    d = d(d, a, b, c, x[11], 10, -1120210379);

    c = d(c, d, a, b, x[2], 15, 718787259);

    b = d(b, c, d, a, x[9], 21, -343485551);

    this.d[0] += a;
    this.d[1] += b;
    this.d[2] += c;
    this.d[3] += d;
  }

  private void b(byte[] input, int len) {
    int index = (int) (this.e >> 3) & 0x3F;
    this.e += (len << 3);
    int partLen = 64 - index;
    int i = 0;
    if (len >= partLen) {
      System.arraycopy(input, 0, this.f, index, partLen);
      a(this.f, 0);
      for (i = partLen; i + 63 < len; i += 64)
        a(input, i);
      index = 0;
    } else {
      i = 0;
    }
    System.arraycopy(input, i, this.f, index, len - i);
  }

  private byte[] a() {
    byte[] bits = new byte[8];
    for (int i = 0; i < 8; i++)
      bits[i] = (byte) (int) (this.e >>> i * 8 & 0xFF);
    int index = (int) (this.e >> 3) & 0x3F;
    int padlen = index < 56 ? 56 - index : 120 - index;
    b(a, padlen);
    b(bits, 8);
    return a(this.d, 16);
  }

  private byte[] a(int[] input, int len) {
    byte[] output = new byte[len];
    int i = 0;
    int j = 0;
    for (; j < len; j += 4) {
      output[j] = (byte) (input[i] & 0xFF);
      output[(j + 1)] = (byte) (input[i] >> 8 & 0xFF);
      output[(j + 2)] = (byte) (input[i] >> 16 & 0xFF);
      output[(j + 3)] = (byte) (input[i] >> 24 & 0xFF);

      i++;
    }

    return output;
  }

  public byte[] getDigest() throws IOException {
    byte[] buffer = new byte[1024];
    int got = -1;
    if (this.g != null)
      return this.g;
    while ((got = this.b.read(buffer)) > 0)
      b(buffer, got);
    this.g = a();
    return this.g;
  }

  public byte[] processString() {
    if (!this.c)
      throw new RuntimeException(getClass().getName() + "[processString]"
          + " not a string.");
    try {
      return getDigest();
    } catch (IOException localIOException) {
    }
    throw new RuntimeException(getClass().getName() + "[processString]"
        + ": implementation error.");
  }

  public String getStringDigest() {
    if (this.g == null) {
      throw new RuntimeException(getClass().getName() + "[getStringDigest]"
          + ": called before processing.");
    }
    return a(this.g);
  }


  public MD5(String input, String encoding) {
    byte[] bytes = null;
    try {
      bytes = input.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("no " + encoding + " encoding!!!");
    }
    this.c = true;
    this.b = new ByteArrayInputStream(bytes);
    this.d = new int[4];
    this.f = new byte[64];
    this.e = 0L;
    this.d[0] = 1732584193;
    this.d[1] = -271733879;
    this.d[2] = -1732584194;
    this.d[3] = 271733878;
  }

  public MD5(String input) {
    this(input, "UTF8");
  }

  public MD5(InputStream in) {
    this.c = false;
    this.b = in;
    this.d = new int[4];
    this.f = new byte[64];
    this.e = 0L;
    this.d[0] = 1732584193;
    this.d[1] = -271733879;
    this.d[2] = -1732584194;
    this.d[3] = 271733878;
  }

	private static MD5 instance = new MD5();
	private MD5() {
	}
	public static MD5 getInstance() {
		return instance;
	}
  
  public void setInput(String input,String encoding){
	  byte[] bytes = null;
	    try {
	      bytes = input.getBytes(encoding);
	    } catch (UnsupportedEncodingException e) {
	      throw new RuntimeException("no " + encoding + " encoding!!!");
	    }
	    this.c = true;
	    this.b = new ByteArrayInputStream(bytes);
	    this.d = new int[4];
	    this.f = new byte[64];
	    this.e = 0L;
	    this.d[0] = 1732584193;
	    this.d[1] = -271733879;
	    this.d[2] = -1732584194;
	    this.d[3] = 271733878;
  }
  /**
   * 
   * <p>功能:根据传入参数，返回加密结果
   *
   * @作者:王学川
   * @时间：2015年4月20日 下午5:43:31
   * @param input
   * @return
   */
  public static String getEncryptResult(String input){
	  
	  MD5 md5 = new MD5(input);
	  md5.processString();
	  String result = md5.getStringDigest();
	  return result;
  }
  public static void main(String[] args) {
	  MD5 m = new MD5();
	  System.out.println(m.getEncryptResult("1{bjOrgAdmin}"));
}
}