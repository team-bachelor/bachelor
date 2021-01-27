package cn.org.bachelor.demo.web.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import cn.org.bachelor.demo.web.domain.Contact;
import cn.org.bachelor.demo.web.vo.ContactOrganizationVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ContactMapper extends Mapper<Contact> {

    /**
     * 查询所有组织机构
     *
     * @return
     */
    @Select("select * from eprs_contacts_contact ORDER BY sort_num DESC")
    List<Contact> selectAllContact();

    @Select({"<script>" +
            "select a.*, b.b_name groupName from eprs_contacts_contact a left join eprs_contacts_organization b on a.group_id = b.id  " +
            "<where> " +
            "<if test='bName != null and bName != \"\" '> AND a.b_name like CONCAT('%',#{bName},'%') </if>" +
            "<if test='phone != null and phone != \"\" '> AND a.phone like CONCAT('%',#{phone},'%') </if>" +
            "<if test='email != null and email != \"\" '> AND a.email like CONCAT('%',#{email},'%') </if>" +
            "<if test='gender != null and gender != \"\" '> AND a.gender = #{gender}</if>" +
            "<if test='officePhone != null and officePhone != \"\" '> AND a.office_phone like CONCAT('%',#{officePhone},'%') </if>" +
            "<if test='homePhone != null and homePhone != \"\" '> AND a.home_phone like CONCAT('%',#{homePhone},'%') </if>" +
            "<if test='homeAddress != null and homeAddress != \"\" '> AND a.home_address like CONCAT('%',#{homeAddress},'%') </if>" +
            "<if test='groupId != null and groupId != \"\" '> AND a.group_id = #{groupId}</if>" +
            "<if test='officeName != null and officeName != \"\" '> AND a.office_name like CONCAT('%',#{officeName},'%') </if>" +
            "<if test='department != null and department != \"\" '> AND a.department = #{department}</if>" +
            "<if test='bStatus != null and bStatus != \"\" '> AND a.b_status = #{bStatus}</if>" +
            "<if test='sortNum != null and sortNum != \"\" '> AND a.sort_num = #{sortNum}</if>" +

            "<if test='addId != null and addId != \"\" '> AND a.add_id = #{addId}</if>" +
            "<if test='updateId != null and updateId != \"\" '> AND a.update_id = #{updateId}</if>" +
            "</where> " +
            "ORDER BY a.add_time DESC" +
            "</script>"})
    List<Contact> selectByParam(Contact contact);


    /**
     * 批量导入
     *
     * @author lixiaolong
     * @date 2020/12/26 10:41
     */
    @Insert({"<script>",
            "insert into eprs_contacts_contact (b_name, phone, " +
                    "	email, gender, office_phone, " +
                    "	home_phone, home_address, " +
                    "	group_id, office_name, " +
                    "	department, " +
                    "	sort_num, add_time, " +
                    "	add_id)values " +
                    "<foreach collection='list' item='contact' index='index' separator=','>" +
                    "(#{contact.bName}, #{contact.phone}, " +
                    "#{contact.email}, #{contact.gender}, #{contact.officePhone}, " +
                    "#{contact.homePhone}, #{contact.homeAddress}, " +
                    "#{contact.groupId}, #{contact.officeName}, " +
                    "#{contact.department}, " +
                    "#{contact.sortNum}, #{contact.addTime}, " +
                    "#{contact.addId})" +
                    "</foreach>" +
                    "</script>"})
    int batchInsert(@Param(value = "list") List<Contact> alist);

    @Select("select a.*,b.*,b.b_name As group_name from eprs_contacts_contact a left join eprs_contacts_organization b" +
            " on a.group_id = b.id where a.id = #{id}")
    ContactOrganizationVo selectAllById(int id);

}
