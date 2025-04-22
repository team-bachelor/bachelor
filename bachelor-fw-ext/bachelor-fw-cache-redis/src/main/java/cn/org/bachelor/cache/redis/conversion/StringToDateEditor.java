// 声明当前类所在的包，用于组织和管理代码结构
package cn.org.bachelor.cache.redis.conversion;

// 引入 Spring 框架的工具类 StringUtils，用于处理字符串相关操作
import org.springframework.util.StringUtils;

// 引入 Java 的 PropertyEditorSupport 类，用于实现属性编辑器
import java.beans.PropertyEditorSupport;
// 引入 Java 的 DateFormat 类，用于日期格式化
import java.text.DateFormat;
// 引入 Java 的 ParseException 异常类，用于处理日期解析异常
import java.text.ParseException;
// 引入 Java 的 SimpleDateFormat 类，用于实现简单的日期格式化
import java.text.SimpleDateFormat;

/**
 * StringToDateEditor 类继承自 PropertyEditorSupport，用于将字符串转换为日期对象。
 * 该类提供了将字符串格式的日期转换为 Date 对象的功能，支持指定日期格式。
 *
 * @author liuzhuo
 * @since 2015/11/11
 */
public class StringToDateEditor extends PropertyEditorSupport {
    /**
     * 将字符串文本设置为日期值。
     * 该方法尝试将输入的字符串解析为日期对象，并设置到属性编辑器中。
     * 如果输入的字符串为空或无法解析为日期，将不会进行设置。
     *
     * @param text 要转换为日期的字符串
     * @throws IllegalArgumentException 如果输入的字符串不符合日期格式要求
     */
    public void setAsText(String text) throws IllegalArgumentException {
        // 检查字符串不为空，原代码中 text != null 多余，因为 StringUtils.isEmpty 已包含此检查
        if (!StringUtils.isEmpty(text)) {
            // 创建一个 SimpleDateFormat 对象，指定日期格式为 "yyyy/MM/dd HH:mm:ss"
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                // 尝试将字符串解析为日期对象，并设置到属性编辑器中
                setValue(sdf.parse(text));
            } catch (ParseException e) {
                // 若解析失败，可考虑使用日志框架替代 e.printStackTrace()
                e.printStackTrace();
            }
        }
        // 原代码中该返回语句多余，因为方法为 void 类型，最后一句默认返回，可移除
    }

    /**
     * 获取当前属性编辑器中的日期值，并将其转换为字符串。
     * 如果属性编辑器中没有设置日期值，将返回 null。
     *
     * @return 日期的字符串表示，如果没有设置日期值则返回 null
     */
    public String getAsText() {
        // 检查属性编辑器中的值是否为空
        if (this.getValue() == null) return null;
        // 用于存储格式化后的日期字符串
        String tsStr = "";
        // 创建一个 SimpleDateFormat 对象，指定日期格式为 "yyyy/MM/dd HH:mm:ss"
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            // 方法一：将属性编辑器中的日期值格式化为字符串
            tsStr = sdf.format(getValue());
        } catch (Exception e) {
            // 若格式化失败，可考虑使用日志框架替代 e.printStackTrace()
            e.printStackTrace();
        }
        // 返回格式化后的日期字符串
        return tsStr;
    }
}