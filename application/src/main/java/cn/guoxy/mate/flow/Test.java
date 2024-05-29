package cn.guoxy.mate.flow;

import cn.guoxy.flow.ProcessNodeExtension;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.pf4j.PluginManager;
import org.springframework.stereotype.Component;

@Component
public class Test {
  private final PluginManager pluginManager;

  public Test(PluginManager pluginManager) {
    this.pluginManager = pluginManager;
  }

  @PostConstruct
  public void aaa() {
    List<ProcessNodeExtension> extensions = pluginManager.getExtensions(ProcessNodeExtension.class);
    for (ProcessNodeExtension extension : extensions) {
      System.out.println(extension.getJsInputStream());
    }
  }
}
