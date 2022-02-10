package cn.org.bachelor.iam.acm.rpc;

import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import cn.org.bachelor.iam.acm.service.AuthorizeServiceStub;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhuo
 * @描述 用于远程调用的用户角色controller
 * @创建时间 2021/09/29
 */
@RestController
@RequestMapping("/acm/rpc/authorize")
public class RpcAuthorizeController {

    @Resource
    private AuthorizeServiceStub authorizeService;


    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode    对象编码
     * @param userCode   用户编码
     * @param accessType 资源访问类型
     * @return 是否能访问
     */
//    @ApiOperation(value = "根据用户编码判断用户是否能访问当前对象")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "objCode", value = "对象编码", paramType = "query", required = true),
//            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", required = true)
//    })
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public boolean isAuthorized(String objCode, String userCode) {
//        return authorizeService.isAuthorized(objCode, userCode, PermissionOptions.AccessType.INTERFACE);
//    }
    @ApiOperation(value = "根据用户编码判断用户是否能访问当前对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objCode", value = "对象编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "accessType", value = "访问类型", paramType = "query", required = false)
    })
    @RequestMapping(value = "is-authorized", method = RequestMethod.GET)
    public boolean isAuthorized(@RequestParam("objCode") String objCode, @RequestParam("userCode") String userCode, @RequestParam("orgCode") PermissionOptions.AccessType accessType) {
        if (accessType == null) {
            accessType = PermissionOptions.AccessType.INTERFACE;
        }
        return authorizeService.isAuthorized(objCode, userCode, accessType);
    }

    /**
     * 计算当前用户的权限
     *
     * @param userCode 用户编码
     * @return 用户权限
     */
    @ApiOperation(value = "计算当前用户的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "user-permission", method = RequestMethod.GET)
    public Map<String, PermissionPoint> calUserPermission(@RequestParam("userCode") String userCode) {
        return authorizeService.calUserPermission(userCode);
    }

    /**
     * @param orgId 组织机构ID
     * @return 权限组列表
     * @描述 取得备选权限列表（按组分开）
     * @author liuzhuo
     * @创建时间 2018/10/27 11:16
     */
    @ApiOperation(value = "取得备选权限列表（按组分开）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构ID", paramType = "query", required = true)
    })
    @RequestMapping(value = "permission-group", method = RequestMethod.GET)
    public List<PermissionGroup> getPermissionGroupList(@RequestParam("orgId") String orgId) {
        return authorizeService.getPermissionGroupList(orgId);
    }

    /**
     * @param roleCode 角色编码
     * @return
     * @description 取得备选权限列表
     * @author liuzhuo
     * @date 2018/10/27 11:16
     */
    @ApiOperation(value = "取得备选权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "role-permission", method = RequestMethod.GET)
    public List<String> getRolePermission(@RequestParam("roleCode") String roleCode) {
        return authorizeService.getRolePermission(roleCode);
    }

    /**
     * 设置指定角色的权限
     *
     * @param roleCode 角色编码
     * @param perms    当前角色拥有的所有权限列表
     * @author liuzhuo
     */
    @ApiOperation(value = "设置指定角色的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "perms", value = "当前角色拥有的所有权限列表", paramType = "body", required = true)
    })
    @RequestMapping(value = "role-permission", method = RequestMethod.PUT)
    public void setRolePermission(@RequestParam("roleCode") String roleCode, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setRolePermission(roleCode, perms);
    }

    /**
     * @param orgId 组织机构ID
     * @return 权限列表
     * @description 取得备选权限列表
     * @author liuzhuo
     * @date 2018/10/27 11:16
     */
    @ApiOperation(value = "取得备选权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构Id", paramType = "query", required = true)
    })
    @RequestMapping(value = "org-permission", method = RequestMethod.GET)
    public List<String> getOrgPermission(@RequestParam("orgId") String orgId) {
        return authorizeService.getOrgPermission(orgId);
    }

    /**
     * 设置指定机构的权限
     *
     * @param orgId 组织机构ID
     * @param perms 当前机构拥有的所有权限列表
     * @author liuzhuo
     */
    @ApiOperation(value = "设置指定机构的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "perms", value = "当前机构拥有的所有权限列表", paramType = "query", required = true)
    })
    @RequestMapping(value = "org-permission", method = RequestMethod.PUT)
    public void setOrgPermission(@RequestParam("orgId") String orgId, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setOrgPermission(orgId, perms);
    }

}
