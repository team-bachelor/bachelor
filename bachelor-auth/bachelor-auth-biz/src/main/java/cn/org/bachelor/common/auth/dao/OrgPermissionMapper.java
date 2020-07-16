package cn.org.bachelor.common.auth.dao;


import cn.org.bachelor.common.auth.domain.OrgPermission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrgPermissionMapper extends Mapper<OrgPermission> {
}