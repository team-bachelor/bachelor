package cn.org.bachelor.iam.acm.feign;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.service.RoleServiceStub;
import cn.org.bachelor.iam.vo.UserVo;
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
@FeignClient(value = "bachelor-ms-iam-webservice", path = "/acm/rpc/role", fallback = RemoteRoleServiceStubFallback.class)
@ConditionalOnProperty(prefix = "bachelor.iam",
        name = {"service-provider"}, havingValue = "rpc")
public interface RemoteRoleServiceStub extends RoleServiceStub {


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
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    List<UserVo> getRoleUsers(@PathVariable("roleCode") String roleCode);

    @Override
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    List<String> getUserRoles(@PathVariable("userCode") String userCode);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    void addUsersToRole(@PathVariable("roleCode") String roleCode, @RequestBody List<UserVo> users);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.PUT)
    void deleteUsersFromRole(@PathVariable("roleCode") String roleCode, @RequestBody List<String> users);

    @Override
    @RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
    Role selectByPrimaryKey(@PathVariable("roleID") String roleID);
}
