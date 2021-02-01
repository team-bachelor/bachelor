package cn.org.bachelor.iam.idm.service;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/7/24
 */
public class ImSysParam {
    private String orgId;
    private String orgName;
    private String deptId;
    private String deptName;
    private String userId;
    private String clientId;
    private String userCode;
    private String userName;
    private String keyWord;
    private String pageSize = "10000";
    private String page = "1";

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, String> toParamMap() {
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(orgId)) {
            param.put("orgId", orgId);
        }
        if (StringUtils.isNotEmpty(userId)) {
            param.put("id", userId);
        }
        if (StringUtils.isNotEmpty(userCode)) {
            param.put("code", userCode);
        }
        ///////
        if (StringUtils.isNotEmpty(userName)) {
            param.put("username", userName);
        }
        if (StringUtils.isNotEmpty(deptId)) {
            param.put("deptId", deptId);
        }
        if (StringUtils.isNotEmpty(pageSize)) {
            param.put("pageSize", pageSize);
        }
        if (StringUtils.isNotEmpty(page)) {
            param.put("currPage", page);
        }
        return param;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
}
