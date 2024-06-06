package cn.guoxy.plugins;

import cn.guoxy.flow.ActionNodeExtension;

/**
 * http插件
 *
 * @author GuoXiaoyong
 */
public class HttpActionNodeExtension implements ActionNodeExtension {

  @Override
  public UiComponent getUiComponent() {
    return new UiComponent("assets/index.umd.js", "");
  }

  @Override
  public String getName() {
    return "http";
  }
}
