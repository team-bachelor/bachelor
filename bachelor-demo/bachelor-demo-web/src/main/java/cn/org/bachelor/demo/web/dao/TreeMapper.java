package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.domain.OrganizationTree;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeMapper {


    /**
     * 组织机构树
     *
     * @return
     */
    @Select("SELECT " +
            "	a.`id`, " +
            "	a.`b_name`, " +
            "	a.`parent_id`, " +
            "	a.`duty_phone`, " +
            "	a.`leading_person`, " +
            "	a.`leading_phone`, " +
            "	a.`fax`, " +
            "	a.`address`, " +
            "	a.`description`, " +
            "	COUNT( b.`group_id` ) counts  " +
            "FROM " +
            "	`eprs_contacts_organization` a " +
            "	LEFT JOIN `eprs_contacts_contact` b ON b.group_id = a.id  " +
            "GROUP BY " +
            "	a.id  " +
            "ORDER BY " +
            "	a.sort_num DESC, a.id ")
    List<OrganizationTree> findOrganizationTree();
}
