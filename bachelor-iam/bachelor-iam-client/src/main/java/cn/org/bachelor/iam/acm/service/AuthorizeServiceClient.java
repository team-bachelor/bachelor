package cn.org.bachelor.iam.acm.service;


import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author liuzhuo
 * @描述 鉴权相关的服务
 * @创建时间 2018/10/22
 */
@Component
@FeignClient(value = "bachelor-ms-iam-service", contextId = "AuthorizeServiceClient", path = "/acm/rpc/role", fallback = AuthorizeServiceClientFallback.class)
public interface AuthorizeServiceClient extends AuthorizeServiceStub {
    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode    对象编码
     * @param userCode   用户编码
     * @param accessType 资源访问类型
     * @return 是否能访问
     */
    @RequestMapping(value = "is-authorized", method = RequestMethod.GET)
    boolean isAuthorized(@RequestParam("objCode") String objCode, @RequestParam("userCode") String userCode, @RequestParam("accessType") PermissionOptions.AccessType accessType);

    /**
     * 计算当前用户的权限
     *
     * @param userCode 用户编码
     * @return 用户权限
     */
    @RequestMapping(value = "user-permission", method = RequestMethod.GET)
    Map<String, PermissionPoint> calUserPermission(@RequestParam("userCode") String userCode);

    /**
     * @param orgId 组织机构ID
     * @return 权限组列表
     * @描述 取得备选权限列表（按组分开）
     * @author liuzhuo
     * @创建时间 2018/10/27 11:16
     */
    @RequestMapping(value = "permission-group", method = RequestMethod.GET)
    List<PermissionGroup> getPermissionGroupList(@RequestParam("orgId") String orgId);

    /**
     * @param roleCode 角色编码
     * @return
     * @description 取得备选权限列表
     * @author liuzhuo
     * @date 2018/10/27 11:16
     */
    @RequestMapping(value = "role-permission", method = RequestMethod.GET)
    List<String> getRolePermission(@RequestParam("roleCode") String roleCode);

    /**
     * 设置指定角色的权限
     *
     * @param roleCode 角色编码
     * @param perms    当前角色拥有的所有权限列表
     * @author liuzhuo
     */
    @RequestMapping(value = "role-permission", method = RequestMethod.PUT)
    void setRolePermission(@RequestParam("roleCode") String roleCode, @RequestBody List<PermissionPoint> perms);

    /**
     * @param orgId 组织机构ID
     * @return 权限列表
     * @description 取得备选权限列表
     * @author liuzhuo
     * @date 2018/10/27 11:16
     */
    @RequestMapping(value = "org-permission", method = RequestMethod.GET)
    List<String> getOrgPermission(@RequestParam("orgId") String orgId);

    /**
     * 设置指定机构的权限
     *
     * @param orgId 组织机构ID
     * @param perms 当前机构拥有的所有权限列表
     * @author liuzhuo
     */
    @RequestMapping(value = "org-permission", method = RequestMethod.PUT)
    void setOrgPermission(@RequestParam("orgId") String orgId, @RequestBody List<PermissionPoint> perms);
}
