package cn.org.bachelor.iam.vo;

import cn.org.bachelor.context.IUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

/**
 * 用户信息
 * @author  liuzhuo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo implements IUser {

    @Schema(name = "id")
    private String id;

    @Schema(name = "所属机构ID")
    private String orgId;

    @Schema(name = "所属机构编码")
    private String orgCode;

    @Schema(name = "所属机构名称")
    private String orgName;

    @Schema(name = "所属部门ID")
    private String deptId;

    @Schema(name = "所属部门名称")
    private String deptName;

    @Schema(name = "用户编码（登录名）")
    private String code;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "用户名（中文）")
    private String name;

    @Schema(name = "访问令牌")
    private String accessToken;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "是否是管理员")
    private boolean isAdministrator;

    @Schema(name = "是否访问后台确认身份")
    private boolean isAccessBackend;

    @Schema(name = "区域ID")
    private String areaId;

    @Getter
    @Schema(name = "区域名称")
    private String areaName;

    @Schema(name = "用户名(用户平台同步用)")
    private String username;

    @Schema(name = "用户编码(用户平台同步用)")
    private String account;

    @Schema(name = "扩展信息")
    private Map<String, Object> extendInfo;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public String getDeptId() {
        return deptId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public boolean isAdministrator() {
        return isAdministrator;
    }

    @Override
    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setUsername(String name){
        this.username = name;
        this.setName(name);
    }

    public void setAccount(String account){
        this.account = account;
        this.setCode(account);
    }
}
