package cn.org.bachelor.iam.idm.login.service;

import cn.org.bachelor.iam.acm.domain.User;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.idm.login.dao.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author lz
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserLoginMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        //根据用户名查询用户信息

        User user = new User();
        user.setCode(userCode);
        List<User> userList = userMapper.select(user);
        //如果查询不到数据就通过抛出异常来给出提示
        if(userList == null || userList.size() == 0){
            throw new RuntimeException("用户名或密码错误");
        }
        user = userList.get(0);

        //TODO 根据用户查询权限信息 添加到LoginUser中

        //封装成UserDetails对象返回 
        return new LoginUser(user);
    }
}

