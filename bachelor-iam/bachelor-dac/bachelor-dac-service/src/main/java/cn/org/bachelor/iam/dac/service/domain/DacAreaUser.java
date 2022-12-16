package cn.org.bachelor.iam.dac.service.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhuangJiaHui
 * @since 2022-11-28
 */
@Data
@ApiModel(value = "DacAreaUser对象", description = "")
@Table(name = "cmn_dac_area_user")
public class DacAreaUser implements Serializable {

    @ApiModelProperty(value = "ID")
    @Column(name = "ID")
    private String id;

    @ApiModelProperty(value = "区域ID")
    @Column(name = "AREA_ID")
    private String areaId;

    @ApiModelProperty(value = "区域名称")
    @Column(name = "AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "区域编码")
    @Column(name = "AREA_CODE")
    private String areaCode;

    @ApiModelProperty(value = "用户账号")
    @Column(name = "USER_CODE")
    private String userCode;

    @ApiModelProperty(value = "用户名")
    @Column(name = "USER_NAME")
    private String userName;

    @ApiModelProperty(value = "机构名")
    @Column(name = "ORG_NAME")
    private String orgName;

    @ApiModelProperty(value = "创建人")
    @Column(name = "CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;
}
