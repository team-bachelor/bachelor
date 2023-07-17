package cn.org.bachelor.iam.oauth2.client.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.exception.RemoteException;
import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.exception.IamBusinessException;
import cn.org.bachelor.iam.exception.IamSystemException;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.idm.service.IamSysParam;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.SignSecurityOAuthClient;
import cn.org.bachelor.iam.oauth2.client.URLConnectionClient;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientHelper;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthResourceRequest;
import cn.org.bachelor.iam.oauth2.response.OAuthResourceResponse;
import cn.org.bachelor.iam.vo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuzhuo
 * @描述 默认的用户平台服务提供类，如果不加载UP相关包则该类也不加载。
 * 若更换用户平台则需要实现UserSysService中的接口。
 * @创建时间 2018/11/9
 * @更新履历 2021/01/20 规范了配置方式，将配置全部转移到OAuth2ClientConfig中。
 */
@Service
@ConditionalOnClass(OAuth2Client.class)
public class Oauth2IamSysService implements IamSysService {

    private static final Logger logger = LoggerFactory.getLogger(Oauth2IamSysService.class);

    @Autowired
    private IamContext iamContext;

    @Autowired
    private OAuth2CientConfig clientConfig;

    /**
     * @param iamContext IamContext
     * @see IamContext
     */
    public Oauth2IamSysService(IamContext iamContext) {
        this.iamContext = iamContext;
    }

    private static Map<String, AdminCache> adminCache = new HashMap<>();

    private class AdminCache {
        private boolean isAdmin;
        private long time;

        public AdminCache(boolean isadmin, long time) {
            this.isAdmin = isadmin;
            this.time = time;
        }
    }

    @Override
    public Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response, String code) {
        OAuth2Client client = new OAuth2Client(clientConfig, ClientHelper.startClient(request, response));
        JSONObject user = null;
        try {
            user = client.bindUser2Session(code);

        } catch (OAuthBusinessException e) {
            throw new IamBusinessException(e);
        }
        ClientHelper.stopClient();
        return user;
    }

    @Override
    public boolean assertIsAdmin(UserVo user) {
        logger.info("用户：[" + user.getName() + "]获取管理员标志");
        boolean isadmin = false;

        try {
            //2020.1.1 新加了一段缓存，2分钟之内同一用户不重复取用户系统。 lz
            if (StringUtils.isNotEmpty(user.getId()) && StringUtils.isNotEmpty(user.getOrgId())) {
                long now = new Date().getTime();
                if (!adminCache.containsKey(user.getId()) && adminCache.get(user.getId()).time < (now - 120000)) {
                    isadmin = isAdministrator(user);
                    adminCache.remove(user.getId());
                    adminCache.put(user.getId(), new AdminCache(isadmin, now));
                } else {
                    isadmin = adminCache.get(user.getId()).isAdmin;
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
        IamSysParam param = new IamSysParam();
        param.setClientId(clientConfig.getId());
        param.setUserId(user.getId());
        param.setOrgId(user.getOrgId());
        List<RoleVo> roles = findUserRolesInClient(param, user.getAccessToken());
        AtomicBoolean result = new AtomicBoolean(false);
        roles.forEach(roleVo -> {
            if ("super_admin".equals(roleVo.getCode())) {
                result.set(true);
            }
        });
        return result.get();
    }

    private List<RoleVo> findUserRolesInClient(IamSysParam param, String astoken) {
        if (StringUtils.isEmpty(param.getClientId())
                || StringUtils.isEmpty(param.getUserId())
                || StringUtils.isEmpty(param.getOrgId())) {
            throw new BusinessException("clientID_userID_orgID_cannot_null_both");
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("clientId", param.getClientId());
        paramMap.put("userId", param.getUserId());
        paramMap.put("orgId", param.getOrgId());
        String json = callApi(clientConfig.getRsURL().getMtUserRoles(), "GET", paramMap, astoken);
        List<RoleVo> roles = resolveJsonList(json, RoleVo.class);
        return roles;
    }

    @Override
    public List<RoleVo> findUserRolesInApp(IamSysParam param) {
        UserVo user = iamContext.getUser();
        String token = null;
        if (user != null && user.getAccessToken() != null) {
            token = user.getAccessToken();
        }
        return findUserRolesInClient(param, token);
    }

    @Override
    public List<UserVo> findUsersInApp(String appID) {
        IamSysParam param = new IamSysParam();
        param.setClientId(appID);
        return findUsersInApp(param);
    }

    @Override
    public List<UserVo> findUsersInApp(IamSysParam param) {
        if (StringUtils.isEmpty(param.getClientId())) {
            if (StringUtils.isEmpty(clientConfig.getId())) {
                throw new BusinessException("clientID_cannot_be_null");
            }
            param.setClientId(clientConfig.getId());
        }
        Map<String, String> paramMap = param.toParamMap();
        String json = callApi(clientConfig.getRsURL().getUserByClientID(), "GET", paramMap);
        List<UserVo> users = resolveJsonList(json, UserVo.class);
        return users;
    }

    @Override
    public UserVo findUsersDetail(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException("userId_cannot_null");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", userId);
        String json = callApi(clientConfig.getRsURL().getUserDetails(), "GET", param);
        List<UserVo> voList = resolveJsonList(json, UserVo.class);
        if(voList == null || voList.size() == 0){
            return null;
        }else{
            return voList.get(0);
        }
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
    public List<AppVo> findUserApps(String userId) {
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
    public List<UserVo> findUsersById(String... userIds) {
        Map<String, String> param = new HashMap<String, String>();
        if (userIds == null || userIds.length == 0) {
            throw new BusinessException("userIds_cannot_null");
        }
        param.put("userIds", StringUtils.join(userIds, ','));
        String json = callApi(clientConfig.getRsURL().getUserByIds(), "GET", param);
        List<UserVo> users = resolveJsonList(json, UserVo.class);
        return users;
    }


    /**
     * @param orgId
     * @return
     */
    private List<UserVo> findUsersByOrgId(String orgId) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        return findUsers(param);
    }

    @Override
    public List<UserVo> findUsers(IamSysParam param) {
        String json = callApi(clientConfig.getRsURL().getUsers(), "GET", param.toParamMap());
        IamSysResult<List<UserVo>> users = new IamSysResult<>();
        return (List<UserVo>) resolveJson2Result(json, UserVo.class, true, users).getRows();
    }

    /**
     * <p>findAllOrgs : 查询所有的机构列表</p>
     *
     * @param
     * @return 机构列表
     */
    @Override
    public List<OrgVo> findAllOrgs() {
        return findOrg(new IamSysParam());
    }

    /**
     * <p> : 根据orgId，查询指定的机构信息</p>
     *
     * @param orgId 机构ID
     * @return 机构列表
     */
    @Override
    public OrgVo findOrg(String orgId) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        List<OrgVo> orgList = findOrg(param);
        if(orgList == null || orgList.size() == 0) {
            return null;
        }else{
            return orgList.get(0);
        }
    }

    /**
     * 查询机构
     *
     * @param param: orgId   机构ID
     * @param param: orgCode 机构编码
     * @param param: orgName 机构名称（模糊查询）
     * @return
     */
    @Override
    public List<OrgVo> findOrg(IamSysParam param) {
        //获取组织机构树
        Map<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(param.getOrgId()))
            paramMap.put("id", param.getOrgId());
        if (StringUtils.isNotEmpty(param.getOrgCode()))
            paramMap.put("code", param.getOrgCode());
        if (StringUtils.isNotEmpty(param.getOrgName()))
            paramMap.put("name", param.getOrgName());
        paramMap.put("pageSize", "1000");
        String json = callApi(clientConfig.getRsURL().getOrgs(), "GET", paramMap);
        return resolveJsonList(json, OrgVo.class);
    }

    @Override
    public List<OrgVo> findDeptsByOrgId(String orgId) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        return findDepts(param);
    }

    /**
     * 获取当前机构的部门
     *
     * @param param：orgId  机构ID
     * @param param：deptId 指定的部门ID
     * @param param：tree   是否返回树形结构，true是树形，false是平面
     * @param param：level  层级数，-1代表无限，0代表本级
     * @return
     */
    @Override
    public List<OrgVo> findDepts(IamSysParam param) {
        //获取组织机构树
        if (StringUtils.isEmpty(param.getOrgId())) {
            throw new BusinessException("org_id_can_not_be_null_or_empty");
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orgId", param.getOrgId());//not null
        if (StringUtils.isNotEmpty(param.getDeptId())) {
            paramMap.put("id", param.getDeptId());//not null
        }
        if (param.getLevel() == null) {
            param.setLevel(0);
        }
        paramMap.put("level", String.valueOf(param.getLevel()));
        String json = callApi(clientConfig.getRsURL().getDepts(), "GET", paramMap);
        List<OrgVo> flatOrgs = resolveJsonList(json, OrgVo.class);
        if (!param.isTree()) {
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
        List<OrgVo> flatOrgs = findDeptsByOrgId(orgId);
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

        List<UserVo> users = findUsersByOrgId(orgId);
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
    public Object login(Object account) {
        return null;
    }
    @Override
    public Object logout(Object account) {
//        if (!org.springframework.util.StringUtils.isEmpty(account)) {
//            redisTemplate.opsForValue().set(delPrefix + account, "LO", timeout, TimeUnit.DAYS);
//        }
        return null;
    }

    @Override
    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, Object refreshToken) {
        if(Objects.isNull(refreshToken)
                || !(refreshToken instanceof String)
                || StringUtils.isEmpty(refreshToken.toString())){
            return null;
        }
        OAuth2Client client = new OAuth2Client(clientConfig,
                ClientHelper.startClient(request, response));
        JSONObject userinfo = client.refreshAccessToken(refreshToken.toString());
        ClientHelper.stopClient();
        return userinfo;
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

    private <T> List<T> resolveJsonList(String json, Class<T> clazz) {
        IamSysResult<List<T>> userSysResult = new IamSysResult<>();
        return (List<T>) resolveJson2Result(json, clazz, true, userSysResult).getRows();
    }

    private <T> T resolveJsonObject(String json, Class<T> clazz) {
        IamSysResult<T> userSysResult = new IamSysResult<>();
        return (T) resolveJson2Result(json, clazz, false, userSysResult).getRows();
    }

    private JSONObject fillResult(String json, IamSysResult userSysResult) {
        //Map<String, Object> tokenMap = jsonMapper.readValue(json, Map.class);
        JSONObject node = JSONObject.parseObject(json);

        String result = node.getString("result");
        String message = node.getString("message");
        int total = node.getInteger("total");
        if ("1001".equals(result)) {
            throw new IamSystemException("ACCESS_TOKEN_EXPIRED");
        } else if (!"200".equals(result)) {
            throw new RemoteException(result + ":" + message);
        }
        userSysResult.setResult(result);
        userSysResult.setMessage(message);
        userSysResult.setTotal(total);
        return node;
    }

    private <T> IamSysResult resolveJson2Result(String json, Class<T> clazz, boolean asList, IamSysResult imSysResult) {
        //Map<String, Object> tokenMap = jsonMapper.readValue(json, Map.class);
        JSONObject node = fillResult(json, imSysResult);
        String rowsString = node.get("rows").toString();
        if (asList) {
            imSysResult.setRows(JSONArray.parseArray(rowsString, clazz));
        } else {
            imSysResult.setRows(JSONObject.parseObject(rowsString, clazz));
        }
        return imSysResult;
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
                UserVo user = iamContext.getUser();
                if (user != null && user.getAccessToken() != null) {
                    token = user.getAccessToken();
                } else {
                    OAuth2ClientCertification upCC =
                            (OAuth2ClientCertification) iamContext.getBaseContext().getSessionAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
                    if (upCC == null) {
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
