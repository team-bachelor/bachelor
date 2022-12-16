package cn.org.bachelor.iam.dac.service.controller;

import cn.org.bachelor.iam.dac.service.pojo.vo.DacAreaVo;
import cn.org.bachelor.iam.dac.service.pojo.vo.SearchDacAreaVo;
import cn.org.bachelor.iam.dac.service.pojo.vo.UpdateDacAreaVo;
import cn.org.bachelor.iam.dac.service.service.DacAreaService;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.org.bachelor.iam.acm.controller
 * @Date 2022/11/28 13:49
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/area")
@Api(tags = {"省市区县"})
public class DacAreaController {

    @Resource
    private DacAreaService dacAreaService;

    @ApiOperation("新增省市县（区）")
    @PostMapping(value = "")
    public ResponseEntity<JsonResponse> addArea(@RequestBody DacAreaVo dacAreaVo) {
        dacAreaService.addArea(dacAreaVo);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation("修改省市县（区）")
        @PutMapping(value = "")
    public ResponseEntity<JsonResponse> updateArea(@RequestBody UpdateDacAreaVo updateDacAreaVo) {
        dacAreaService.updateArea(updateDacAreaVo);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation("获取权限区域列表")
    @GetMapping(value = "/dacList")
    public ResponseEntity<JsonResponse> getDacAreaList(SearchDacAreaVo searchDacAreaVo) {
        return JsonResponse.createHttpEntity(dacAreaService.getDacAreaList(searchDacAreaVo), HttpStatus.OK);
    }

    @ApiOperation("下拉列表")
    @GetMapping(value = "/tree")
    public ResponseEntity<JsonResponse> areaTree(@RequestParam String deep) {
        return JsonResponse.createHttpEntity(dacAreaService.areaTree(deep), HttpStatus.OK);
    }

    @ApiOperation("删除权限区域")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JsonResponse> deleteArea(@PathVariable String id) {
        dacAreaService.deleteArea(id);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }


    @ApiOperation("获取所有的区域")
    @GetMapping(value = "/getAllArea")
    public ResponseEntity<JsonResponse> getAllArea() {
        List<Map<Object, Object>> map = dacAreaService.getAllArea();
        return JsonResponse.createHttpEntity(map, HttpStatus.OK);
    }

    @ApiOperation("根据name换code")
    @GetMapping(value = "/getCodeByName")
    public ResponseEntity<JsonResponse> getCodeByName(@RequestParam String name) {
        String code = dacAreaService.getCodeByName(name);
        return JsonResponse.createHttpEntity(code, HttpStatus.OK);
    }


}
