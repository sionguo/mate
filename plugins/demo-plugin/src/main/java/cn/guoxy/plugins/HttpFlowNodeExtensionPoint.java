package cn.guoxy.plugins;

import cn.guoxy.flow.FlowNodeExtensionPoint;
import org.pf4j.Extension;

@Extension
public class HttpFlowNodeExtensionPoint implements FlowNodeExtensionPoint {

  @Override
  public void hello() {
    System.out.println("aaa");
  }
}
