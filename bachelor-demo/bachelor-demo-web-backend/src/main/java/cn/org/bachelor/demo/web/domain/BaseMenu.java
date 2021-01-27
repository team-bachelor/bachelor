package cn.org.bachelor.demo.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gxf on 2021/1/16 14:59
 */
@Data
@Accessors(chain = true)
@Table(name = "cmn_acm_menu")
@ApiModel(value = "Menu", description = "菜单管理")
public class BaseMenu implements Serializable {

    private static final long serialVersionUID = -8251445793612683202L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Id
    private String id;

//    /**
//     * 菜单名称
//     */
//    @ApiModelProperty(value = "菜单名称", required = true)
//    private String name;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    private String title;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码", required = true)
    private String code;

//    /**
//     * 菜单定位
//     */
//    @Column(name = "path")
//    @ApiModelProperty(value = "菜单定位", required = true)
//    private String uri;
    /**
     * 菜单定位
     */
    @Column(name = "path")//将字段映射成前端字段--->path
    @ApiModelProperty(value = "菜单定位", required = true)
    private String path;

    /**
     * 图标路径
     */
    @ApiModelProperty(value = "图标路径", required = true)
    private String icon;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型", required = true)
    private String type;

    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID", required = true)
    private String parentID;
    /**
     * 说明
     */
    @ApiModelProperty(value = "说明", required = true)
    private String comment;
//    /**
//     * 排序
//     */
//    @ApiModelProperty(value = "排序", required = true)
//    private String seqOrder;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = true)
    private Date updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private String updateUser;

    /**
     * 路由
     */
    @ApiModelProperty(value = "路由", required = true)
    private String component;

    /**
     * 子菜单
     */
    @Transient
    @ApiModelProperty(value = "子菜单", required = true)
    private List<BaseMenu> children;


    /**
     * 子菜单
     */
    @Transient
    @ApiModelProperty(value = "子菜单", required = true)
    private Map<String, Object> meta;


}
