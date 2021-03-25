package cn.org.bachelor.demo.web.service;

import com.github.pagehelper.PageInfo;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.demo.web.dao.ContactMapper;
import cn.org.bachelor.demo.web.domain.Contact;
import cn.org.bachelor.demo.web.vo.ContactOrganizationVo;
import cn.org.bachelor.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ContactService extends BaseService<Contact> {

    @Resource
    ContactMapper bookInfoMapper;

    public int insert(HttpServletRequest request, Contact contact) {
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        contact.setAddId(userId).setAddTime(new Date());
        return bookInfoMapper.insert(contact);
    }

    public int update(HttpServletRequest request, Contact contact) {
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        contact.setUpdateId(userId);
        return bookInfoMapper.updateByPrimaryKeySelective(contact);
    }

    public PageInfo<Contact> selectAllByPage() {
        List<Contact> bookInfoList = bookInfoMapper.selectAllContact();
        PageInfo<Contact> bookInfoPage = new PageInfo<>(bookInfoList);
        return bookInfoPage;
    }

    public PageInfo<Contact> selectByParams(Contact contact) {
        List<Contact> bookInfoList = bookInfoMapper.selectByParam(contact);
        PageInfo<Contact> bookInfoPageInfo = new PageInfo<>(bookInfoList);
        return bookInfoPageInfo;
    }

    public ContactOrganizationVo selectAllById(int id) {
        return bookInfoMapper.selectAllById(id);

    }


}
