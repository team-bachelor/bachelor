package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.idm.service.IamSysParam;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.vo.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("defaultLocalIamService")
public class DefaultLocalIamService implements IamSysService {

    @Override
    public AppVo findAppByCode(String appCode) {
        return null;
    }

    @Override
    public List<AppVo> findUserApps(String userId) {
        return null;
    }

    @Override
    public List<UserVo> findUsersInApp(String appID) {
        return null;
    }

    @Override
    public List<UserVo> findUsersInApp(IamSysParam param) {
        return null;
    }

    @Override
    public UserVo findUsersDetail(String userId) {
        return null;
    }

    @Override
    public List<RoleVo> findUserRolesInApp(IamSysParam param) {
        return null;
    }

    @Override
    public List<UserVo> findUsersById(String... userIds) {
        return null;
    }

    @Override
    public List<UserVo> findUsers(IamSysParam param) {
        return null;
    }

    @Override
    public List<OrgVo> findAllOrgs() {
        return null;
    }

    @Override
    public OrgVo findOrg(String orgId) {
        return null;
    }

    @Override
    public List<OrgVo> findOrg(IamSysParam param) {
        return null;
    }

    @Override
    public DeptDetailVo findDeptDetail(String deptId) {
        return null;
    }

    @Override
    public List<OrgVo> findDeptsByOrgId(String orgId) {
        return null;
    }

    @Override
    public List<OrgVo> findDepts(IamSysParam param) {
        return null;
    }

    @Override
    public DataPermVo processDataPerm(String orgId, Set<String> deptIds, boolean isAdmin) {
        return null;
    }

    @Override
    public boolean assertIsAdmin(UserVo user) {
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
