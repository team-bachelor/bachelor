package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.vo.ContactOrganizationVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiaolong
 */
@Repository
public interface ContactOrganizationVoMapper {

    @Select("select a.*,b.* from eprs_contacts_contact a left join eprs_contacts_organization b on a.group_id = b.id ORDER BY a.sort_num DESC")
    List<ContactOrganizationVo> selectAllVo();

}
