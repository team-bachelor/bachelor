package cn.org.bachelor.jms.rabbit.handler;

/**
 * Created by Ric on 2016/12/26.
 */
public interface MsgHandler {
    void handle(String msg);
}
