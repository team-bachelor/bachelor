package cn.org.bachelor.iam.acm.permission;

import java.util.List;

/**
 * @描述
 * @创建人 liuzhuo
 * @创建时间 2018/10/27
 * @author liuzhuo
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

    public List<PermissionPoint> getPerms() {
        return perms;
    }

    public void setPerms(List<PermissionPoint> perms) {
        this.perms = perms;
    }

    private List<PermissionPoint> perms;
}
