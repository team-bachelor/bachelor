package org.bachelor.common.auth.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cmn_auth_obj_domain")
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
     * 获取域名称
     *
     * @return NAME - 域名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置域名称
     *
     * @param name 域名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取域编码
     *
     * @return CODE - 域编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置域编码
     *
     * @param code 域编码
     */
    public void setCode(String code) {
        this.code = code;
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
}