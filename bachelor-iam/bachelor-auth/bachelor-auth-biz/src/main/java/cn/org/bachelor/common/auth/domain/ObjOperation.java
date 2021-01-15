package cn.org.bachelor.common.auth.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cmn_auth_obj_operation")
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
     * 获取操作名称
     *
     * @return NAME - 操作名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置操作名称
     *
     * @param name 操作名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取操作编码
     *
     * @return CODE - 操作编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置操作编码
     *
     * @param code 操作编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取对应的HTTP METHOD
     *
     * @return METHOD - 对应的HTTP METHOD
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置对应的HTTP METHOD
     *
     * @param method 对应的HTTP METHOD
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取是否系统默认
     *
     * @return IS_SYS - 是否系统默认
     */
    public Boolean getIsSys() {
        return isSys;
    }

    /**
     * 设置是否系统默认
     *
     * @param isSys 是否系统默认
     */
    public void setIsSys(Boolean isSys) {
        this.isSys = isSys;
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