package cn.org.bachelor.iam.idm.login.event;

import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {

    @Getter
    private boolean pass;

    public LoginEvent(UsernamePasswordCredential user, boolean pass) {
        super(user);
        this.pass = pass;
    }

    @Override
    public UsernamePasswordCredential getSource() {
        return (UsernamePasswordCredential) super.getSource();
    }
}
