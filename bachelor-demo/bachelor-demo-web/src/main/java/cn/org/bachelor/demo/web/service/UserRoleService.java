package cn.org.bachelor.demo.web.service;

import cn.org.bachelor.iam.acm.domain.UserRole;
import cn.org.bachelor.demo.web.dao.UserRoleVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author whongyu
 * @create by 2020-12-09
 */
@Service
public class UserRoleService {

    @Resource
    UserRoleVoMapper userRoleVoMapper;

    public UserRole getUserRole(String userId) {
        UserRole userRole = userRoleVoMapper.getByUserId(userId);
        return userRole;
    }
}
