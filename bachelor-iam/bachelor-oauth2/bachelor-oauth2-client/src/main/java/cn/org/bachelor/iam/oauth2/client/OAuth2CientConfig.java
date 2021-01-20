/**
 *
 */
package cn.org.bachelor.iam.oauth2.client;

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

    private String id;

    private String secret;

    private String targetURL;

    private boolean toLoginRedirectURL = true;

    private String loginRedirectURL;

    private String logoutRedirectURL;

    private boolean loginFilterEnable = true;

    private AsUrlConfig asURL = new AsUrlConfig();

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
        return    "<br/>clientId            :" + id
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

        private String base = "";

        private String app = "/app";

        private String userInfoDetail="/userInfoDetail";

        private String userRole ="/userRoles";

        private String users="/users";

        private String depts="/depts";

        private String orgs="/orgs";

        private String userByIds="../userapi/user/getUserByIds";

        private String userByClientID="/users/authorizeByApp";

        private String appsByUserId="/users/authorizedApp";

        private String mtUserRoles="/mtUserRoles";

        private String userDetails="/user_details";

        private String deptDetails="/deptDetails";

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getUserInfoDetail() {
            return userInfoDetail;
        }

        public void setUserInfoDetail(String userInfoDetail) {
            this.userInfoDetail = userInfoDetail;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUsers() {
            return users;
        }

        public void setUsers(String users) {
            this.users = users;
        }

        public String getDepts() {
            return depts;
        }

        public void setDepts(String depts) {
            this.depts = depts;
        }

        public String getOrgs() {
            return orgs;
        }

        public void setOrgs(String orgs) {
            this.orgs = orgs;
        }

        public String getUserByIds() {
            return userByIds;
        }

        public void setUserByIds(String userByIds) {
            this.userByIds = userByIds;
        }

        public String getUserByClientID() {
            return userByClientID;
        }

        public void setUserByClientID(String userByClientID) {
            this.userByClientID = userByClientID;
        }

        public String getAppsByUserId() {
            return appsByUserId;
        }

        public void setAppsByUserId(String appsByUserId) {
            this.appsByUserId = appsByUserId;
        }

        public String getMtUserRoles() {
            return mtUserRoles;
        }

        public void setMtUserRoles(String mtUserRoles) {
            this.mtUserRoles = mtUserRoles;
        }

        public String getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(String userDetails) {
            this.userDetails = userDetails;
        }

        public String getDeptDetails() {
            return deptDetails;
        }

        public void setDeptDetails(String deptDetails) {
            this.deptDetails = deptDetails;
        }

        public String toString() {
            return    "<br/>base                :" + getBase()
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

        private String base = "";

        private String authorize = "/authorize";

        private String accessToken = "/accessToken";

        private String logout = "/logout";

        private String userInfo = "/userInfo";

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getAuthorize() {
            return authorize;
        }

        public void setAuthorize(String authorize) {
            this.authorize = authorize;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getLogout() {
            return logout;
        }

        public void setLogout(String logout) {
            this.logout = logout;
        }

        public String getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = userInfo;
        }

        public String toString() {
            return    "<br/>base     :" + getBase()
                    + "<br/>authorize  :" + getAuthorize()
                    + "<br/>accessToken:" + getAccessToken()
                    + "<br/>logout     :" + getLogout()
                    + "<br/>userInfo   :" + getUserInfo();
        }
    }
}
