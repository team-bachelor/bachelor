package cn.org.bachelor.iam.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IamApp {
    @Schema(name = "id")
    @JsonProperty("id")
    private String id;

    @Schema(name = "编码")
    private String code;

    @Schema(name = "名称")
    @JsonProperty("name")
    private String name;

    @Schema(name = "")
    @JsonProperty("地址")
    private String url;

    @Schema(name = "客户端ID")
    private String clientId;

    @Schema(name = "客户端秘钥")
    private String clientSecret;

    @Schema(name = "at过期时间atk_validaty")
    private Integer accessTokenExpiry;

    @Schema(name = "rt过期时间")
    private Integer refreshTokenExpiry;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "更新人")
    private String updateUser;

    @Schema(name = "更新时间")
    private Date updateTime;

    @Schema(name = "创建人")
    private String createUser;

    @Schema(name = "创建时间")
    private Date createTime;
}
