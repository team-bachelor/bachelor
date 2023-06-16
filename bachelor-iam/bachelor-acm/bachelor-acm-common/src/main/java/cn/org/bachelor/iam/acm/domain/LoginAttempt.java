package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_login_attempt")
public class LoginAttempt {
    @Id
    private String id;
    private String type;
    private String orgId;
    private String code;
    private Integer attempt;
    private String ip;
    private String area;
    private Date updateTime;
}
