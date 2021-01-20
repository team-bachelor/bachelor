package cn.org.bachelor.usermanager.controller;

import cn.org.bachelor.usermanager.service.UserManageService;
import cn.org.bachelor.usermanager.utils.ResponseEntiy;
import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserManageService userManageService;

  /**
   * 新增用户必填参数Description:
   *
   {
   "deptId":"688819c6d2224b23bf8451e9be14254d",
   "orgId":"bcbc3664b35d473aab01d1876f255311",
   "account":"aaaa1234",
   "username":"HelloWorld"
   }
   */
  @RequestMapping(value = "/user",method = RequestMethod.POST)
  public ResponseEntity<JsonResponse> createUser(HttpServletRequest request,@RequestBody Map<String, ?> user){
    logger.info("create user : {}",user);
    ResponseEntiy responseEntiy = userManageService.saveUser(user);
    if(ResponseEntiy.SUCCESS.equalsIgnoreCase(responseEntiy.getResult())){
      return JsonResponse.createHttpEntity(responseEntiy.getMessage());
    }
    return JsonResponse.createHttpEntity(responseEntiy.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 删除指定id的用户
   * @param userId
   * @return
   */
  @RequestMapping(value = "/user/{userId}",method = RequestMethod.DELETE)
  public ResponseEntity<JsonResponse> deleteUser(@PathVariable("userId") String userId){
    logger.info("del User {}",userId);
    Map<String,Object> user = new HashMap<>();
    user.put("userId",userId);
    ResponseEntiy responseEntiy =  userManageService.delUser(user);
    return JsonResponse.createHttpEntity(responseEntiy.getMessage());
  }

  /**
   *  指定用户Id即可
   * @param userId
   * @return
   */
  @RequestMapping(value = "/user/pwd/{userId}",method = RequestMethod.PUT)
  public ResponseEntity<JsonResponse> resetPwd(@PathVariable("userId") String userId){
    logger.info("reset User pwd {}",userId);
    Map<String,Object> user = new HashMap<>();
    user.put("userId",userId);
    ResponseEntiy responseEntiy =  userManageService.resetPass(user);
    return JsonResponse.createHttpEntity(responseEntiy.getMessage());
  }

  /**
   *  修改密码，需要传入原密码
   * @param userId
   * @param user
   * {
   *   "originalPwd": "原密码",
   *   "userId":"用户Id",
   *   "account": "登录账号",
   *   "password": "新密码"
   * }
   * 示例，如
   * {
   * "originalPwd":"Pdmi1234",
   * "userId":"2513b9c70a9b47f1ab931f911509c840",
   * "account":"liuxiujun",
   * "password":"Pdmi2345"
   * }
   * @return
   */
  @RequestMapping(value = "/user/updatePwd/{userId}",method = RequestMethod.PUT)
  public ResponseEntity<JsonResponse> resetPwd(@PathVariable("userId") String userId,@RequestBody Map<String, ?> user){
    logger.info("update User pwd {}",userId);
    ResponseEntiy responseEntiy =  userManageService.updatepassword(user);
    return JsonResponse.createHttpEntity(responseEntiy.getMessage());
  }
}
