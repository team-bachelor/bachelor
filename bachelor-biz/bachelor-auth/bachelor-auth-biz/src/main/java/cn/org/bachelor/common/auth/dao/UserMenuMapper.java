package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.UserMenu;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMenuMapper extends Mapper<UserMenu> {
}