package cn.org.bachelor.iam.acm.vo;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/30
 */
public enum PermissionType {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionType(String key) {
        this.key = key;
    }
}
