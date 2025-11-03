package cn.org.bachelor.iam.dac.service.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by Intellij IDEA.
 * User:  ZhuangJiaHui
 */
@Data
public class QueryAreaUserDTO extends QueryDTO {

    //用户名称：userName
    @Schema(name = "用户名称")
    private String userName;

    //行政区域编码：areaCode
//    @Schema(name = "行政区域编码")
//    private String areaCode;

    //行政区域ID
    @Schema(name = "行政区域ID")
    private String areaId;

}
