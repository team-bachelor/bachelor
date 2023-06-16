package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.acm.domain.UserStatus;
import cn.org.bachelor.iam.idm.login.EventPublisher;
import cn.org.bachelor.iam.idm.login.LoginEvent;
import cn.org.bachelor.iam.idm.login.LoginException;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.idm.login.config.IamLoginConfig;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.token.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IamLoginConfig iamConf;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserStatusService userStatusService;

    public Authentication login(UsernamePasswordCredential user) {
        preLogin(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            //authenticate存入redis
//        redisCache.setCacheObject("login:" + userId, loginUser);
            loginAttemptService.clearLoginAttempt(user);
            eventPublisher.publish(new LoginEvent(user, true));
            return authenticate;
        } catch(Exception e){
            loginAttemptService.setLoginAttempt(user);
            throw new LoginException("用户名或密码错误", e);
        }
    }

    private void preLogin(UsernamePasswordCredential user) {
        UserStatus status = userStatusService.getUserStatus(user);
        if(status == null){
            return;
        }
        if(status.getDisabled()){
            throw new LoginException("该账户已禁用");
        }
        if(status.getExpired()){
            throw new LoginException("该账户已过期");
        }
        if(status.getLocked()){
            throw new LoginException("该账户被锁定");
        }
        if(status.getCredentialsExpired()){
            throw new LoginException("账户凭证已过期");
        }

    }

    public String getJwt(Authentication authenticate) {
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        JwtToken token = new JwtToken();
        token.setSub(userId);
        String jwt = token.generate(iamConf.getPrivateKey());

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

