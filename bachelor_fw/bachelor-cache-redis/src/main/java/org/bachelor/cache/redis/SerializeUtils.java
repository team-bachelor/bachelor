package org.bachelor.cache.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 *
 * @author 刘卓
 * @since 2015/8/25
 */
public class SerializeUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);
	
	/**
	 * 反序列化
	 * @param bytes 需要被反序列化的byte数组
	 * @return 反序列化后的对象
	 */
	public static Object deserialize(byte[] bytes) {
		
		Object result = null;
		
		if (isEmpty(bytes)) {
			return null;
		}

		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				}
				catch (ClassNotFoundException ex) {
					throw new Exception("Failed to deserialize object type", ex);
				}
			}
			catch (Throwable ex) {
				throw new Exception("Failed to deserialize", ex);
			}
		} catch (Exception e) {
			logger.error("Failed to deserialize",e);
		}
		return result;
	}
	
	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/**
	 * 序列化
	 * @param object 需要被序列化的对象
	 * @return 序列化的结果
	 */
	public static byte[] serialize(Object object) {
		
		byte[] result = null;
		
		if (object == null) {
			return new byte[0];
		}
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try  {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
							"but received an object of type [" + object.getClass().getName() + "]");
				}
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result =  byteStream.toByteArray();
			}
			catch (Throwable ex) {
				throw new Exception("Failed to serialize", ex);
			}
		} catch (Exception ex) {
			logger.error("Failed to serialize",ex);
		}
		return result;
	}
}
