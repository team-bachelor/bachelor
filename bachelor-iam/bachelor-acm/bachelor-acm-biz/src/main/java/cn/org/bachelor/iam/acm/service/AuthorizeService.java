package cn.org.bachelor.iam.acm.service;


import cn.org.bachelor.iam.IamValueHolderService;
import cn.org.bachelor.iam.acm.dao.*;
import cn.org.bachelor.iam.acm.domain.*;
import cn.org.bachelor.iam.acm.domain.ObjPermission;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import cn.org.bachelor.iam.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * @描述 鉴权相关的服务
 * @创建人 liuzhuo
 * @创建时间 2018/10/22
 */
@Service
public class AuthorizeService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeService.class);

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private OrgPermissionMapper orgPermissionMapper;

    @Autowired
    private ObjPermissionMapper permissionMapper;

    @Autowired
    private ObjDomainMapper objDomainMapper;

    @Autowired
    private ObjOperationMapper objOperationMapper;

    @Autowired
    private IamValueHolderService valueHolder;

    private static final String DEF_AUTH_OP_ALLOW = PermissionOptions.CheckLevel.NONE.toString();
    private static final String DEF_AUTH_OP_CHECK = PermissionOptions.CheckLevel.Authorized.toString();
    private static final String DEF_AUTH_OP_LOGON = PermissionOptions.CheckLevel.LOGON.toString();

    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode  对象编码
     * @param userCode 用户编码
     * @return 是否能访问
     */
    public boolean isAuthorized(String objCode, String userCode) {
        return isAuthorized(objCode, userCode, PermissionOptions.AccessType.INTERFACE);
    }

    public boolean isAuthorized(String objCode, String userCode, PermissionOptions.AccessType accessType) {
        if (StringUtil.isEmpty(objCode)) {
            return false;
        }
        ObjPermission example = new ObjPermission();
        example.setCode(objCode);
        example = permissionMapper.selectOne(example);
        //不需要验证则通过
        if (example == null ) {
            if(accessType == PermissionOptions.AccessType.INTERFACE) {
                return false;
            }else{
                return true;
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("obj=[" + objCode + "],user=[" + userCode + "],defAuthOp=[" + example.getDefAuthOp() + "];");
        }
        if (DEF_AUTH_OP_ALLOW.equals(example.getDefAuthOp())) {
            return true;
        } else if (DEF_AUTH_OP_LOGON.equals(example.getDefAuthOp()) && StringUtils.isNotEmpty(userCode)) {
            return true;
        } else
            //需要验证则验证
            if (DEF_AUTH_OP_CHECK.equals(example.getDefAuthOp())) {
                if (StringUtil.isNotEmpty(userCode)) {
                    Map<String, PermissionPoint> permMap = calUserPermission(userCode);
                    if (permMap.containsKey(objCode)) {
                        return true;
                    }
                }
            }
        return false;
    }

    /**
     * 计算当前用户的权限
     *
     * @param userCode 用户编码
     * @return 用户权限
     */
    public Map<String, PermissionPoint> calUserPermission(String userCode) {

        Map<String, PermissionPoint> result = new HashMap<>();
        boolean isadmin = false;
        if (userCode.equals(valueHolder.getCurrentUser().getCode())
                && valueHolder.getCurrentUser().isAdministrator()) {
            isadmin = true;
        }
        List<RolePermission> rpList = null;
        if (isadmin) {
            List<PermissionGroup> pglist = getPermissionGroupList(null);
            for (PermissionGroup pg : pglist) {
                for (PermissionPoint p : pg.getPerms()) {
                    result.put(p.getObjCode(), p);
                }
            }
            return result;
        } else {
            rpList = rolePermissionMapper.selectViaUserCode(userCode);
        }
        for (RolePermission p : rpList) {
            if (result.containsKey(p.getObjCode())) {
                continue;
            }
            result.put(p.getObjCode(),
                    new PermissionPoint(p.getId(), p.getObjCode(), p.getObjCode(),
                            p.getObjUri(), p.getObjOperate()));
        }

        List<UserPermission> upList = userPermissionMapper.selectViaUserCode(userCode);
        for (UserPermission p : upList) {
            if (result.containsKey(p.getObjCode())) {
                continue;
            }
            result.put(p.getObjCode(),
                    new PermissionPoint(p.getId(), p.getObjCode(), p.getObjCode(),
                            p.getObjUri(), p.getObjOperate()));
        }
        return result;
    }

    /**
     * @描述 取得备选权限列表（按组分开）
     * @param orgID 组织机构ID
     * @author liuzhuo
     * @创建时间 2018/10/27 11:16
     * @return 权限组列表
     */
    public List<PermissionGroup> getPermissionGroupList(String orgID) {
        Example example = new Example(ObjDomain.class);
        example.setOrderByClause("SEQ_ORDER ASC");
        List<ObjDomain> domains = objDomainMapper.selectByExample(example);
        List<ObjOperation> operationsList = objOperationMapper.selectAll();
        Map<String, String> opsMap = new HashMap<>(operationsList.size());
        for (ObjOperation ops : operationsList) {
            opsMap.put(ops.getCode(), ops.getName());
        }
        List<PermissionGroup> result = new ArrayList<>();
        for (ObjDomain d : domains) {
            PermissionGroup group = new PermissionGroup();
            result.add(group);
            group.setGroupName(d.getName());
            group.setGroupCode(d.getCode());
            example = new Example(ObjPermission.class);
            example.setOrderByClause("SEQ_ORDER ASC");
            example.createCriteria().andEqualTo("domainCode", d.getCode())
                    .andEqualTo("defAuthOp", DEF_AUTH_OP_CHECK);
            List<ObjPermission> objPermissions = permissionMapper.selectByExample(example);
            List<PermissionPoint> permList = Collections.EMPTY_LIST;
            group.setPerms(permList);
            if (objPermissions != null && objPermissions.size() != 0) {
                permList = new ArrayList<>(objPermissions.size());
                group.setPerms(permList);
                for (ObjPermission o : objPermissions) {
                    PermissionPoint p = forPermission(o, PermissionModel.ROLE);
                    String op = p.getObjOperate();
                    p.setOperateName(opsMap.containsKey(op) ? opsMap.get(op) : op);
                    permList.add(p);
                }
            }
        }
        return result;
    }

    /**
     * @param roleCode
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    public List<String> getRolePermission(String roleCode) {
        RolePermission rp = new RolePermission();
        rp.setRoleCode(roleCode);
        List<RolePermission> rolePerms = rolePermissionMapper.select(rp);
        List<String> rolePermCodes = null;
        if (rolePerms != null) {
            rolePermCodes = new ArrayList<>(rolePerms.size());
            for (RolePermission p : rolePerms) {
                rolePermCodes.add(p.getObjCode());
            }
        }
        return rolePermCodes;
    }

    private PermissionPoint forPermission(ObjPermission o, PermissionModel type) {
        PermissionPoint p = new PermissionPoint();
        p.setObjCode(o.getCode());
        p.setObjOperate(o.getOperate());
        p.setObjUri(o.getUri());
        p.setType(type);
        return p;
    }

    /**
     * @param roleCode
     * @param perms    当前角色拥有的所有权限列表
     * @author liuzhuo
     */
    public void setRolePermission(String roleCode, List<PermissionPoint> perms) {
        //设置查询的样例
        RolePermission rp = new RolePermission();
        rp.setRoleCode(roleCode);
        //查询数据库中的角色权限
        List<RolePermission> permsDB = rolePermissionMapper.select(rp);
        //以权限编码为key,以权限本身为value,构造一个map
        Map<String, RolePermission> rpDB = new HashMap<>(permsDB.size());
        for (RolePermission r : permsDB) {
            rpDB.put(r.getObjCode(), r);
        }
        //获取需要插入数据库的权限
        for (PermissionPoint r : perms) {
            // rpParam.put(r.getObjCode(), r);
            //判断数据库中是否已经存在该权限
            if (rpDB.containsKey(r.getObjCode())) {
                //如果存在则从map中删除这个权限(剩下的就是要删除的)
                rpDB.remove(r.getObjCode());
            } else {
                //如果不存在则插入数据库
                String userId = "system";
                UserVo userVo = valueHolder.getCurrentUser();
                if (userVo != null && StringUtils.isNotEmpty(userVo.getCode())) {
                    userId = userVo.getCode();
                }
                rolePermissionMapper.insert(forRolePermission(userId, roleCode, r));
            }
        }
        //将map中剩下的记录从数据库中删除
        for (RolePermission r : rpDB.values()) {
            rolePermissionMapper.deleteByPrimaryKey(r.getId());
        }
    }

    private RolePermission forRolePermission(String operator, String roleCode, PermissionPoint r) {
        RolePermission rp = new RolePermission();
        rp.setId(UUID.randomUUID().toString());
        rp.setRoleCode(roleCode);
        rp.setObjCode(r.getObjCode());
        rp.setObjUri(r.getObjUri());
        rp.setObjOperate(r.getObjOperate());
        rp.setUpdateTime(new Date());
        rp.setUpdateUser(operator);
        return rp;
    }

    /**
     * @param orgId
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    public List<String> getOrgPermission(String orgId) {
        OrgPermission rp = new OrgPermission();
        rp.setOrgCode(orgId);
        List<OrgPermission> orgPerms = orgPermissionMapper.select(rp);
        List<String> orgPermCodes = null;
        if (orgPerms != null) {
            orgPermCodes = new ArrayList<>(orgPerms.size());
            for (OrgPermission p : orgPerms) {
                orgPermCodes.add(p.getObjCode());
            }
        }
        return orgPermCodes;
    }

    /**
     * @param orgId
     * @param perms 当前机构拥有的所有权限列表
     * @author liuzhuo
     */
    public void setOrgPermission(String orgId, List<PermissionPoint> perms) {
        //设置查询的样例
        OrgPermission rp = new OrgPermission();
        rp.setOrgCode(orgId);
        //查询数据库中的机构权限
        List<OrgPermission> permsDB = orgPermissionMapper.select(rp);
        //以权限编码为key,以权限本身为value,构造一个map
        Map<String, OrgPermission> rpDB = new HashMap<>(permsDB.size());
        for (OrgPermission r : permsDB) {
            rpDB.put(r.getObjCode(), r);
        }
        //获取需要插入数据库的权限
        for (PermissionPoint r : perms) {
            // rpParam.put(r.getObjCode(), r);
            //判断数据库中是否已经存在该权限
            if (rpDB.containsKey(r.getObjCode())) {
                //如果存在则从map中删除这个权限(剩下的就是要删除的)
                rpDB.remove(r.getObjCode());
            } else {
                //如果不存在则插入数据库
                String userId = "system";
                UserVo userVo = valueHolder.getCurrentUser();
                if (userVo == null || StringUtils.isEmpty(userVo.getCode())) {
                    userId = userVo.getCode();
                }
                orgPermissionMapper.insert(forOrgPermission(userId, orgId, r));
            }
        }
        //将map中剩下的记录从数据库中删除
        for (OrgPermission r : rpDB.values()) {
            orgPermissionMapper.deleteByPrimaryKey(r.getId());
        }
    }

    private OrgPermission forOrgPermission(String operator, String orgCode, PermissionPoint r) {
        OrgPermission rp = new OrgPermission();
        rp.setId(UUID.randomUUID().toString());
        rp.setOrgCode(orgCode);
        rp.setObjCode(r.getObjCode());
        rp.setObjUri(r.getObjUri());
        rp.setObjOperate(r.getObjOperate());
        rp.setUpdateTime(new Date());
        rp.setUpdateUser(operator);
        return rp;
    }
}
