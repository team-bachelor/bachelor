package cn.org.bachelor.iam.vo;

import cn.org.bachelor.context.IUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo implements IUser {
    private String id;
    private String orgId;
    private String orgCode;
    private String orgName;
    private String deptId;
    private String deptName;
    private String code;
    private String password;
    private String name;
    private String accessToken;
    private String tenantId;
    private boolean isAdministrator;
    private boolean isAccessBackend;

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

}
