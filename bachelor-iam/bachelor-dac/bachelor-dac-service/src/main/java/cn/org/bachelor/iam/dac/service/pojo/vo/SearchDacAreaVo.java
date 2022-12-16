package cn.org.bachelor.iam.dac.service.pojo.vo;

import cn.org.bachelor.iam.dac.service.pojo.dto.QueryDTO;
import lombok.Data;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.org.bachelor.iam.acm.pojo.vo
 * @Date 2022/11/28 17:24
 * @Version 1.0
 */
@Data
public class SearchDacAreaVo extends QueryDTO {

    private String name;

    private String code;
}
