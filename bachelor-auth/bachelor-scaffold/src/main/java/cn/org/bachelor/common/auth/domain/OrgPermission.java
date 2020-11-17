package cn.org.bachelor.common.auth.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cmn_auth_org_permission")
public class OrgPermission {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 机构编码
     */
    @Column(name = "ORG_CODE")
    private String orgCode;

    /**
     * 对象编码
     */
    @Column(name = "OBJ_CODE")
    private String objCode;

    /**
     * 对象定位
     */
    @Column(name = "OBJ_URI")
    private String objUri;

    /**
     * 对象操作
     */
    @Column(name = "OBJ_OPERATE")
    private String objOperate;

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
     * 获取机构编码
     *
     * @return ORG_CODE - 机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置机构编码
     *
     * @param orgCode 机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取对象编码
     *
     * @return OBJ_CODE - 对象编码
     */
    public String getObjCode() {
        return objCode;
    }

    /**
     * 设置对象编码
     *
     * @param objCode 对象编码
     */
    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    /**
     * 获取对象定位
     *
     * @return OBJ_URI - 对象定位
     */
    public String getObjUri() {
        return objUri;
    }

    /**
     * 设置对象定位
     *
     * @param objUri 对象定位
     */
    public void setObjUri(String objUri) {
        this.objUri = objUri;
    }

    /**
     * 获取对象操作
     *
     * @return OBJ_OPERATE - 对象操作
     */
    public String getObjOperate() {
        return objOperate;
    }

    /**
     * 设置对象操作
     *
     * @param objOperate 对象操作
     */
    public void setObjOperate(String objOperate) {
        this.objOperate = objOperate;
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