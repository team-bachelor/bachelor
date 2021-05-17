package cn.org.bachelor.jms.rabbit.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ric on 2016/12/26.
 * msg.user
 * msg.password
 * msg.host
 * msg.port
 */
public class MsgProperties {
    private static final Logger log = LoggerFactory
            .getLogger(MsgProperties.class);

    private static final String PROP_FILE = "msg.properties";
    private static Properties props = new Properties();
    private static MsgProperties instance = null;

    private MsgProperties(){
        init();
    }

    public String get(String key){
        return props.getProperty(key);
    }

    public static MsgProperties getInstance(){
        if(instance == null){
            synchronized (props){
                if(instance == null){
                    instance = new MsgProperties();
                }
            }
        }
        return instance;
    }

    private void init(){
        InputStream input = null;
        try {
            input = MsgProperties.class.getClassLoader().getResourceAsStream(PROP_FILE);
            if(input == null){
                log.warn("Sorry, unable to find " + PROP_FILE);
                return;
            }
            props.load(input);
            log.info("Msg load props:" + props.entrySet());
        } catch (IOException ex) {
            log.error("Load props error ! e:", ex);
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("Close input error ! e:", e);
                }
            }
        }
    }

}
