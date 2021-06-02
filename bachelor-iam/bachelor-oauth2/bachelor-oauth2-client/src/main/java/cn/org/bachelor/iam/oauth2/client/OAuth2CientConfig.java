/**
 *
 */
package cn.org.bachelor.iam.oauth2.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author team bachelor
 * 2021-01-20 更新将配置改为立体形式
 */
@Configuration("o2cc")
@PropertySource(value = {
        "classpath:OAuth2-config.properties",
}, encoding = "utf-8", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "bachelor.iam.client", ignoreInvalidFields = true)
public class OAuth2CientConfig {

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
     * 认证服务接口地址集合
     */
    private AsUrlConfig asURL = new AsUrlConfig();

    /**
     * 资源服务认证地址集合
     */
    private RsUrlConfig rsURL = new RsUrlConfig();

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

    public String toString() {
        return "<br/>clientId            :" + id
                + "<br/>clientSecret        :" + secret
                + "<br/>targetURL           :" + targetURL
                + "<br/>isToLoginRedirectURL:" + toLoginRedirectURL
                + "<br/>loginRedirectURL    :" + loginRedirectURL
                + "<br/>logoutRedirectURL   :" + logoutRedirectURL
                + "<br/>loginFilterEnable   :" + loginFilterEnable
                + "<br/>asURL               :" + getAsURL().toString()
                + "<br/>rsURL               :" + getRsURL().toString();
    }

    public boolean isLoginFilterEnable() {
        return loginFilterEnable;
    }

    public void setLoginFilterEnable(boolean loginFilterEnable) {
        this.loginFilterEnable = loginFilterEnable;
    }

    /**
     * @author team bachelor
     *
     */
    public static class RsUrlConfig {

        /**
         * 资源服务根地址
         */
        private String base = "";

        /**
         * 获取应用信息的接口地址
         */
        private String app = "/app";

        /**
         * 获取用户详细信息的接口地址
         */
        private String userInfoDetail = "/userInfoDetail";

        /**
         * 获取用户角色信息的接口地址
         */
        private String userRole = "/userRoles";

        /**
         * 获取用户信息的接口地址
         */
        private String users = "/users";

        /**
         * 获取部门信息的接口地址
         */
        private String depts = "/depts";

        /**
         * 获取组织结构信息的接口地址
         */
        private String orgs = "/orgs";

        /**
         * 根据用户id获取用户信息的接口地址
         */
        private String userByIds = "../userapi/user/getUserByIds";

        /**
         * 获取可访问应用的全部用户的接口地址
         */
        private String userByClientID = "/users/authorizeByApp";

        /**
         * 获取用户可访问的应用的接口地址
         */
        private String appsByUserId = "/users/authorizedApp";

        /**
         * 获取用户角色信息的接口地址
         */
        private String mtUserRoles = "/mtUserRoles";

        /**
         * 获取用户详细信息的接口地址
         */
        private String userDetails = "/user_details";

        /**
         * 获取部门详细信息的接口地址
         */
        private String deptDetails = "/deptDetails";

        private String prefix(String url) {
            return prefix(url, null);
        }

        private String prefix(String url, String newBase) {
            return newBase + (StringUtils.isEmpty(this.getBase()) ? url : url.replace(this.getBase(), ""));
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            app = prefix(app, base);
            userInfoDetail = prefix(userInfoDetail, base);
            userRole = prefix(userRole, base);
            users = prefix(users, base);
            depts = prefix(depts, base);
            orgs = prefix(orgs, base);
            userByIds = prefix(userByIds, base);
            userByClientID = prefix(userByClientID, base);
            appsByUserId = prefix(appsByUserId, base);
            mtUserRoles = prefix(mtUserRoles, base);
            userDetails = prefix(userDetails, base);
            deptDetails = prefix(deptDetails, base);
            this.base = base;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = prefix(app);
        }

        public String getUserInfoDetail() {
            return userInfoDetail;
        }

        public void setUserInfoDetail(String userInfoDetail) {
            this.userInfoDetail = prefix(userInfoDetail);
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = prefix(userRole);
        }

        public String getUsers() {
            return users;
        }

        public void setUsers(String users) {
            this.users = prefix(users);
        }

        public String getDepts() {
            return depts;
        }

        public void setDepts(String depts) {
            this.depts = prefix(depts);
        }

        public String getOrgs() {
            return orgs;
        }

        public void setOrgs(String orgs) {
            this.orgs = prefix(orgs);
        }

        public String getUserByIds() {
            return userByIds;
        }

        public void setUserByIds(String userByIds) {
            this.userByIds = prefix(userByIds);
        }

        public String getUserByClientID() {
            return userByClientID;
        }

        public void setUserByClientID(String userByClientID) {
            this.userByClientID = prefix(userByClientID);
        }

        public String getAppsByUserId() {
            return appsByUserId;
        }

        public void setAppsByUserId(String appsByUserId) {
            this.appsByUserId = prefix(appsByUserId);
        }

        public String getMtUserRoles() {
            return mtUserRoles;
        }

        public void setMtUserRoles(String mtUserRoles) {
            this.mtUserRoles = prefix(mtUserRoles);
        }

        public String getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(String userDetails) {
            this.userDetails = prefix(userDetails);
        }

        public String getDeptDetails() {
            return deptDetails;
        }

        public void setDeptDetails(String deptDetails) {
            this.deptDetails = prefix(deptDetails);
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

    public static class AsUrlConfig {

        /**
         * 认证服务的根接口地址
         */
        private String base = "";

        /**
         * 授权的接口地址
         */
        private String authorize = "/authorize";

        /**
         * 获取token的接口地址
         */
        private String accessToken = "/accessToken";

        /**
         * 登出的接口地址
         */
        private String logout = "/logout";

        /**
         * 获取用户基本信息的接口地址
         */
        private String userInfo = "/userInfo";


        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
            authorize = prefix(authorize);
            accessToken = prefix(accessToken);
            logout = prefix(logout);
            userInfo = prefix(userInfo);
        }

        private String prefix(String url) {
            return this.getBase() + url;
        }

        public String getAuthorize() {
            return authorize;
        }

        public void setAuthorize(String authorize) {
            this.authorize = prefix(authorize);
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = prefix(accessToken);
        }

        public String getLogout() {
            return logout;
        }

        public void setLogout(String logout) {
            this.logout = prefix(logout);
        }

        public String getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = prefix(userInfo);
        }

        public String toString() {
            return "<br/>base     :" + getBase()
                    + "<br/>authorize  :" + getAuthorize()
                    + "<br/>accessToken:" + getAccessToken()
                    + "<br/>logout     :" + getLogout()
                    + "<br/>userInfo   :" + getUserInfo();
        }
    }
}
