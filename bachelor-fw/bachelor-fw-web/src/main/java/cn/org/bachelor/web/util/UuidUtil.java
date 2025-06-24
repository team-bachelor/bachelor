// 声明该类所在的包
package cn.org.bachelor.web.util;

// 引入 UUID 类，用于生成通用唯一识别码
import java.util.UUID;

/**
 * uuid 工具类，用于获取 32 位不重复字符串
 *
 * @author [作者姓名]
 * @since [项目开始日期]
 */
public class UuidUtil {

    /**
     * 生成 32 位不重复的 UUID 字符串
     *
     * @return 去除横线后的 32 位 UUID 字符串
     */
    public static String getUUID() {
        // 生成一个随机的 UUID 实例
        UUID uuid = UUID.randomUUID();
        // 将生成的 UUID 转换为字符串，并去除其中的横线
        return uuid.toString().replaceAll("-", "");
    }
}