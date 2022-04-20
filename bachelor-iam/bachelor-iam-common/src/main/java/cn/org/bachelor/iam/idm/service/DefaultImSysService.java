package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.core.exception.RemoteException;
import cn.org.bachelor.core.exception.SystemException;
import cn.org.bachelor.iam.IamValueHolderService;
import cn.org.bachelor.iam.idm.exception.ImSysException;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.SignSecurityOAuthClient;
import cn.org.bachelor.iam.oauth2.client.URLConnectionClient;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthResourceRequest;
import cn.org.bachelor.iam.oauth2.response.OAuthResourceResponse;
import cn.org.bachelor.iam.vo.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @描述 默认的用户平台服务提供类，如果不加载UP相关包则该类也不加载。
 *       若更换用户平台则需要实现UserSysService中的接口。
 * @author liuzhuo
 * @创建时间 2018/11/9
 * @更新履历  2021/01/20 规范了配置方式，将配置全部转移到OAuth2ClientConfig中。
 */
@Service
@ConditionalOnClass(OAuth2Client.class)
public class DefaultImSysService implements ImSysService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultImSysService.class);
    private ObjectMapper jsonMapper = new ObjectMapper();
    @Autowired
    private IamValueHolderService valueHolder;

    @Autowired
    private OAuth2CientConfig clientConfig;

    /**
     * @param valueHolder AuthValueHolderService
     * @see IamValueHolderService
     */
    public DefaultImSysService(IamValueHolderService valueHolder) {
        this.valueHolder = valueHolder;
    }

    private class AdminCache {
        private boolean isAdmin;
        private long time;

        public AdminCache(boolean isadmin, long time) {
            this.isAdmin = isadmin;
            this.time = time;
        }
    }

    private static Map<String, AdminCache> adminCahce = new HashMap<>();

    @Override
    public boolean checkUserIsAdmin(UserVo user) {
        logger.info("用户：[" + user.getName() + "]获取管理员标志");
        boolean isadmin = false;

        try {
            //2020.1.1 新加了一段缓存，2分钟之内同一用户不重复取用户系统。 lz
            if (StringUtils.isNotEmpty(user.getId()) && StringUtils.isNotEmpty(user.getOrgId())) {
                long now = new Date().getTime();
                if (!adminCahce.containsKey(user.getId()) && adminCahce.get(user.getId()).time < (now - 120000)) {
                    isadmin = isAdministrator(user);
                    adminCahce.remove(user.getId());
                    adminCahce.put(user.getId(), new AdminCache(isadmin, now));
                } else {
                    isadmin = adminCahce.get(user.getId()).isAdmin;
                }

            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        user.setAdministrator(isadmin);
        logger.info("用户：[" + user.getName() + "]管理员标志:" + isadmin);
        return isadmin;
    }

    private boolean isAdministrator(UserVo user) {
        if (user.getAccessToken() == null) {
            return false;
        }
        List<RoleVo> roles = findUserRolesInClient(clientConfig.getId(), user.getId(), user.getOrgId(), user.getAccessToken());
        AtomicBoolean result = new AtomicBoolean(false);
        roles.forEach(roleVo -> {
            if ("super_admin".equals(roleVo.getCode())) {
                result.set(true);
            }
        });
        return result.get();
    }

    @Override
    public List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID, String astoken) {
        if (StringUtils.isEmpty(clientID) || StringUtils.isEmpty(userID) || StringUtils.isEmpty(orgID)) {
            throw new BusinessException("clientID_userID_orgID_cannot_null_both");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("clientId", clientID);
        param.put("userId", userID);
        param.put("orgId", orgID);
        String json = callApi(clientConfig.getRsURL().getMtUserRoles(), "GET", param, astoken);
        List<RoleVo> roles = resolveJsonList(json, RoleVo.class);
        return roles;
    }

    @Override
    public List<RoleVo> findUserRolesInClient(String clientID, String userID, String orgID) {
        UserVo user = valueHolder.getCurrentUser();
        String token = null;
        if (user != null && user.getAccessToken() != null) {
            token = user.getAccessToken();
        }
        return findUserRolesInClient(clientID, userID, orgID, token);
    }

    @Override
    public List<UserVo> findUsersByClientID(String clientID) {
        ImSysParam param = new ImSysParam();
        param.setClientId(clientID);
        return findUsersByClientID(param);
    }

    @Override
    public List<UserVo> findUsersByClientID(ImSysParam param) {
        if (StringUtils.isEmpty(param.getClientId())) {
            throw new BusinessException("clientID_cannot_be_null");
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("clientId", param.getClientId());
        if (StringUtils.isNotEmpty(param.getDeptId())) {
            paramMap.put("deptId", param.getDeptId());
        }
        if (StringUtils.isNotEmpty(param.getUserName())) {
            paramMap.put("userName", param.getUserName());
        }
        if (StringUtils.isNotEmpty(param.getDeptName())) {
            paramMap.put("deptName", param.getDeptName());
        }
        if (StringUtils.isNotEmpty(param.getPage()) || StringUtils.isNotEmpty(param.getPageSize())) {
            paramMap.put("pageSize", param.getPageSize());
            paramMap.put("page", param.getPage());
        } else {
            paramMap.put("pageSize", Integer.toString(2000));
        }
        String json = callApi(clientConfig.getRsURL().getUserByClientID(), "GET", paramMap);
        List<UserVo> users = resolveJsonList(json, UserVo.class);
        return users;
    }

    @Override
    public List<UserVo> findUsersDetail(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException("userId_cannot_null");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", userId);
        String json = callApi(clientConfig.getRsURL().getUserDetails(), "GET", param);
        List<UserVo> voList = resolveJsonList(json, UserVo.class);
        return voList;
    }

    @Override
    public DeptDetailVo findDeptDetail(String deptId) {
        if (StringUtils.isEmpty(deptId)) {
            throw new BusinessException("deptId_cannot_null");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", deptId);
        String json = callApi(clientConfig.getRsURL().getDeptDetails(), "GET", param);
        DeptDetailVo voList = resolveJsonObject(json, DeptDetailVo.class);
        return voList;
    }

    @Override
    public List<AppVo> findAppsByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException("userId_cannot_null");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("userId", userId);
        String json = callApi(clientConfig.getRsURL().getAppsByUserId(), "GET", param);
        List<AppVo> appVoList = resolveJsonList(json, AppVo.class);
        return appVoList;
    }

    @Override
    public AppVo findAppByCode(String appCode) {
        if (StringUtils.isEmpty(appCode)) {
            throw new BusinessException("appCode_cannot_null");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("code", appCode);
        String json = callApi(clientConfig.getRsURL().getApp(), "GET", param);
        return resolveJsonObject(json, AppVo.class);
    }

    @Override
    public List<UserVo> findUserByIds(String userIds) {
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isEmpty(userIds) && StringUtils.isEmpty(userIds)) {
            throw new BusinessException("userIds_cannot_null");
        }
        if (StringUtils.isNotEmpty(userIds)) {
            param.put("userIds", userIds);
        }
        String json = callApi(clientConfig.getRsURL().getUserByIds(), "GET", param);
        List<UserVo> users = resolveJsonList(json, UserVo.class);
        return users;
    }


    /**
     * @param orgId
     * @param keyWord
     * @return
     */
    @Override
    public List<UserVo> findUsers(String orgId, String keyWord) {
        return findUsers(orgId, null, keyWord);
    }

    @Override
    public ImSysResult<List<UserVo>> findUsers(ImSysParam param) {
        String json = callApi(clientConfig.getRsURL().getUsers(), "GET", param.toParamMap());
        return resolveJson2Result(json, UserVo.class, true);
    }

    @Override
    public List<UserVo> findUsers(String orgId, String deptId, String userNameParttern) {
        if (StringUtils.isEmpty(orgId)) {
            throw new BusinessException("orgId_can_not_null_or_empty");
        }
        ImSysParam param = new ImSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setUserName(userNameParttern);
        return findUsers(param).getRows();
    }

    @Override
    public UserVo findUser(String orgId, String userId, String userCode) {
        if (StringUtils.isEmpty(orgId) && StringUtils.isEmpty(userId)) {
            throw new BusinessException("orgId_userId_cannot_null_both");
        }
        ImSysParam param = new ImSysParam();
        param.setOrgId(orgId);
        param.setUserId(userId);
        param.setUserCode(userCode);
        List<UserVo> users = findUsers(param).getRows();
        if (users != null && users.size() != 0)
            return users.get(0);
        return null;
    }

    /**
     * <p>findAllOrgs : 查询所有的机构列表</p>
     *
     * @param
     * @return 机构列表
     */
    @Override
    public List<OrgVo> findAllOrgs() {
        return findOrgs(null);
    }

    /**
     * <p> : 根据orgId，查询指定的机构信息</p>
     *
     * @param orgId 机构ID
     * @return 机构列表
     */
    @Override
    public List<OrgVo> findOrgs(String orgId) {
        return findOrgs(orgId, null, null);
    }

    /**
     * 查询机构
     *
     * @param id   机构ID
     * @param code 机构编码
     * @param name 机构名称（模糊查询）
     * @return
     */
    @Override
    public List<OrgVo> findOrgs(String id, String code, String name) {
        //获取组织机构树
        Map<String, String> param = new HashMap<String, String>();

        if (StringUtils.isNotEmpty(id))
            param.put("id", id);
        if (StringUtils.isNotEmpty(code))
            param.put("code", code);
        if (StringUtils.isNotEmpty(name))
            param.put("name", name);
        param.put("pageSize", "1000");
        String json = callApi(clientConfig.getRsURL().getOrgs(), "GET", param);
        return resolveJsonList(json, OrgVo.class);
    }

    @Override
    public List<OrgVo> findDepts(String orgId) {
        return findDepts(orgId, null, false);
    }

    @Override
    public List<OrgVo> findDepts(String orgId, boolean tree) {
        return findDepts(orgId, null, tree);
    }

    @Override
    public List<OrgVo> findDepts(String orgId, String deptId, boolean tree) {
        return findDepts(orgId, deptId, tree, null);
    }

    /**
     * 查找子部门
     *
     * @param orgId  机构ID
     * @param deptId 父机构ID
     * @return 部门信息
     */
    @Override
    public OrgVo findDept(String orgId, String deptId) {
        List<OrgVo> orgs = findDepts(orgId, deptId, true, null);
        for (OrgVo org : orgs) {
            if (org.getId().equals(deptId)) {
                if (org.getSubOrgs() != null) {
                    org.getSubOrgs().forEach(sub -> {
                        sub.setSubOrgs(null);
                    });
                }
                return org;
            }
        }
        return null;
    }

    /**
     * 获取当前机构的部门
     *
     * @param orgId  机构ID
     * @param deptId 指定的部门ID
     * @param tree   是否返回树形结构，true是树形，false是平面
     * @param level  层级数，-1代表无限，0代表本级
     * @return
     */
    @Override
    public List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level) {
        return findDepts(orgId, deptId, tree, level, null);
    }

    @Override
    public List<OrgVo> findDepts(String orgId, String deptId, boolean tree, Integer level, String pid) {
        //获取组织机构树
        if (StringUtils.isEmpty(orgId)) {
            throw new BusinessException("org_id_can_not_be_null_or_empty");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("orgId", orgId);//not null
        if (StringUtils.isNotEmpty(deptId)) {
            param.put("id", deptId);//not null
        }
        if (level == null) {
            level = 0;
        }
        if (StringUtils.isNotEmpty(pid)) {
            param.put("pid", pid);
        }
        param.put("level", level.toString());
        String json = callApi(clientConfig.getRsURL().getDepts(), "GET", param);
        List<OrgVo> flatOrgs = resolveJsonList(json, OrgVo.class);
        if (!tree) {
            return flatOrgs;
        }
        return flatToTree(flatOrgs);
    }

    private List<OrgVo> flatToTree(List<OrgVo> flatOrgs) {
        Map<String, OrgVo> orgsMap = new HashMap<>(flatOrgs.size());
        //构建树形结构
        for (OrgVo o : flatOrgs) {
            orgsMap.put(o.getId(), o);
        }
        //删除有父节点的组织,保留一级部门
        for (OrgVo o : flatOrgs) {
            if (StringUtils.isNotEmpty(o.getParentId())) {
                OrgVo p = orgsMap.get(o.getParentId());
                if (p == null) continue;
                if (p.getSubOrgs() == null) {
                    p.setSubOrgs(new ArrayList<OrgVo>());
                }
                p.getSubOrgs().add(orgsMap.remove(o.getId()));
            }
        }
        return new ArrayList<>(orgsMap.values());
    }

    /**
     * 计算数据权限对象（二级机构树+部门ID和用户列表）
     *
     * @param orgId
     * @param deptIds
     * @return
     */
    @Override
    public DataPermVo processDataPerm(String orgId, Set<String> deptIds, boolean isAdmin) {
        List<OrgVo> flatOrgs = findDepts(orgId);
        Map<String, OrgVo> orgsMap = new HashMap<>(flatOrgs.size());
        Map<String, OrgVo> deptMap = new HashMap<>(flatOrgs.size());
        //构建树形结构
        if (logger.isDebugEnabled()) {
            logger.debug("==============构建组织结构树=============");
            logger.debug("==============数据库中的组织机构=============");
        }
        for (OrgVo o : flatOrgs) {
            orgsMap.put(o.getId(), o);
            if (logger.isDebugEnabled())
                logger.debug(o.getId() + "=" + o.getName());
            //构建一个想要获取的部门map
            if (isAdmin || deptIds.contains(o.getId())) {
                deptMap.put(o.getId(), o);
            }
        }
        //构建父子关系
        for (OrgVo o : flatOrgs) {
            if (StringUtils.isNotEmpty(o.getParentId())) {
                OrgVo p = orgsMap.get(o.getParentId());
                o.setParent(p);
            }
        }
        //刷掉多余的节点
        if (isAdmin) {
            for (OrgVo o : flatOrgs) {
                o.setHold(true);
            }
        } else {
            for (String id : deptIds) {
                if (orgsMap.containsKey(id)) {
                    mark4Reserv(orgsMap.get(id));
                }
            }
        }
        Set<String> tempSet = new HashSet<>(orgsMap.keySet());
        for (String key : tempSet) {
            if (!orgsMap.get(key).isHold()) {
                if (logger.isDebugEnabled())
                    logger.debug("remove dept=" + orgsMap.get(key).getId() + "=" + orgsMap.get(key).getName());
                flatOrgs.remove(orgsMap.remove(key));
            }
        }
        //构建两层的树
        List<OrgVo> treeOrgs = new ArrayList<>();
        for (OrgVo org : orgsMap.values()) {
            //顶级部门为单位
            if (org.getParent() == null) {
                treeOrgs.add(org);
            }
        }
        if (logger.isDebugEnabled())
            logger.debug("==============当前用户可访问的组织机构=============");
        for (OrgVo org : orgsMap.values()) {
            if (logger.isDebugEnabled())
                logger.debug(org.getId() + "=" + org.getName());
            set2TopOrg(org.getParent(), org);
        }

        List<UserVo> users = findUsers(orgId, null);
        Map<String, List<UserVo>> userMap = new HashMap<>(flatOrgs.size());
        if (logger.isDebugEnabled())
            logger.debug("==============数据库中的用户=============");
        for (UserVo user : users) {
            if (logger.isDebugEnabled()) {
                logger.debug(user.getDeptId() + "|" + user.getId() + "|" + user.getName());
            }
            if (orgsMap.containsKey(user.getDeptId()) && (isAdmin || deptIds.contains(user.getDeptId()))) {
                setUserToTreeOrgs(orgsMap.get(user.getDeptId()), userMap, user);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("==============当前用户能访问的用户=============");
            for (String org : userMap.keySet()) {
                //logger.debug("组织机构=" + org + "=" + orgsMap.get(org).getName());
                for (UserVo user : userMap.get(org)) {
                    logger.debug(org + "=" + user.getId() + "=" + user.getName());
                }
            }
        }
        return new DataPermVo(treeOrgs, deptMap, userMap);
    }

//    @Autowired
//    protected StringRedisTemplate redisTemplate;
    //TODO 要解决redis依赖问题
    // 设置1天有效时间
    private long timeout = 1;
    private String delPrefix = "del_";
    private String bachelorPrefix = "bachelor";

    @Override
    public void logout(String account) {
//        if (!org.springframework.util.StringUtils.isEmpty(account)) {
//            redisTemplate.opsForValue().set(delPrefix + account, "LO", timeout, TimeUnit.DAYS);
//        }
    }

    @Override
    public void saveRefreshToken(String account, String refreshToken, long expire) {
//        if (!org.springframework.util.StringUtils.isEmpty(account)) {
//            redisTemplate.opsForValue().set(bachelorPrefix + account, refreshToken, expire, TimeUnit.SECONDS);
//        }
    }

    @Override
    public String getRefreshToken(String account) {
//        if (!org.springframework.util.StringUtils.isEmpty(account)) {
//            return redisTemplate.opsForValue().get(bachelorPrefix + account);
//        }
        return null;
    }

    private void setUserToTreeOrgs(OrgVo orgVo, Map<String, List<UserVo>> userMap, UserVo user) {
        if (orgVo == null) return;
        if (!userMap.containsKey(orgVo.getId())) {
            userMap.put(orgVo.getId(), new ArrayList<>());
        }
        if (!userMap.get(orgVo.getId()).contains(user)) {
            userMap.get(orgVo.getId()).add(user);
        }
        //setUserToTreeOrgs(orgVo.getParent(), userMap, user);
    }

    private void set2TopOrg(OrgVo parent, OrgVo org) {
        if (parent == null) return;
        if (parent.getParent() != null) {
            set2TopOrg(parent.getParent(), org);
        } else {
            if (parent.getSubOrgs() == null) {
                parent.setSubOrgs(new ArrayList<>());
            }
            parent.getSubOrgs().add(org);
        }
    }

    private void mark4Reserv(OrgVo orgVo) {
        if (orgVo.isHold()) return;
        orgVo.setHold(true);
        if (orgVo.getParent() != null) {
            if (!orgVo.getParent().isHold()) {
                mark4Reserv(orgVo.getParent());
            }
        }
    }

    private <T> List<T> resolveJsonList(String json, Class clazz) {
        return resolveJson(json, clazz, true);
    }

    private <T> T resolveJsonObject(String json, Class clazz) {
        return resolveJson(json, clazz, false);
    }

    private <T> T resolveJson(String json, Class clazz, boolean asList) {
        return (T) resolveJson2Result(json, clazz, asList).getRows();
    }

    private <T> ImSysResult<T> resolveJson2Result(String json, Class clazz, boolean asList) {
        try {

            //Map<String, Object> tokenMap = jsonMapper.readValue(json, Map.class);
            JsonNode node = jsonMapper.readTree(json);

            String result = node.get("result").asText();
            String message = node.get("message").asText();
            int total = node.get("total").asInt();
            if ("1001".equals(result)) {
                throw new ImSysException("ACCESS_TOKEN_EXPIRED");
            } else if (!"200".equals(result)) {
                throw new RemoteException(result + ":" + message);
            }
            ImSysResult<T> userSysResult = new ImSysResult();
            userSysResult.setResult(result);
            userSysResult.setMessage(message);
            userSysResult.setTotal(total);
            String rowsString = node.get("rows").toString();
            jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JavaType jvt = null;
            if (asList) {
                jvt = jsonMapper.getTypeFactory().constructParametricType(List.class, clazz);
            } else {
                jvt = jsonMapper.getTypeFactory().constructType(clazz);
            }
            userSysResult.setRows(jsonMapper.readValue(rowsString, jvt));
            return userSysResult;
        } catch (IOException e) {
            throw new BusinessException("invalid return string", e);
        }
    }

    private String callApi(String url, String methodType,
                          Map<String, String> param, String astoken) {
        if (param == null) {
            param = new HashMap<>(2);
        }
//        param.put("currPage", "1");
//        param.put("pageSize", "1000");
        logger.debug("访问接口：" + url);
        logger.debug("输入参数：\n" + param);
        OAuthResourceResponse resourceResponse = null;
        try {
            SignSecurityOAuthClient oAuthClient = new SignSecurityOAuthClient(
                    new URLConnectionClient());

            DefaultOAuthResourceRequest userInfoRequest = new DefaultOAuthResourceRequest(
                    url, methodType);

            String token = "";
            if (astoken == null) {
                UserVo user = valueHolder.getCurrentUser();
                if (user != null && user.getAccessToken() != null) {
                    token = user.getAccessToken();
                }else{
                    OAuth2ClientCertification upCC =
                            (OAuth2ClientCertification) valueHolder.getValueHolderService().getSessionAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
                    if(upCC == null){
                        throw new BusinessException("access token can not be null!");
                    }
                    token = upCC.getAccessToken();
                }
            } else {
                token = astoken;
            }
            userInfoRequest.setAccessToken(token);
            if (param != null && param.size() > 0) {
                for (String key : param.keySet()) {
                    userInfoRequest.setParameter(key, param.get(key));
                }
            }
            resourceResponse = oAuthClient.resource(userInfoRequest, OAuthResourceResponse.class);
        } catch (Exception e) {
            throw new SystemException("用户平台异常，请稍后再试！", e);
        }
        String json = resourceResponse.getBody();
        logger.debug("invoke user platform : 返回json: \n" + json);
        return json;
    }

    private String callApi(String url, String methodType,
                          Map<String, String> param) {
        return callApi(url, methodType, param, null);
    }
}
