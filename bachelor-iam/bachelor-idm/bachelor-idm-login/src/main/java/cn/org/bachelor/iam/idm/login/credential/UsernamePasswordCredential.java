package cn.org.bachelor.iam.idm.login.credential;

import cn.org.bachelor.iam.credential.AbstractIamCredential;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsernamePasswordCredential extends AbstractIamCredential<String> {
    public UsernamePasswordCredential() {
        super();
    }

    private String username;
    private String password;
    private String captcha;

    @JsonProperty("i")
    private String index;

    public void setUsername(String username) {
        this.username = username;
        this.setSubject(username);
    }

    public void setPassword(String password) {
        this.password = password;
        this.setCredential(password);
    }
}
