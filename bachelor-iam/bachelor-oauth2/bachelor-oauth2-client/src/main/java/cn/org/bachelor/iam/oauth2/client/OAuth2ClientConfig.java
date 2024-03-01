/**
 *
 */
package cn.org.bachelor.iam.oauth2.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static cn.org.bachelor.iam.oauth2.client.OAuth2ClientConfig.OAuthConstant.*;

/**
 * @author team bachelor
 * 2021-01-20 更新将配置改为立体形式
 */
@Configuration("o2cc")
@PropertySource(value = {
        "classpath:OAuth2-config.properties",
}, encoding = "utf-8", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "bachelor.iam.client", ignoreInvalidFields = true)
public class OAuth2ClientConfig implements InitializingBean {

    /**
     * 用户平台中的client_id
     */
    private String id;

    /**
     * 用户平台中的client_secret
     */
    private String secret;

    /**
     * 登录过程中获取astoken成功后的回调地址，用于页面跳转型的认证，jwt型中认证没用
     */
    private String targetURL;

    /**
     * 认证失败时是否导向到登录的重定向地址
     */
    private boolean toLoginRedirectURL = true;

    /**
     * 登录的重定向回调地址
     */
    private String loginRedirectURL;

    /**
     * 登出的重定向回调地址
     */
    private String logoutRedirectURL;

    /**
     * 是否启用登录过滤器(过滤未登录的请求)
     */
    private boolean loginFilterEnable = true;


    /**
     * 服务器版本 默认1.0/2.0
     */
    private String version;

    /**
     * 认证服务接口地址集合
     */
    private AsUrlConfig asURL = new AsUrlConfig(this);

    /**
     * 资源服务认证地址集合
     */
    private RsUrlConfig rsURL = new RsUrlConfig(this);


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public boolean isToLoginRedirectURL() {
        return toLoginRedirectURL;
    }

    public void setToLoginRedirectURL(boolean toLoginRedirectURL) {
        this.toLoginRedirectURL = toLoginRedirectURL;
    }

    public String getLoginRedirectURL() {
        return loginRedirectURL;
    }

    public void setLoginRedirectURL(String loginRedirectURL) {
        this.loginRedirectURL = loginRedirectURL;
    }

    public String getLogoutRedirectURL() {
        return logoutRedirectURL;
    }

    public void setLogoutRedirectURL(String logoutRedirectURL) {
        this.logoutRedirectURL = logoutRedirectURL;
    }

    public AsUrlConfig getAsURL() {
        return asURL;
    }

    public void setAsURL(AsUrlConfig asURL) {
        this.asURL = asURL;
    }

    public RsUrlConfig getRsURL() {
        return rsURL;
    }

    public void setRsURL(RsUrlConfig rsURL) {
        this.rsURL = rsURL;
    }

    public boolean isLoginFilterEnable() {
        return loginFilterEnable;
    }

    public void setLoginFilterEnable(boolean loginFilterEnable) {
        this.loginFilterEnable = loginFilterEnable;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        return "<br/>clientId            :" + getId()
                + "<br/>clientSecret        :" + getSecret()
                + "<br/>targetURL           :" + getTargetURL()
                + "<br/>isToLoginRedirectURL:" + getLoginRedirectURL()
                + "<br/>loginRedirectURL    :" + getLoginRedirectURL()
                + "<br/>logoutRedirectURL   :" + getLogoutRedirectURL()
                + "<br/>loginFilterEnable   :" + isLoginFilterEnable()
                + "<br/>version             :" + getVersion()
                + "<br/>asURL               :" + getAsURL().toString()
                + "<br/>rsURL               :" + getRsURL().toString();
    }

    public static class OAuthConstant {
        public static class V1 {
            public static class AS {
                public static final String AUTHORIZE = "/authorize";
                public static final String ACCESS_TOKEN = "/accessToken";
                public static final String LOGOUT = "/logout";
                public static final String USER_INFO = "/userInfo";
            }

            public static class RS {
                public static final String APP = "/app";
                public static final String USER_DETAIL_WITH_ROLE = "/userInfoDetail";
                public static final String USER_ROLE = "/userRoles";
                public static final String USER_LIST = "/users";
                public static final String DEPT_LIST = "/depts";
                public static final String ORG_LIST = "/orgs";
                public static final String USER_BY_ID = "../userapi/user/getUserByIds";
                public static final String USER_BY_CLIENT_ID = "/users/authorizeByApp";
                public static final String APP_BY_USER_ID = "/users/authorizedApp";
                public static final String MT_USER_ROLE = "/mtUserRoles";
                public static final String USER_DETAIL = "/user_details";
                public static final String DEPT_DETAIL = "/deptDetails";
            }
        }

        public static class V2 {
            public static final String V2_CONFIG_KEY = "2.0";

            public static class AS {
                public static final String AUTHORIZE = "/authorize";
                public static final String ACCESS_TOKEN = "/token";
                public static final String LOGOUT = "/logout";
                public static final String USER_INFO = "/api/user/summary";
            }

            public static class RS {
                public static final String APP = "/api/app";
                public static final String USER_DETAIL_WITH_ROLE = "/api/user/with_role";
                public static final String USER_ROLE = "/api/user/role";
                public static final String USER_LIST = "/api/user/list";
                public static final String DEPT_LIST = "/api/org/dept/list";
                public static final String ORG_LIST = "/api/org/list";
                public static final String USER_BY_ID = "/api/user/list/by_ids";
                public static final String USER_BY_CLIENT_ID = "/api/user/list/by_client";
                public static final String APP_BY_USER_ID = "/api/app/list/by_user";
                public static final String MT_USER_ROLE = "/api/user/role";
                public static final String USER_DETAIL = "/api/user/detail";
                public static final String DEPT_DETAIL = "/api/org/dept/list/detail";
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.getAsURL().afterPropertiesSet();
        this.getRsURL().afterPropertiesSet();
    }

    protected static String prefix(String prefix, String url) {
        if (url == null) {
            return "";
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        return prefix + url;
    }

    /**
     * @author team bachelor
     *
     */
    public class RsUrlConfig {

        /**
         * 资源服务根地址
         */
        private String base = "";

        /**
         * 获取应用信息的接口地址
         */
        private String app = V1.RS.APP;

        /**
         * 获取用户详细信息的接口地址
         */
        private String userInfoDetail = V1.RS.USER_DETAIL_WITH_ROLE;

        /**
         * 获取用户角色信息的接口地址
         */
        private String userRole = V1.RS.USER_ROLE;

        /**
         * 获取用户信息的接口地址
         */
        private String users = V1.RS.USER_LIST;

        /**
         * 获取部门信息的接口地址
         */
        private String depts = V1.RS.DEPT_LIST;

        /**
         * 获取组织结构信息的接口地址
         */
        private String orgs = V1.RS.ORG_LIST;

        /**
         * 根据用户id获取用户信息的接口地址
         */
        private String userByIds = V1.RS.USER_BY_ID;

        /**
         * 获取可访问应用的全部用户的接口地址
         */
        private String userByClientID = V1.RS.USER_BY_CLIENT_ID;

        /**
         * 获取用户可访问的应用的接口地址
         */
        private String appsByUserId = V1.RS.APP_BY_USER_ID;

        /**
         * 获取用户角色信息的接口地址
         */
        private String mtUserRoles = V1.RS.MT_USER_ROLE;

        /**
         * 获取用户详细信息的接口地址
         */
        private String userDetails = V1.RS.USER_DETAIL;

        /**
         * 获取部门详细信息的接口地址
         */
        private String deptDetails = V1.RS.DEPT_DETAIL;

        private OAuth2ClientConfig parent;

        public RsUrlConfig(OAuth2ClientConfig oAuth2ClientConfig) {
            this.parent = oAuth2ClientConfig;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
//            app = prefix(base, app);
//            userInfoDetail = prefix(base, userInfoDetail);
//            userRole = prefix(base, userRole);
//            users = prefix(base, users);
//            depts = prefix(base, depts);
//            orgs = prefix(base, orgs);
//            userByIds = prefix(base, userByIds);
//            userByClientID = prefix(base, userByClientID);
//            appsByUserId = prefix(base, appsByUserId);
//            mtUserRoles = prefix(base, mtUserRoles);
//            userDetails = prefix(base, userDetails);
//            deptDetails = prefix(base, deptDetails);
            this.base = base;
        }

        public void afterPropertiesSet() {
            if(OAuthConstant.V2.V2_CONFIG_KEY.equals(parent.getVersion())){
                app = V2.RS.APP;
                userInfoDetail = V2.RS.USER_DETAIL_WITH_ROLE;
                userRole = V2.RS.USER_ROLE;
                users = V2.RS.USER_LIST;
                depts = V2.RS.DEPT_LIST;
                orgs = V2.RS.ORG_LIST;
                userByIds = V2.RS.USER_BY_ID;
                userByClientID = V2.RS.USER_BY_CLIENT_ID;
                appsByUserId = V2.RS.APP_BY_USER_ID;
                mtUserRoles = V2.RS.MT_USER_ROLE;
                userDetails = V2.RS.USER_DETAIL;
                deptDetails = V2.RS.DEPT_DETAIL;
            }
        }

        public String getApp() {
            return prefix(this.getBase(), app);
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getUserInfoDetail() {
            return prefix(this.getBase(), userInfoDetail);
        }

        public void setUserInfoDetail(String userInfoDetail) {
            this.userInfoDetail = userInfoDetail;
        }

        public String getUserRole() {
            return prefix(this.getBase(), userRole);
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUsers() {
            return prefix(this.getBase(), users);
        }

        public void setUsers(String users) {
            this.users = users;
        }

        public String getDepts() {
            return prefix(this.getBase(), depts);
        }

        public void setDepts(String depts) {
            this.depts = depts;
        }

        public String getOrgs() {
            return prefix(this.getBase(), orgs);
        }

        public void setOrgs(String orgs) {
            this.orgs = orgs;
        }

        public String getUserByIds() {
            return prefix(this.getBase(), userByIds);
        }

        public void setUserByIds(String userByIds) {
            this.userByIds = userByIds;
        }

        public String getUserByClientID() {
            return prefix(this.getBase(), userByClientID);
        }

        public void setUserByClientID(String userByClientID) {
            this.userByClientID = userByClientID;
        }

        public String getAppsByUserId() {
            return prefix(this.getBase(), appsByUserId);
        }

        public void setAppsByUserId(String appsByUserId) {
            this.appsByUserId = appsByUserId;
        }

        public String getMtUserRoles() {
            return prefix(this.getBase(), mtUserRoles);
        }

        public void setMtUserRoles(String mtUserRoles) {
            this.mtUserRoles = mtUserRoles;
        }

        public String getUserDetails() {
            return prefix(this.getBase(), userDetails);
        }

        public void setUserDetails(String userDetails) {
            this.userDetails = userDetails;
        }

        public String getDeptDetails() {
            return prefix(this.getBase(), deptDetails);
        }

        public void setDeptDetails(String deptDetails) {
            this.deptDetails = deptDetails;
        }

        public String toString() {
            return "<br/>base                :" + getBase()
                    + "<br/>app                 :" + getApp()
                    + "<br/>userInfoDetail      :" + getUserInfoDetail()
                    + "<br/>useRole             :" + getUserRole()
                    + "<br/>users               :" + getUsers()
                    + "<br/>depts               :" + getDepts()
                    + "<br/>orgs                :" + getOrgs()
                    + "<br/>userByIds           :" + getUserByIds()
                    + "<br/>userByClientID      :" + getUserByClientID()
                    + "<br/>appsByUserId        :" + getAppsByUserId()
                    + "<br/>mtUserRoles         :" + getMtUserRoles()
                    + "<br/>userDetails         :" + getUserDetails()
                    + "<br/>deptDetails         :" + getDeptDetails();
        }
    }

    /**
     * @author team bachelor
     *
     */

    public class AsUrlConfig {

        /**
         * 认证服务的根接口地址
         */
        private String base = "";

        /**
         * 认证服务的根接口地址,用于跳转
         */
        private String apiBase = "";

        /**
         * 授权的接口地址
         */
        private String authorize = V1.AS.AUTHORIZE;

        /**
         * 获取token的接口地址
         */
        private String accessToken = V1.AS.ACCESS_TOKEN;

        /**
         * 登出的接口地址
         */
        private String logout = V1.AS.LOGOUT;

        /**
         * 获取用户基本信息的接口地址
         */
        private String userInfo = V1.AS.USER_INFO;

        private OAuth2ClientConfig parent;

        public AsUrlConfig(OAuth2ClientConfig oAuth2ClientConfig) {
            this.parent = oAuth2ClientConfig;
        }

        public String getApiBase() {
            return this.apiBase;
        }

        public void setApiBase(String apiBase) {
            this.apiBase = apiBase;
//            if (!this.apiBase.equals(this.base)) {
//
//                userInfo = userInfo.replace(this.base, "");
//            }
//
//            userInfo = prefixApi(userInfo);
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            if (this.base.equals(this.apiBase)) {
                this.setApiBase(base);
            }
            this.base = base;
//            if (!this.base.equals(this.apiBase)) {
//                logout = logout.replace(this.apiBase, "");
//                authorize = authorize.replace(this.apiBase, "");
//                accessToken = accessToken.replace(this.apiBase, "");
//            }
//            logout = prefix(logout);
//            authorize = prefix(authorize);
//            accessToken = prefix(accessToken);
        }

        public String getAuthorize() {
            return prefix(this.getBase(), authorize);
        }

        public void setAuthorize(String authorize) {
            this.authorize = authorize;
        }

        public String getAccessToken() {
            return prefix(this.getBase(), accessToken);
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getLogout() {
            return prefix(this.getBase(), logout);
        }

        public void setLogout(String logout) {
            this.logout = logout;
        }

        public String getUserInfo() {
            return prefix(this.getApiBase(), userInfo);
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = userInfo;
        }

        public String toString() {
            return "<br/>base     :" + getBase()
                    + "<br/>authorize  :" + getAuthorize()
                    + "<br/>accessToken:" + getAccessToken()
                    + "<br/>logout     :" + getLogout()
                    + "<br/>userInfo   :" + getUserInfo();
        }

        public void afterPropertiesSet() {
            if(OAuthConstant.V2.V2_CONFIG_KEY.equals(parent.getVersion())){
                authorize = V2.AS.AUTHORIZE;
                accessToken = V2.AS.ACCESS_TOKEN;
                logout = V2.AS.LOGOUT;
                userInfo = V2.AS.USER_INFO;
            }
        }
    }
}
