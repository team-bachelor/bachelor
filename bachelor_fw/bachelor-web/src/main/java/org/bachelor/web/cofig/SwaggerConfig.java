package org.bachelor.web.cofig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2018/11/5 13:58
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {
  String[] basePackage;
  String title;
  String version;
  String description;


  public String[] getBasePackage() {
    return basePackage;
  }

  public void setBasePackage(String[] basePackage) {
    this.basePackage = basePackage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
