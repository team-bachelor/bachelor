package cn.org.bachelor.iam.acm.permission;

/**
 * @描述: 权限模型类别
 * @创建人: liuzhuo
 * @创建时间: 2018/10/30
 */
public enum PermissionModel {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionModel(String key) {
        this.key = key;
    }
}
