package cn.org.bachelor.iam.dac.service.pojo.vo;

import lombok.Data;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.org.bachelor.iam.acm.pojo.vo
 * @Date 2022/11/28 15:57
 * @Version 1.0
 */
@Data
public class DacAreaVo {
    private String name;

    private String code;

    private String parentCode;

    private String level;
}
