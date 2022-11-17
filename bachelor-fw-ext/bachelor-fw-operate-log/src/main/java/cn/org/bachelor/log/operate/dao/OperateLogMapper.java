package cn.org.bachelor.log.operate.dao;

import cn.org.bachelor.log.operate.domain.OperateLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OperateLogMapper extends Mapper<OperateLog> {

}