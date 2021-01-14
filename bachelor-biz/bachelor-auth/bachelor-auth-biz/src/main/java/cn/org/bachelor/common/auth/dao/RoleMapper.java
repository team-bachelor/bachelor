package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleMapper extends Mapper<Role> {
}