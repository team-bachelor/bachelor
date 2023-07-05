package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.iam.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuzhuo
 */
public interface IamSysService {

    /**
     * @param appCode 应用编码
     * @return 应用信息
     */
    AppVo findAppByCode(String appCode);

    /**
     * @param userId 用户id
     * @return 应用列表
     */
    List<AppVo> findUserApps(String userId);

    /**
     * @param user 用户信息
     * @return 用户是否为系统管理员
     */
    boolean assertIsAdmin(UserVo user);

    /**
     * @param appID 应用id
     * @return 可访问指定应用的所有用户列表
     */
    List<UserVo> findUsersInApp(String appID);

    /**
     *
     * @param param 查询参数
     * @return
     */
    List<UserVo> findUsersInApp(IamSysParam param);

    /**
     * @param userId 用户id
     * @return 用户详细信息
     */
    UserVo findUsersDetail(String userId);

    /**
     * @param param: appID  应用id
     * @param param: userID 用户id
     * @param param: orgID  机构id
     * @return 在当前应用中的用户角色
     */
    List<RoleVo> findUserRolesInApp(IamSysParam param);


    /**
     * @param userIds 用户id（可多个）
     * @return 用户列表
     */
    List<UserVo> findUsersById(String... userIds);

    /**
     * @param param: orgId           机构id
     * @param param: deptId          部门id
     * @param param: userName 用户名称（模糊查询）
     * @return 用户列表
     */
    List<UserVo> findUsers(IamSysParam param);

//    /**
//     * @param orgId    机构id
//     * @param userId   用户id
//     * @param userCode 用户编码
//     * @return 用户信息
//     */
//    UserVo findUser(String orgId, String userId, String userCode);

    /**
     * 获取所有机构
     *
     * @return 机构列表
     */
    List<OrgVo> findAllOrgs();

    /**
     * @param orgId 机构id
     * @return 机构列表
     */
    OrgVo findOrg(String orgId);

    /**
     * 查询机构
     *
     * @param param: orgId   机构ID
     * @param param: orgCode 机构编码
     * @param param: orgName 机构名称（模糊查询）
     * @return
     */
    List<OrgVo> findOrg(IamSysParam param);

    /**
     * @param deptId 部门id
     * @return 部门详细信息
     */
    DeptDetailVo findDeptDetail(String deptId);

    /**
     * @param orgId 机构id（筛选范围）
     * @return 部门列表
     */
    List<OrgVo> findDeptsByOrgId(String orgId);

    /**
     * @param param: orgId  机构id（筛选范围）
     * @param param: deptId 部门id（筛选范围）
     * @param param: tree   是否返回树形（true返回树形，false返回平面）
     * @param param: level  向下获取的层级
     * @return 部门列表
     */
    List<OrgVo> findDepts(IamSysParam param);

    /**
     * @param orgId   机构id（筛选范围）
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
     * 刷新令牌
     * @param request
     * @param response
     * @param token 用于刷新的对象
     * @return
     */
    Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, Object token);

    /**
     * 获取用户是否为管理员
     * @param user
     * @return
     */
    boolean checkUserIsAdmin(UserVo user);

    /**
     *
     * @param request
     * @param response
     * @param code
     * @return
     */
    Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response, String code);
}
