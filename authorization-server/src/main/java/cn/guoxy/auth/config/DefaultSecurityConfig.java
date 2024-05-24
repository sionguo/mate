package cn.guoxy.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 默认安全配置
 *
 * @author GuoXiaoyong
 */
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            authorize ->
                // 放行静态资源
                authorize
                    .requestMatchers("/assets/**", "webjars/**", "/login", "/captcha")
                    .permitAll()
                    // 其余请求需要认证
                    .anyRequest()
                    .authenticated())
        // 指定登录页面
        .formLogin(formLogin -> formLogin.loginPage("/login"));
    // 添加BearerTokenAuthenticationFilter，将认证服务当做一个资源服务，解析请求头中的token
    http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));
    return http.build();
  }

  // TODO 暂时写一个默认用户
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user =
        User.withUsername("admin")
            .password(passwordEncoder.encode("123456"))
            .roles("admin", "normal")
            .authorities("app", "web")
            .build();
    return new InMemoryUserDetailsManager(user);
  }
}
