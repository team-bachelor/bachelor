package cn.org.bachelor.jms.rabbit.channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by Ric on 2016/12/26.
 */
public class ChannelFactory {

    private static ChannelUtil channelUtil =
            new ChannelUtil(new GenericObjectPool<Connection>(new ConnectionFactory()));

    public static Channel getChannel(){
        return channelUtil.createChannel();
    }
}
