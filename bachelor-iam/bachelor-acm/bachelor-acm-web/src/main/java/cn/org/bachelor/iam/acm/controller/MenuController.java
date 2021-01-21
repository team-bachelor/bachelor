package cn.org.bachelor.iam.acm.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.org.bachelor.iam.acm.service.MenuService;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @描述: 用户角色controller
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
@RestController
@RequestMapping("/acm")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 设置角色的菜单
     *
     * @param role
     * @param menus
     */
    @ApiOperation(value = "设置角色的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "menus", value = "要设置给角色的菜单code", paramType = "body", required = true, example = "[\n\"123\", \n\"456\"\n]")
    })
    @RequestMapping(value = "/role_menu/{role}", method = RequestMethod.POST)
    public ResponseEntity setRoleMenu(@PathVariable("role") String role, @RequestBody List<String> menus) {
        menuService.setRoleMenu(role, menus);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得角色的菜单
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "获得角色的菜单")
    @ApiImplicitParam(name = "role", value = "角色的编码", paramType = "path", required = true)
    @RequestMapping(value = "/role_menu/{role}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getRoleMenu(@PathVariable String role) {
        return JsonResponse.createHttpEntity(menuService.getRoleMenu(role));
    }

    /**
     * 设置机构的菜单
     *
     * @param org
     * @param menus
     */
    @ApiOperation(value = "设置机构的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "menus", value = "要设置给机构的菜单code", paramType = "body", required = true)
    })
    @RequestMapping(value = "/org_menu/{org}", method = RequestMethod.POST)
    public ResponseEntity setOrgMenu(@PathVariable("org") String org, @RequestBody List<String> menus) {
        menuService.setOrgMenu(org, menus);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    /**
     * 获得机构的菜单
     *
     * @param org
     * @return
     */
    @ApiOperation(value = "获得机构的菜单")
    @ApiImplicitParam(name = "org", value = "机构的编码", paramType = "path", required = true)
    @RequestMapping(value = "/org_menu/{org}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgMenu(@PathVariable String org) {
        return JsonResponse.createHttpEntity(menuService.getOrgMenu(org));
    }

    /**
     * 获得全部菜单
     *
     * @param orgID
     * @return
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
     * @param user
     * @return
     */
    @ApiOperation(value = "获得用户的菜单")
    @ApiImplicitParam(name = "user", value = "用户的编码", paramType = "path", required = true)
    @RequestMapping(value = "/user_menu/{user}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserMenu(@PathVariable String user) {
        List menus = menuService.calUserMenu(user);
        return JsonResponse.createHttpEntity(menus);
    }


}
