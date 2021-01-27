package cn.org.bachelor.demo.web.domain;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;


/**
 * 通讯录日志
 *
 * @author whongyu
 * @email lvtu_rain@163.com
 * @date 2020-12-07 09:43:59
 */
@Data
@Table(name = "eprs_contacts_logs")
@ApiModel(value = "Logs", description = "日志")
public class Logs {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 模块接口
     */
    @ApiModelProperty(value = "模块接口")
    private String modules;
    /**
     * 用户操作
     */
    @ApiModelProperty(value = "用户操作")
    private String operation;
    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间")
    private Integer time;
    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;
    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ip;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String addTime;

    public String getAddTime() {
        this.addTime = DateUtil.now();
        return addTime;
    }
}
