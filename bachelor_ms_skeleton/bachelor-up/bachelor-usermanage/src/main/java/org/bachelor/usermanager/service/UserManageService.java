package cn.org.bachelor.usermanager.service;

import feign.Headers;
import cn.org.bachelor.usermanager.config.CoreFeignConfiguration;
import cn.org.bachelor.usermanager.utils.ResponseEntiy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


/**
 * Description:
 *
 * @Modified By:http://221.2.140.133:8600/usermanage
 */
@FeignClient(url = "${usermanage.url}", path = "/usermanage", name="usermanage", configuration = CoreFeignConfiguration.class)
public interface UserManageService {
  @RequestMapping(value="/user/saveUser",method= RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @Headers("Content-Type: application/x-www-form-urlencoded")
  ResponseEntiy<String> saveUser(Map<String, ?> user);

  @RequestMapping(value = "/user/delUser",method=RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @Headers("Content-Type: application/x-www-form-urlencoded")
  ResponseEntiy<String> delUser(Map<String, ?> user);


  @RequestMapping(value = "/user/resetPass",method=RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @Headers("Content-Type: application/x-www-form-urlencoded")
  ResponseEntiy<String> resetPass(Map<String, ?> user);

  @RequestMapping(value = "/userpassword/updatepassword",method=RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @Headers("Content-Type: application/x-www-form-urlencoded")
  ResponseEntiy<String> updatepassword(Map<String, ?> user);

}
