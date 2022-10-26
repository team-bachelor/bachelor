package cn.org.bachelor.jms.rabbit.producer;

import cn.org.bachelor.jms.rabbit.pojo.MsgData;

import java.util.List;

/**
 * Created by Ric on 2016/12/26.
 */
public interface MsgProducer {

    void send(MsgData msg);
    void sends(List<MsgData> msgs);
}
