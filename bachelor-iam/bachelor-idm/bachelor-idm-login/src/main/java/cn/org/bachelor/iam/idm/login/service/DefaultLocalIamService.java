package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.idm.service.IamSysParam;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.pojo.*;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("defaultLocalIamService")
public class DefaultLocalIamService implements IamSysService {

    @Override
    public IamApp findAppByCode(String appCode) {
        return null;
    }

    @Override
    public List<IamApp> findUserApps(String userId) {
        return null;
    }

    @Override
    public List<IamUser> findUsersInApp(String appID) {
        return null;
    }

    @Override
    public List<IamUser> findUsersInApp(IamSysParam param) {
        return null;
    }

    @Override
    public IamUser findUsersDetail(String userId) {
        return null;
    }

    @Override
    public List<IamRole> findUserRolesInApp(IamSysParam param) {
        return null;
    }

    @Override
    public List<IamUser> findUsersById(String... userIds) {
        return null;
    }

    @Override
    public List<IamUser> findUsers(IamSysParam param) {
        return null;
    }

    @Override
    public List<IamOrg> findAllOrgs() {
        return null;
    }

    @Override
    public IamOrg findOrg(String orgId) {
        return null;
    }

    @Override
    public List<IamOrg> findOrg(IamSysParam param) {
        return null;
    }

    @Override
    public IamDept findDeptDetail(String deptId) {
        return null;
    }

    @Override
    public List<IamOrg> findDeptsByOrgId(String orgId) {
        return null;
    }

    @Override
    public List<IamOrg> findDepts(IamSysParam param) {
        return null;
    }

    @Override
    public DataPermission processDataPerm(String orgId, Set<String> deptIds, boolean isAdmin) {
        return null;
    }

    @Override
    public boolean assertIsAdmin(IamUser user) {
        return false;
    }

    @Override
    public Object login(Object account) {
        return null;
    }

    @Override
    public Object logout(Object account) {
        return null;
    }

    @Override
    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, Object refreshToken) {
        return null;
    }

    @Override
    public Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response, String code) {
        return null;
    }
}
