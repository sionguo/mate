package cn.guoxy.mate.configuration;

import cn.guoxy.common.tsid.Tsid;
import cn.guoxy.mate.common.BaseEntity;
import cn.guoxy.mate.common.MethodContext;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.util.StringUtils;

/**
 * jdbc配置
 *
 * @author GuoXiaoyong
 */
@Configuration
@EnableJdbcAuditing
public class JdbcConfiguration {
  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> Optional.ofNullable(MethodContext.getCurrentUser());
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
