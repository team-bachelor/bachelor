package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.acm.UserType;
import cn.org.bachelor.iam.acm.domain.LoginAttempt;
import cn.org.bachelor.iam.idm.login.EventPublisher;
import cn.org.bachelor.iam.idm.login.LoginEvent;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.idm.login.dao.LoginAttemptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class LoginAttemptService {

    @Autowired
    private LoginAttemptMapper loginAttemptMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IamContext iamContext;

    @Autowired
    private EventPublisher eventPublisher;


    public void clearLoginAttempt(UsernamePasswordCredential user) {
        String ip = iamContext.getRemoteIP();
        Example e = new Example(LoginAttempt.class);
        e.createCriteria()
                .andEqualTo("code", user.getUsername())
                .andEqualTo("type", UserType.MANAGER.name());
        loginAttemptMapper.deleteByExample(e);
    }

    public void setLoginAttempt(UsernamePasswordCredential user) {
        String ip = iamContext.getRemoteIP();
        Example e = new Example(LoginAttempt.class);
        e.createCriteria()
                .andEqualTo("code", user.getUsername())
                .andEqualTo("type", UserType.MANAGER.name());
        List<LoginAttempt> list = loginAttemptMapper.selectByExample(e);
        LoginAttempt la;
        if (list == null || list.size() == 0) {
            LoginUser userDetail = userDetailsService.loadUserByUsername(user.getUsername());
            la = new LoginAttempt();
            la.setId(userDetail.getId());
            la.setCode(user.getUsername());
            la.setIp(ip);
            la.setAttempt(1);
            la.setType(UserType.MANAGER.name());
            la.setUpdateTime(new Date());

        } else {
            la = list.get(0);
            loginAttemptMapper.deleteByExample(e);
            la.setAttempt(la.getAttempt() + 1);
        }
        loginAttemptMapper.insert(la);
        user.setFailCount(la.getAttempt());
        eventPublisher.publish(new LoginEvent(user, false));
    }

}

