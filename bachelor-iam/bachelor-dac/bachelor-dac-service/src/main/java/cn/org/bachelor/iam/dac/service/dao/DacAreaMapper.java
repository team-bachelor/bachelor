package cn.org.bachelor.iam.dac.service.dao;

import cn.org.bachelor.iam.dac.service.domain.DacArea;
import cn.org.bachelor.iam.dac.service.pojo.vo.SearchDacAreaVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.zhonghuanzhiyuan.eprs.plan.dao
 * @Date 2022/11/28 11:54
 * @Version 1.0
 */
@Repository
public interface DacAreaMapper extends Mapper<DacArea> {

    @Select(("<script>" +
            "SELECT CODE FROM cmn_dac_area WHERE ISNULL(PARENT_CODE) " +
            "</script>"))
    List<String> selectParentCodeIsNull();

    @Select(("<script>" +
            "SELECT CODE FROM cmn_dac_area Where PARENT_CODE =  (select PARENT_CODE from cmn_dac_area where CODE = #{code}) " +
            "</script>"))
    List<String> selectAllCode(String code);

    @Select(("<script>" +
            "SELECT CODE FROM cmn_dac_area WHERE PARENT_CODE = #{parentCode} " +
            "</script>"))
    List<String> selectCodeByParentCode(String parentCode);

    @Select(("<script>" +
            "SELECT PARENT_CODE FROM cmn_dac_area WHERE CODE = #{code} " +
            "</script>"))
    String selectParentCodeByCode(String code);

    @Select(("<script>" +
            "SELECT * FROM cmn_dac_area WHERE ISNULL(PARENT_CODE) " +
            "</script>"))
    List<DacArea> selectDacAreaParentCodeIsNull();

    @Select(("<script>" +
            "SELECT ID as id, NAME as name, CODE as code, PARENT_CODE as parentCode FROM cmn_dac_area WHERE IS_DELETED = '1' and PARENT_CODE = #{code} " +
            "</script>"))
    List<DacArea> selectDacAreaByParentCode(String code);


    @Select("<script> " +
            "select * from cmn_dac_area where IS_DELETED = 1 " +
            "    <if test=\"code != null and code != ''\"> " +
            "        and PARENT_CODE = #{code} " +
            "    </if>" +
            "    <if test=\"name != null and name != ''\"> " +
            "        and NAME like '${name}%' " +
            "    </if>" +
            "order by UPDATE_TIME desc" +
            "</script>")
    List<DacArea> getDacAreaList(SearchDacAreaVo searchDacAreaVo);

    @Insert("<script> " +
            " insert into cmn_dac_area (ID, NAME, CODE, " +
            "      PARENT_CODE, UPDATE_USER, UPDATE_TIME, CREATE_USER, " +
            "      CREATE_TIME) " +
            "    values (#{id}, #{name}, #{code}, " +
            "      #{parentCode}, #{updateUser}, #{updateTime}, #{createUser}, " +
            "      #{createTime}) " +
            "</script>")
    void insertDacArea(DacArea dacArea);

    @Select("<script>" +
            "SELECT CODE FROM cmn_dac_area WHERE CODE = #{code} " +
            "</script>")
    List<String> selectCodeByCode(String code);

    @Update("<script>" +
            "update cmn_dac_area set IS_DELETED = '0' where ID = #{id} " +
            "</script>")
    void deleteArea(String id);

    @Select("<script>" +
            "SELECT * FROM cmn_dac_area WHERE ID = #{id} " +
            "</script>")
    DacArea selectAreaById(String id);

    @Update("<script>" +
            "update cmn_dac_area set NAME = #{name} where ID = #{id} " +
            "</script>")
    void updateDacArea(@Param(value = "id") String id, @Param(value = "name") String name);

    @Select("<script>" +
            "SELECT CODE as typeCode, IFNULL(PARENT_CODE, null) as parentTypeCode, NAME as typeName FROM cmn_dac_area " +
            "</script>")
    List<Map<Object, Object>> getAllArea();

    @Select("<script>" +
            "SELECT CODE FROM cmn_dac_area where NAME = #{name} " +
            "</script>")
    String selectCodeByName(String name);

    @Select("<script>" +
            "SELECT NAME FROM cmn_dac_area where CODE = #{code} " +
            "</script>")
    String selectNameByCode(String code);

    @Select("<script>" +
            "SELECT code,name FROM cmn_dac_area " +
            "</script>")
    List<Map<String, String>> selectAllAreaCode();
}
