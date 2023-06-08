package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.acm.domain.User;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.idm.login.config.IamLoginConfig;
import cn.org.bachelor.iam.token.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IamLoginConfig iamConf;

    public String login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getCode(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        JwtToken token = new JwtToken();
        token.setSub(userId);
        String jwt = JwtToken.create(token, iamConf.getPrivateKey());
        //authenticate存入redis

//        redisCache.setCacheObject("login:" + userId, loginUser);
        //把token响应给前端
        return jwt;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        String userid = loginUser.getUser().getId();
//        redisCache.deleteObject("login:" + userid);
    }
}

