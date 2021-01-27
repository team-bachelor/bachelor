package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import cn.org.bachelor.demo.web.common.annotation.ExcelColumn;


/**
 * 导入通讯录信息对应的excel
 *
 * @author lixiaolong
 * @date 2020/12/26 11:09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BookInfoExcel", description = "导入通讯录信息对应的excel")
public class ContactExcel {

    /**
     * 姓名
     */
    @ExcelColumn(value = "姓名", col = 1)
    @ApiModelProperty(value = "姓名", required = true)
    private String bName;
    /**
     * 手机号
     */
    @ExcelColumn(value = "手机号", col = 1)
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    /**
     * 邮箱
     */
    @ExcelColumn(value = "邮箱", col = 1)
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 性别1男2女0保密
     */
    @ExcelColumn(value = "性别", col = 1)
    @ApiModelProperty(value = "性别1男2女0保密", required = true)
    private String gender;
    /**
     * 办公电话
     */
    @ExcelColumn(value = "办公电话", col = 1)
    @ApiModelProperty(value = "办公电话")
    private String officePhone;
    /**
     * 家庭电话
     */
    @ExcelColumn(value = "家庭电话", col = 1)
    @ApiModelProperty(value = "家庭电话")
    private String homePhone;
    /**
     * 家庭地址
     */
    @ExcelColumn(value = "家庭地址", col = 1)
    @ApiModelProperty(value = "家庭地址")
    private String homeAddress;
    /**
     * 所属机构
     */
    @ExcelColumn(value = "所属机构", col = 1)
    @ApiModelProperty(value = "所属机构", required = true)
    private String groupName;
    /**
     * 工作单位
     */
    @ExcelColumn(value = "工作单位", col = 1)
    @ApiModelProperty(value = "工作单位")
    private String officeName;
    /**
     * 部门
     */
    @ExcelColumn(value = "部门", col = 1)
    @ApiModelProperty(value = "部门")
    private String department;
    /**
     * 排序号：数字越大越排在前面，默认为0
     */
    @ExcelColumn(value = "排序号", col = 1)
    @ApiModelProperty(value = "排序号：数字越大越排在前面，默认为0", required = true)
    private Integer sortNum;
}

