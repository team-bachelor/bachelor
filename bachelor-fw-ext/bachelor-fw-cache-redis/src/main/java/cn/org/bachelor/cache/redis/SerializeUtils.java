package cn.org.bachelor.cache.redis;

// 导入日志记录器接口
import org.slf4j.Logger;
// 导入日志记录器工厂类
import org.slf4j.LoggerFactory;
// 导入字节数组输入流类，用于从字节数组中读取数据
import java.io.ByteArrayInputStream;
// 导入字节数组输出流类，用于将数据写入字节数组
import java.io.ByteArrayOutputStream;
// 导入输入流类，是所有输入流的基类
import java.io.InputStream;
// 导入对象输入流类，用于从输入流中读取对象
import java.io.ObjectInputStream;
// 导入对象输出流类，用于将对象写入输出流
import java.io.ObjectOutputStream;
// 导入输出流类，是所有输出流的基类
import java.io.OutputStream;
// 导入序列化接口，用于标记可序列化的对象
import java.io.Serializable;

/**
 * 该类提供了对象序列化和反序列化的工具方法，用于将对象转换为字节数组以及将字节数组转换回对象。
 *
 * @author 刘卓
 * @since 2015/8/25
 */
public class SerializeUtils {
	// 声明一个静态的、不可变的日志记录器实例，用于记录该类的日志信息
	private static final Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

	/**
	 * 反序列化方法，将字节数组转换为对象。
	 *
	 * @param bytes 需要被反序列化的byte数组
	 * @return 反序列化后的对象，如果输入的字节数组为空则返回 null
	 */
	public static Object deserialize(byte[] bytes) {
		// 初始化反序列化结果对象
		Object result = null;
		// 检查输入的字节数组是否为空
		if (isEmpty(bytes)) {
			return null;
		}
		try {
			// 创建一个字节数组输入流，用于从字节数组中读取数据
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				// 创建一个对象输入流，用于从字节流中读取对象
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					// 从对象输入流中读取对象并赋值给结果变量
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					// 当读取的对象的类未找到时，抛出异常并记录错误信息
					throw new Exception("Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				// 当反序列化过程中出现其他异常时，抛出异常并记录错误信息
				throw new Exception("Failed to deserialize", ex);
			}
		} catch (Exception e) {
			// 捕获并记录反序列化过程中出现的异常信息
			logger.error("Failed to deserialize", e);
		}
		return result;
	}

	/**
	 * 判断字节数组是否为空的方法。
	 *
	 * @param data 需要检查的字节数组
	 * @return 如果字节数组为 null 或者长度为 0，则返回 true，否则返回 false
	 */
	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/**
	 * 序列化方法，将对象转换为字节数组。
	 *
	 * @param object 需要被序列化的对象
	 * @return 序列化后的字节数组，如果输入的对象为 null，则返回一个长度为 0 的字节数组
	 */
	public static byte[] serialize(Object object) {
		// 初始化序列化结果字节数组
		byte[] result = null;
		// 检查输入的对象是否为 null
		if (object == null) {
			return new byte[0];
		}
		try {
			// 创建一个字节数组输出流，用于将数据写入字节数组
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try {
				// 检查对象是否实现了 Serializable 接口
				if (!(object instanceof Serializable)) {
					// 如果对象未实现 Serializable 接口，抛出异常并提示需要可序列化的对象
					throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
							"but received an object of type [" + object.getClass().getName() + "]");
				}
				// 创建一个对象输出流，用于将对象写入字节流
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
				// 将对象写入对象输出流
				objectOutputStream.writeObject(object);
				// 刷新对象输出流，确保所有数据都被写入
				objectOutputStream.flush();
				// 将字节数组输出流中的数据转换为字节数组并赋值给结果变量
				result = byteStream.toByteArray();
			} catch (Throwable ex) {
				// 当序列化过程中出现异常时，抛出异常并记录错误信息
				throw new Exception("Failed to serialize", ex);
			}
		} catch (Exception ex) {
			// 捕获并记录序列化过程中出现的异常信息
			logger.error("Failed to serialize", ex);
		}
		return result;
	}
}
