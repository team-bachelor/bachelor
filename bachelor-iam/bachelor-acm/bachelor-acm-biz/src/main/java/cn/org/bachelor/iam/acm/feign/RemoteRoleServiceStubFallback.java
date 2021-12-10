package cn.org.bachelor.iam.acm.feign;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.vo.UserVo;
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
public class RemoteRoleServiceStubFallback implements RemoteRoleServiceStub {

    @Override
    public List<Role> findViaOrg(String orgCode, String keyWord) {
        return null;
    }

    @Override
    public Role createRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(@PathVariable String roleID) {

    }

    @Override
    public void modifyRole(Role role) {

    }

    @Override
    public List<UserVo> getRoleUsers(@PathVariable String roleCode) {
        return null;
    }

    @Override
    public List<String> getUserRoles(@PathVariable String userCode) {
        return null;
    }

    @Override
    public void addUsersToRole(@PathVariable String roleCode, List<UserVo> users) {

    }

    @Override
    public void deleteUsersFromRole(@PathVariable String roleCode, List<String> users) {

    }

    @Override
    public Role selectByPrimaryKey(@PathVariable String roleID) {
        return null;
    }
}
