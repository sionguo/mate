package cn.guoxy.mate.springdoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 春季文档配置
 *
 * @author GuoXiaoyong
 */
@Configuration
@EnableConfigurationProperties(SpringDocProperties.class)
public class SpringDocConfig {
  private final SpringDocProperties springDocProperties;

  public SpringDocConfig(SpringDocProperties springDocProperties) {
    this.springDocProperties = springDocProperties;
  }

  @Bean
  public OpenAPI openAPI() {
    final String apiTitle = "Open API";
    final String apiVersion = "1.0.0";
    final String apiDescription = "任务、笔记、文件API";
    final Info info = new Info().title(apiTitle).version(apiVersion).description((apiDescription));
    final SecurityScheme oAuth2Security =
        new SecurityScheme()
            .name("OAuth2Security")
            .type(SecurityScheme.Type.OAUTH2)
            .in(SecurityScheme.In.HEADER)
            .bearerFormat("jwt")
            .flows(
                new OAuthFlows()
                    .authorizationCode(
                        new OAuthFlow()
                            .authorizationUrl(springDocProperties.getAuthorizationUrl())
                            .tokenUrl(springDocProperties.getTokenUrl())));

    final SecurityRequirement requirement = new SecurityRequirement().addList("OAuth2Security");

    return new OpenAPI()
        .addSecurityItem(requirement)
        .components(new Components().addSecuritySchemes("OAuth2Security", oAuth2Security))
        .info(info);
  }
}
