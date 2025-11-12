package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.pojo.IamUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 角色服务
 */
@Component
@ConditionalOnProperty(prefix = "bachelor.iam",
        name = {"service-provider"}, havingValue = "rpc")
public class RoleServiceClientFallback implements RoleServiceClient {

    @Override
    public List<Role> findViaOrg(String orgCode, String keyWord) {
        return null;
    }

    @Override
    public Role createRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(@PathVariable("roleID") String roleID) {

    }

    @Override
    public void modifyRole(Role role) {

    }

    @Override
    public List<IamUser> getRoleUsers(@PathVariable("roleCode") String roleCode) {
        return null;
    }

    @Override
    public List<IamUser> getLocalRoleUsers(String roleCode) {
        return null;
    }

    @Override
    public List<String> getUserRoles(@PathVariable("userCode") String userCode) {
        return null;
    }

    @Override
    public void addUsersToRole(@PathVariable("roleCode") String roleCode, List<IamUser> users) {

    }

    @Override
    public void deleteUsersFromRole(@PathVariable("roleCode") String roleCode, List<String> users) {

    }

    @Override
    public Role selectByPrimaryKey(@PathVariable("roleID") String roleID) {
        return null;
    }
}
