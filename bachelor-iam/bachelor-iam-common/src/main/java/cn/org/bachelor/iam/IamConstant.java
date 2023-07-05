package cn.org.bachelor.iam;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/5
 */
public class IamConstant {
    public static final String USER_KEY = "BACHELOR_USER_HOLD_KEY";
    public static final String HTTP_HEADER_TOKEN_KEY = "bachelor_authorization";
    public static final String ACCESS_BACKEND = "up_access_backend";//是否访问后台获取用户状态，N为不访问，其余为访问
    public static final String UP_USER_ID = "_up_user_id";
    public static final String UP_OPEN_ID = "_up_open_id";
    public static final String UP_USER = "_up_user";
    //    public static final String ACCESS = "_access_token";
    public static final String UP_ORG_ID ="_up_org_id";
    public static final String UP_USER_NAME ="_up_user_name";
    public static final String UP_ORG_NAME ="_up_org_name";
    public static final String UP_DEPT_ID ="_up_dept_id";
    public static final String UP_DEPT_NAME ="_up_dept_name";
    public static final String SESSION_AUTHENTICATION_KEY ="up_authenticate_key";
    public static final String ISV = "_isv_name";
    public static final String ORIGINAL_URL = "_Original_URL";
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String COOKIE_SEPARATOR = "|";
    public static final String COOKIE_NAME="RCLOUD";
    public static final String OAUTH_STATE="oauth_state";
    public static final String OAUTH_CB_STATE="state";
    public static final String TEMPLATE_REPLACE_STRING ="${error}";
    public static final String TEMPLATE_NAME="error-client.html";
    public static final String STATE_ERROR="验证state失败";
    public static final String GET_ACCESS_TOKEN_ERROR ="客户端获取令牌失败";
    public static final String GET_USERINFO_ERROR="客户端获取用户基本信息失败";
}
