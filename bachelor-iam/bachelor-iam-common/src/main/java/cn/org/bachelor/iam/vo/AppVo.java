package cn.org.bachelor.iam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AppVo {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public void setAccessTokenExpiry(Integer accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    public Integer getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(Integer refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
