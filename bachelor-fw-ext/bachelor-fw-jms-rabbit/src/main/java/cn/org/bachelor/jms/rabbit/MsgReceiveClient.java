package cn.org.bachelor.jms.rabbit;

import cn.org.bachelor.jms.rabbit.consumer.MsgConsumer;
import cn.org.bachelor.jms.rabbit.handler.MsgHandler;
import com.google.inject.Singleton;

import javax.inject.Inject;

/**
 * Created by Ric on 2016/12/26.
 */
@Singleton
public class MsgReceiveClient {

    private MsgConsumer msgConsumer;

    @Inject
    public MsgReceiveClient(MsgConsumer msgConsumer){
        this.msgConsumer = msgConsumer;
    }

    public void receive(MsgHandler msgHandler){
        //MsgHandler msgHandler = new TestHandler();
        msgConsumer.receive(msgHandler);
    }

}
