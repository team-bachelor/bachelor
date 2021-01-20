package cn.org.bachelor.iam.acm.vo;

import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/27
 */
public class PermissionGroup {

    private String groupCode;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<PermissionVo> getPerms() {
        return perms;
    }

    public void setPerms(List<PermissionVo> perms) {
        this.perms = perms;
    }

    private List<PermissionVo> perms;
}
