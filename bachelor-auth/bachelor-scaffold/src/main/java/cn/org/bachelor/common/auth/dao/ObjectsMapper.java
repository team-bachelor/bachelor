package cn.org.bachelor.common.auth.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import cn.org.bachelor.common.auth.domain.Objects;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ObjectsMapper extends Mapper<Objects> {
    @Select("SELECT\n" +
            "o.ID,\n" +
            "o.NAME,\n" +
            "o.CODE,\n" +
            "o.URI,\n" +
            "o.OPERATE,\n" +
            "o.TYPE,\n" +
            "o.DOMAIN_CODE,\n" +
            "d.NAME as DOMAIN_NAME,\n" +
            "o.SEQ_ORDER as OBJ_ORDER,\n" +
            "d.SEQ_ORDER as DOM_ORDER\n" +
            "FROM cmn_auth_objects o \n" +
            "LEFT JOIN cmn_auth_obj_domain d \n" +
            "ON d.code = o.domain_code \n" +
            "AND o.TYPE = #{type}\n" +
            "ORDER BY d.SEQ_ORDER, o.SEQ_ORDER")
    @Results(value = {
            @Result(id = true, property = "id", column = "ID"),
            @Result (property="name", column="NAME"),
            @Result (property="permCode", column="CODE"),
            @Result (property="objUri", column="URI"),
            @Result (property="objOperate", column="OPERATE"),
            @Result (property="type", column="TYPE"),
            @Result (property="domainCode", column="DOMAIN_CODE"),
            @Result (property="domainName", column="DOMAIN_NAME")
    })
    List<cn.org.bachelor.common.auth.vo.Objects> findAllForType(String type);

}