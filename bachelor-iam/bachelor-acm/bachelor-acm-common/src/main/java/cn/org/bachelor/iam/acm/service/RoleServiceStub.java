package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.vo.UserVo;

import java.util.List;

/**
 *
 * @author LZ
 */
public interface RoleServiceStub {
    /**
     * @param orgCode
     * @param keyWord
     * @return
     */
    List<Role> findViaOrg(String orgCode, String keyWord);

    /**
     * @param role
     * @return
     */
    Role createRole(Role role);

//    /**
//     * @param param
//     * @return
//     */
//    List<UserVo> findUserByClientID(ImSysParam param);

    /**
     * @param roleID
     */
    void deleteRole(String roleID);

    /**
     * @param role
     */
    void modifyRole(Role role);

    /**
     * @param roleCode
     * @return
     */
    List<UserVo> getRoleUsers(String roleCode);

    /**
     * @param userCode
     * @return
     */
    List<String> getUserRoles(String userCode);

    /**
     * @param roleCode
     * @param users
     */
    void addUsersToRole(String roleCode, List<UserVo> users);

    /**
     * @param roleCode
     * @param users
     */
    void deleteUsersFromRole(String roleCode, List<String> users);

    /**
     * @param roleID 角色ID
     * @return 角色信息
     */
    Role selectByPrimaryKey(String roleID);
}
