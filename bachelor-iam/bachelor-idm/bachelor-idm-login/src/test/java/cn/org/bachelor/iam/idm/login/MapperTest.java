package cn.org.bachelor.iam.idm.login;

import cn.org.bachelor.iam.acm.domain.User;
import cn.org.bachelor.iam.idm.login.dao.UserLoginMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class)
public class MapperTest {

    @Autowired
    private UserLoginMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        System.out.println(passwordEncoder.encode("1234"));
    }
}
