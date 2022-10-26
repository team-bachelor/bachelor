package cn.org.bachelor.jms.rabbit.di;

import cn.org.bachelor.jms.rabbit.consumer.DefaultMsgConsumer;
import cn.org.bachelor.jms.rabbit.consumer.MsgConsumer;
import cn.org.bachelor.jms.rabbit.producer.DefaultMsgProducer;
import cn.org.bachelor.jms.rabbit.producer.MsgProducer;
import com.google.inject.AbstractModule;

/**
 * Created by Ric on 2016/12/26.
 */
public class MsgModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MsgProducer.class).to(DefaultMsgProducer.class);
        bind(MsgConsumer.class).to(DefaultMsgConsumer.class);
    }
}
