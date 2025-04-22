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
 * 该类是默认的消息消费者实现类，实现了 MsgConsumer 接口，用于接收消息。
 *
 * @author Ric
 * @since 2016/12/26
 */
public class DefaultMsgConsumer implements MsgConsumer {

    /**
     * 日志记录器，用于记录该类的相关日志信息。
     */
    private static final Logger log = LoggerFactory
            .getLogger(DefaultMsgConsumer.class);

    /**
     * 消息的主题，从配置文件中获取。
     */
    private static String topic =
            MsgProperties.getInstance().get(TOPIC);

    /**
     * 接收消息的方法，通过 RabbitMQ 通道绑定队列并消费消息。
     *
     * @param handler 消息处理器，用于处理接收到的消息
     */
    @Override
    public void receive(MsgHandler handler) {
        // 从通道工厂获取通道
        Channel channel = ChannelFactory.getChannel();
        try {
            // 声明一个队列并获取队列名称
            String queue = channel.queueDeclare().getQueue();
            // 将队列绑定到指定主题
            channel.queueBind(queue, topic, StringUtils.EMPTY);
            // 创建一个消息消费者
            Consumer consumer =
                    new BaseHandler(channel, handler, queue);
            // 开始消费消息
            channel.basicConsume(queue, true, consumer);
        } catch (Exception e) {
            // 记录消费消息时出现的错误
            log.error("consumer msg error ! e:", e);
        }
    }

}
