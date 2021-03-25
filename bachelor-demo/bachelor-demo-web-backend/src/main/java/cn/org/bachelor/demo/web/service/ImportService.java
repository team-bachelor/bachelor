package cn.org.bachelor.demo.web.service;

import lombok.extern.slf4j.Slf4j;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.demo.web.dao.ContactMapper;
import cn.org.bachelor.demo.web.dao.OrganizationMapper;
import cn.org.bachelor.demo.web.domain.Contact;
import cn.org.bachelor.demo.web.domain.ContactExcel;
import cn.org.bachelor.demo.web.domain.Organization;
import cn.org.bachelor.demo.web.domain.OrganizationExcel;
import cn.org.bachelor.demo.web.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lixiaolong
 * @date 2020/12/7 10:37
 * @description 导入excel操作
 */

@Service
@Slf4j
public class ImportService {
    @Resource
    OrganizationMapper organizationMapper;
    @Resource
    ContactMapper contactMapper;

    /**
     * 用来区分导入的数据
     */
    private final static String CONTACT = "2";

    /**
     * 通信录导入
     *
     * @param file     待导入的excel文件
     * @param dataType 文件类型 1:通讯录组织机构 2通讯录信息
     * @return
     * @Auther: lixiaolong
     * @Date: 2020/12/7 10:49
     */
    public int importExcel(HttpServletRequest request, MultipartFile file, String dataType) {
        long t1 = System.currentTimeMillis();
        int num = 0;
        if (CONTACT.equals(dataType)) {
            num = importContact(request, file);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("导入文件用时! cost:%sms", (t2 - t1)));

        return num;
    }

    /**
     * 导入通讯录组织机构 <br/>
     * 上级机构导入时，
     * 若为空 则表示没有上级机构，
     * 若不为空，则根据汉字取查询上级机构id，若找不到，则创建上级机构，并返回主键，
     * <p>
     * 同理 组织机构导入时也这么判断，若存在则进行修改 若不存在则进行创建
     *
     * @param file
     * @return int
     * @author lixiaolong
     * @date 2020/12/26 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    public int importOrganization(HttpServletRequest request, MultipartFile file) {

        List<OrganizationExcel> list = ExcelUtils.readExcel("", OrganizationExcel.class, file);
        log.info(list.get(0).toString());

        List<Organization> alist = new LinkedList<>();
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        Date date = new Date();
        for (OrganizationExcel t : list) {
            Organization book = new Organization();
            BeanUtils.copyProperties(t, book);
            book.setAddId(userId).setAddTime(date);
            alist.add(book);
        }
        return organizationMapper.batchInsert(alist);
    }

    /**
     * 导入通讯录信息 <br/>
     * 导入通讯录信息时候 判断所属机构是否存在 存在则取出来id
     * 不存在则新增一个组织机构并且返回id
     *
     * @param file
     * @return int
     * @author lixiaolong
     * @date 2020/12/26 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    public int importContact(HttpServletRequest request, MultipartFile file) {

        List<ContactExcel> list = ExcelUtils.readExcel("", ContactExcel.class, file);
        log.info(list.get(0).toString());

        List<Contact> alist = new LinkedList<>();
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        Date date = new Date();
        for (ContactExcel t : list) {
            Contact book = new Contact();
            BeanUtils.copyProperties(t, book);
            book.setAddId(userId).setAddTime(date);

            // 处理所属机构
            Organization organization = organizationMapper.findByName(t.getGroupName());
            if (organization != null) {
                book.setGroupId(organization.getId());
            } else {
                organization = new Organization();
                organization.setBName(t.getGroupName())
                        .setSortNum(t.getSortNum())
                        .setAddress(t.getOfficeName())
                        .setDutyPhone(t.getOfficePhone())
                        .setLeadingPerson(t.getBName())
                        .setLeadingPhone(t.getPhone())
                        .setAddTime(date)
                        .setAddId(userId);
                organizationMapper.insertReturnKey(organization);
                book.setGroupId(organization.getId());
            }

            // 处理性别 1男2女 0保密
            switch (t.getGender()) {
                case "男":
                    book.setGender(1);
                    break;
                case "女":
                    book.setGender(2);
                    break;
                default:
                    book.setGender(0);
            }

            alist.add(book);
        }
        int num = contactMapper.batchInsert(alist);
        alist.clear();
        return num;
    }

}
