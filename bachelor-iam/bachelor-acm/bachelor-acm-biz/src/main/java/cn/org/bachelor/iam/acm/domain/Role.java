package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "cmn_acm_role")
public class Role {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 角色名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 角色编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 组织机构编码
     */
    @Column(name = "ORG_CODE")
    private String orgCode;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_USER")
    private String updateUser;
}