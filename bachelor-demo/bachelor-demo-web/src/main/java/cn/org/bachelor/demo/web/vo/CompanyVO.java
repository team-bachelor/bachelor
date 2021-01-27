package cn.org.bachelor.demo.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 公司信息VO类
 *
 * @author whongyu
 * @create by 2020-06-30
 */
@Data
public class CompanyVO {

    private String company_registration_number;

    private String company_name;

    private List<Object> childRen;
}
