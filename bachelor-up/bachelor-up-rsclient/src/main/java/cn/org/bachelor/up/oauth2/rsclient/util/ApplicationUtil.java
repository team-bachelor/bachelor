package cn.org.bachelor.up.oauth2.rsclient.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * 获取application.properties配置文件信息
 * @Description:
 */
public class ApplicationUtil {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("Jedis_config");
	
	/**
	 * 根据key获取value
	 * @param key application.properties文件中的key
	 * @return
	 * @throws
	 */
	public static String getValue(String key,String defValue){
		if(!containsKey(key)){
			return defValue;
		}else{
			String value =  bundle.getString(key);
			if(value == null  || "".equals(value.trim())){
				return defValue;
			}
			try {
				return new String(value.getBytes("ISO8859-1"),"UTF-8");//转换成UTF-8编码格式
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
	}
	
	/**
	 * 根据key获取value
	 * @Description: 
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		if(!containsKey(key)){
			return null;
		}else{
			String value =  bundle.getString(key);
			if(value == null  || "".equals(value.trim())){
				return null;
			}
			try {
				return new String(value.getBytes("ISO8859-1"),"UTF-8");//转换成UTF-8编码格式
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
	}
	
	/**
	 * 判断配置文件中是否有该Key
	 * @Description: 
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key){
		return bundle.containsKey(key);
	}
}
