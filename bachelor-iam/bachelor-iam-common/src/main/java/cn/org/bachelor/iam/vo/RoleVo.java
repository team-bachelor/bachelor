package cn.org.bachelor.iam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色信息
 *
 * @author liuzhuo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleVo {

    @Schema(name = "名称")
    private String name;

    @Schema(name = "编码")
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
