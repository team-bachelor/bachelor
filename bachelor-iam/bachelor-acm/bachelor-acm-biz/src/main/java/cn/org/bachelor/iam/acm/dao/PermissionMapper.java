package cn.org.bachelor.iam.acm.dao;

import cn.org.bachelor.iam.acm.domain.ObjPermission;
import cn.org.bachelor.iam.acm.vo.ObjPermissionVo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PermissionMapper extends Mapper<ObjPermission> {
    @Select("SELECT\n" +
            "o.ID,\n" +
            "o.NAME,\n" +
            "o.CODE,\n" +
            "o.URI,\n" +
            "o.OPERATE,\n" +
            "o.TYPE,\n" +
            "o.DOMAIN_CODE,\n" +
            "d.NAME as DOMAIN_NAME,\n" +
            "o.IS_SYS,\n" +
            "o.SEQ_ORDER as OBJ_ORDER,\n" +
            "d.SEQ_ORDER as DOM_ORDER\n" +
            "FROM cmn_acm_obj_permission o \n" +
            "LEFT JOIN cmn_acm_obj_domain d \n" +
            "ON d.code = o.domain_code \n" +
            "AND o.TYPE = #{type}\n" +
            "ORDER BY d.SEQ_ORDER, o.SEQ_ORDER")
    @Results(value = {
            @Result(id = true, property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "code", column = "CODE"),
            @Result(property = "uri", column = "URI"),
            @Result(property = "operate", column = "OPERATE"),
            @Result(property = "isSys", column = "IS_SYS"),
            @Result(property = "type", column = "TYPE"),
            @Result(property = "domainCode", column = "DOMAIN_CODE"),
            @Result(property = "domainName", column = "DOMAIN_NAME")
    })
    List<ObjPermissionVo> findAllForType(String type);

}