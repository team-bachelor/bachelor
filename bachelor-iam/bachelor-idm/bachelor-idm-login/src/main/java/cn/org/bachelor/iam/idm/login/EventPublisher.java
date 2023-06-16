package cn.org.bachelor.iam.idm.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private ApplicationContext publisher;

    public void publish(ApplicationEvent event) {
        publisher.publishEvent(event);
    }

}
