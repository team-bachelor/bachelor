package cn.org.bachelor.iam.acm.dao;


import cn.org.bachelor.iam.acm.domain.OrgPermission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrgPermissionMapper extends Mapper<OrgPermission> {
}