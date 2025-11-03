package cn.org.bachelor.iam.dac.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhuangJiaHui
 * @since 2022-11-28
 */
@Data
@Schema(name = "DacAreaUser对象", description = "")
@Table(name = "cmn_dac_area_user")
public class DacAreaUser implements Serializable {

    @Schema(name = "ID")
    @Column(name = "ID")
    private String id;

    @Schema(name = "区域ID")
    @Column(name = "AREA_ID")
    private String areaId;

    @Schema(name = "区域名称")
    @Column(name = "AREA_NAME")
    private String areaName;

    @Schema(name = "区域编码")
    @Column(name = "AREA_CODE")
    private String areaCode;

    @Schema(name = "用户账号")
    @Column(name = "USER_CODE")
    private String userCode;

    @Schema(name = "用户名")
    @Column(name = "USER_NAME")
    private String userName;

    @Schema(name = "机构名")
    @Column(name = "ORG_NAME")
    private String orgName;

    @Schema(name = "创建人")
    @Column(name = "CREATE_USER")
    private String createUser;

    @Schema(name = "创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;
}
