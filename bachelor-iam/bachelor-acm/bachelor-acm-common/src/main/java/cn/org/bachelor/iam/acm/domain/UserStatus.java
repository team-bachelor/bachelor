package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_user_status")
public class UserStatus {
    @Id
    private String id;
    private String type;
    private String orgId;
    private String code;
    private Boolean expired = false;
    private Boolean locked = false;
    private Boolean credentialsExpired = false;
    private Boolean disabled = false;
    private Date updateTime;
}
