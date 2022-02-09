package cn.org.bachelor.iam.acm.service;


import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;

import java.util.List;
import java.util.Map;

/**
 * @描述 鉴权相关的服务
 * @创建人 liuzhuo
 * @创建时间 2018/10/22
 */
public interface AuthorizeServiceStub {
    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode  对象编码
     * @param userCode 用户编码
     * @return 是否能访问
     */
    boolean isAuthorized(String objCode, String userCode);

    boolean isAuthorized(String objCode, String userCode, PermissionOptions.AccessType accessType);

    /**
     * 计算当前用户的权限
     *
     * @param userCode 用户编码
     * @return 用户权限
     */
    Map<String, PermissionPoint> calUserPermission(String userCode);

    /**
     * @param orgID 组织机构ID
     * @return 权限组列表
     * @描述 取得备选权限列表（按组分开）
     * @author liuzhuo
     * @创建时间 2018/10/27 11:16
     */
    List<PermissionGroup> getPermissionGroupList(String orgID);

    /**
     * @param roleCode
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    List<String> getRolePermission(String roleCode);

    /**
     * @param roleCode
     * @param perms    当前角色拥有的所有权限列表
     * @author liuzhuo
     */
    void setRolePermission(String roleCode, List<PermissionPoint> perms);

    /**
     * @param orgId
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    List<String> getOrgPermission(String orgId);

    /**
     * @param orgId
     * @param perms 当前机构拥有的所有权限列表
     * @author liuzhuo
     */
    void setOrgPermission(String orgId, List<PermissionPoint> perms);
}
