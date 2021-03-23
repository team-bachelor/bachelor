package cn.org.bachelor.jms.rabbit.handler;

/**
 * Created by Ric on 2016/12/26.
 */
public class TestHandler implements MsgHandler {
    @Override
    public void handle(String msg) {
        System.out.println("in test handle msg:" + msg);
    }
}
