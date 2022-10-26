package cn.org.bachelor.jms.rabbit.consumer;

import cn.org.bachelor.jms.rabbit.channel.ChannelFactory;
import cn.org.bachelor.jms.rabbit.handler.BaseHandler;
import cn.org.bachelor.jms.rabbit.handler.MsgHandler;
import cn.org.bachelor.jms.rabbit.properties.MsgProperties;
import cn.org.bachelor.jms.rabbit.util.StringUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.org.bachelor.jms.rabbit.constant.MsgConstant.TOPIC;

/**
 * Created by Ric on 2016/12/26.
 */
public class DefaultMsgConsumer implements MsgConsumer {

    private static final Logger log = LoggerFactory
            .getLogger(DefaultMsgConsumer.class);

    private static String topic =
            MsgProperties.getInstance().get(TOPIC);

    @Override
    public void receive(MsgHandler handler) {
        Channel channel = ChannelFactory.getChannel();
        try {
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, topic, StringUtils.EMPTY);
            Consumer consumer =
                    new BaseHandler(channel, handler, queue);
            channel.basicConsume(queue, true, consumer);
        } catch (Exception e) {
            log.error("consumer msg error ! e:", e);
        }
    }

}
