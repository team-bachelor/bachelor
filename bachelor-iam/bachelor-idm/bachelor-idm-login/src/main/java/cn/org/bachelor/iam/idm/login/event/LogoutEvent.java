package cn.org.bachelor.iam.idm.login.event;

import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent {


    public LogoutEvent(String userId) {
        super(userId);
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }
}
