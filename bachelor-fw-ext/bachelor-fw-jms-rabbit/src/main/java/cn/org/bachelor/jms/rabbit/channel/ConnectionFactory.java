package cn.org.bachelor.jms.rabbit.channel;

import cn.org.bachelor.jms.rabbit.connection.ConnFactory;
import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by Ric on 2016/12/26.
 */
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    @Override
    public Connection create() throws Exception {
        return ConnFactory.getConn();
    }

    @Override
    public PooledObject<Connection> wrap(Connection obj) {
        return new DefaultPooledObject<>(obj);
    }
}
