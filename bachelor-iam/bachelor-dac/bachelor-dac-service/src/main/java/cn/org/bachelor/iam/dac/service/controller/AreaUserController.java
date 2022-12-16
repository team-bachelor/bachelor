package cn.org.bachelor.iam.dac.service.controller;

import cn.org.bachelor.iam.dac.service.domain.DacAreaUser;
import cn.org.bachelor.iam.dac.service.pojo.dto.QueryAreaUserDTO;
import cn.org.bachelor.iam.dac.service.service.AreaUserService;
import cn.org.bachelor.web.json.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User:  ZhuangJiaHui
 */
@RestController
@RequestMapping("/acm/areaUser")
@Slf4j
@Api(tags = "用户管理")
public class AreaUserController {

    @Autowired
    private AreaUserService areaUserService;

    //用户管理	查询已关联的用户列表	bachelor-web-cmn-acm/acm/areaUser/list	post
    @ApiOperation(value = "查询已关联的用户列表", notes = "")
    @PostMapping("/list")
    public ResponseEntity<JsonResponse> list(@RequestBody QueryAreaUserDTO dto) {
        return JsonResponse.createHttpEntity(areaUserService.list(dto), HttpStatus.OK);
    }

    //用户管理	添加用户	bachelor-web-cmn-acm/acm/areaUser/add	post
    @ApiOperation(value = "添加用户", notes = "")
    @PostMapping("")
    public ResponseEntity<JsonResponse> add(@RequestBody List<DacAreaUser> areaUsers) {
        List<String> msg = areaUserService.add(areaUsers);
        if (msg != null && msg.size() != 0) {
            return JsonResponse.createHttpEntity(null, "用户:" + msg.toString() + ",已经存在", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }


    //用户管理	删除用户	bachelor-web-cmn-acm/acm/areaUser/delete	post
    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("")
    public ResponseEntity<JsonResponse> delete(@RequestParam List<String> ids) {
        areaUserService.delete(ids);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "根据用户USER_CODE获取AREA_ID")
    @GetMapping("/getAreaIdByUserCode")
    public JSONObject getAreaIdByUserCode(@RequestParam String userCode) {
        log.info("用户编码userCode:{}", userCode);
        JSONObject object = new JSONObject();
        object.put("areaId", areaUserService.getAreaIdByUserCode(userCode));
        return object;
    }

}
