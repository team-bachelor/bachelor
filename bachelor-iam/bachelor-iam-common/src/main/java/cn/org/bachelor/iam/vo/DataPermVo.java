package cn.org.bachelor.iam.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * <p>数据权限信息</p>
 * 创建时间: 2019/6/26
 *
 * @author liuzhuo
 */
public class DataPermVo {

    @Schema(name = "部门列表")
    private List<OrgVo> depts;

    @Schema(name = "机构id与机构")
    private Map<String, OrgVo> deptMap;

    @Schema(name = "机构id与用户列表")
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
