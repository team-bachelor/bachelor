package cn.org.bachelor.iam.token;

import cn.org.bachelor.context.IUser;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.iam.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.org.bachelor.iam.token.JwtToken.PayloadKey.*;

/**
 * JSONWebToken
 *
 * @author liuzhuo
 */
public class JwtToken {

    public static final String Ver1 = "VER_1";
    public static final String Ver2 = "VER_2";

    /**
     * JWT 负载中的键名常量类，包含了各种标准和自定义的 JWT 声明。
     */
    public static class PayloadKey {
        // JWT 签发者
        public static final String ISS = "iss";
        // JWT 所面向的用户
        public static final String SUB = "sub";
        // 接收 JWT 的一方
        public static final String AUD = "aud";
        // JWT 的过期时间
        public static final String EXP = "exp";
        // 定义在什么时间之前，该 JWT 都是不可用的
        public static final String NBF = "nbf";
        // JWT 的签发时间
        public static final String IAT = "iat";
        // JWT 的唯一身份标识，主要用来作为一次性 token，从而回避重放攻击
        public static final String JTI = "jti";
        // JWT 版本
        public static final String VER = "ver";
        // 规范外的其他内容
        public static final String CLAIMS = "claims";
        // 用户 ID
        public static final String USER_ID = "userid";
        // 用户名称
        public static final String USER_NAME = "user_name";
        // 用户账号
        public static final String ACCOUNT = "account";
        // 用户代码
        public static final String USER_CODE = "user_code";
        // 组织机构名称
        public static final String ORG_NAME = "org_name";
        // 组织机构 ID
        public static final String ORG_ID = "org_id";
        // 部门名称
        public static final String DEPT_NAME = "dept_name";
        // 部门 ID
        public static final String DEPT_ID = "dept_id";
        // 组织机构代码
        public static final String ORG_CODE = "org_code";
        // 访问令牌
        public static final String ACCESS_TOKEN = "accesstoken";
        // 是否为管理员
        public static final String IS_ADMINISTRATOR = "is_administrator";
        // 租户 ID
        public static final String TENANT_ID = "tenant_id";
        // 区域 ID
        public static final String AREA_ID = "area_id";
        // 开放 ID
        public static final String OPEN_ID = "open_id";
        // 开放 ID（另一种表示）
        public static final String OPEN_ID_CAM = "openid";
        // 区域名称
        public static final String AREA_NAME = "area_name";
    }

    /**
     * 创建 JWT 并生成字符串表示。
     *
     * @param privateKey 私钥，用于对 JWT 进行签名
     * @return 返回生成的 JWT 字符串
     */
    public String generate(String privateKey) {
        return generate(JSONObject.toJSONString(this), privateKey);
    }

    /**
     * 私有静态方法，用于根据负载字符串和私钥生成 JWT。
     *
     * @param payloadStr 负载信息的 JSON 字符串
     * @param privateKey 私钥，用于对 JWT 进行签名
     * @return 返回生成的 JWT 字符串
     */
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

    /**
     * 根据用户信息和凭证创建 JwtToken 实例。
     *
     * @param userDetail  用户详细信息
     * @param expiresTime 过期时间
     * @return 创建好的 JwtToken 实例
     */
    public static JwtToken create(IUser userDetail, Date expiresTime) {
        if (userDetail == null) {
            throw new BusinessException("user detail can not be null!");
        }
        Date expTime_Date = expiresTime;
        // 有效期保持与用户系统一致
        if (expTime_Date == null) {
            expTime_Date = DateUtils.addHours(new Date(), 3);
        }
        long expTime = expTime_Date.getTime();
        long currentTime = new Date().getTime();
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
        map.put(ACCESS_TOKEN, userDetail.getAccessToken());
        map.put(USER_ID, userDetail.getId());
        if (userDetail.getExtendInfo() != null && userDetail.getExtendInfo().size() > 0) {
            map.putAll(userDetail.getExtendInfo());
        }
        token.setClaims(map);
        return token;
    }

    /**
     * 解析和验证 JWT 字符串。
     *
     * @param token     JWT 字符串
     * @param publicKey 公钥，用于验证 JWT 签名
     * @return 解析后的 JwtToken 实例
     */
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
            if (StringUtils.isEmpty(payloadDto.getVer()) || Ver1.equals(payloadDto.getVer())) {
                payloadDto.setClaims(JSONObject.parseObject(payload));
            }
            return payloadDto;
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    // jwt签发者
    private String iss;

    // jwt所面向的用户
    private String sub;

    // 接收jwt的一方
    private String aud;

    // jwt的过期时间，这个过期时间必须要大于签发时间
    private Long exp;

    // 定义在什么时间之前，该jwt都是不可用的
    private Long nbf;

    // jwt的签发时间
    private Long iat;

    // jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
    private String jti;

    // 版本
    private String ver;

    // 规范外的其他内容
    private Map<String, Object> claims;

    /**
     * 获取规范外的其他内容
     *
     * @return 包含规范外其他内容的Map对象
     */
    public Map<String, Object> getClaims() {
        return claims;
    }

    /**
     * 设置规范外的其他内容
     *
     * @param claims 包含规范外其他内容的Map对象
     */
    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    /**
     * 获取 jwt 签发者
     *
     * @return jwt 签发者的字符串
     */
    public String getIss() {
        return iss;
    }

    /**
     * 设置 jwt 签发者
     *
     * @param iss jwt 签发者的字符串
     */
    public void setIss(String iss) {
        this.iss = iss;
    }

    /**
     * 获取 jwt 所面向的用户
     *
     * @return jwt 所面向用户的字符串
     */
    public String getSub() {
        return sub;
    }

    /**
     * 设置 jwt 所面向的用户
     *
     * @param sub jwt 所面向用户的字符串
     */
    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     * 获取接收 jwt 的一方
     *
     * @return 接收 jwt 一方的字符串
     */
    public String getAud() {
        return aud;
    }

    /**
     * 设置接收 jwt 的一方
     *
     * @param aud 接收 jwt 一方的字符串
     */
    public void setAud(String aud) {
        this.aud = aud;
    }

    /**
     * 获取 jwt 的过期时间
     *
     * @return jwt 过期时间的 Long 类型值
     */
    public Long getExp() {
        return exp;
    }

    /**
     * 设置 jwt 的过期时间
     *
     * @param exp jwt 过期时间的 Long 类型值
     */
    public void setExp(Long exp) {
        this.exp = exp;
    }

    /**
     * 获取 jwt 在什么时间之前不可用
     *
     * @return 表示不可用时间的 Long 类型值
     */
    public Long getNbf() {
        return nbf;
    }

    /**
     * 设置 jwt 在什么时间之前不可用
     *
     * @param nbf 表示不可用时间的 Long 类型值
     */
    public void setNbf(Long nbf) {
        this.nbf = nbf;
    }

    /**
     * 获取 jwt 的签发时间
     *
     * @return jwt 签发时间的 Long 类型值
     */
    public Long getIat() {
        return iat;
    }

    /**
     * 设置 jwt 的签发时间
     *
     * @param iat jwt 签发时间的 Long 类型值
     */
    public void setIat(Long iat) {
        this.iat = iat;
    }

    /**
     * 获取 jwt 的唯一身份标识
     *
     * @return jwt 唯一身份标识的字符串
     */
    public String getJti() {
        return jti;
    }

    /**
     * 设置 jwt 的唯一身份标识
     *
     * @param jti jwt 唯一身份标识的字符串
     */
    public void setJti(String jti) {
        this.jti = jti;
    }

    /**
     * 获取 jwt 的版本
     *
     * @return jwt 版本的字符串
     */
    public String getVer() {
        return ver;
    }

    /**
     * 设置 jwt 的版本
     *
     * @param ver jwt 版本的字符串
     */
    public void setVer(String ver) {
        this.ver = ver;
    }

}
