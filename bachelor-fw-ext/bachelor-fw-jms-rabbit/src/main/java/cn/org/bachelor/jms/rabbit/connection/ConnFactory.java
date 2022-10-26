package cn.org.bachelor.jms.rabbit.connection;

import cn.org.bachelor.jms.rabbit.constant.MsgConstant;
import cn.org.bachelor.jms.rabbit.properties.MsgProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ric on 2016/12/26.
 */
public class ConnFactory {

    private static final Logger log = LoggerFactory
            .getLogger(ConnFactory.class);

    private static ConnectionFactory factory = new ConnectionFactory();
    private static MsgProperties props = MsgProperties.getInstance();
    private static boolean inited = false;

    private static void init(){
        factory.setUsername(props.get(MsgConstant.USER));
        factory.setPassword(props.get(MsgConstant.PWD));
        factory.setVirtualHost(props.get(MsgConstant.PATH));
        factory.setHost(props.get(MsgConstant.HOST));
        factory.setPort(Integer.parseInt(props.get(MsgConstant.PORT)));
        inited = true;
    }

    public static Connection getConn(){
        if(!inited){
            init();
        }
        try {
            Connection connection = factory.newConnection();
            return connection;
        }catch(Exception e){
            log.error("getConn error ! e:", e);
        }
        return null;
    }
}
