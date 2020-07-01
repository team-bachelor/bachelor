package org.bachelor.common.auth.vo;

import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/6/26
 */
public class DataPermVo {
    private List<OrgVo> depts;
    private Map<String, OrgVo> deptMap;
    private Map<String, List<UserVo>> orgUserMap;

    public DataPermVo(List<OrgVo> treeOrgs, Map<String, OrgVo> deptMap, Map<String, List<UserVo>> userMap) {
        this.depts = treeOrgs;
        this.deptMap = deptMap;
        this.orgUserMap = userMap;
    }

    public List<OrgVo> getDepts() {
        return depts;
    }

    public void setDepts(List<OrgVo> depts) {
        this.depts = depts;
    }

    public Map<String, List<UserVo>> getOrgUserMap() {
        return orgUserMap;
    }

    public void setOrgUserMap(Map<String, List<UserVo>> orgUserMap) {
        this.orgUserMap = orgUserMap;
    }

    public Map<String, OrgVo> getDeptMap() {
        return deptMap;
    }

    public void setDeptMap(Map<String, OrgVo> deptMap) {
        this.deptMap = deptMap;
    }
}
