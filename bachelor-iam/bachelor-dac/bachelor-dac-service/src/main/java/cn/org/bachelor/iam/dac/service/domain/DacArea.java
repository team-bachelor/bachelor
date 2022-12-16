package cn.org.bachelor.iam.dac.service.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
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
@ApiModel(value = "DacArea", description = "")
public class DacArea implements Serializable {
    @ApiModelProperty(value = "ID")
    @Column(name = "ID")
    private String id;

    @ApiModelProperty(value = "NAME")
    @Column(name = "NAME")
    private String name;

    @ApiModelProperty(value = "CODE")
    @Column(name = "CODE")
    private String code;

    @ApiModelProperty(value = "PARENT_CODE")
    @Column(name = "PARENT_CODE")
    private String parentCode;

    @ApiModelProperty(value = "更新人")
    @Column(name = "UPDATE_USER")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    @Column(name = "CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Transient
    private List<DacArea> children;


}
