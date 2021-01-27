package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    private Long id;
    @ApiModelProperty(value = "用户名", required = true)
    @Column(name = "user_name")
    private String userName;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户类型", required = true)
    @Column(name = "user_type")
    private String userType;
    @ApiModelProperty(value = "是否可用", required = true)
    private Integer enabled;
    @ApiModelProperty(value = "真实姓名", required = true)
    @Column(name = "real_name")
    private String realName;
    @ApiModelProperty(value = "qq", required = true)
    private String qq;
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    @ApiModelProperty(value = "电话", required = true)
    private String tel;

}
