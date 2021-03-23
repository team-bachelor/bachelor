package cn.org.bachelor.jms.rabbit.channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ric on 2016/12/26.
 */
public class ChannelUtil {

    private static final Logger log = LoggerFactory
            .getLogger(ChannelUtil.class);

    private ObjectPool<Connection> pool;

    public ChannelUtil(ObjectPool<Connection> pool){
        this.pool = pool;
    }

    public Channel createChannel(){
        Connection conn = null;
        Channel channel = null;
        try {
            conn = pool.borrowObject();
            channel = conn.createChannel();
            return channel;
        } catch (Exception e) {
            log.error("Create channel error ! e:", e);
        } finally {
            if(conn != null){
                try {
                    pool.returnObject(conn);
                } catch (Exception e) {
                    log.error("Return obj error ! e:" + e);
                }
            }
        }
        return channel;
    }
}
