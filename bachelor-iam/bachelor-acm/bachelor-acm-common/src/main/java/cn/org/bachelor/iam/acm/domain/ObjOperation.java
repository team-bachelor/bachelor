package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "cmn_acm_obj_operation")
public class ObjOperation {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 操作名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 操作编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 对应的HTTP METHOD
     */
    @Column(name = "METHOD")
    private String method;

    /**
     * 是否系统默认
     */
    @Column(name = "IS_SYS")
    private Boolean isSys;

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