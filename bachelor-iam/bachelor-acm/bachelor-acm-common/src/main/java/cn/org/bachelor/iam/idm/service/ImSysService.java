package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.iam.acm.vo.*;

import java.util.List;
import java.util.Set;

/**
 *
 * @author liuzhuo
 */
public interface ImSysService {

    /**
     * @param user 用户信息
     * @return 用户是否为系统管理员
     */
    boolean checkUserIsAdmin(UserVo user);

    /**
     * @param clientID client id
     * @param userID 用户id
     * @param orgID 机构id
     * @param astoken astoken
     * @return 在当前应用中的用户角色
     */
    List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID, String astoken);

    /**
     * @param clientID client id
     * @param userID 用户id
     * @param orgID 机构id
     * @return 在当前应用中的用户角色
     */
    List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID);

    /**
     * @param clientID client id
     * @return 可访问指定应用的所有用户列表
     */
    List<UserVo> findUsersByClientID(String clientID);

    /**
     * @param param
     * @return
     */
    List<UserVo> findUsersByClientID(ImSysParam param);

    /**
     * @param userId 用户id
     * @return 用户详细信息
     */
    List<UserVo> findUsersDetail(String userId);

    /**
     * @param deptId 部门id
     * @return 部门详细信息
     */
    DeptDetailVo findDeptDetail(String deptId);

    /**
     * @param userId 用户id
     * @return 应用列表
     */
    List<AppVo> findAppsByUserId(String userId);

    /**
     * @param appCode 应用编码
     * @return 应用信息
     */
    AppVo findAppByCode(String appCode);

    /**
     * @param userIds 用户id（可多个）
     * @return 用户列表
     */
    List<UserVo> findUserByIds(String userIds);

    /**
     * @param orgId 机构列表
     * @param keyWord 关键词
     * @return 用户列表
     */
    List<UserVo> findUsers(String orgId, String keyWord);

    /**
     * @param param 参数
     * @return 用户列表
     */
    ImSysResult<List<UserVo>> findUsers(ImSysParam param);

    /**
     * @param orgId 机构id
     * @param deptId 部门id
     * @param userNameParttern 用户名称（模糊查询）
     * @return 用户列表
     */
    List<UserVo> findUsers(String orgId, String deptId, String userNameParttern);

    /**
     * @param orgId 机构id
     * @param userId 用户id
     * @param userCode 用户编码
     * @return 用户信息
     */
    UserVo findUser(String orgId, String userId, String userCode);

    /**
     * 获取所有机构
     * @return 机构列表
     */
    List<OrgVo> findAllOrgs();

    /**
     * @param orgId 机构id
     * @return 机构列表
     */
    List<OrgVo> findOrgs(String orgId);

    /**
     * @param id 机构id
     * @param code 机构编码
     * @param name 机构名称
     * @return 机构列表
     */
    List<OrgVo> findOrgs(String id, String code, String name);

    /**
     * @param orgId 机构id（筛选范围）
     * @return 部门列表
     */
    List<OrgVo> findDepts(String orgId);

    /**
     * @param orgId 机构id（筛选范围）
     * @param tree 是否返回树形（true返回树形，false返回平面）
     * @return 部门列表
     */
    List<OrgVo> findDepts(String orgId, boolean tree);

    /**
     * @param orgId 机构id（筛选范围）
     * @param deptId 部门id（筛选范围）
     * @param tree 是否返回树形（true返回树形，false返回平面）
     * @return 部门列表
     */
    List<OrgVo> findDepts(String orgId, String deptId, boolean tree);

    /**
     * @param orgId 机构id
     * @param deptId 部门id
     * @return 部门信息（找不到返回null）
     */
    OrgVo findDept(String orgId, String deptId);

    /**
     * @param orgId 机构id（筛选范围）
     * @param deptId 部门id（筛选范围）
     * @param tree 是否返回树形（true返回树形，false返回平面）
     * @param level 向下获取的层级
     * @return 部门列表
     */
    List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level);

    /**
     * @param orgId 机构id（筛选范围）
     * @param deptId 部门id（筛选范围）
     * @param tree 是否返回树形（true返回树形，false返回平面）
     * @param level 向下获取的层级
     * @param pid 父级部门id
     * @return 部门列表
     */
    List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level, String pid);

    /**
     * @param orgId 机构id（筛选范围）
     * @param deptIds 部门id（筛选范围）
     * @param isAdmin 是否是管理员
     * @return 数据权限
     */
    DataPermVo processDataPerm(String orgId, Set<String> deptIds, boolean isAdmin);

    /**
     * @param account 用户账号
     */
    void logout(String account);

    /**
     * @param account 用户账号
     * @param refreshToken IM系统发放的refreshToken
     * @param expire 过期时间
     */
    void saveRefreshToken(String account, String refreshToken, long expire);

    /**
     * @param account 用户账号
     * @return IM系统发放的refreshToken
     */
    String getRefreshToken(String account);
}
