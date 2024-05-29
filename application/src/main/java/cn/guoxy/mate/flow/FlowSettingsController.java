package cn.guoxy.mate.flow;

import cn.guoxy.flow.FlowNodeExtension;
import java.util.List;
import org.pf4j.PluginManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 流设置控制器
 *
 * @author GuoXiaoyong
 */
@Controller
public class FlowSettingsController {

  private final PluginManager pluginManager;

  public FlowSettingsController(PluginManager pluginManager) {
    this.pluginManager = pluginManager;
  }

  @GetMapping("flow-nodes")
  private ResponseEntity flowNodes() {
    List<FlowNodeExtension> extensions = pluginManager.getExtensions(FlowNodeExtension.class);

    return ResponseEntity.ok(extensions);
  }
}
