package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.ObjOperation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ObjOperationMapper extends Mapper<ObjOperation> {
}