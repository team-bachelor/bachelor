package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_obj_permission")
public class ObjPermission {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 对象名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 对象编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 对象定位
     */
    @Column(name = "URI")
    private String uri;

    /**
     * 对象操作
     */
    @Column(name = "OPERATE")
    private String operate;

    /**
     * 对象类型
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 所属域编码
     */
    @Column(name = "DOMAIN_CODE")
    private String domainCode;

    /**
     * 是否系统默认
     */
    @Column(name = "IS_SYS")
    private Boolean isSys;

    /**
     * 排序
     */
    @Column(name = "SEQ_ORDER")
    private Integer seqOrder;

    /**
     * 默认权限行为
     */
    @Column(name = "DEF_AUTH_OP")
    private String defAuthOp;

    /**
     * 服务对象
     */
    @Column(name = "SERVE_FOR")
    private String serveFor;

    /**
     * 说明
     */
    @Column(name = "COMMENT")
    private String comment;

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