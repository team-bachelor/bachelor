package cn.org.bachelor.iam.vo;

import cn.org.bachelor.context.IUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/1
 */
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

    @ApiModelProperty("扩展字段")
    private Map<String,Object> extFields;

    public void setId(String id) {
        this.id = id;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

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
    public String getName() {
        return name;
    }
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAccessBackend() {
        return isAccessBackend;
    }

    public void setAccessBackend(boolean accessBackend) {
        isAccessBackend = accessBackend;
    }

    public Map<String, Object> getExtFields() {
        return extFields;
    }

    public void setExtFields(Map<String, Object> extFields) {
        this.extFields = extFields;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
