package cn.org.bachelor.iam.dac.service.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class QueryDTO {
    @ApiModelProperty(value = "页数", required = true, dataType = "java.lang.Integer")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", required = true, dataType = "java.lang.Integer")
    private Integer pageSize;
}

