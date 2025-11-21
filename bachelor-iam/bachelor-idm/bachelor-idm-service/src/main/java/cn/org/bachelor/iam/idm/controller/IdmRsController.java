package cn.org.bachelor.iam.idm.controller;

import cn.org.bachelor.iam.idm.service.IamSysParam;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.pojo.IamApp;
import cn.org.bachelor.iam.pojo.IamUser;
import cn.org.bachelor.web.json.JsonResponse;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuzhuo
 * @描述
 * @创建人 liuzhuo
 * @创建时间 2019/4/2
 */
@RestController
/**
 * 将原auth-login合并，原/user/接口一并归到/dim/
 */
//@CrossOrigin
@RequestMapping("/api/idm/rs")
public class IdmRsController {

    private static final Logger logger = LoggerFactory.getLogger(IdmRsController.class);
    @Autowired
    private IamSysService userSysService;

    @Operation(description = "根据当前clientID查询用户")
    @Parameters({
            @Parameter(name = "orgId", description = "组织机构编码", in = ParameterIn.PATH, required = true),
            @Parameter(name = "deptId", description = "部门编码", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "userName", description = "用户名（模糊匹配）", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "pageSize", description = "每页的记录数", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "pageNum", description = "当前页数", in = ParameterIn.QUERY, required = false)
    })
    @RequestMapping(value = "/users/{orgId}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers(@PathVariable("orgId") String orgId,
                                             @RequestParam(value = "deptId", required = false) String deptId,
                                             @RequestParam(value = "userName", required = false) String userName,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setPageSize(pageSize);
        param.setPageNum(pageNum);
        List<IamUser> result = userSysService.findUsers(param);
        //ResponseEntity response = JsonResponse.createHttpEntity(result.getRows());

        //response.getHeaders().add("total", String.valueOf(result.getTotal()));
        return JsonResponse.createHttpEntity(result);
    }

    /**
     * 根据当前clientID查询用户
     *
     * @param orgId   组织机构编码
     * @param deptId  部门编码
     * @param keyWord 查询关键词，同时用于匹配用户名称和编码
     * @return
     */
    @Operation(description = "根据组织机构编码、部门编码查询用户")
    @Parameters({
            @Parameter(name = "orgId", description = "组织机构编码", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "deptId", description = "部门编码", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "keyWord", description = "查询关键词，同时用于匹配用户名称和编码", in = ParameterIn.QUERY, required = false)
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers(@RequestParam("orgId") String orgId,
                                             @RequestParam(value = "deptId", required = false) String deptId,
                                             @RequestParam(value = "keyWord", required = false) String keyWord) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setKeyWord(keyWord);
        return JsonResponse.createHttpEntity(userSysService.findUsers(param));
    }

    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 返回用户列表
     */
    @Operation(description = "查询用户")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", in = ParameterIn.PATH, required = true)
    })
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUser(@PathVariable("userId") String userId) {
        List l = userSysService.findUsersById(userId);
        return JsonResponse.createHttpEntity(l == null || l.size() == 0 ? null : l.get(0));
    }

    /**
     * 查询用户
     *
     * @param userCode 用户编码
     * @param orgId    机构ID
     * @return 返回用户列表
     */
    @Operation(description = "查询用户")
    @Parameters({
            @Parameter(name = "userCode", description = "用户编码", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "orgId", description = "机构ID", in = ParameterIn.QUERY, required = true)
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByCode(@RequestParam("userCode") String userCode, @RequestParam("orgId") String orgId) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        param.setUserCode(userCode);
        return JsonResponse.createHttpEntity(userSysService.findUsers(param));
    }

    @Operation(description = "获得机构下部门列表")
    @Parameters({
            @Parameter(name = "orgId", description = "父机构ID", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "tree", description = "是否要树状结构", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "deptId", description = "父部门ID", in = ParameterIn.QUERY, required = false)
    })
    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getDepts(@RequestParam("orgId") String orgId,
                                             @RequestParam(value = "deptId", required = false) String deptId,
                                             @RequestParam(value = "tree", required = false, defaultValue = "false") boolean tree) {
        IamSysParam param = new IamSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setTree(tree);
        param.setLevel(0);
        List permg = userSysService.findDepts(param);
        return JsonResponse.createHttpEntity(permg);
    }

    @Operation(description = "为当前用户登出系统")
    @RequestMapping(value = "/dept/detail", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> searchDept(@RequestParam("deptId") String deptId) {
        logger.info("deptId {}", deptId);
        return JsonResponse.createHttpEntity(userSysService.findDeptDetail(deptId), HttpStatus.OK);
    }

    @Operation(description = "获得机构列表")
    @Parameters({
            @Parameter(name = "id", description = "机构ID", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "code", description = "机构编码", in = ParameterIn.QUERY, required = true)
    })
    @RequestMapping(value = "/orgs", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgs() {
        List permg = userSysService.findAllOrgs();
        return JsonResponse.createHttpEntity(permg);
    }

    @Operation(description = "获得指定机构")
    @Parameters({
            @Parameter(name = "orgId", description = "机构ID", in = ParameterIn.QUERY, required = true),
    })
    @RequestMapping(value = "/org/{orgId}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrg(@PathVariable("orgId") String orgId) {
        return JsonResponse.createHttpEntity(userSysService.findOrg(orgId));
    }

    @Operation(description = "根据用户ID获取用户列表")
    @Parameters({
            @Parameter(name = "userIds", description = "用户ID（逗号分隔）", in = ParameterIn.QUERY, required = true)
    })
    @RequestMapping(value = "/users/ids", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByIds(@RequestParam("userIds") String userIds) {
        List<IamUser> iamUserList = userSysService.findUsersById(userIds.split(","));
        return JsonResponse.createHttpEntity(iamUserList);
    }


    @Value("${spring.application.portal-code:}")
    private String portalCode;

    @Operation(description = "获取跳转会portal的地址")
    @RequestMapping(value = "/app/portal/url", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getPortalURL() {
        if (portalCode == null || "".equalsIgnoreCase(portalCode))
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        else {
            IamApp app = userSysService.findAppByCode(portalCode);
            return JsonResponse.createHttpEntity(app == null ? "" : app.getUrl());
        }
    }

    @Operation(description = "根据app的编码获取app详细信息")
    @Parameters({
            @Parameter(name = "appCode", description = "app的编码", in = ParameterIn.PATH, required = true)
    })
    @RequestMapping(value = "/app/{appCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByCode(@PathVariable("appCode") String appCode) {
        IamApp app = userSysService.findAppByCode(appCode);
        return JsonResponse.createHttpEntity(app);
    }

    @Operation(description = "根据用户ID获取该用户可以登录的app")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", in = ParameterIn.PATH, required = true)
    })
    @RequestMapping(value = "/user/{userId}/apps", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByUserId(@PathVariable("userId") String userId) {
        List<IamApp> apps = userSysService.findUserApps(userId);
        return JsonResponse.createHttpEntity(apps);
    }

    /**
     * 查询用户
     *
     * @return 返回用户列表
     */
    @Operation(description = "根据当前clientID查询用户")
    @RequestMapping(value = "/usersInClient", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "deptId", description = "部门编码", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "deptName", description = "部门名（模糊匹配）", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "userName", description = "用户名（模糊匹配）", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "pageSize", description = "每页的记录数", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "page", description = "当前页数", in = ParameterIn.QUERY, required = false)
    })
    public HttpEntity<JsonResponse> getUsersByClient(@RequestParam(value = "deptId", required = false) String deptId,
                                                     @RequestParam(value = "deptName", required = false) String deptName,
                                                     @RequestParam(value = "userName", required = false) String userName,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                     @RequestParam(value = "page", required = false) Integer pageNum) {
        IamSysParam param = new IamSysParam();
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setDeptName(deptName);
        param.setPageNum(pageNum == null ? param.getPageNum() : pageNum);
        param.setPageSize(pageSize == null ? param.getPageSize() : pageSize);
        return JsonResponse.createHttpEntity(userSysService.findUsersInApp(param));
    }
}
