package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.acm.domain.UserStatus;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.idm.login.*;
import cn.org.bachelor.iam.idm.login.config.IamLocalLoginConfig;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IamLocalLoginConfig iamConf;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IamContext iamContext;

    public Authentication login(UsernamePasswordCredential user) {
        preLogin(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            //authenticate存入redis
            RedisCacheHelper.setLoginUser(redisTemplate, (LoginUser) authenticate.getPrincipal());
//        redisCache.setCacheObject("login:" + userId, loginUser);
            loginAttemptService.clearLoginAttempt(user);
            eventPublisher.publish(new LoginEvent(user, true));
            return authenticate;
        } catch (Exception e) {
            loginAttemptService.setLoginAttempt(user);
            throw new LoginException("用户名或密码错误", e);
        }
    }

    private void preLogin(UsernamePasswordCredential user) {
        UserStatus status = userStatusService.getUserStatus(user);
        if (status == null) {
            return;
        }
        if (status.getDisabled()) {
            throw new LoginException("该账户已禁用");
        }
        if (status.getExpired()) {
            throw new LoginException("该账户已过期");
        }
        if (status.getLocked()) {
            throw new LoginException("该账户被锁定");
        }
        if (status.getCredentialsExpired()) {
            throw new LoginException("账户凭证已过期");
        }

    }

    public String getJwt(Authentication authenticate) {
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        AbstractIamCredential credential = new AbstractIamCredential<String>() {
        };
        credential.setCredential("");
        credential.setExpiresTime(getLoginExpiresTime());
        JwtToken token = JwtToken.create(loginUser, credential);
        String jwt = token.generate(iamConf.getPrivateKey());

        //把token响应给前端
        return jwt;
    }

    private Date getLoginExpiresTime() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE, 1);
        // 这个时间就是日期往后推一天的结果
        return calendar.getTime();
    }

    public void refreshLogin() {
        UserVo user = iamContext.getUser();
        if(user != null) {
            String userId = user.getId();
            RedisCacheHelper.refreshLoginUser(redisTemplate, userId);
        }
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        RedisCacheHelper.delLoginUser(redisTemplate, loginUser.getId());
    }
}

