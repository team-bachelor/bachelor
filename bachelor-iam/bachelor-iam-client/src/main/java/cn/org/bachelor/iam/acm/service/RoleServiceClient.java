package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.pojo.IamUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色服务远程调用对象
 */
//@Component("remoteRoleService")
@Component
@FeignClient(value = "${bachelor.iam.service.role:bachelor-web-cmn-acm}", contextId = "RoleServiceClient", path = "/acm/rpc/role", fallback = RoleServiceClientFallback.class)
@ConditionalOnProperty(prefix = "bachelor.iam",
        name = {"service-provider"}, havingValue = "rpc")
public interface RoleServiceClient extends RoleServiceStub {


    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    List<Role> findViaOrg(@RequestParam("orgCode") String orgCode,
                          @RequestParam("keyWord") String keyWord);

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    Role createRole(@RequestBody Role role);

    @Override
    @RequestMapping(value = "/{roleID}", method = RequestMethod.DELETE)
    void deleteRole(@PathVariable("roleID") String roleID);

    @Override
    @RequestMapping(value = "", method = RequestMethod.PUT)
    void modifyRole(@RequestBody Role role);

    @Override
    @RequestMapping(value = "/local/users/{roleCode}", method = RequestMethod.GET)
    List<IamUser> getLocalRoleUsers(@PathVariable("roleCode") String roleCode);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    List<IamUser> getRoleUsers(@PathVariable("roleCode") String roleCode);

    @Override
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    List<String> getUserRoles(@PathVariable("userCode") String userCode);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    void addUsersToRole(@PathVariable("roleCode") String roleCode, @RequestBody List<IamUser> users);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.PUT)
    void deleteUsersFromRole(@PathVariable("roleCode") String roleCode, @RequestBody List<String> users);

    @Override
    @RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
    Role selectByPrimaryKey(@PathVariable("roleID") String roleID);
}
