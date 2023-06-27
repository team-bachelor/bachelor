package cn.org.bachelor.iam.acm.domain;

import cn.org.bachelor.context.IUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 用户表(User)实体类
 *
 * @author lz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmn_acm_user")
public class User implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="org_id")
    private String orgId;

    @Column(name="dept_id")
    private String deptId;

    @Column(name="code")
    private String code;

    @Column(name="password")
    private String password;

    @Column(name="salt")
    private String salt;

    @Column(name="name")
    private String name;

    @Column(name="telephone")
    private String telephone;

    @Column(name="email")
    private String email;

    @Column(name="status")
    private String status;

    @Column(name="app_id")
    private String appId;

    @Column(name="version")
    private String version;

    @Column(name="del_flag")
    private String delFlag;

    @Column(name="update_user")
    private String updateUser;

    @Column(name="update_time")
    private String updateTime;

    @Column(name="create_user")
    private String createUser;

    @Column(name="create_time")
    private String createTime;
}


