package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.domain.Logs;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author lixiaolong
 */
@Repository
public interface LogsMapper extends Mapper<Logs> {
}
