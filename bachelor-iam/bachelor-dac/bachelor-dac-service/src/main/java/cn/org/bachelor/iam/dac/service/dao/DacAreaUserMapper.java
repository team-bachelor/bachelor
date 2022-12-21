package cn.org.bachelor.iam.dac.service.dao;

import cn.org.bachelor.iam.dac.service.domain.DacAreaUser;
import cn.org.bachelor.iam.dac.service.pojo.dto.QueryAreaUserDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DacAreaUserMapper extends Mapper<DacAreaUser> {

    @Select("<script>" +
            "SELECT * FROM cmn_dac_area_user " +
            " <where> " +
            //"    <if test='areaName != null and areaName != \"\"'> AND AREA_NAME = #{areaName} </if>" +
            //"    <if test='areaCode != null and areaCode != \"\"'> AND AREA_CODE = #{areaCode} </if>" +
            "    <if test='areaId != null and areaId != \"\"'> AND AREA_ID = #{areaId} </if>" +
            "    <if test='userName != null and userName != \"\"'> AND USER_NAME = #{userName} </if>" +
            //"    <if test='userCode != null and userCode != \"\"'> AND USER_CODE = #{userCode} </if>" +
            " </where> " +
            " ORDER BY CREATE_TIME DESC " +
            "</script>")
    List<DacAreaUser> selectByParam(QueryAreaUserDTO dto);

    @Delete("<script> " +
            "DELETE FROM cmn_dac_area_user " +
            " WHERE ID IN " +
            "    <foreach collection ='ids' item='id' open='(' separator=',' close=')'>" +
            "       #{id} " +
            "    </foreach> " +
            "</script> ")
    int deleteByIds(@Param("ids") List<String> ids);

    @Select("<script>" +
            "SELECT count(1) FROM cmn_dac_area_user " +
            " WHERE USER_CODE = #{userCode} " +
            "</script>")
    Integer countByUserCode(String userCode);

    @Select("<script>" +
            "SELECT AREA_ID FROM cmn_dac_area_user where USER_CODE = #{userCode} limit 1" +
            "</script>")
    String getAreaIdByUserCode(String userCode);

    @Select("<script>" +
            "SELECT AREA_CODE FROM cmn_dac_area_user where USER_CODE = #{userCode} limit 1" +
            "</script>")
    String getAreaCodeByUserCode(String userCode);
}
