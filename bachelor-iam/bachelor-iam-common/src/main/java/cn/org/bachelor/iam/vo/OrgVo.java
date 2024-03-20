package cn.org.bachelor.iam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织机构信息
 * @author  liuzhuo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgVo {

    @ApiModelProperty("id")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty("编码")
    @JsonProperty("code")
    private String code;

    @ApiModelProperty("简称")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty("全名")
    @JsonProperty("fullName")
    private String fullName;

    @ApiModelProperty("上级名称")
    @JsonProperty("pid")
    private String parentId;

    @ApiModelProperty("统一社会信用代码")
    @JsonProperty("uscCode")
    private String uscCode;

    @ApiModelProperty("子机构")
    @JsonProperty("subs")
    private List<OrgVo> subOrgs;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrgVo parent;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean hold;
}
