package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_menu")
public class Menu {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 菜单名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 菜单编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 菜单定位
     */
    @Column(name = "URI")
    private String uri;

    /**
     * 菜单定位
     */
    @Column(name = "ICON")
    private String icon;

    /**
     * 菜单组
     */
    @Column(name = "GROUP_NAME")
    private String groupName;

    /**
     * 父级ID
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 说明
     */
    @Column(name = "COMMENT")
    private String comment;

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

    @Column(name = "TENANT_ID")
    private String tenantId;
}