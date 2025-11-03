package cn.org.bachelor.iam.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author liuzhuo
 */
@Data
public class IamDept {


    @Schema(name = "id")
    @JsonProperty("id")
    private String id;

    @Schema(name = "编码")
    private String code;

    @Schema(name = "名称")
    @JsonProperty("name")
    private String name;

    @Schema(name = "机构id")
    private String orgId;

    @Schema(name = "父部门ID")
    private String pid;

    @Schema(name = "部门类型ID")
    private String deptTypeId;

    @Schema(name = "邮编")
    private String postcode;

    @Schema(name = "地址")
    private String address;

    @Schema(name = "电话")
    private String telephone;

    @Schema(name = "传真")
    private String fax;

    @Schema(name = "区域ID")
    private String areaId;

    @Schema(name = "隶属关系")
    private String relationshipId;

    @Schema(name = "单位级别")
    private String levelId;

    @Schema(name = "主管单位名称")
    private String chargeDeptName;

    @Schema(name = "单位性质")
    private String propertyId;

    @Schema(name = "机构id与机构")
    private String economicTypeId;

    @Schema(name = "机构id与机构")
    private String industryId;

    @Schema(name = "机构id与机构")
    private String sort;

    @Schema(name = "更新人")
    private String updateUser;

    @Schema(name = "更新时间")
    private Date updateTime;

    @Schema(name = "创建人")
    private String createUser;

    @Schema(name = "创建时间")
    private Date createTime;
}
