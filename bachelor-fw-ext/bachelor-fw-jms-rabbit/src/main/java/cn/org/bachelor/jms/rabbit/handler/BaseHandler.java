package cn.org.bachelor.jms.rabbit.handler;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Ric on 2016/12/26.
 */
public class BaseHandler extends DefaultConsumer {

    private static final Logger log = LoggerFactory
            .getLogger(BaseHandler.class);

    private MsgHandler msgHandler;
    private String queue;

    public BaseHandler(Channel channel, MsgHandler msgHandler,
                       String queue) {
        super(channel);
        this.msgHandler = msgHandler;
        this.queue = queue;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body, "UTF-8");
        //System.out.println("Queue:" + queue + ", [x] Received '" + msg + "'");
        log.info("queue:" + queue + ",msg:" + msg);
        msgHandler.handle(msg);
    }
}
