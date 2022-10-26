package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "cmn_acm_role_permission")
public class RolePermission {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 角色编码
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;

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
}