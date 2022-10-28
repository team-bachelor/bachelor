package cn.org.bachelor.iam.idm.controller;

import cn.org.bachelor.iam.idm.service.DefaultImSysService;
import cn.org.bachelor.iam.vo.AppVo;
import cn.org.bachelor.iam.idm.service.ImSysParam;
import cn.org.bachelor.iam.idm.service.ImSysResult;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @描述
 * @创建人 liuzhuo
 * @创建时间 2019/4/2
 * @author liuzhuo
 */
@RestController
/**
 * 将原auth-login合并，原/user/接口一并归到/dim/
 */
//@CrossOrigin
@RequestMapping("/idm/rs")
public class IdmRsController {

    private static final Logger logger = LoggerFactory.getLogger(IdmRsController.class);
    @Autowired
    private DefaultImSysService userSysService;
    @Autowired
    private OAuth2CientConfig clientConfig;

    @ApiOperation(value = "根据当前clientID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "用户名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", required = false)
    })
    @RequestMapping(value = "/users/{orgId}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers(@PathVariable String orgId, String deptId, String userName, Integer pageSize, Integer page) {
        ImSysParam param = new ImSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setPage(String.valueOf(page));
        param.setPageSize(String.valueOf(pageSize));
        ImSysResult<List<UserVo>> result = userSysService.findUsers(param);
        JsonResponse jr = new JsonResponse(result.getRows());
        jr.setStatus(ResponseStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.add("total", String.valueOf(result.getTotal()));

        //ResponseEntity response = JsonResponse.createHttpEntity(result.getRows());

        //response.getHeaders().add("total", String.valueOf(result.getTotal()));
        return new ResponseEntity<JsonResponse>(jr, headers, HttpStatus.OK);
    }

    /**
     * 根据当前clientID查询用户
     * @param orgId 组织机构编码
     * @param deptId 部门编码
     * @param keyWord 查询关键词，同时用于匹配用户名称和编码
     * @return
     */
    @ApiOperation(value = "根据组织机构编码、部门编码查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "keyWord", value = "查询关键词，同时用于匹配用户名称和编码", paramType = "query", required = false)
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers(String orgId, String deptId, String keyWord) {
        return JsonResponse.createHttpEntity(userSysService.findUsers(orgId, deptId, keyWord));
    }

    /**
     * 查询用户
     *
     * @param userID 用户ID
     * @return 返回用户列表
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID", value = "用户ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/user/{userID}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUser(@PathVariable String userID) {
        return JsonResponse.createHttpEntity(userSysService.findUser(null, userID, null));
    }

    /**
     * 查询用户
     *
     * @param userCode 用户编码
     * @param orgId    机构ID
     * @return 返回用户列表
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "orgId", value = "机构ID", paramType = "query", required = true)
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByCode(String userCode, String orgId) {
        return JsonResponse.createHttpEntity(userSysService.findUser(orgId, null, userCode));
    }

    @ApiOperation(value = "获得机构下部门列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "父机构ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "tree", value = "是否要树状结构", paramType = "query", required = false, defaultValue = "false"),
            @ApiImplicitParam(name = "deptId", value = "父部门ID", paramType = "query", required = false)
    })
    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getDepts(String orgId, String deptId, boolean tree) {
        List permg = userSysService.findDepts(orgId, deptId, tree);
        return JsonResponse.createHttpEntity(permg);
    }

    @ApiOperation(value = "为当前用户登出系统")
    @RequestMapping(value = "/dept/detail", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> searchDept(@RequestParam("id") String deptId) {
        logger.info("deptId {}", deptId);
        return JsonResponse.createHttpEntity(userSysService.findDeptDetail(deptId), HttpStatus.OK);
    }

    @ApiOperation(value = "获得机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机构ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "机构编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "/orgs", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgs() {
        List permg = userSysService.findAllOrgs();
        return JsonResponse.createHttpEntity(permg);
    }

    @ApiOperation(value = "根据用户ID获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户ID（逗号分隔）", paramType = "query", required = true)
    })
    @RequestMapping(value = "/users/ids", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByIds(String userIds) {
        List<UserVo> userVoList = userSysService.findUserByIds(userIds);
        return JsonResponse.createHttpEntity(userVoList);
    }


    @Value("${spring.application.portal-code:''}")
    private String portalCode;
    @ApiOperation(value = "获取跳转会portal的地址")
    @RequestMapping(value = "/app/portal/url", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getPortalURL() {
        if(portalCode == null || "".equalsIgnoreCase(portalCode))
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        else {
            AppVo app = userSysService.findAppByCode(portalCode);
            return JsonResponse.createHttpEntity(app == null ? "" : app.getUrl());
        }
    }

    @ApiOperation(value = "根据app的编码获取app详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appCode", value = "app的编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/app/{appCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByCode(@PathVariable String appCode) {
        AppVo app = userSysService.findAppByCode(appCode);
        return JsonResponse.createHttpEntity(app);
    }

    @ApiOperation(value = "根据用户ID获取该用户可以登录的app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/user/{userId}/apps", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByUserId(@PathVariable String userId) {
        List<AppVo> apps = userSysService.findAppsByUserId(userId);
        return JsonResponse.createHttpEntity(apps);
    }

    /**
     * 查询用户
     *
     * @return 返回用户列表
     */
    @ApiOperation(value = "根据当前clientID查询用户")
    @RequestMapping(value = "/usersInClient", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "deptName", value = "部门名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "用户名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", required = false)
    })
    public HttpEntity<JsonResponse> getUsersByClient(String deptId, String deptName, String userName, Integer pageSize, Integer page) {
        ImSysParam param = new ImSysParam();
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setDeptName(deptName);
        param.setPage(page == null ? null : String.valueOf(page));
        param.setPageSize(page == null ? null : String.valueOf(pageSize));
        param.setClientId(clientConfig.getId());
        return JsonResponse.createHttpEntity(userSysService.findUsersByClientID(param));
    }
}
