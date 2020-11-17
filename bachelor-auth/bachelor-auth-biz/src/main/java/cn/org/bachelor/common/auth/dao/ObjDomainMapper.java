package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.ObjDomain;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ObjDomainMapper extends Mapper<ObjDomain> {

    @Override
    List<ObjDomain> selectAll();
}