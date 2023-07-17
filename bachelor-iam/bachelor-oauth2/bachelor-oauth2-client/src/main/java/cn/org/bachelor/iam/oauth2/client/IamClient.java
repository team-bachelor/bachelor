package cn.org.bachelor.iam.oauth2.client;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public interface IamClient {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Date parseExpireTime(String expireTime) {
        Date expTime;
        try {
            expTime = sdf.parse(expireTime);
        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            expTime = calendar.getTime();
        }
        return expTime;
    }

    /**
     * 获取授权码
     *
     * @return
     */
    String getAuthorizationCode();

    /**
     * 获取phone_id
     *
     * @return
     */
    String getPhoneId();

    /**
     * 引导用户授权
     *
     * @throws IOException
     */
    void toGetAuthorizationCode(HttpServletRequest request) throws IOException;


    JSONObject refreshAccessToken(String currentRefreshToken);

    /**
     * 调用API获取用户信息并将用户信息绑定到会话中
     *
     * @param authorizationCode
     * @return
     * @throws Exception
     */
    JSONObject bindUser2Session(String authorizationCode);

    /**
     * 是否从OAuth2认证服务器返回的请求，一般情况为出错了
     *
     * @return
     */
    boolean isCallback();

//    boolean toOriginalURL() throws Exception;

    boolean toTargetURL() throws Exception;

//    boolean isExcluded(String patterns);

    boolean isExcluded(String except_url_pattern, String except_param, HttpServletRequest request);

//    boolean isLogin();

    boolean isLogin(HttpServletRequest req);

//    String[] isCookie() throws Exception;

    /**
     * 验证state是否相同
     *
     * @param request
     * @return
     */
    boolean isValidState(HttpServletRequest request);
}
