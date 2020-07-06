package cn.org.bachelor.common.auth.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cmn_auth_user_role")
public class UserRole {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    //用户系统的用户ID
    @Column(name = "USER_ID")
    private String userId;
//    //用户系统的用户中文名
//    @Column(name = "USER_NAME")
//    private String userName;
    //用户系统的用户机构ID
//    @Column(name = "USER_ORGID")
//    private String userOrgId;
//    //部门名称
//    @Column(name = "USER_DEPT_NAME")
//    private String userDeptName;
//
//    //部门路径'->'分割
//    @Column(name = "USER_DEPT_PATH")
//    private String userDeptPath;

    @Column(name = "USER_CODE")
    private String userCode;

    /**
     * 角色编码
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;

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
     * @return USER_CODE
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * @param userCode
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取角色编码
     *
     * @return ROLE_CODE - 角色编码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色编码
     *
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getUserOrgId() {
//        return userOrgId;
//    }
//
//    public void setUserOrgId(String userOrgId) {
//        this.userOrgId = userOrgId;
//    }
//
//    public String getUserDeptName() {
//        return userDeptName;
//    }
//
//    public void setUserDeptName(String userDeptName) {
//        this.userDeptName = userDeptName;
//    }
//
//    public String getUserDeptPath() {
//        return userDeptPath;
//    }
//
//    public void setUserDeptPath(String userDeptPath) {
//        this.userDeptPath = userDeptPath;
//    }

}