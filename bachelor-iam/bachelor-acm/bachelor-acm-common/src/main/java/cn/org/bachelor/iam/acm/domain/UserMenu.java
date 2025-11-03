package cn.org.bachelor.iam.acm.domain;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cmn_acm_user_menu")
public class UserMenu {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户编码
     */
    @Column(name = "USER_CODE")
    private String userCode;

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