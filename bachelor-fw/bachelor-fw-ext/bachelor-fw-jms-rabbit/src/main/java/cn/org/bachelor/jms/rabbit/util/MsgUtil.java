package cn.org.bachelor.jms.rabbit.util;

import com.rabbitmq.client.Channel;

import java.text.SimpleDateFormat;

/**
 * Created by Ric on 2016/12/26.
 */
public class MsgUtil {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    public static String dstr() {
        return df.format(System.currentTimeMillis());
    }

    public static String long2date(long date) {
        return df.format(date);
    }

    public static void close(Channel channel) throws Exception {
        if (channel != null) {
            channel.close();
        }
    }
}
