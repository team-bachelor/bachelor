
package cn.org.bachelor.demo.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;


/**
 * @ClassName UserInfoVO
 * @Description: 用户涉密信息，如密码等字段不能回显到用户页面
 * @Author Alexhendar
 * @Date 2018/10/9 16:06
 * @Version 1.0
 **/
@Data
public class UserInfoVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户类型")
    private String userType;
    @ApiModelProperty(value = "是否可用")
    private Integer enabled;
    @ApiModelProperty(value = "真实姓名")
    @Column(name = "real_name")
    private String realName;
    @ApiModelProperty(value = "qq")
    private String qq;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String tel;
}

