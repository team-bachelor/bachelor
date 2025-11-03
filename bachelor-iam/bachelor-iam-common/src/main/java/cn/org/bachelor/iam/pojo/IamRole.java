package cn.org.bachelor.iam.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色信息
 *
 * @author liuzhuo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IamRole {

    @Schema(name = "名称")
    private String name;

    @Schema(name = "编码")
    private String code;
}
