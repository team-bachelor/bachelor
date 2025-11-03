package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
