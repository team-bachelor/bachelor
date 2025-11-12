package cn.org.bachelor.iam.acm.rpc;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.service.RoleServiceStub;
import cn.org.bachelor.iam.pojo.IamUser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author liuzhuo
 * @描述 用于远程调用的用户角色controller
 * @创建时间 2021/09/29
 */
@RestController
@RequestMapping("/rpc/acm/role")
public class RpcRoleController {

    @Resource
    private RoleServiceStub roleService;


    /**
     * @param role 角色数据
     * @return 返回创建后的角色信息
     * @描述 创建角色
     */
    @Operation(description = "创建角色")
    @Parameters({
            @Parameter(name = "role", description = "角色数据", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "修改角色")
    @Parameters({
            @Parameter(name = "role", description = "要修改的角色", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "根据角色ID获取角色")
    @Parameters({
            @Parameter(name = "roleID", description = "角色ID", in = ParameterIn.PATH, required = true)
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
    @Operation(description = "删除角色")
    @Parameters({
            @Parameter(name = "roleID", description = "角色的ID", in = ParameterIn.PATH, required = true)
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
    @Operation(description = "查询角色")
    @Parameters({
            @Parameter(name = "orgCode", description = "组织机构编码", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "keyWord", description = "查询关键词，同时用于匹配角色名称和编码", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "获取角色下的用户")
    @Parameters({
            @Parameter(name = "roleCode", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "local", description = "是否只取本地角色", in = ParameterIn.PATH, required = false)
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    public List<IamUser> getRoleUsers(@PathVariable("roleCode") String roleCode) {
        return roleService.getRoleUsers(roleCode);
    }

    @Operation(description = "获取角色下的用户")
    @Parameters({
            @Parameter(name = "roleCode", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "local", description = "是否只取本地角色", in = ParameterIn.PATH, required = false)
    })
    @RequestMapping(value = "/local/users/{roleCode}", method = RequestMethod.GET)
    public List<IamUser> getLocalRoleUsers(@PathVariable("roleCode") String roleCode) {
        return roleService.getLocalRoleUsers(roleCode);
    }

    /**
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 增加结果
     * @描述 为角色增加用户
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @Operation(description = "为角色增加用户")
    @Parameters({
            @Parameter(name = "roleCode", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "users", description = "要添加的用户编码", in = ParameterIn.QUERY, required = true, example = "[{\"\"}]")
    })
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    public void addUsersToRole(@PathVariable("roleCode") String roleCode, @RequestBody List<IamUser> users) {
        roleService.addUsersToRole(roleCode, users);
    }

    /**
     * @param roleCode 角色编码
     * @param users    用户编码
     * @return 删除结果
     * @描述 将用户从角色中删除
     * @更新履历 2021.1.28 访问路径 /role_user/{roleCode} => /role/users/{roleCode}
     */
    @Operation(description = "将用户从角色中删除")
    @Parameters({
            @Parameter(name = "roleCode", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "users", description = "要删除的用户编码", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "查询用户")
    @Parameters({
            @Parameter(name = "userCode", description = "用户编码", in = ParameterIn.PATH, required = true)
    })
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    public List<String> getUserRoles(@PathVariable("userCode") String userCode) {
        return roleService.getUserRoles(userCode);
    }

}
