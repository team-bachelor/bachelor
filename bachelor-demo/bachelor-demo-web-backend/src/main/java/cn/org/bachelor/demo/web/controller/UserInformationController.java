package cn.org.bachelor.demo.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.org.bachelor.demo.web.domain.UserInfo;
import cn.org.bachelor.demo.web.service.UserInfoService;
import cn.org.bachelor.demo.web.service.UserRoleService;
import cn.org.bachelor.iam.oauth2.client.util.ClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.org.bachelor.iam.acm.domain.UserRole;
import cn.org.bachelor.demo.web.vo.UserInfoVO;
import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Description: 用户管理接口: 添加用户 修改用户信息 删除指定的用户 查询指定用户 查询用户列表
 *
 * @Author Alexhendar
 * @Date: Created in 2018/10/9 15:55
 * @Modified By:
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = {"用户信息"})
@RequestMapping("/userinfo")
public class UserInformationController {

    private static final Logger logger = LoggerFactory.getLogger(UserInformationController.class);

    @Resource
    UserInfoService userInfoService;
    @Resource
    UserRoleService userRoleService;
    @GetMapping(value = "")
        public ResponseEntity<JsonResponse> getUsersDetail1(HttpServletRequest request) {
        return JsonResponse.createHttpEntity("ok", HttpStatus.OK);
    }
    @ApiOperation(value = "获取用户的详情")
    @GetMapping(value = "getUsersDetail")
    public ResponseEntity<JsonResponse> getUsersDetail(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", ClientUtil.getCurrentAccount());
        map.put("userName", ClientUtil.getCurrentUserName());
        map.put("user_id", ClientUtil.getCurrentUserId());
        map.put("token_expire", ClientUtil.getCurrentAsTokenExpir());
        UserRole userRole = userRoleService.getUserRole(ClientUtil.getCurrentUserId());
        if (userRole != null) {
            map.put("role_code", userRole.getRoleCode());
        }
        String userCode = request.getHeader("user_code");

        System.err.println("userCode:" + userCode);
        map.put("user_code", userCode);
        return JsonResponse.createHttpEntity(map, HttpStatus.OK);

    }

    @ApiOperation(value = "获取用户的角色")
    @RequestMapping(value = "findUserRole", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> getUsersDetail(@RequestParam String userId) {
        UserRole userRole = userRoleService.getUserRole(userId);
        return JsonResponse.createHttpEntity(userRole, HttpStatus.OK);
    }

    /**
     * <p>
     * findUserListByPage : 查询用户列表，@ApiImplicitParams为示例，实际分页由bachelor处理
     * </p>
     *
     * @param
     * @return ResponseEntity<JsonResponse>
     * @Auther: Alexhendar
     * @Date: 2018/10/19 17:08
     */
    @ApiOperation(value = "用户列表查询", notes = "查询用户列表，分页使用start,page参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "分页当前页码", paramType = "query", required = false,
                    dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "每个分页元素个数", paramType = "query", required = false,
                    dataType = "Integer")})
    @GetMapping(value = "/user")
    public ResponseEntity<JsonResponse> findUserListByPage(@RequestParam int pageNum, @RequestParam int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<UserInfoVO> pageInfo = userInfoService.selectAllByPage();
        return JsonResponse.createHttpEntity(pageInfo, HttpStatus.OK);
    }


    /**
     * <p>
     * findUserById : 根据用户ID查询指定用户
     * </p>
     *
     * @param userid
     * @return ResponseEntity
     * @Auther: Alexhendar
     * @Date: 2018/10/19 17:10
     */
    @ApiOperation(value = "根据用户ID查询指定用户")
    @ApiImplicitParam(name = "userid", value = "userid", paramType = "path", required = true)
    @GetMapping(value = "/user/{userid}")
    public ResponseEntity<JsonResponse> findUserById(@PathVariable("userid") Long userid) {

        UserInfo userInfo = userInfoService.selectByPrimaryKey(userid);
        if (userInfo != null) {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtil.copyProperties(userInfo, userInfoVO);
            return JsonResponse.createHttpEntity(userInfoVO, HttpStatus.OK);
        } else {
            // 业务异常，用户不存在
            return JsonResponse.createHttpEntity(userInfo, HttpStatus.NO_CONTENT);
        }
    }


    /**
     * <p>
     * addUserInfo : 新增用户
     * </p>
     *
     * @param userInfo
     * @return ResponseEntity
     * @Auther: Alexhendar
     * @Date: 2018/10/19 17:11
     */
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/user")
    public ResponseEntity<JsonResponse> addUserInfo(@RequestBody UserInfo userInfo) {
        // 新增操作，日志记录
        logger.info("add user with {} : ",
                ReflectionToStringBuilder.toString(userInfo, ToStringStyle.SHORT_PREFIX_STYLE));

        int res = userInfoService.insert(userInfo);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 插入用户失败
            return JsonResponse.createHttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * <p>
     * updateUserInfo : 修改用户信息 根据主键更新实体全部字段，所有属性值都需要在userInfo中，没有设置的，null值也会更新到数据库
     * </p>
     *
     * @param userInfo
     * @return ResponseEntity
     * @Auther: Alexhendar
     * @Date: 2018/10/19 17:11
     */
    @ApiOperation(value = "修改用户信息", notes = "根据主键更新实体全部字段，所有属性值都需要在userInfo中，没有设置的，null值不会更新到数据库")
    @PutMapping(value = "/user")
    public ResponseEntity<JsonResponse> updateUserInfo(@RequestBody UserInfo userInfo) {
        // 修改操作，日志记录
        logger.info("update user with {} : ",
                ReflectionToStringBuilder.toString(userInfo, ToStringStyle.SHORT_PREFIX_STYLE));
        int res = userInfoService.update(userInfo);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 修改用户信息失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * <p>
     * deleteUserInfo : 删除指定的用户
     * </p>
     *
     * @param userid
     * @return ResponseEntity
     * @Auther: Alexhendar
     * @Date: 2018/10/19 17:12
     */
    @ApiOperation(value = "删除指定的用户", notes = "根据主键删除指定的用户")
    @ApiImplicitParam(name = "userid", value = "userid", paramType = "path", required = true)
    @DeleteMapping(value = "/user/{userid}")
    public ResponseEntity<JsonResponse> deleteUserInfo(@PathVariable("userid") Long userid) {
        // 删除操作，日志记录
        logger.info("delete user {} : ", userid);
        int res = userInfoService.deleteByPrimaryKey(userid);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 删除指定用户失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }


}

