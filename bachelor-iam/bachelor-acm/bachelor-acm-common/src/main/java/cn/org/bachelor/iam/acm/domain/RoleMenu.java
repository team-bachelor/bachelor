package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "cmn_acm_role_menu")
public class RoleMenu {
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
     * 菜单编码
     */
    @Column(name = "MENU_CODE")
    private String menuCode;

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