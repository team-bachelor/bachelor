package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通讯录组织机构
 * </p>
 *
 * @author lixiaolong
 * @since 2020-12-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrganizationTree", description = "通讯录组织机构树")
public class OrganizationTree implements Serializable {


    private static final long serialVersionUID = -8251445793612683202L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称", required = true)
    private String bName;

    /**
     * 父机构
     */
    @ApiModelProperty(value = "上级机构")
    private Integer parentId;
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
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 值班电话
     */
    @ApiModelProperty(value = "值班电话")
    private String dutyPhone;
    /**
     * 主管负责人（非必填）
     */
    @ApiModelProperty(value = "主管负责人（非必填）")
    private String leadingPerson;
    /**
     * 负责人联系电话
     */
    @ApiModelProperty(value = "负责人联系电话")
    private String leadingPhone;
    /**
     * 传真
     */
    @ApiModelProperty(value = "传真")
    private String fax;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 添加时间:申请时间
     */
    @ApiModelProperty(value = "添加时间:申请时间")
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
    /**
     * 子组织机构
     */
    @Transient
    @ApiModelProperty(value = "子组织机构")
    private List<OrganizationTree> children;
    @Transient
    @ApiModelProperty(value = "组织机构下的人员数量")
    private int counts;
}

