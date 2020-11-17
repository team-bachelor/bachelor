package cn.org.bachelor.usermanager.config;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2018/11/28 9:42
 * @Modified By:
 */
@Component
public class CoreFeignConfiguration {
  @Autowired
  private ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  @Primary
  @Scope(SCOPE_PROTOTYPE)
  public Encoder feignFormEncoder() {
    return new FormEncoder(new SpringEncoder(this.messageConverters));
  }
}
