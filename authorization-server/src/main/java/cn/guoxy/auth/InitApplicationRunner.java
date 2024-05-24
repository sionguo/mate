package cn.guoxy.auth;

import java.util.UUID;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

/**
 * init应用程序运行程序
 *
 * @author GuoXiaoyong
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {
  private final RegisteredClientRepository registeredClientRepository;

  public InitApplicationRunner(RegisteredClientRepository registeredClientRepository) {
    this.registeredClientRepository = registeredClientRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    RegisteredClient registeredClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            // 客户端id
            .clientId("messaging-client")
            // 客户端秘钥，使用密码解析器加密
            .clientSecret("{noop}123456")
            // 客户端认证方式，基于请求头的认证
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            // 配置资源服务器使用该客户端获取授权时支持的方式
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            // 授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
            // 配置一个百度的域名回调，稍后使用该回调获取code
            .redirectUri("https://www.baidu.com")
            .postLogoutRedirectUri("https://www.baidu.com")
            // 该客户端的授权范围，OPENID与PROFILE是IdToken的scope，获取授权时请求OPENID的scope时认证服务会返回IdToken
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            // 自定scope
            .scope("message.read")
            .scope("message.write")
            // 客户端设置，设置用户需要确认授权
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build();

    RegisteredClient repositoryByClientId =
        registeredClientRepository.findByClientId(registeredClient.getClientId());
    if (repositoryByClientId == null) {
      registeredClientRepository.save(registeredClient);
    }

    // 设备码授权客户端
    RegisteredClient deviceClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("device-message-client")
            // 公共客户端
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            // 设备码授权
            .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            // 自定scope
            .scope("message.read")
            .scope("message.write")
            .build();
    RegisteredClient byClientId =
        registeredClientRepository.findByClientId(deviceClient.getClientId());
    if (byClientId == null) {
      registeredClientRepository.save(deviceClient);
    }

    // PKCE客户端
    RegisteredClient pkceClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("pkce-message-client")
            // 公共客户端
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            // 设备码授权
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            // 授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost，本机使用127.0.0.1访问
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
            .redirectUri("http://127.0.0.1:8999/callback")
            .redirectUri("http://localhost:8999/callback")
            .redirectUri("http://192.168.10.33:8999/callback")
            .postLogoutRedirectUri("http://127.0.0.1:8999/")
            .postLogoutRedirectUri("http://localhost:8999/")
            .clientSettings(ClientSettings.builder().requireProofKey(Boolean.TRUE).build())
            // 自定scope
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("message.read")
            .scope("message.write")
            .build();
    RegisteredClient findPkceClient =
        registeredClientRepository.findByClientId(pkceClient.getClientId());
    if (findPkceClient == null) {
      registeredClientRepository.save(pkceClient);
    }
  }
}
