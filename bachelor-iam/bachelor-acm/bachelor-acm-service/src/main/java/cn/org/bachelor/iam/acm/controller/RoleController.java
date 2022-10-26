package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.service.RoleServiceStub;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @描述 用户角色controller
 * @创建时间 2018/10/22
 * @author liuzhuo
 * @更新履历 2021.1.28 访问路径 => /acm/role
 */
@RestController
@RequestMapping("/acm/role")
//@CrossOrigin
public class RoleController {

    @Resource
    private RoleServiceStub roleService;


    /**
     * @描述 创建角色
     *
     * @param role 角色数据
     * @return 返回创建后的角色信息
     */
    @ApiOperation(value = "创建角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色数据", paramType = "body", required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<JsonResponse> createRole(@RequestBody Role role) {
        return JsonResponse.createHttpEntity(roleService.createRole(role));
    }

    /**
     * @描述 修改角色
     *
     * @param role 角色数据
     * @return 更新成功返回OK
     */
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "要修改的角色", paramType = "body", required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public HttpEntity<JsonResponse> modifyRole(@RequestBody Role role) {
        roleService.modifyRole(role);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * @描述 根据角色ID获取角色
     *
     * @param roleID 角色ID
     * @return 角色对象
     */
    @ApiOperation(value = "根据角色ID获取角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleID", value = "角色ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRole(@PathVariable String roleID) {
        return JsonResponse.createHttpEntity(roleService.selectByPrimaryKey(roleID));
    }

    /**
     * @描述 删除角色
     *
     * @param roleID 角色的ID
     * @return 删除成功返回OK
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleID", value = "角色的ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/{roleID}", method = RequestMethod.DELETE)
    public HttpEntity<JsonResponse> deleteRole(@PathVariable String roleID) {
        roleService.deleteRole(roleID);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * @描述 查询角色
     *
     * @param orgCode 组织机构编码
     * @param keyWord 查询关键词，同时用于匹配角色名称和编码
     * @return 返回角色列表
     */
    @ApiOperation(value = "查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "组织机构编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "keyWord", value = "查询关键词，同时用于匹配角色名称和编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> findRoles(String orgCode, String keyWord) {
        return JsonResponse.createHttpEntity(roleService.findViaOrg(orgCode, keyWord));
    }

    /**
     * @描述 获取角色下的用户
     *
     * @param roleCode 角色编码
     * @return 用户列表
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "获取角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRoleUser(@PathVariable("roleCode") String roleCode) {
        return JsonResponse.createHttpEntity(roleService.getRoleUsers(roleCode));
    }

    /**
     * @描述 为角色增加用户
     *
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 增加结果
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "为角色增加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "users", value = "要添加的用户编码", paramType = "body", required = true, example = "[{\"\"}]")
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    public HttpEntity<JsonResponse> addRoleUser(@PathVariable("roleCode") String roleCode, @RequestBody List<UserVo> users) {
        roleService.addUsersToRole(roleCode, users);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * @描述 将用户从角色中删除
     *
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 删除结果
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "将用户从角色中删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "users", value = "要删除的用户编码", paramType = "body", required = true)
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.PUT)
    public HttpEntity<JsonResponse> delRoleUser(@PathVariable("roleCode") String roleCode, @RequestBody List<String> users) {
        roleService.deleteUsersFromRole(roleCode, users);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }
        //    /**
        ////     * @描述 根据当前clientID查询用户
        ////     * @param deptId 部门编码
        ////     * @param deptName 部门名（模糊匹配）
        ////     * @param userName 用户名（模糊匹配）
        ////     * @param pageSize 每页的记录数
        ////     * @param page 当前页数
        ////     * @return 返回用户列表
        ////     * @更新履历 2021.1.28 转移到RsController.usersInClient
        ////     *
        ////     */
//    @ApiOperation(value = "根据当前clientID查询用户")
//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
//            @ApiImplicitParam(name = "deptName", value = "部门名（模糊匹配）", paramType = "query", required = false),
//            @ApiImplicitParam(name = "userName", value = "用户名（模糊匹配）", paramType = "query", required = false),
//            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", paramType = "query", required = false),
//            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", required = false)
//    })
//    public HttpEntity<JsonResponse> getUsers(String deptId, String deptName, String userName, Integer pageSize, Integer page) {
//        ImSysParam param = new ImSysParam();
//        param.setDeptId(deptId);
//        param.setUserName(userName);
//        param.setDeptName(deptName);
//        param.setPage(page == null ? null : String.valueOf(page));
//        param.setPageSize(page == null ? null : String.valueOf(pageSize));
//        return JsonResponse.createHttpEntity(roleService.findUserByClientID(param));
//    }

    /**
     * @描述 查询用户的角色
     *
     * @param userCode 用户编码
     * @return 返回用户列表
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRolesViaUser(@PathVariable String userCode) {
        return JsonResponse.createHttpEntity(roleService.getUserRoles(userCode));
    }

}
