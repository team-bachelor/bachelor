package cn.org.bachelor.jms.rabbit;

import cn.org.bachelor.jms.rabbit.pojo.MsgData;
import cn.org.bachelor.jms.rabbit.producer.MsgProducer;
import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ric on 2016/12/26.
 */
@Singleton
public class MsgSendClient {

    private MsgProducer msgProducer;

    @Inject
    public MsgSendClient(MsgProducer msgProducer){
        this.msgProducer = msgProducer;
    }

    public void send(String msg){
        MsgData m = new MsgData(msg);
        msgProducer.send(m);
    }

    public void sends(List<String> msgs){
        List<MsgData> ms = new ArrayList<>();
        for(String msg : msgs){
            MsgData m = new MsgData(msg);
            ms.add(m);
        }
        msgProducer.sends(ms);
    }

}
