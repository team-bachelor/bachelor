package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.UserType;
import cn.org.bachelor.iam.acm.dao.UserStatusMapper;
import cn.org.bachelor.iam.acm.domain.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserStatusService {

    @Autowired
    private UserStatusMapper userStatusMapper;

    public UserStatus getUserStatus(String userName) {
        Example e = new Example(UserStatus.class);
        e.createCriteria()
                .andEqualTo("code", userName)
                .andEqualTo("type", UserType.MANAGER.name());
        List<UserStatus> list = userStatusMapper.selectByExample(e);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

}

