// 声明当前类所在的包
package cn.org.bachelor.cache.redis.conversion;

// 引入 Spring 框架的 StringUtils 工具类，用于处理字符串相关操作
import org.springframework.util.StringUtils;

// 引入 Java 的 PropertyEditorSupport 类，它是一个属性编辑器的基类，用于支持将字符串转换为特定类型的属性
import java.beans.PropertyEditorSupport;
// 引入 Java 的 Timestamp 类，用于表示 SQL 时间戳
import java.sql.Timestamp;
// 引入 Java 的 DateFormat 类，用于日期格式化
import java.text.DateFormat;
// 引入 Java 的 SimpleDateFormat 类，用于实现简单的日期格式化
import java.text.SimpleDateFormat;

/**
 * StringToTimestampEditor 类继承自 PropertyEditorSupport，用于将字符串转换为 Timestamp 对象。
 * 该类提供了将字符串格式的时间戳转换为 Timestamp 对象的功能，同时也支持将 Timestamp 对象转换为字符串。
 *
 * @author liuzhuo
 * @since 2015/11/11
 */
public class StringToTimestampEditor extends PropertyEditorSupport {
    /**
     * 将字符串文本设置为 Timestamp 值。
     * 该方法尝试将输入的字符串解析为时间戳，并设置到属性编辑器中。
     * 如果输入的字符串为空或无法解析为时间戳，将不会进行设置。
     *
     * @param text 要转换为时间戳的字符串
     * @throws IllegalArgumentException 如果输入的字符串无法转换为有效的时间戳
     */
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        // 检查字符串不为空，原代码中 text != null 多余，因为 StringUtils.isEmpty 已包含此检查
        if (!StringUtils.isEmpty(text)) {
            // 将字符串转换为 long 类型的时间戳，原代码使用 Long.valueOf 存在冗余装箱，建议使用 Long.parseLong
            long time = Long.parseLong(text);
            // 创建一个 Timestamp 对象，并设置到属性编辑器中
            setValue(new Timestamp(time));
        }
        // 原代码中该返回语句多余，因为方法为 void 类型，最后一句默认返回，可移除
    }

    /**
     * 获取当前属性编辑器中的 Timestamp 值，并将其转换为字符串。
     * 如果属性编辑器中没有设置 Timestamp 值，将返回 null。
     *
     * @return 时间戳的字符串表示，如果没有设置时间戳值则返回 null
     */
    public String getAsText() {
        // 检查属性编辑器中的值是否为空
        if (this.getValue() == null) return null;
        // 用于存储格式化后的时间戳字符串
        String tsStr = "";
        // 创建一个 SimpleDateFormat 对象，指定日期格式为 "yyyy/MM/dd HH:mm:ss"
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            // 将属性编辑器中的时间戳值格式化为字符串
            tsStr = sdf.format(getValue());
        } catch (Exception e) {
            // 若格式化失败，原代码使用 e.printStackTrace 输出异常信息，建议使用日志框架记录
            e.printStackTrace();
        }
        // 返回格式化后的时间戳字符串
        return tsStr;
    }
}