package cn.org.bachelor.iam.dac.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.zhonghuanzhiyuan.eprs.plan.domain
 * @Date 2022/11/28 11:51
 * @Version 1.0
 */
@Data
@Schema(name = "DacArea", description = "DacArea")
public class DacArea implements Serializable {
    @Schema(name = "ID")
    @Column(name = "ID")
    private String id;

    @Schema(name = "NAME")
    @Column(name = "NAME")
    private String name;

    @Schema(name = "CODE")
    @Column(name = "CODE")
    private String code;

    @Schema(name = "PARENT_CODE")
    @Column(name = "PARENT_CODE")
    private String parentCode;

    @Schema(name = "更新人")
    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Schema(name = "更新时间")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Schema(name = "创建人")
    @Column(name = "CREATE_USER")
    private String createUser;

    @Schema(name = "创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Transient
    private List<DacArea> children;


}
