package cn.org.bachelor.jms.rabbit.consumer;

import cn.org.bachelor.jms.rabbit.handler.MsgHandler;

/**
 * Created by Ric on 2016/12/26.
 */
public interface MsgConsumer {

    void receive(MsgHandler handler);
}
