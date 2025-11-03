package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.domain.Menu;
import cn.org.bachelor.iam.acm.service.MenuService;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
//@CrossOrigin
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
    @Operation(description = "设置角色的菜单")
    @Parameters({
            @Parameter(name = "role", description = "角色的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "menus", description = "要设置给角色的菜单code", in = ParameterIn.QUERY, required = true, example = "[\n\"123\", \n\"456\"\n]")
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
    @Operation(description = "新增菜单")
    @Parameters({
            @Parameter(name = "menu", description = "菜单数据", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "更新菜单")
    @Parameters({
            @Parameter(name = "menu", description = "菜单数据", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "删除菜单")
    @Parameters({
            @Parameter(name = "menuId", description = "菜单数据", in = ParameterIn.PATH, required = true)
    })
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
    @Operation(description = "获得角色的菜单")
    @Parameter(name = "role", description = "角色的编码", in = ParameterIn.PATH, required = true)
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
    @Operation(description = "设置机构的菜单")
    @Parameters({
            @Parameter(name = "org", description = "机构的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "menus", description = "要设置给机构的菜单code", in = ParameterIn.QUERY, required = true)
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
    @Operation(description = "获得机构的菜单")
    @Parameter(name = "org", description = "机构的编码", in = ParameterIn.PATH, required = true)
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
    @Operation(description = "获得全部菜单")
    @Parameter(name = "orgID", description = "组织机构的编码", in = ParameterIn.QUERY, required = false)
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
    @Operation(description = "获得用户的菜单")
    @Parameter(name = "user", description = "用户的编码", in = ParameterIn.PATH, required = true)
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
     * @param parentId 父级菜单ID
     * @return
     */
    @Operation(description = "获得用户的菜单(分组）")
    @Parameters({
            @Parameter(name = "userCode", description = "用户的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "group", description = "菜单分组", in = ParameterIn.PATH, required = true),
            @Parameter(name = "parentId", description = "父级菜单ID", in = ParameterIn.QUERY)
    })
    @RequestMapping(value = "/user/menu/{userCode}/{group}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserMenu(@PathVariable String userCode,
                                                @PathVariable String group,
                                                @RequestParam(value = "parent", required = false) String parentId) {
        List menus = menuService.calUserMenu(userCode, group, parentId);
        return JsonResponse.createHttpEntity(menus);
    }

    /**
     * 获得用户的菜单(分组)
     *
     * @param userCode 用户的编码
     * @param group    菜单分组
     * @return
     */
    @Operation(description = "获得用户的菜单(分组)-用于基于IS的微前端")
    @Parameters({
            @Parameter(name = "userCode", description = "用户的编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "group", description = "菜单分组", in = ParameterIn.PATH, required = true),
            @Parameter(name = "parentId", description = "父级菜单ID", in = ParameterIn.QUERY)
    })
    @RequestMapping(value = "/user/menu/is/{userCode}/{group}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getIsUserMenu(@PathVariable String userCode,
                                                  @PathVariable String group,
                                                  @RequestParam(value = "parent", required = false) String parentId) {
        List menus = menuService.getUserISMenu(userCode, group, parentId);
        return JsonResponse.createHttpEntity(menus);
    }
}
