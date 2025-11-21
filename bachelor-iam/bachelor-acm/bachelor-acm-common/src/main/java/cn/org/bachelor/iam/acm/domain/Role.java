package cn.org.bachelor.iam.acm.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import jakarta.persistence.*;

@Data
@Schema(description = "角色")
@Table(name = "cmn_acm_role")
public class Role {
    /**
     * ID
     */
    @Id
    @Schema(description = "角色ID")
    @Column(name = "ID")
    private String id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Column(name = "NAME")
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Column(name = "CODE")
    private String code;

    @Transient
    @Schema(description = "组织机构名称")
    private String orgName;

    /**
     * 组织机构编码
     */
     @Schema(description = "组织机构编码")
    @Column(name = "ORG_CODE")
    private String orgCode;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @Column(name = "UPDATE_USER")
    private String updateUser;
}