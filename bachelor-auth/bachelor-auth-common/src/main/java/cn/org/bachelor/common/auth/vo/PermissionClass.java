package cn.org.bachelor.common.auth.vo;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/30
 */
public enum PermissionClass {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionClass(String key) {
        this.key = key;
    }
}
