package cn.org.bachelor.iam.utils;

import cn.org.bachelor.iam.token.JwtToken;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String getAndTokenClaim(Map<String, Object> tokenClaims, String payload, boolean urlencode, boolean remove) {
        String value = tokenClaims.getOrDefault(payload, "").toString();
        if (remove) {
            tokenClaims.remove(payload);
        }
        if (urlencode) {
            value = urlEncode(value);
        }
        return value;
    }

    public static String getAndRemoveTokenClaim(Map<String, Object> tokenClaims, String payload, boolean urlencode) {
        return getAndTokenClaim(tokenClaims, payload, urlencode, true);
    }

    public static boolean isValidToken(JwtToken jwtToken) {
        return jwtToken.getExp() > new Date().getTime();
    }

    public static String urlEncode(String param) {
        try {
            return StringUtils.isEmpty(param) ? StringUtils.EMPTY : URLEncoder.encode(param, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}
