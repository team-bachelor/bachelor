package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.UserRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserRoleMapper extends Mapper<UserRole> {
    List<UserRole> selectViaUserCode(String userCode);
}