package cn.org.bachelor.iam.idm.login.dao;

import cn.org.bachelor.iam.acm.domain.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserLoginMapper extends BaseMapper<User> {
}
