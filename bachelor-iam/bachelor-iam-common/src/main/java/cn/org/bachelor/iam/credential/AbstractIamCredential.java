package cn.org.bachelor.iam.credential;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public abstract class AbstractIamCredential<T> {
    public AbstractIamCredential() {
        this.createTime = new Date();
    }

    private String subject;
    private T credential;
    private Date createTime;
    private Date expiresTime;
    private Integer failCount = 0;
}
