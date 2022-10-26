package cn.org.bachelor.iam.acm.rpc;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.service.RoleServiceStub;
import cn.org.bachelor.iam.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuzhuo
 * @描述 用于远程调用的用户角色controller
 * @创建时间 2021/09/29
 */
@RestController
@RequestMapping("/acm/rpc/role")
public class RpcRoleController {

    @Resource
    private RoleServiceStub roleService;


    /**
     * @param role 角色数据
     * @return 返回创建后的角色信息
     * @描述 创建角色
     */
    @ApiOperation(value = "创建角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色数据", paramType = "body", required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    /**
     * @param role 角色数据
     * @return 更新成功返回OK
     * @描述 修改角色
     */
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "要修改的角色", paramType = "body", required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void modifyRole(@RequestBody Role role) {
        roleService.modifyRole(role);
    }

    /**
     * @param roleID 角色ID
     * @return 角色对象
     * @描述 根据角色ID获取角色
     */
    @ApiOperation(value = "根据角色ID获取角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleID", value = "角色ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
    public Role selectByPrimaryKey(@PathVariable("roleID") String roleID) {
        return roleService.selectByPrimaryKey(roleID);
    }

    /**
     * @param roleID 角色的ID
     * @return 删除成功返回OK
     * @描述 删除角色
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleID", value = "角色的ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/{roleID}", method = RequestMethod.DELETE)
    public void deleteRole(@PathVariable("roleID") String roleID) {
        roleService.deleteRole(roleID);
    }

    /**
     * @param orgCode 组织机构编码
     * @param keyWord 查询关键词，同时用于匹配角色名称和编码
     * @return 返回角色列表
     * @描述 查询角色
     */
    @ApiOperation(value = "查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "组织机构编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "keyWord", value = "查询关键词，同时用于匹配角色名称和编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> findViaOrg(String orgCode, String keyWord) {
        return roleService.findViaOrg(orgCode, keyWord);
    }

    /**
     * @param roleCode 角色编码
     * @return 用户列表
     * @描述 获取角色下的用户
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "获取角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    public List<UserVo> getRoleUsers(@PathVariable("roleCode") String roleCode) {
        return roleService.getRoleUsers(roleCode);
    }

    /**
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 增加结果
     * @描述 为角色增加用户
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "为角色增加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "users", value = "要添加的用户编码", paramType = "body", required = true, example = "[{\"\"}]")
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    public void addUsersToRole(@PathVariable("roleCode") String roleCode, @RequestBody List<UserVo> users) {
        roleService.addUsersToRole(roleCode, users);
    }

    /**
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 删除结果
     * @描述 将用户从角色中删除
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @ApiOperation(value = "将用户从角色中删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleCode", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "users", value = "要删除的用户编码", paramType = "body", required = true)
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.PUT)
    public void deleteUsersFromRole(@PathVariable("roleCode") String roleCode, @RequestBody List<String> users) {
        roleService.deleteUsersFromRole(roleCode, users);
    }

    /**
     * @param userCode 用户编码
     * @return 返回用户列表
     * @描述 查询用户的角色
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    public List<String> getUserRoles(@PathVariable("userCode") String userCode) {
        return roleService.getUserRoles(userCode);
    }

}
