package cn.org.bachelor.demo.web.controller;

import cn.org.bachelor.demo.web.domain.BaseMenu;
import cn.org.bachelor.demo.web.service.BaseMenuService;
import cn.org.bachelor.demo.web.common.annotation.AppointLog;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gxf on 2021/1/16 15:12
 */
@Slf4j
@RestController
@RequestMapping(value = "/menu")
@Api(tags = {"动态菜单服务"})
public class BaseMenuController {

    private static final Logger logger = LoggerFactory.getLogger(BaseMenuController.class);

    @Resource
    BaseMenuService baseMenuService;

    @AppointLog(module = "动态菜单服务", method = "菜单新增接口")
    @ApiOperation(value = "菜单新增接口")
    @PostMapping(value = "")
    public ResponseEntity<JsonResponse> add(HttpServletRequest request, @RequestBody BaseMenu baseMenu) {
        // 新增操作，日志记录
        logger.info("add cmn_acm_menu with {} : ",
                ReflectionToStringBuilder.toString(baseMenu, ToStringStyle.SHORT_PREFIX_STYLE));

        int res = baseMenuService.insert(request, baseMenu);

        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 插入失败
            return JsonResponse.createHttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @AppointLog(module = "动态菜单服务", method = "查询菜单树")
    @ApiOperation(value = "查询菜单树", notes = "查询菜单树")
    @GetMapping(value = "/menuTree")
    public ResponseEntity<JsonResponse> findOrganizationTree() {
        List<BaseMenu> tree = baseMenuService.selectAll();
        return JsonResponse.createHttpEntity(tree, HttpStatus.OK);
    }
}
