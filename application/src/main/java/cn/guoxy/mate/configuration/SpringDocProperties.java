package cn.guoxy.mate.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring文档属性
 *
 * @author GuoXiaoyong
 */
@Data
@ConfigurationProperties(prefix = "springdoc.authorization-code")
public class SpringDocProperties {
  private String authorizationUrl;
  private String tokenUrl;
}
