package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.domain.Menu;
import cn.org.bachelor.iam.acm.service.MenuService;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuzhuo
 * @描述 用户角色controller
 * @创建人 liuzhuo
 * @创建时间 2018/10/22
 */
@RestController
@RequestMapping("/acm")
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 设置角色的菜单
     *
     * @param role  角色的编码
     * @param menus 要设置给角色的菜单code
     * @return OK
     * @更新履历 2021.1.28 访问路径 /role_menu/{role} => /role/menu/{role}
     */
    @ApiOperation(value = "设置角色的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "menus", value = "要设置给角色的菜单code", paramType = "body", required = true, example = "[\n\"123\", \n\"456\"\n]")
    })
    @RequestMapping(value = "/role/menu/{role}", method = RequestMethod.POST)
    public ResponseEntity setRoleMenu(@PathVariable("role") String role, @RequestBody List<String> menus) {
        menuService.setRoleMenu(role, menus);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单数据
     * @return OK
     * @更新履历 2021.1.28 访问路径 /role_menu/{role} => /role/menu/{role}
     */
    @ApiOperation(value = "新增菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu", value = "菜单数据", paramType = "body", required = true)
    })
    @PostMapping(value = "/menu")
    public ResponseEntity newMenu(@RequestBody Menu menu) {
        menuService.insert(menu);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 更新菜单
     *
     * @param menu 菜单数据
     * @return OK
     * @更新履历 2021.1.28 访问路径 /role_menu/{role} => /role/menu/{role}
     */
    @ApiOperation(value = "设置角色的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu", value = "菜单数据", paramType = "body", required = true)
    })
    @PutMapping(value = "/menu")
    public ResponseEntity updateMenu(@RequestBody Menu menu) {
        menuService.update(menu);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单Id
     * @return OK
     * @更新履历 2021.1.28 访问路径 /role_menu/{role} => /role/menu/{role}
     */
    @ApiOperation(value = "设置角色的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单数据", paramType = "path", required = true)
    })
    //@PutMapping(value = "/menu/{menuId}")
    @DeleteMapping(value = "/menu/{menuId}")
    public ResponseEntity updateMenu(@PathVariable String menuId) {
        menuService.delete(menuId);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得角色的菜单
     *
     * @param role 角色的编码
     * @return list of menu code
     * @更新履历 2021.1.28 访问路径 /role/menu/{role} => /role/menu/{role}
     */
    @ApiOperation(value = "获得角色的菜单")
    @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true)
    @RequestMapping(value = "/role/menu/{role}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRoleMenu(@PathVariable String role) {
        return JsonResponse.createHttpEntity(menuService.getRoleMenu(role));
    }

    /**
     * 设置机构的菜单
     *
     * @param org
     * @param menus
     * @更新履历 2021.1.28 访问路径 /org/menu/{org} => /org/menu/{org}
     */
    @ApiOperation(value = "设置机构的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "menus", value = "要设置给机构的菜单code", paramType = "body", required = true)
    })
    @RequestMapping(value = "/org/menu/{org}", method = RequestMethod.POST)
    public ResponseEntity setOrgMenu(@PathVariable("org") String org, @RequestBody List<String> menus) {
        menuService.setOrgMenu(org, menus);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得机构的菜单
     *
     * @param org
     * @return
     * @更新履历 2021.1.28 访问路径 /org_menu/{org} => /org/menu/{org}
     */
    @ApiOperation(value = "获得机构的菜单")
    @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true)
    @RequestMapping(value = "/org/menu/{org}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgMenu(@PathVariable String org) {
        return JsonResponse.createHttpEntity(menuService.getOrgMenu(org));
    }

    /**
     * 获得全部菜单
     *
     * @param orgID 组织机构的编码
     * @return 全部菜单
     */
    @ApiOperation(value = "获得全部菜单")
    @ApiImplicitParam(name = "orgID", value = "组织机构的编码", paramType = "query", required = false)
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getMenus(String orgID) {
        return JsonResponse.createHttpEntity(menuService.getMenuList());
    }

    /**
     * 获得用户的菜单
     *
     * @param userCode 用户的编码
     * @return 用户可访问的菜单
     * @更新履历 2021.1.28 访问路径 /user_menu/{userCode} => /user/menu/{userCode}
     */
    @ApiOperation(value = "获得用户的菜单")
    @ApiImplicitParam(name = "user", value = "用户的编码", paramType = "path", required = true)
    @RequestMapping(value = "/user/menu/{userCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserMenu(@PathVariable String userCode) {
        List menus = menuService.calUserMenu(userCode);
        return JsonResponse.createHttpEntity(menus);
    }

    /**
     * 获得用户的菜单(分组）
     *
     * @param userCode 用户的编码
     * @param group    菜单分组
     * @return
     */
    @RequestMapping(value = "/user/menu/{userCode}/{group}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserMenu(@PathVariable String userCode, @PathVariable String group) {
        List menus = menuService.calUserMenu(userCode, group);
        return JsonResponse.createHttpEntity(menus);
    }

    /**
     * 获得用户的菜单(分组）
     *
     * @param userCode 用户的编码
     * @param group    菜单分组
     * @return
     */
    @RequestMapping(value = "/user/menu/is/{userCode}/{group}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getIsUserMenu(@PathVariable String userCode, @PathVariable String group) {
        List menus = menuService.getUserISMenu(userCode, group);
        return JsonResponse.createHttpEntity(menus);
    }
}
