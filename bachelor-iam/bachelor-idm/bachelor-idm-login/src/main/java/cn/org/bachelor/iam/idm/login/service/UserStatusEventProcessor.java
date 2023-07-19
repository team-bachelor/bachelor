package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.acm.UserType;
import cn.org.bachelor.iam.acm.domain.UserStatus;
import cn.org.bachelor.iam.idm.login.event.LoginEvent;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.acm.dao.UserStatusMapper;
import cn.org.bachelor.web.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserStatusEventProcessor {

    @Autowired
    private UserStatusMapper userStatusMapper;

    @Value("${bachelor.iam.login.attemptLimit:3}")
    private Integer limitCount;


    private UserStatus getUserStatus(UsernamePasswordCredential user) {
        Example e = new Example(UserStatus.class);
        e.createCriteria()
                .andEqualTo("code", user.getUsername())
                .andEqualTo("type", UserType.MANAGER.name());
        List<UserStatus> list = userStatusMapper.selectByExample(e);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    @EventListener(LoginEvent.class)
    public void setLock(LoginEvent event) {
        if (!event.isPass()) {
            UsernamePasswordCredential upc = event.getSource();
            Integer fc = upc.getFailCount();
            if (fc >= limitCount){
                UserStatus status = getUserStatus(upc);
                if(status == null){
                    status = new UserStatus();
                    status.setId(UuidUtil.getUUID());
                    status.setCode(upc.getUsername());
                    status.setType(UserType.MANAGER.name());
                    status.setUpdateTime(new Date());
                    status.setLocked(true);
                    userStatusMapper.insertSelective(status);
                }else{
                    status.setLocked(true);
                    userStatusMapper.updateByPrimaryKeySelective(status);
                }

            }
        }
    }

}

