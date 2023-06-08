package cn.org.bachelor.iam.idm.login.util;

import cn.org.bachelor.iam.acm.domain.User;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.utils.JwtUtil;
import cn.org.bachelor.iam.vo.UserVo;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static cn.org.bachelor.iam.utils.JwtUtil.getAndRemoveTokenClaim;

public class UserUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

    public static LoginUser vo2LoginUser(UserVo vo) {
        User po = new User();
        LoginUser lu = new LoginUser(po);
        po.setId(vo.getId());
        po.setOrgId(vo.getOrgId());
        lu.setOrgCode(vo.getOrgCode());
        lu.setOrgName(vo.getOrgName());
        po.setDeptId(vo.getDeptId());
        lu.setDeptName(vo.getDeptName());
        po.setCode(vo.getCode());
        po.setPassword(vo.getPassword());
        po.setName(vo.getName());
        lu.setAccessToken(vo.getAccessToken());
        lu.setTenantId(vo.getTenantId());
        return lu;
    }

    public static LoginUser jwt2LoginUser(JwtToken jwt) {

        User po = new User();
        LoginUser lu = new LoginUser(po);
        boolean isValidToken = JwtUtil.isValidToken(jwt);
        if (jwt != null && isValidToken) {
            //TODO 安全性有待提高
            Map<String, Object> tokenClaims = new HashMap<>(jwt.getClaims().size());
            tokenClaims.putAll(jwt.getClaims());
            po.setOrgId(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_ID, false));
            lu.setOrgCode(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_CODE, false));
            po.setCode(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_CODE, false));
            po.setId(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_ID, false));
            po.setName(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_NAME, true));
            po.setDeptId(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_ID, false));
            lu.setDeptName(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_NAME, true));
            lu.setOrgName(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_NAME, true));
            lu.setAccessToken(getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ACCESS_TOKEN, false));

//            tokenClaims.keySet().forEach(key -> {
//                builder.header(key, getAndTokenClaim(tokenClaims, key, false, false));
//            });
//
            logger.info("build header complete: " + JSONObject.toJSONString(lu));
        }
        return lu;
    }

    public static UserVo loginUser2Vo(LoginUser lu) {
        if (lu == null || lu.getUser() == null) {
            return null;
        }
        UserVo vo = new UserVo();
        User user = lu.getUser();
        vo.setId(user.getId());
        vo.setOrgId(user.getOrgId());
        vo.setOrgCode(lu.getOrgCode());
        vo.setOrgName(lu.getOrgName());
        vo.setDeptId(user.getDeptId());
        vo.setDeptName(lu.getDeptName());
        vo.setCode(user.getCode());
        vo.setPassword(user.getPassword());
        vo.setName(user.getName());
        vo.setAccessToken(lu.getAccessToken());
        vo.setTenantId(lu.getTenantId());
        vo.setAdministrator(false);
        vo.setAccessBackend(false);
        return vo;
    }
}
