package cn.guoxy.mate.plugin;

import java.nio.file.Path;
import java.util.List;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * spring插件管理器
 *
 * @author GuoXiaoyong
 */
public class SpringPluginManager extends DefaultPluginManager
    implements PluginManager, InitializingBean, DisposableBean {

  public SpringPluginManager() {}

  public SpringPluginManager(List<Path> pluginsRoots) {
    super(pluginsRoots);
  }

  public SpringPluginManager(Path... pluginsRoots) {
    super(pluginsRoots);
  }

  @Override
  public void destroy() throws Exception {
    startPlugins();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    loadPlugins();
    startPlugins();
  }
}
