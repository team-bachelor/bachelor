package org.bachelor.common.auth.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cmn_auth_menu")
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

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取菜单名称
     *
     * @return NAME - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单编码
     *
     * @return CODE - 菜单编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置菜单编码
     *
     * @param code 菜单编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取菜单定位
     *
     * @return URI - 菜单定位
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置菜单定位
     *
     * @param uri 菜单定位
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    /**
     * 获取父级ID
     *
     * @return PARENT_ID - 父级ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID
     *
     * @param parentId 父级ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取说明
     *
     * @return COMMENT - 说明
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置说明
     *
     * @param comment 说明
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取排序
     *
     * @return SEQ_ORDER - 排序
     */
    public Short getSeqOrder() {
        return seqOrder;
    }

    /**
     * 设置排序
     *
     * @param seqOrder 排序
     */
    public void setSeqOrder(Short seqOrder) {
        this.seqOrder = seqOrder;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新人
     *
     * @return UPDATE_USER - 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人
     *
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}