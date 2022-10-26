package cn.org.bachelor.jms.rabbit.producer;

import cn.org.bachelor.jms.rabbit.constant.MsgConstant;
import cn.org.bachelor.jms.rabbit.channel.ChannelFactory;
import cn.org.bachelor.jms.rabbit.pojo.MsgData;
import cn.org.bachelor.jms.rabbit.properties.MsgProperties;
import static cn.org.bachelor.jms.rabbit.util.MsgUtil.close;

import cn.org.bachelor.jms.rabbit.util.StringUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ric on 2016/12/26.
 */
public class DefaultMsgProducer implements MsgProducer {

    private static final Logger log = LoggerFactory
            .getLogger(DefaultMsgProducer.class);

    private static String topic =
            MsgProperties.getInstance().get(MsgConstant.TOPIC);

    @Override
    public void send(MsgData msg) {
        Channel channel = ChannelFactory.getChannel();
        try {
            channel.basicPublish(topic, StringUtils.EMPTY,
                    null, msg.getMsg().getBytes());
            log.info("producer send msg:" + msg);
            close(channel);
        } catch (Exception e) {
            log.error("msg publist error ! e:" + e);
        }
    }

    @Override
    public void sends(List<MsgData> msgs) {
        for(MsgData msg : msgs){
            send(msg);
        }
    }

}
