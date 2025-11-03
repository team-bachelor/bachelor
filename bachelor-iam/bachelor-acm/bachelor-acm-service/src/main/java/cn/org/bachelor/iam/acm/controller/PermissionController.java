package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import cn.org.bachelor.iam.acm.service.AuthorizeService;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述  鉴权controller
 * @author  liuzhuo
 * @创建时间  2018/10/22
 * @更新履历 2021.1.28 访问路径 => /acm/permission
 * @version 2.0
 */
@RestController
//@CrossOrigin
@RequestMapping("/acm/permission")
public class PermissionController {
    @Autowired
    private AuthorizeService authorizeService;

    /**
     * @描述 设置角色的权限
     * @param role 角色编码
     * @param perms 要设置给角色的权限
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /role_permission/{role} => /role/{role}
     */
    @Operation(description = "设置角色的权限")
    @Parameters({
            @Parameter(name = "role", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "perms", description = "要设置给角色的权限", in = ParameterIn.QUERY, required = true)
    })
    @RequestMapping(value = "/role/{role}", method = RequestMethod.POST)
    public ResponseEntity setRolePermission(@PathVariable("role") String role, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setRolePermission(role, perms);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * @描述 获得角色的权限
     * @param role 角色的编码
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /role_permission/{role} => /role/{role}
     */
    @Operation(description = "获得角色的权限")
    @Parameter(name = "role", description = "角色的编码", in = ParameterIn.PATH, required = true)
    @RequestMapping(value = "/role/{role}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRolePermission(@PathVariable String role) {
        List permg = authorizeService.getRolePermission(role);
        return JsonResponse.createHttpEntity(permg);
    }

    /**
     * @描述 设置机构的权限
     * @param org 机构的编码
     * @param perms 要设置给机构的权限
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /org_permission/{org} => /org/{org}
     */
    @Operation(description = "设置机构的权限")
    @Parameters({
            @Parameter(name = "org", description = "机构的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "perms", description = "要设置给机构的权限", in = ParameterIn.QUERY, required = true)
    })
    @RequestMapping(value = "/org/{org}", method = RequestMethod.POST)
    public ResponseEntity setOrgPermission(@PathVariable("org") String org, @RequestBody List<PermissionPoint> perms) {
        authorizeService.setOrgPermission(org, perms);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * @描述 获得机构的权限
     * @param org 机构的编码
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /org_permission/{org} => /org/{org}
     */
    @Operation(description = "获得机构的权限")
    @Parameter(name = "org", description = "机构的编码", in = ParameterIn.PATH, required = true)
    @RequestMapping(value = "/org/{org}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgPermission(@PathVariable String org) {
        List permg = authorizeService.getOrgPermission(org);
        return JsonResponse.createHttpEntity(permg);
    }

    /**
     * @描述 获得所有权限（按组分开）
     * @param orgID 机构的编码（租户ID）
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /permissions => /grouped
     */
    @Operation(description = "获得全部权限")
    @Parameter(name = "orgID", description = "机构的编码（租户ID）", in = ParameterIn.QUERY, required = true)
    @RequestMapping(value = "/grouped", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getPermissions(String orgID) {
        return JsonResponse.createHttpEntity(authorizeService.getPermissionGroupList(orgID));
    }

    /**
     * @描述 获得用户的权限
     *
     * @param user 用户的编码
     * @return 设置结果
     * @更新履历 2021.1.28 访问路径 /user_permission/{user} => /user/{user}
     */
    @Operation(description = "获得用户的权限")
    @Parameter(name = "user", description = "用户的编码", in = ParameterIn.PATH, required = true)
    @RequestMapping(value = "/user/{user}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserPermission(@PathVariable String user) {
        Map permg = authorizeService.calUserPermission(user);

        if (permg != null) {
            return JsonResponse.createHttpEntity(new ArrayList<>(permg.values()));
        }
        return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
    }
}
