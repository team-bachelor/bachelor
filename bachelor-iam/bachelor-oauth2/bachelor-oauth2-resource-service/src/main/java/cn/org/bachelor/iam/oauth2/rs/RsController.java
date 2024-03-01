package cn.org.bachelor.iam.oauth2.rs;


import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api")
public class RsController {


    @Autowired
    private IamSysService service;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private IamContext context;

    /**
     * <p>获取用户基本信息</p>
     * <p>原地址："/userInfo"<br/>
     * 现地址："/user/summary"</p>
     */
    @ResponseBody
    @RequestMapping("/user/summary")
    public HttpEntity<JsonResponse> getUserSummary() {
        String token = RsClientHelper.getRsRequest().getAccessToken();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
        String userCode = (String) oAuth2Authentication.getPrincipal();

        return null;
    }

    /**
     * <p>获取某个用户所在的全职组织部门和角色<br/>
     * 根据clientId取得当前用户在指定client中的角色<br/>
     * 多用于登录后取得用户信息</p>
     * <p>原地址："/userInfoDetail"<br/>
     * 现地址："/user/detail/with_role"</p>
     */
    @ResponseBody
    @RequestMapping("/user/detail/with_role")
    public HttpEntity<JsonResponse> getUserWithRole() {
        return null;
    }

    /**
     * <p>获取用户详细信息<br/>
     * 主要区别是获取了用户的扩展字段</p>
     * <p>原地址："/user_details"<br/>
     * 现地址："/user/detail"</p>
     */
    @ResponseBody
    @RequestMapping("/user/detail")
    public HttpEntity<JsonResponse> getUserDetail() {
        return null;
    }

    /**
     * <p>用户详细信息综合查询</p>
     * <p>原地址："/users"<br/>
     * 现地址："/user/list"</p>
     */
    @ResponseBody
    @RequestMapping("/user/list")
    public HttpEntity<JsonResponse> getUserList() {
        return null;
    }

    /**
     * <p>根据用户id获取用户信息，复用/user/list</p>
     * <p>原地址："../userapi/user/getUserByIds"<br/>
     * 现地址："/user/list/by_ids"</p>
     */
    @ResponseBody
    @RequestMapping("/user/list/by_ids")
    public HttpEntity<JsonResponse> getUserSummaryByIds() {
        return null;
    }

    /**
     * <p>获取用户角色信息，获取指定账户在指定机构下的账户角色</p>
     * <p>原地址："/mtUserRoles"<br/>
     * 现地址："/user/mt/role"</p>
     */
    @ResponseBody
    @RequestMapping("/userInfoDetail")
    public HttpEntity<JsonResponse> getUserRoles() {
        return null;
    }


    /**
     * <p>获取组织结构(租户)信息</p>
     * <p>原地址："/orgs"<br/>
     * 现地址："/org/list"</p>
     */
    @ResponseBody
    @RequestMapping("/org/list")
    public HttpEntity<JsonResponse> getOrg() {
        return null;
    }

    /**
     * <p>获取部门信息</p>
     * <p>原地址："/depts"<br/>
     * 现地址："/org/dept/list"</p>
     */
    @ResponseBody
    @RequestMapping("/org/dept/list")
    public HttpEntity<JsonResponse> getOrgDeptList() {
        return null;
    }

    /**
     * <p>获取部门详细信息，获取带有扩展字段的部门信息</p>
     * <p>原地址："/deptDetails"<br/>
     * 现地址："/org/dept/list/detail"</p>
     */
    @ResponseBody
    @RequestMapping("/org/dept/list/detail")
    public HttpEntity<JsonResponse> getOrgDeptListDetail() {
        return null;
    }

    /**
     * <p>获取应用信息</p>
     * <p>原地址："/app"<br/>
     * 现地址："/user/summary"</p>
     */
    @ResponseBody
    @RequestMapping("/app")
    public HttpEntity<JsonResponse> getApp() {
        return null;
    }

    /**
     * <p>获取可访问应用的全部用户</p>
     * <p>原地址："/users/authorizeByApp"<br/>
     * 现地址："/user/list/by_client"</p>
     */
    @ResponseBody
    @RequestMapping("/user/list/by_client")
    public HttpEntity<JsonResponse> getUserListByClient() {
        return null;
    }

    /**
     * <p>获取用户可访问的应用</p>
     * <p>原地址："/users/authorizedApp"<br/>
     * 现地址："/user/summary"</p>
     */
    @ResponseBody
    @RequestMapping("/app/list/by_user")
    public HttpEntity<JsonResponse> getAppListByUser() {
        return null;
    }

}
