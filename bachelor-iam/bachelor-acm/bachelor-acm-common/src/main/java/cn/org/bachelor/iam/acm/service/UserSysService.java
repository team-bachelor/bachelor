package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.vo.*;

import java.util.List;
import java.util.Set;

public interface UserSysService {
    boolean checkUserIsAdmin(UserVo user);

    List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID, String astoken);

    List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID);

    List<UserVo> findUserByClientID(String clientID);

    List<UserVo> findUserByClientID(UserSysParam param);

    List<UserVo> findUserDetail(String userId);

    DeptDetailVo findDeptDetail(String deptId);

    List<AppVo> findAppsByUserId(String userId);

    AppVo findAppsByCode(String appCode);

    List<UserVo> findUserByIds(String userIds);

    List<UserVo> findUsers(String orgId, String keyWord);

    UserSysResult<List<UserVo>> findUsers(UserSysParam param);

    List<UserVo> findUsers(String orgId, String deptId, String userNameParttern);

    UserVo findUser(String orgId, String userId, String userCode);

    List<OrgVo> findAllOrgs();

    List<OrgVo> findOrgs(String orgId);

    List<OrgVo> findOrgs(String id, String code, String name);

    List<OrgVo> findDepts(String orgId);

    List<OrgVo> findDepts(String orgId, boolean tree);

    List<OrgVo> findDepts(String orgId, String deptId, boolean tree);

    OrgVo findChildDepts(String orgId, String deptId);

    List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level);

    List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level, String pid);

    DataPermVo processDataPerm(String orgId, Set<String> deptIds, boolean isAdmin);

    void logout(String account);

    void saveRefreshToken(String account, String refreshToken, long expire);

    String getRefreshToken(String account);
}
