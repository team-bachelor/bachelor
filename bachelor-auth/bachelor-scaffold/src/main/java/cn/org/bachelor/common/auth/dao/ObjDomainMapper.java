package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.ObjDomain;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ObjDomainMapper extends Mapper<ObjDomain> {

    @Override
    List<ObjDomain> selectAll();
}