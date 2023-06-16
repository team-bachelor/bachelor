package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.iam.utils.StringUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/7/24
 */
@Data
public class ImSysParam {
    private String orgId;
    private String orgName;
    private String orgCode;
    private String deptId;
    private String deptName;
    private String deptCode;
    private String clientId;
    private String clientName;
    private String clientCode;
    private String userId;
    private String userName;
    private String userCode;
    private String keyWord;
    private boolean tree = false;
    private Integer level;
    private Integer pageSize = 10000;
    private Integer pageNum = 1;

    public Map<String, String> toParamMap() {
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(getOrgId())) {
            param.put("orgId", getOrgId());
        }
        if (StringUtils.isNotEmpty(getUserId())) {
            param.put("id", getUserId());
        }
        if (StringUtils.isNotEmpty(getUserCode())) {
            param.put("code", getUserCode());
        }
        ///////
        if (StringUtils.isNotEmpty(getUserName())) {
            param.put("username", getUserName());
        }
        if (StringUtils.isNotEmpty(getDeptId())) {
            param.put("deptId", getDeptId());
        }
        if (StringUtils.isNotEmpty(getDeptName())) {
            param.put("deptName", getDeptName());
        }
        if (StringUtils.isNotEmpty(getClientId())) {
            param.put("clientId", getClientId());
        }
        if (getPageSize() != null) {
            param.put("pageSize", String.valueOf(getPageSize()));
        }
        if (getPageNum() != null) {
            param.put("currPage", String.valueOf(getPageNum()));
        }
        return param;
    }
}
