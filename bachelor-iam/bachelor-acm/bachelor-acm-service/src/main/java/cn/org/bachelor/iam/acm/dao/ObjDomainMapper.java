package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.ObjDomain;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ObjDomainMapper extends Mapper<ObjDomain> {
}