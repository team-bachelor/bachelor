package cn.org.bachelor.iam.idm.login.dao;

import cn.org.bachelor.iam.acm.domain.LoginAttempt;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Intellij IDEA.
 * User:  lz
 */
@Repository
public interface LoginAttemptMapper extends Mapper<LoginAttempt> {

}
