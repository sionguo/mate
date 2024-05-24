package cn.guoxy.mate.common.security;

import cn.guoxy.mate.common.MethodContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            smc -> {
              smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
        .authorizeHttpRequests(
            arc -> {
              arc.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                  .permitAll()
                  .requestMatchers("/actuator/**")
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));

    http.addFilterAfter(new MethodContextFilter(), SecurityContextHolderAwareRequestFilter.class);

    return http.build();
  }
}
