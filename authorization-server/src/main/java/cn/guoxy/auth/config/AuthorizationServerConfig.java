package cn.guoxy.auth.config;

import cn.guoxy.auth.ApplicationProperties;
import cn.guoxy.auth.OauthCorsFilter;
import cn.guoxy.auth.authentication.DeviceClientAuthenticationConverter;
import cn.guoxy.auth.authentication.DeviceClientAuthenticationProvider;
import cn.guoxy.auth.jose.Jwks;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings.Builder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CorsFilter;

/**
 * 授权服务器配置
 *
 * @author GuoXiaoyong
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ApplicationProperties.class)
public class AuthorizationServerConfig {
  private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authorizationServerSecurityFilterChain(
      HttpSecurity http,
      RegisteredClientRepository registeredClientRepository,
      AuthorizationServerSettings authorizationServerSettings)
      throws Exception {
    // 配置默认的设置，忽略认证端点的csrf校验
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    // 新建设备码converter和provider
    DeviceClientAuthenticationConverter deviceClientAuthenticationConverter =
        new DeviceClientAuthenticationConverter(
            "/**" + authorizationServerSettings.getDeviceAuthorizationEndpoint());
    DeviceClientAuthenticationProvider deviceClientAuthenticationProvider =
        new DeviceClientAuthenticationProvider(registeredClientRepository);

    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        // 开启oidc相关端点
        .oidc(Customizer.withDefaults())
        // 设置自定义用户确认授权页
        .authorizationEndpoint(
            authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
        // 设置设备码用户验证url(自定义用户验证页)
        .deviceAuthorizationEndpoint(
            deviceAuthorizationEndpoint -> deviceAuthorizationEndpoint.verificationUri("/activate"))
        // 设置验证设备码用户确认页面
        .deviceVerificationEndpoint(
            deviceVerificationEndpoint ->
                deviceVerificationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
        .clientAuthentication(
            clientAuthentication ->
                // 客户端认证添加设备码的converter和provider
                clientAuthentication
                    .authenticationConverter(deviceClientAuthenticationConverter)
                    .authenticationProvider(deviceClientAuthenticationProvider));

    // 当未登录时访问认证端点时重定向至login页面
    http.exceptionHandling(
            (exceptions) ->
                exceptions.defaultAuthenticationEntryPointFor(
                    new LoginUrlAuthenticationEntryPoint("/login"),
                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
        // 处理使用access token访问用户信息端点和客户端注册端点
        .oauth2ResourceServer(
            oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

    // 增加cors Header
    http.addFilterBefore(new OauthCorsFilter(), CorsFilter.class);
    return http.build();
  }

  /**
   * 配置客户端Repository
   *
   * @param jdbcOperations jdbc操作
   * @return {@code RegisteredClientRepository}
   */
  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations) {
    return new JdbcRegisteredClientRepository(jdbcOperations);
  }

  /**
   * 配置基于db的oauth2的授权管理服务
   *
   * @param jdbcOperations jdbc操作
   * @param registeredClientRepository 已注册客户端存储库
   * @return {@code OAuth2AuthorizationService}
   */
  @Bean
  public OAuth2AuthorizationService authorizationService(
      JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
  }

  /**
   * 配置基于db的授权确认管理服务
   *
   * @param jdbcOperations jdbc操作
   * @param registeredClientRepository 已注册客户端存储库
   * @return {@code OAuth2AuthorizationConsentService}
   */
  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(
      JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
  }

  /**
   * 配置jwk源，使用非对称加密，公开用于检索匹配指定选择器的JWK的方法
   *
   * @return {@code JWKSource<SecurityContext>}
   */
  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  /**
   * 配置jwt解析器
   *
   * @param jwkSource jwk源
   * @return {@code JwtDecoder}
   */
  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  /**
   * 添加认证服务器配置，设置jwt签发者、默认端点请求地址等
   *
   * @return {@code AuthorizationServerSettings}
   */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings(
      ApplicationProperties applicationProperties) {
    Builder builder = AuthorizationServerSettings.builder();
    if (StringUtils.hasText(applicationProperties.getIssuer())) {
      builder.issuer(applicationProperties.getIssuer());
    }
    return builder.build();
  }
}
