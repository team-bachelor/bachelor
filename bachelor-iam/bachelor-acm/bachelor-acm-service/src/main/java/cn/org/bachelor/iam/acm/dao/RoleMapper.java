package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleMapper extends Mapper<Role> {
}