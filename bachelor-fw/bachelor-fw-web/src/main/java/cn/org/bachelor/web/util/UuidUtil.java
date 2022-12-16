package cn.org.bachelor.web.util;

import java.util.UUID;

/**
 * uuid 工具类，用于获取32位不重复字符串
 */
public class UuidUtil {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
