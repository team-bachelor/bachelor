package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.acm.dao.RoleMapper;
import cn.org.bachelor.iam.acm.dao.RoleMenuMapper;
import cn.org.bachelor.iam.acm.dao.RolePermissionMapper;
import cn.org.bachelor.iam.acm.dao.UserRoleMapper;
import cn.org.bachelor.iam.acm.domain.Role;
import cn.org.bachelor.iam.acm.domain.RoleMenu;
import cn.org.bachelor.iam.acm.domain.RolePermission;
import cn.org.bachelor.iam.acm.domain.UserRole;
import cn.org.bachelor.iam.idm.service.IamSysParam;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author : liuzhuo
 * @描述:
 * @创建时间: 2018/10/27
 */
//@Service("dbRoleService")
@Service
@ConditionalOnProperty(prefix = "bachelor.iam",
        name = {"service-provider"}, havingValue = "db", matchIfMissing = true)
public class RoleService implements RoleServiceStub {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private IamSysService iamSysService;

    @Value("${bachelor.iam.client.id:}")
    private String clientId;

    /**
     * @param orgCode
     * @param keyWord
     * @return
     */
    @Override
    public List<Role> findViaOrg(String orgCode, String keyWord) {
        Example ex = new Example(Role.class);
        ex.setOrderByClause("NAME ASC");
        if (StringUtils.isNotEmpty(orgCode)) {
            ex.createCriteria()
                    .andEqualTo("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(keyWord)) {
            keyWord = "%" + keyWord + "%";
            ex.or().orLike("name", keyWord)
                    .orLike("code", keyWord);
        }
        return roleMapper.selectByExample(ex);
    }

    /**
     * @param role
     * @return
     */
    @Override
    public Role createRole(Role role) {
        role.setId(UUID.randomUUID().toString());
        role.setUpdateTime(new Date());
        role.setUpdateUser("system");
        Role r = new Role();
        r.setCode(role.getCode());
        r = roleMapper.selectOne(r);
        if (r != null) {
            throw new BusinessException("duplicate_role_code");
        }
        roleMapper.insert(role);
        return role;
    }

//    @Override
//    public List<UserVo> findUserByClientID(IamSysParam param) {
//        param.setClientId(clientConfig.getId());
//        List<UserVo> users = imSysService.findUsersByClientID(param);
//        return users;
//    }

    /**
     *
     * @param roleID
     */
    @Override
    public void deleteRole(String roleID) {
        Role role = roleMapper.selectByPrimaryKey(roleID);
        if (role == null) {
            return;
        }
        Example ex = new Example(RolePermission.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        rolePermissionMapper.deleteByExample(ex);
        ex = new Example(UserRole.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        userRoleMapper.deleteByExample(ex);
        ex = new Example(RoleMenu.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        roleMenuMapper.deleteByExample(ex);
        roleMapper.delete(role);
    }

    /**
     * @param role
     */
    @Override
    public void modifyRole(Role role) {
        if (StringUtils.isEmpty(role.getId())) {
            throw new BusinessException("role_id_must_be_exist");
        }
        Role roleDB = roleMapper.selectByPrimaryKey(role.getId());
        //
        roleDB.setName(role.getName());
        roleDB.setOrgCode(role.getOrgCode());
        roleDB.setUpdateTime(new Date());
        roleDB.setUpdateUser("system");
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 获得角色下的用户
     *
     * @param roleCode
     * @return
     */
    @Override
    public List<UserVo> getRoleUsers(String roleCode) {
        UserRole ur = new UserRole();
        ur.setRoleCode(roleCode);
        List<UserRole> userRoles = userRoleMapper.select(ur);
        IamSysParam usp = new IamSysParam();
        usp.setClientId(clientId);
        List<UserVo> remote = iamSysService.findUsersInApp(usp);
        Map<String, UserVo> rMap = new HashMap<>(userRoles.size());
        remote.forEach(i -> {
            rMap.put(i.getCode(), i);
        });
        List<UserVo> result = new ArrayList<>();
        userRoles.forEach(i -> {
            //访问用户服务调用查询
            if (rMap.containsKey(i.getUserCode())) {
                result.add(rMap.get(i.getUserCode()));
                return;
            }
            String id = i.getUserId();
            UserVo u = null;
            if (!StringUtils.isEmpty(id)) {
                u = iamSysService.findUsersDetail(id);
            }
            if(u == null){
                u = new UserVo();
                u.setCode(i.getUserCode());
                u.setName("该用户已不存在，请删除！");
            }
            result.add(u);
        });
        return result;
    }

    /**
     * 获得角色下的用户
     *
     * @param userCode
     * @return roleCodes
     */
    @Override
    public List<String> getUserRoles(String userCode) {
        UserRole ur = new UserRole();
        ur.setUserCode(userCode);
        List<UserRole> userRoles = userRoleMapper.select(ur);
        List<String> result = new ArrayList<>(userRoles.size());
        userRoles.forEach(userRole -> {
            if (!result.contains(userRole.getRoleCode())) {
                result.add(userRole.getRoleCode());
            }
        });
        return result;
    }

    /**
     * @param roleCode
     * @param users
     */
    @Override
    public void addUsersToRole(String roleCode, List<UserVo> users) {
        if (StringUtils.isEmpty(roleCode)) {
            throw new BusinessException("role_code_must_be_exist");
        }
        for (UserVo user : users) {
            UserRole ur = new UserRole();
            ur.setRoleCode(roleCode);
            ur.setUserCode(user.getCode());
            ur.setUserId(user.getId());
//            ur.setUserOrgId(user.getOrgId());
//            ur.setUserDeptName(user.getDeptName());
//            ur.setUserDeptPath(user.getDeptPath());
            if (userRoleMapper.selectOne(ur) != null) {
                throw new BusinessException("user_already_in_role", roleCode, user.getCode());
            }
            ur.setId(UUID.randomUUID().toString());
            ur.setUpdateTime(new Date());
            ur.setUpdateUser("system");
            userRoleMapper.insert(ur);
        }
    }

    /**
     * @param roleCode
     * @param users
     */
    @Override
    public void deleteUsersFromRole(String roleCode, List<String> users) {
        Example ex = new Example(UserRole.class);
        ex.createCriteria()
                .andEqualTo("roleCode", roleCode)
                .andIn("userCode", users);
        userRoleMapper.deleteByExample(ex);
    }


    /**
     * @param roleID
     * @return
     */
    @Override
    public Role selectByPrimaryKey(String roleID) {
        return roleMapper.selectByPrimaryKey(roleID);
    }
}
