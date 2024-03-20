package cn.org.bachelor.iam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色信息
 * @author liuzhuo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleVo {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
