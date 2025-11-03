package cn.org.bachelor.iam.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 组织机构信息
 *
 * @author liuzhuo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IamOrg {

    @Schema(name = "id")
    @JsonProperty("id")
    private String id;

    @Schema(name = "编码")
    @JsonProperty("code")
    private String code;

    @Schema(name = "简称")
    @JsonProperty("name")
    private String name;

    @Schema(name = "全名")
    @JsonProperty("fullName")
    private String fullName;

    @Schema(name = "上级名称")
    @JsonProperty("pid")
    private String parentId;

    @Schema(name = "统一社会信用代码")
    @JsonProperty("uscCode")
    private String uscCode;

    @Schema(name = "子机构")
    @JsonProperty("subs")
    private List<IamOrg> subOrgs;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private IamOrg parent;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean hold;
}
