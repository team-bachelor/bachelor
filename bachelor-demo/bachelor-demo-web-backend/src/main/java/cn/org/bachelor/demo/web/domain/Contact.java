package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通讯录
 * </p>
 *
 * @author lixiaolong
 * @since 2020-12-24
 */
@Data
@Accessors(chain = true)
@Table(name = "eprs_contacts_contact")
@ApiModel(value = "BookInfo", description = "通讯录")
public class Contact implements Serializable {


    private static final long serialVersionUID = -4415874872666073560L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Id
    private Integer id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String bName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 性别1男2女0保密
     */
    @ApiModelProperty(value = "性别1男2女0保密", required = true)
    private Integer gender;
    /**
     * 办公电话
     */
    @ApiModelProperty(value = "办公电话")
    private String officePhone;
    /**
     * 家庭电话
     */
    @ApiModelProperty(value = "家庭电话")
    private String homePhone;
    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "家庭地址")
    private String homeAddress;
    /**
     * 所属机构id eprs_contacts_organization表的id
     */
    @ApiModelProperty(value = "所属机构id eprs_contacts_organization表的id", required = true)
    private Integer groupId;
    /**
     * 工作单位
     */
    @ApiModelProperty(value = "工作单位")
    private String officeName;
    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String department;
    /**
     * 是否有效：默认为是（1），否（0）
     */
    @ApiModelProperty(value = "是否有效：默认为是（1），否（0）", required = true)
    private Integer bStatus;
    /**
     * 排序号：数字越大越排在前面，默认为0
     */
    @ApiModelProperty(value = "排序号：数字越大越排在前面，默认为0", required = true)
    private Integer sortNum;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 添加人id
     */
    @ApiModelProperty(value = "添加人id")
    private String addId;
    /**
     * 修改人id
     */
    @ApiModelProperty(value = "修改人id")
    private String updateId;

    /** 扩展字段*/
    /**
     * 所属机构 eprs_contacts_organization表的id
     */
    @Transient
    @ApiModelProperty(value = "所属机构 eprs_contacts_organization表的b_name", required = true)
    private String groupName;


}

