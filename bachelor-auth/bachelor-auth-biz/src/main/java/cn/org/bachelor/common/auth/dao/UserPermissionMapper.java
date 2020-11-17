package cn.org.bachelor.common.auth.dao;

import cn.org.bachelor.common.auth.domain.UserPermission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserPermissionMapper extends Mapper<UserPermission> {

    @Select("SELECT up.ID,\n" +
            "        up.USER_CODE,\n" +
            "        up.OBJ_CODE,\n" +
            "        up.OBJ_URI,\n" +
            "        up.OBJ_OPERATE,\n" +
            "        up.UPDATE_TIME,\n" +
            "        up.UPDATE_USER\n" +
            "        FROM cmn_auth_user_permission up WHERE\n" +
            "        up.USER_CODE =  #{userCode}")
    @Results(value = {
            @Result(id = true, column = "ID", property = "id"),
            @Result(property = "userCode", column = "USER_CODE"),
            @Result(property = "objCode", column = "OBJ_CODE"),
            @Result(property = "objUri", column = "OBJ_URI"),
            @Result(property = "objOperate", column = "OBJ_OPERATE"),
            @Result(property = "updateTime", column = "UPDATE_TIME"),
            @Result(property = "updateUser", column = "UPDATE_USER")
    })
    List<UserPermission> selectViaUserCode(String userCode);
}