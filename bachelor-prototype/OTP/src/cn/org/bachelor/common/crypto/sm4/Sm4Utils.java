package cn.org.bachelor.common.crypto.sm4;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sm4Utils
{
    private String secretKey = "";
    private String iv = "";
    private boolean hexString = false;
    private static Pattern P = Pattern.compile("\\s*|\t|\r|\n");
    public Sm4Utils()
    {
    }

    public String encryptData_ECB(String plainText,String secretKey)
    {
        try
        {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = Util.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public String decryptData_ECB(String cipherText,String secretKey)
    {
        try
        {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = Util.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public String encryptData_CBC(String plainText,String secretKey)
    {
        try
        {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("UTF-8"));
            String cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText,String secretKey)
    {
        try
        {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString)
            {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            }
            else
            {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException
    {
        String s = "dDn+HALAnTrIPh5ArUiKqojwcSOaoRoaDnTjgTtHQ3TIZWqn+volYy6BsZVWjnaFCljlBOJ+OUt6K6kip1zi6aqaxSvr0HcB9YrwUdUeD61GKQ3B8Je35+lHSNANUwKyUkUvRt5cukOdTKlU+AE9abrO+3TFtdLuKx61mrUl7EdI4VIAJX/VcOQCFwepF+irXb1vp5qP4nO2LQwQR+HVzVrI9A7ZbZEXizrwImfWO6dYJuXTphBnaEJ0L3piAXZzjrmZzxeyAEjIhv/K/Mfjypg1QKOUDcS0DaSJFzYMm/6RV+Q7smg4cxveT9wcEGlCaepQ2HXA5leN2OUfUouPrL/pTGeCPmKgOFPV/RFavxegUoXSAKdhDJsuYoh6ECfWPBbJQyIt228RykYa3g54YEETxorrsAyiRVcrv93vOuuPXfSXjXI7Hwc3d6mQY9FZorZqcBBkQ6CuU3oDjQLB+Ei0fta1XsIu4TJMr3DmsAemOgln4HzvBltEVEjEzKB4dhRTed9K0sKyM2Deaj4gkg==";
        Sm4Utils sm4 = new Sm4Utils();
        String po = sm4.decryptData_ECB(s,"2916054684417924");
        System.out.println(po);
        System.out.println(sm4.encryptData_ECB(po, "2916054684417924"));
//		String plainText = "{\"callerPhone\":\"13297966360\",\"unbindTime\":\"2\",\"callDurationSeconds\":\"6000\",\"calleeId\":\"048ad6c7-27f3-4432-80d6-47409293a623\",\"isRecord\":\"0\"}";
//
//		Sm4Utils sm4 = new Sm4Utils();
//
//		sm4.hexString = false;
//
//		System.out.println("ECB模式");
//		String cipherText = sm4.encryptData_ECB(plainText,"2916054684417924");
//		System.out.println("密文: " + cipherText);
//		System.out.println("");
//
//		plainText = sm4.decryptData_ECB("","2916054684417924");
//		System.out.println("明文: " + plainText);
//		System.out.println("");
//
//		System.out.println("CBC模式");
//		sm4.iv = "UISwD9fW6cFh9SNS";
//		cipherText = sm4.encryptData_CBC(plainText,"2916054684417924");
//		System.out.println("密文: " + cipherText);
//		System.out.println("");
//
//		plainText = sm4.decryptData_CBC(cipherText,"2916054684417924");
//		System.out.println("明文: " + plainText);

//		String s = "{\"auths\":[\"face_tencent_modelId\",\"fr_tencent_modelId\",\"fr_face++_modelId\",\"voice_xf_modelId\"],\"backgroundImage\":\"www.baidu.com\",\"code\":\"456723\",\"epassVersion\":\"V2.1.0\",\"industry\":\"2\",\"logo\":\"www.baidu.com\",\"name\":\"杨杰\",\"secretKey\":\"q234562323232323\",\"secretType\":\"appEncrypt\",\"sortName\":\"开发杨杰\",\"state\":\"2\"}\n";
//		Sm4Utils sm4 = new Sm4Utils();
//		String cipherText = sm4.encryptData_ECB(s,"2916054684417924");
//		System.out.println(cipherText);
    }
}
