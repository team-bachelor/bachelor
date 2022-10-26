package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "cmn_acm_user_role")
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
}