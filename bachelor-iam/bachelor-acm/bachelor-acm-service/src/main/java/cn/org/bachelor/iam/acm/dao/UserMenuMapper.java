package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.UserMenu;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMenuMapper extends Mapper<UserMenu> {
}