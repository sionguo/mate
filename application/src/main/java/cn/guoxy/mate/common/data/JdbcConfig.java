package cn.guoxy.mate.common.data.config;

import cn.guoxy.mate.common.data.BaseEntity;
import com.github.f4b6a3.tsid.Tsid;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.StringUtils;

/**
 * jdbc配置
 *
 * @author GuoXiaoyong
 */
@Configuration
@EnableJdbcAuditing
public class JdbcConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> {
      SecurityContext context = SecurityContextHolder.getContext();
      Authentication authentication = context.getAuthentication();
      Object principal = authentication.getPrincipal();
      if (principal instanceof OAuth2AuthenticatedPrincipal p) {
        return Optional.of(p.getName());
      }
      return Optional.empty();
    };
  }

  @Bean
  public BeforeConvertCallback<BaseEntity> beforeConvertCallback() {
    return aggregate -> {
      if (!StringUtils.hasText(aggregate.getId())) {
        aggregate.setId(Tsid.fast().toString());
      }
      return aggregate;
    };
  }
}
