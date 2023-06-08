package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.RolePermission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RolePermissionMapper extends Mapper<RolePermission> {

    @Select("SELECT rp.ID,\n" +
            "rp.ROLE_CODE,\n" +
            "rp.OBJ_CODE,\n" +
            "rp.OBJ_URI,\n" +
            "rp.OBJ_OPERATE,\n" +
            "rp.UPDATE_TIME,\n" +
            "rp.UPDATE_USER\n" +
            "FROM cmn_acm_role_permission rp\n" +
            "JOIN cmn_acm_user_role ur ON\n" +
            "rp.ROLE_CODE = ur.ROLE_CODE AND\n" +
            "ur.USER_CODE = #{userCode}")
    @Results(value = {
            @Result(id = true, column = "ID", property = "id"),
            @Result(property = "roleCode", column = "ROLE_CODE"),
            @Result(property = "objCode", column = "OBJ_CODE"),
            @Result(property = "objUri", column = "OBJ_URI"),
            @Result(property = "objOperate", column = "OBJ_OPERATE"),
            @Result(property = "updateTime", column = "UPDATE_TIME"),
            @Result(property = "updateUser", column = "UPDATE_USER")
    })
    List<RolePermission> selectViaUserCode(String userCode);
}