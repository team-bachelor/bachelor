package cn.org.bachelor.iam.dac.service.rpc;

import cn.org.bachelor.iam.dac.service.service.DacAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Hyw
 * @package cn.org.bachelor.iam.acm.rpc
 * @date 2022/12/13 17:03
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/rpc/area")
@Api(tags = {"远程调用省市区县"})
public class RpcDacAreaController {

    @Resource
    private DacAreaService dacAreaService;

    @ApiOperation("根据code换name")
    @GetMapping(value = "/getNameByCode")
    public String getNameByCode(@RequestParam(value = "code") String code) {
        return dacAreaService.getNameByCode(code);
    }

    @ApiOperation("获取所有的区域的Code和Name")
    @GetMapping(value = "/getAllAreaCode")
    public Map<String, String> getAllAreaCode() {
        return dacAreaService.getAllAreaCode();
    }

}
