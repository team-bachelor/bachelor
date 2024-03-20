package cn.org.bachelor.iam.vo;

import cn.org.bachelor.context.IUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 用户信息
 * @author  liuzhuo
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo implements IUser {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("所属机构ID")
    private String orgId;

    @ApiModelProperty("所属机构编码")
    private String orgCode;

    @ApiModelProperty("所属机构名称")
    private String orgName;

    @ApiModelProperty("所属部门ID")
    private String deptId;

    @ApiModelProperty("所属部门名称")
    private String deptName;

    @ApiModelProperty("用户编码（登录名）")
    private String code;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户名（中文）")
    private String name;

    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("租户ID")
    private String tenantId;

    @ApiModelProperty("是否是管理员")
    private boolean isAdministrator;

    @ApiModelProperty("是否访问后台确认身份")
    private boolean isAccessBackend;

    @ApiModelProperty("区域ID")
    private String areaId;

    @ApiModelProperty("区域名称")
    private String areaName;

    @ApiModelProperty("扩展字段")
    private Map<String, Object> extFields;

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
    public String getAreaName() {
        return areaName;
    }

    @Override
    public String getName() {
        return name;
    }
}
