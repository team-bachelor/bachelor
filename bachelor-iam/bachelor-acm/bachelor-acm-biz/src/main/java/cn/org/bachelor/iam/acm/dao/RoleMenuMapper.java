package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.RoleMenu;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleMenuMapper extends Mapper<RoleMenu> {
    @Select("SELECT rm.ID, " +
            "rm.ROLE_CODE, " +
            "rm.MENU_CODE, " +
            "rm.UPDATE_TIME, " +
            "rm.UPDATE_USER " +
            "FROM cmn_acm_role_menu rm " +
            "JOIN cmn_acm_user_role ur " +
            "ON rm.ROLE_CODE = ur.ROLE_CODE " +
            "AND ur.USER_CODE = #{userCode}")
    @Results(value = {
            @Result(id = true, column = "ID", property = "id"),
            @Result(property = "roleCode", column = "ROLE_CODE"),
            @Result(property = "objCode", column = "OBJ_CODE"),
            @Result(property = "updateTime", column = "UPDATE_TIME"),
            @Result(property = "updateUser", column = "UPDATE_USER")
    })
    List<RoleMenu> selectViaUserCode(String userCode);
}