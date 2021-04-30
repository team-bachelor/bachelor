package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_obj_domain")
public class ObjDomain {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 域名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 域编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 是否系统默认
     */
    @Column(name = "IS_SYS")
    private Boolean isSys;

    /**
     * 排序
     */
    @Column(name = "SEQ_ORDER")
    private Short seqOrder;

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