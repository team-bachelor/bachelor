package cn.org.bachelor.jms.rabbit;

import cn.org.bachelor.jms.rabbit.di.MsgModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Ric on 2016/12/26.
 */
public class MsgFactory {
    private static Injector injector = Guice.createInjector(new MsgModule());

    public static MsgSendClient getSendClient(){
        return injector.getInstance(MsgSendClient.class);
    }

    public static MsgReceiveClient getReceiveClient(){
        return injector.getInstance(MsgReceiveClient.class);
    }
}
