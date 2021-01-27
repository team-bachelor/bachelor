package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import cn.org.bachelor.demo.web.common.annotation.ExcelColumn;

/**
 * 导入通讯录组织机构对应的excel
 *
 * @author lixiaolong
 * @date 2020/12/26 11:09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BookGroupExcel", description = "导入通讯录组织机构对应的excel")
public class OrganizationExcel {

    /**
     * 姓名
     */
    @ExcelColumn(value = "组织机构名称", col = 1)
    @ApiModelProperty(value = "组织机构名称", required = true)
    private String bName;
    /**
     * 父类别
     */
    @ExcelColumn(value = "上级机构", col = 1)
    @ApiModelProperty(value = "上级机构")
    private Integer parentId;
    /**
     * 排序号：数字越大越排在前面，默认为0
     */
    @ExcelColumn(value = "排序号", col = 1)
    @ApiModelProperty(value = "排序号：数字越大越排在前面，默认为0", required = true)
    private Integer sortNum;
    /**
     * 地址
     */
    @ExcelColumn(value = "地址", col = 1)
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 值班电话
     */
    @ExcelColumn(value = "值班电话", col = 1)
    @ApiModelProperty(value = "值班电话")
    private String dutyPhone;
    /**
     * 主管负责人（非必填）
     */
    @ExcelColumn(value = "主管负责人", col = 1)
    @ApiModelProperty(value = "主管负责人（非必填）")
    private String leadingPerson;
    /**
     * 负责人联系电话
     */
    @ExcelColumn(value = "负责人联系电话", col = 1)
    @ApiModelProperty(value = "负责人联系电话")
    private String leadingPhone;
    /**
     * 传真
     */
    @ExcelColumn(value = "传真", col = 1)
    @ApiModelProperty(value = "传真")
    private String fax;
    /**
     * 描述
     */
    @ExcelColumn(value = "描述", col = 1)
    @ApiModelProperty(value = "描述")
    private String description;

}
