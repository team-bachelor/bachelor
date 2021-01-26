package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.org.bachelor.iam.acm.service.AuthorizeService;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述: 鉴权controller
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
@RestController
@RequestMapping("/acm")
public class AuthorizeController {
    @Autowired
    private AuthorizeService authorizeService;

    /**
     * 设置角色的权限
     *
     * @param role
     * @param perms
     */
    @ApiOperation(value = "设置角色的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "perms", value = "要设置给角色的权限", paramType = "body", required = true)
    })
    @RequestMapping(value = "/role_permission/{role}", method = RequestMethod.POST)
    public ResponseEntity setRolePermission(@PathVariable("role") String role, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setRolePermission(role, perms);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得角色的权限
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "获得角色的权限")
    @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true)
    @RequestMapping(value = "/role_permission/{role}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRolePermission(@PathVariable String role) {
        List permg = authorizeService.getRolePermission(role);
        return JsonResponse.createHttpEntity(permg);
    }

    /**
     * 设置机构的权限
     *
     * @param org
     * @param perms
     */
    @ApiOperation(value = "设置机构的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "perms", value = "要设置给机构的权限", paramType = "body", required = true)
    })
    @RequestMapping(value = "/org_permission/{org}", method = RequestMethod.POST)
    public ResponseEntity setOrgPermission(@PathVariable("org") String org, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setOrgPermission(org, perms);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得机构的权限
     *
     * @param org
     * @return
     */
    @ApiOperation(value = "获得机构的权限")
    @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true)
    @RequestMapping(value = "/org_permission/{org}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgPermission(@PathVariable String org) {
        List permg = authorizeService.getOrgPermission(org);
        return JsonResponse.createHttpEntity(permg);
    }

    /**
     * 获得所有权限
     *
     * @param orgID
     * @return
     */
    @ApiOperation(value = "获得全部权限")
    @ApiImplicitParam(name = "orgID", value = "角色的编码", paramType = "query", required = true)
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getPermissions(String orgID) {
        return JsonResponse.createHttpEntity(authorizeService.getPermissionGroupList(orgID));
    }

    /**
     * 获得用户的权限
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "获得用户的权限")
    @ApiImplicitParam(name = "user", value = "用户的编码", paramType = "path", required = true)
    @RequestMapping(value = "/user_permission/{user}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserPermission(@PathVariable String user) {
        Map permg = authorizeService.calUserPermission(user);

        if (permg != null) {
            return JsonResponse.createHttpEntity(new ArrayList<>(permg.values()));
        }
        return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
    }
}
