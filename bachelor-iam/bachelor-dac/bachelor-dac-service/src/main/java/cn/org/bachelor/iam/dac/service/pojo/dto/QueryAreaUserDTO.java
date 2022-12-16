package cn.org.bachelor.iam.dac.service.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 * User:  ZhuangJiaHui
 */
@Data
public class QueryAreaUserDTO extends QueryDTO {

    //用户名称：userName
    @ApiModelProperty(value = "用户名称")
    private String userName;

    //行政区域编码：areaCode
//    @ApiModelProperty(value = "行政区域编码")
//    private String areaCode;

    //行政区域ID
    @ApiModelProperty(value = "行政区域ID")
    private String areaId;

}
