package cn.guoxy.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 应用程序属性
 *
 * @author GuoXiaoyong
 */
@Data
@ConfigurationProperties(prefix = "application.authorization")
public class ApplicationProperties {
  private String issuer;
}
