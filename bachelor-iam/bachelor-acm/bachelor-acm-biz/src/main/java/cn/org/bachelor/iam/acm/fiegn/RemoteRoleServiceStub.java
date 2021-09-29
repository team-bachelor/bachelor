package cn.org.bachelor.iam.acm.fiegn;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.service.RoleServiceStub;
import cn.org.bachelor.iam.idm.service.ImSysParam;
import cn.org.bachelor.iam.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 告警触发器
 */
@Service("remoteRoleService")
@FeignClient(value = "bachelor-ms-iam-webservice", path="/acm/rpc/role", fallback = RemoteRoleServiceStubFallback.class)
public interface RemoteRoleServiceStub extends RoleServiceStub {


    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    List<Role> findViaOrg(String orgCode, String keyWord);

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    Role createRole(Role role);

    @Override
    @RequestMapping(value = "/{roleID}", method = RequestMethod.DELETE)
    void deleteRole(@PathVariable String roleID);

    @Override
    @RequestMapping(value = "", method = RequestMethod.PUT)
    void modifyRole(Role role);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.GET)
    List<UserVo> getRoleUsers(@PathVariable String roleCode);

    @Override
    @RequestMapping(value = "/roles/{userCode}", method = RequestMethod.GET)
    List<String> getUserRoles(@PathVariable String userCode);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.POST)
    void addUsersToRole(@PathVariable String roleCode, List<UserVo> users);

    @Override
    @RequestMapping(value = "/users/{roleCode}", method = RequestMethod.PUT)
    void deleteUsersFromRole(@PathVariable String roleCode, List<String> users);

    @Override
    @RequestMapping(value = "/{roleID}", method = RequestMethod.GET)
    Role selectByPrimaryKey(@PathVariable String roleID);
}
