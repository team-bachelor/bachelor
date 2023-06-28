package cn.org.bachelor.iam.token;

import cn.org.bachelor.context.IUser;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.org.bachelor.iam.token.JwtToken.PayloadKey.*;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/24
 */
//TODO 处理过时的spring security方法
public class JwtToken {

    public static final String Ver1 = "VER_1";
    public static final String Ver2 = "VER_2";

    public static class PayloadKey {
        public static final String ISS = "iss";
        public static final String SUB = "sub";
        public static final String AUD = "aud";
        public static final String EXP = "exp";
        public static final String NBF = "nbf";
        public static final String IAT = "iat";
        public static final String JTI = "jti";
        public static final String VER = "ver";
        public static final String CLAIMS = "claims";
        public static final String USER_ID = "userId";
        public static final String USER_NAME = "user_name";
        public static final String USER_CODE = "user_code";
        public static final String ORG_NAME = "org_name";
        public static final String ORG_ID = "org_id";
        public static final String DEPT_NAME = "dept_name";
        public static final String DEPT_ID = "dept_id";
        public static final String ORG_CODE = "org_code";
        public static final String ACCESS_TOKEN = "accesstoken";
        public static final String IS_ADMINISTRATOR = "is_administrator";
        public static final String TENANT_ID = "tenant_id";
        public static final String AREA_ID = "area_id";
        public static final String AREA_NAME = "area_name";
    }

    /**
     * 创建jwt
     *
     * @param privateKey 私钥
     * @return
     */
    public String generate(String privateKey) {
        return generate(JSONObject.toJSONString(this), privateKey);
    }

    private static String generate(String payloadStr, String privateKey) {
        try {
            //准备JWS-header
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT).build();
            //将负载信息装载到payload
            Payload payload = new Payload(payloadStr);
            //封装header和payload到JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            //创建HMAC签名器
            JWSSigner jwsSigner = new MACSigner(privateKey);
            //签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static JwtToken create(IUser userDetail, AbstractIamCredential credential) {
        if (userDetail == null) {
            throw new BusinessException("user detail can not be null!");
        }
        Date expTime_Date = credential.getExpiresTime();
        // 有效期保持与用户系统一致
        long expTime = expTime_Date.getTime();
        long currentTime = new Date().getTime();
//        Map<String, Object> userObject = JSONParser.parseJSON(userinfo);
//        userinfo.put(ACCESS_TOKEN, accesstoken);
        JwtToken token = new JwtToken();
        token.setExp(expTime);
        token.setIat(currentTime);
        token.setIss("");// jwt签发者
        token.setSub(userDetail.getCode());// jwt所面向的用户
        token.setAud("");// 接收jwt的一方
        token.setNbf(0L);
        token.setJti("");
        token.setVer(JwtToken.Ver2);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(USER_NAME, userDetail.getName());
        map.put(USER_CODE, userDetail.getCode());
        map.put(ORG_ID, userDetail.getOrgId());
        map.put(ORG_NAME, userDetail.getOrgName());
        map.put(DEPT_ID, userDetail.getDeptId());
        map.put(DEPT_NAME, userDetail.getDeptName());
        map.put(ACCESS_TOKEN, credential.getCredential());
        map.put(USER_ID, userDetail.getId());
        token.setClaims(map);
        // 存储refreshToken为token有效期的2倍
//        userSysService.saveRefreshToken(userinfo.getString("account"), refreshToken, 2 * (expTime - currentTime));
//        userinfo.put(JwtToken.PayloadKey.EXP, expTime);
//        userinfo.put(JwtToken.PayloadKey.IAT, currentTime);
//
//        userinfo.put(JwtToken.PayloadKey.ISS, ""); // jwt签发者
//        userinfo.put(JwtToken.PayloadKey.SUB, userinfo.get("account")); // jwt所面向的用户
//        userinfo.put(JwtToken.PayloadKey.AUD, ""); // 接收jwt的一方
//        userinfo.put(JwtToken.PayloadKey.NBF, ""); // 接收jwt的一方
//        userinfo.put(JwtToken.PayloadKey.JTI, ""); // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
//
//        userinfo.put(JwtToken.PayloadKey.USER_NAME, userinfo.get("username"));
//        userinfo.put(JwtToken.PayloadKey.USER_CODE, userinfo.get("account"));
//
////        userinfo.put(USER_ID, userId);
//
//        if (userDetail != null) {
//            userinfo.put(JwtToken.PayloadKey.ORG_ID, userDetail.getOrgId());
//            userinfo.put(JwtToken.PayloadKey.ORG_NAME, userDetail.getOrgName());
//            userinfo.put(JwtToken.PayloadKey.DEPT_ID, userDetail.getDeptId());
//            userinfo.put(JwtToken.PayloadKey.DEPT_NAME, userDetail.getDeptName());
//        }
//        logger.info(userinfo.toString());
//        String userStr = null;
//        if(userinfo instanceof  JSONObject){
//            userStr = ((JSONObject) userinfo).toJSONString();
//        }else{
//            userStr = JSONObject.toJSONString(userinfo);
//        }token.generate(privateKey);
//        String token = JwtToken.generate(userStr, privateKey);
        return token;
    }

    /**
     * 解析和验证
     *
     * @param token     token
     * @param publicKey 公钥
     * @return
     */
//    public static JwtToken decodeAndVerify(String token, RSAPublicKey publicKey) {
//        Jwt j = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
//        return fromJson(j.getClaims());
//    }

//    public static JwtToken decodeAndVerify(String token, String publicKey) {
//        Jwt j = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
//        return fromJson(j.getClaims());
//    }
//    public static JwtToken decodeAndVerify(String token, RSAPublicKey publicKey) {
//        Jwt j = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
//        return decodeAndVerify(token, publicKey.);
//    }
    public static JwtToken decodeAndVerify(String token, String publicKey) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            //创建HMAC验证器
            JWSVerifier jwsVerifier = new MACVerifier(publicKey);
            if (!jwsObject.verify(jwsVerifier)) {
                throw new BusinessException("jwt签名不合法!");
            }
            String payload = jwsObject.getPayload().toString();
            JwtToken payloadDto = JSONObject.parseObject(payload, JwtToken.class);
            if (payloadDto.getExp() < new Date().getTime()) {
                throw new BusinessException("jwt令牌已过期!");
            }
            return payloadDto;
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static JwtToken decode(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String payload = jwsObject.getPayload().toString();
            JwtToken payloadDto = JSONObject.parseObject(payload, JwtToken.class);
            if (StringUtils.isBlank(payloadDto.getVer()) || Ver1.equals(payloadDto.getVer())) {
                payloadDto.setClaims(JSONObject.parseObject(payload));
            }
            return payloadDto;
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }
//    public static JwtToken decode(String token) {
//        Jwt j = JwtHelper.decode(token);
//        return fromJson(j.getClaims());
//    }

    //jwt签发者
    private String iss;

    //jwt所面向的用户
    private String sub;

    //接收jwt的一方
    private String aud;

    //jwt的过期时间，这个过期时间必须要大于签发时间
    private Long exp;

    //定义在什么时间之前，该jwt都是不可用的.
    private Long nbf;

    //jwt的签发时间
    private Long iat;

    //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
    private String jti;

    //版本
    private String ver;

    //规范外的其他内容
    private Map<String, Object> claims;

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getNbf() {
        return nbf;
    }

    public void setNbf(Long nbf) {
        this.nbf = nbf;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
//    public static String toJson(JwtToken token) {
//        if (token == null) return null;
//
//        Map<String, Object> tokenMap = token.getClaims();
//        if (tokenMap == null) {
//            tokenMap = new HashMap<>();
//        }
//        if (isNotEmpty(token.getIss())) {
//            tokenMap.put(PayloadKey.ISS, token.getIss());
//        }
//        if (isNotEmpty(token.getSub())) {
//            tokenMap.put(PayloadKey.SUB, token.getSub());
//        }
//        if (isNotEmpty(token.getAud())) {
//            tokenMap.put(PayloadKey.AUD, token.getAud());
//        }
//        if (token.getExp() != null) {
//            tokenMap.put(PayloadKey.EXP, String.valueOf(token.getExp()));
//        }
//        if (token.getNbf() != null) {
//            tokenMap.put(PayloadKey.NBF, String.valueOf(token.getNbf()));
//        }
//        if (token.getIat() != null) {
//            tokenMap.put(PayloadKey.IAT, String.valueOf(token.getIat()));
//        }
//        if (isNotEmpty(token.getJti())) {
//            tokenMap.put(PayloadKey.JTI, token.getJti());
//        }
//        JSONObject.toJSONString(tokenMap)
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(tokenMap);
//        } catch (JsonProcessingException e) {
//            throw new BusinessException(e);
//        }
//    }

//    private static boolean isNotEmpty(String s) {
//        return s != null && !"".equals(s);
//    }

//    public static JwtToken fromJson(String json) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Map<String, Object> tokenMap = mapper.readValue(json, Map.class);
//            return resolveMap(tokenMap);
//        } catch (IOException e) {
//            throw new BusinessException("invalid jwt string", e);
//        }
//
//    }

//    private static JwtToken resolveMap(Map<String, Object> tokenMap) {
//        JwtToken token = new JwtToken();
//        token.setIss(getAndRemoveClaim(PayloadKey.ISS, tokenMap));
//        token.setSub(getAndRemoveClaim(PayloadKey.SUB, tokenMap));
//        token.setAud(getAndRemoveClaim(PayloadKey.AUD, tokenMap));
//
//        String v = null;
//        try {
//            v = getAndRemoveClaim(PayloadKey.EXP, tokenMap);
//            if (isNotEmpty(v)) {
//                token.setExp(Long.valueOf(v));
//            }
//        } catch (NumberFormatException e) {
//        }
//        try {
//            v = getAndRemoveClaim(PayloadKey.NBF, tokenMap);
//            if (isNotEmpty(v))
//                token.setNbf(Long.valueOf(v));
//        } catch (NumberFormatException e) {
//        }
//        try {
//            v = getAndRemoveClaim(PayloadKey.IAT, tokenMap);
//            if (isNotEmpty(v))
//                token.setIat(Long.valueOf(v));
//        } catch (NumberFormatException e) {
//        }
//        token.setJti(getAndRemoveClaim(PayloadKey.JTI, tokenMap));
//        token.setClaims(tokenMap);
//        return token;
//    }
//
//    private static String getAndRemoveClaim(String claimName, Map<String, Object> tokenMap) {
//        if (tokenMap.containsKey(claimName)) {
//            return tokenMap.remove(claimName).toString();
//        }
//        return null;
//    }

}
