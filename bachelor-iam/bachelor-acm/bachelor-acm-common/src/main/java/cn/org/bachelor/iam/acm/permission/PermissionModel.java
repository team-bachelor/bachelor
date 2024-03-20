package cn.org.bachelor.iam.acm.permission;

/**
 *  权限模型类别
 * @author liuzhuo
 * @创建时间: 2018/10/30
 */
public enum PermissionModel {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionModel(String key) {
        this.key = key;
    }
}
