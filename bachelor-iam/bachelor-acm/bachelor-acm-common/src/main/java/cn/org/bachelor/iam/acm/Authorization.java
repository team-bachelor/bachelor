package cn.org.bachelor.iam.acm;

import cn.org.bachelor.iam.acm.permission.PermissionOptions;

public interface Authorization {
    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode  对象编码
     * @param userCode 用户编码
     * @param accessType 访问资源类型
     * @return 是否能访问
     */
//    boolean isAuthorized(String objCode, String userCode);

    boolean isAuthorized(String objCode, String userCode, PermissionOptions.AccessType accessType);
}
