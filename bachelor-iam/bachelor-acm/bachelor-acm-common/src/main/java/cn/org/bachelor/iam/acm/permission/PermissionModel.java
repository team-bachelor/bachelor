package cn.org.bachelor.iam.acm.permission;

/**
 * @描述: 权限模型类别
 * @创建人: liuzhuo
 * @创建时间: 2018/10/30
 */
<<<<<<<< HEAD:bachelor-iam/bachelor-iam-common/src/main/java/cn/org/bachelor/iam/vo/PermissionClass.java
public enum PermissionClass {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionClass(String key) {
========
public enum PermissionModel {
    USER("USER"), ROLE("ROLE"), ORG("ORG");
    private final String key;

    PermissionModel(String key) {
>>>>>>>> 1f197210624ca12e365eeb3c8dea8f66ce884393:bachelor-iam/bachelor-acm/bachelor-acm-common/src/main/java/cn/org/bachelor/iam/acm/permission/PermissionModel.java
        this.key = key;
    }
}
