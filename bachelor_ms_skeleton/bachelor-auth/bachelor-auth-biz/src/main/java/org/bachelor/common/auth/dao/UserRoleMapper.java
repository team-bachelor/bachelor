package org.bachelor.common.auth.dao;

import org.bachelor.common.auth.domain.UserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {
    List<UserRole> selectViaUserCode(String userCode);
}