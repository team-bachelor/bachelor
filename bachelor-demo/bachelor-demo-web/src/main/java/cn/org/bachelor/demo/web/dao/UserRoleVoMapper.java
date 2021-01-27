package cn.org.bachelor.demo.web.dao;

import org.apache.ibatis.annotations.Select;
import cn.org.bachelor.iam.acm.domain.UserRole;
import cn.org.bachelor.demo.web.vo.UserRoleVO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author whongyu
 * @create by 2020-12-09
 */
@Repository
public interface UserRoleVoMapper extends Mapper<UserRoleVO> {

    @Select("<script>" +
            "select * from cmn_acm_user_role where user_id =#{userId}" +
            "</script>")
    UserRole getByUserId(String userId);
}
