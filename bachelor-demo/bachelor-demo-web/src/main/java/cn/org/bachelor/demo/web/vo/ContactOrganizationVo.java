package cn.org.bachelor.demo.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * eprs_contacts_organization表和 eprs_contacts_contact
 */
@Data
public class ContactOrganizationVo {

    private Integer id;
    private String bName;
    private Integer parentId;
    private Integer bStatus;
    private String address;
    private String dutyPhone;
    private String leadingPerson;
    private String leadingPhone;
    private String fax;
    private String description;
    private Date addTime;
    private Date updateTime;
    private String addId;
    private String updateId;

    private String groupName;
    private String phone;
    private String email;
    private Integer gender;
    private String officePhone;
    private String homePhone;
    private String homeAddress;
    private Integer groupId;
    private String officeName;
    private String department;
    private Integer sortNum;

}
