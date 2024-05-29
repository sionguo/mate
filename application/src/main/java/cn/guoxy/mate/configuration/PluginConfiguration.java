package cn.guoxy.mate.configuration;

import cn.guoxy.mate.plugin.SpringPluginManager;
import org.pf4j.PluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guoxiaoyong
 */
@Configuration
public class PluginConfiguration {
  @Bean
  public PluginManager pluginManager() {
    return new SpringPluginManager();
  }
}
