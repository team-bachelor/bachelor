package cn.org.bachelor.demo.web.service;

import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.demo.web.dao.ContactOrganizationVoMapper;
import cn.org.bachelor.demo.web.dao.OrganizationMapper;
import cn.org.bachelor.demo.web.domain.Organization;
import cn.org.bachelor.demo.web.vo.ContactOrganizationVo;
import cn.org.bachelor.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author 易罐
 */
@Service
public class OrganizationService extends BaseService<Organization> {

    @Resource
    OrganizationMapper organizationMapper;
    @Resource
    ContactOrganizationVoMapper contactOrganizationVoMapper;


    public int update(HttpServletRequest request, Organization organization) {
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        organization.setUpdateId(userId);
        return organizationMapper.updateByPrimaryKeySelective(organization);
    }

    public int insert(HttpServletRequest request, Organization organization) {
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        organization.setAddId(userId).setAddTime(new Date());
        return organizationMapper.insert(organization);
    }

    public PageInfo<Organization> selectByParams(Organization organization) {
        List<Organization> list = organizationMapper.selectByParams(organization);
        return new PageInfo<>(list);
    }

    /**
     * 分页查询
     *
     * @return
     */
    public PageInfo<ContactOrganizationVo> selectAllVo() {
        List<ContactOrganizationVo> list = contactOrganizationVoMapper.selectAllVo();
        return new PageInfo<>(list);
    }

}
