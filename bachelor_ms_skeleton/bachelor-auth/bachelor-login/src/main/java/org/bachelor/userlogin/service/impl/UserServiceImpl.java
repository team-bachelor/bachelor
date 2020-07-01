package org.bachelor.userlogin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl {

  @Autowired
  protected StringRedisTemplate redisTemplate;

  // 设置1天有效时间
  private long timeout = 1;
  private String delPrefix = "del_";
  private String bachelorPrefix = "bachelor";

  /**
   * 解析token,存入redis,并设置ttl
   *
   * @param account
   */
  public void logout(String account) {
    if(!StringUtils.isEmpty(account)){
      redisTemplate.opsForValue().set(delPrefix + account, "LO", timeout, TimeUnit.DAYS);
    }
  }

  public void saveRefreshToken(String account, String refreshToken,long expire){
    if(!StringUtils.isEmpty(account)){
      redisTemplate.opsForValue().set(bachelorPrefix + account,refreshToken,expire, TimeUnit.SECONDS);
    }
  }

  public String getCurrentRefreshToken(String account){
    if(!StringUtils.isEmpty(account)){
      return redisTemplate.opsForValue().get(bachelorPrefix + account);
    }
    return null;
  }


}
