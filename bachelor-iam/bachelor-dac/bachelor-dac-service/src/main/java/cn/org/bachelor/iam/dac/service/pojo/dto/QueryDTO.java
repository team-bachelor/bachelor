package cn.org.bachelor.iam.dac.service.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "查询参数")
@Data
public class QueryDTO {
    @Schema(name = "页数", requiredMode = Schema.RequiredMode.REQUIRED, type = "java.lang.Integer")
    private Integer pageNum;

    @Schema(name = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED, type = "java.lang.Integer")
    private Integer pageSize;
}

