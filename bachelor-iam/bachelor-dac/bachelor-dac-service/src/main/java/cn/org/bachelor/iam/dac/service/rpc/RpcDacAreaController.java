package cn.org.bachelor.iam.dac.service.rpc;

import cn.org.bachelor.iam.dac.service.service.DacAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Hyw
 * @version 1.0
 * @package cn.org.bachelor.iam.acm.rpc
 * @date 2022/12/13 17:03
 */
@RestController
@RequestMapping(value = "/rpc/area")
@Tag(name = "远程调用省市区县")
public class RpcDacAreaController {

    @Resource
    private DacAreaService dacAreaService;

    @Operation(description = "根据code换name")
    @GetMapping(value = "/getNameByCode")
    public String getNameByCode(@RequestParam(value = "code") String code) {
        return dacAreaService.getNameByCode(code);
    }

    @Operation(description = "获取所有的区域的Code和Name")
    @GetMapping(value = "/getAllAreaCode")
    public Map<String, String> getAllAreaCode() {
        return dacAreaService.getAllAreaCode();
    }

}
