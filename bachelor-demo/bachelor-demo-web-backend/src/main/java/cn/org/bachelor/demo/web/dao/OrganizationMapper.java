package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.domain.Organization;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrganizationMapper extends Mapper<Organization> {


    @Select("select * from eprs_contacts_organization ORDER BY sort_num DESC")
    List<Organization> selectAllOrganization();

    @Select({"<script>" +
            "select * from eprs_contacts_organization " +
            "<where> " +
            "<if test='bName != null and bName != \"\" '> AND b_name like CONCAT('%',#{bName},'%') </if>" +
            "<if test='parentId != null and parentId != \"\" '> AND parent_id = #{parentId}</if>" +
            "<if test='bStatus != null and bStatus != \"\" '> AND b_status = #{bStatus}</if>" +
            "<if test='sortNum != null and sortNum != \"\" '> AND sort_num = #{sortNum}</if>" +
            "<if test='address != null and address != \"\" '> AND address like CONCAT('%',#{address},'%') </if>" +
            "<if test='dutyPhone != null and dutyPhone != \"\" '> AND duty_phone like CONCAT('%',#{dutyPhone},'%') </if>" +
            "<if test='leadingPerson != null and leadingPerson != \"\" '> AND leading_person like CONCAT('%',#{leadingPerson},'%') </if>" +
            "<if test='leadingPhone != null and leadingPhone != \"\" '> AND leading_phone like CONCAT('%',#{leadingPhone},'%') </if>" +
            "<if test='fax != null and fax != \"\" '> AND fax like CONCAT('%',#{fax},'%') </if>" +
            "<if test='description != null and description != \"\" '> AND description like CONCAT('%',#{description},'%') </if>" +
            "<if test='addId != null and addId != \"\" '> AND add_id = #{addId}</if>" +
            "<if test='updateId != null and updateId != \"\" '> AND update_id = #{updateId}</if>" +
            "</where> " +
            "ORDER BY sort_num DESC" +
            "</script>"})
    List<Organization> selectByParams(Organization organization);


    /**
     * 批量导入
     *
     * @author lixiaolong
     * @date 2020/12/26 10:41
     */
    @Insert({"<script>",
            "insert into eprs_contacts_organization (b_name, parent_id, " +
                    "	sort_num, address, " +
                    "	duty_phone, leading_person, " +
                    "	leading_phone, fax, " +
                    "	description, add_time, " +
                    "	add_id " +
                    "	)values " +
                    "<foreach collection='list' item='organization' index='index' separator=','>" +
                    "(#{organization.bName}, #{organization.parentId}, " +
                    "#{organization.sortNum}, #{organization.address}, " +
                    "#{organization.dutyPhone}, #{organization.leadingPerson}, " +
                    "#{organization.leadingPhone}, #{organization.fax}, " +
                    "#{organization.description}, #{organization.addTime}, " +
                    "#{organization.addId} " +
                    ")" +
                    "</foreach>" +
                    "</script>"})
    int batchInsert(@Param(value = "list") List<Organization> alist);


    /**
     * 根据组织机构名称查询组织机构 按照sort_num 取最大的那个
     *
     * @param name 组织机构名称
     * @return Organization 组织机构
     * @author lixiaolong
     * @date 2020/12/26 13:51
     */
    @Select("<script>" +
            "select a.* from eprs_contacts_organization a where a.b_name = #{name} ORDER BY sort_num DESC limit 1 " +
            "</script>")
    Organization findByName(@Param("name") String name);

    /**
     * 新增并返回主键
     *
     * @param organization 组织机构
     * @return Organization 组织机构
     * @author lixiaolong
     * @date 2020/12/26 13:51
     */
    @Insert("<script>" +
            "insert into eprs_contacts_organization" +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "	<if test=\"id != null\">" +
            "		id," +
            "	</if>" +
            "	<if test=\"bName != null\">" +
            "		b_name," +
            "	</if>" +
            "	<if test=\"parentId != null\">" +
            "		parent_id," +
            "	</if>" +
            "	<if test=\"bStatus != null\">" +
            "		b_status," +
            "	</if>" +
            "	<if test=\"sortNum != null\">" +
            "		sort_num," +
            "	</if>" +
            "	<if test=\"address != null\">" +
            "		address," +
            "	</if>" +
            "	<if test=\"dutyPhone != null\">" +
            "		duty_phone," +
            "	</if>" +
            "	<if test=\"leadingPerson != null\">" +
            "		leading_person," +
            "	</if>" +
            "	<if test=\"leadingPhone != null\">" +
            "		leading_phone," +
            "	</if>" +
            "	<if test=\"fax != null\">" +
            "		fax," +
            "	</if>" +
            "	<if test=\"description != null\">" +
            "		description," +
            "	</if>" +
            "	<if test=\"addTime != null\">" +
            "		add_time," +
            "	</if>" +
            "	<if test=\"updateTime != null\">" +
            "		update_time," +
            "	</if>" +
            "	<if test=\"addId != null\">" +
            "		add_id," +
            "	</if>" +
            "	<if test=\"updateId != null\">" +
            "		update_id," +
            "	</if>" +
            "</trim>" +
            "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">" +
            "	<if test=\"id != null\">" +
            "		#{id}," +
            "	</if>" +
            "	<if test=\"bName != null\">" +
            "		#{bName}," +
            "	</if>" +
            "	<if test=\"parentId != null\">" +
            "		#{parentId}," +
            "	</if>" +
            "	<if test=\"bStatus != null\">" +
            "		#{bStatus}," +
            "	</if>" +
            "	<if test=\"sortNum != null\">" +
            "		#{sortNum}," +
            "	</if>" +
            "	<if test=\"address != null\">" +
            "		#{address}," +
            "	</if>" +
            "	<if test=\"dutyPhone != null\">" +
            "		#{dutyPhone}," +
            "	</if>" +
            "	<if test=\"leadingPerson != null\">" +
            "		#{leadingPerson}," +
            "	</if>" +
            "	<if test=\"leadingPhone != null\">" +
            "		#{leadingPhone}," +
            "	</if>" +
            "	<if test=\"fax != null\">" +
            "		#{fax}," +
            "	</if>" +
            "	<if test=\"description != null\">" +
            "		#{description}," +
            "	</if>" +
            "	<if test=\"addTime != null\">" +
            "		#{addTime,jdbcType=TIMESTAMP}," +
            "	</if>" +
            "	<if test=\"updateTime != null\">" +
            "		#{updateTime,jdbcType=TIMESTAMP}," +
            "	</if>" +
            "	<if test=\"addId != null\">" +
            "		#{addId}," +
            "	</if>" +
            "	<if test=\"updateId != null\">" +
            "		#{updateId}," +
            "	</if>" +
            "</trim>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertReturnKey(Organization organization);

}
