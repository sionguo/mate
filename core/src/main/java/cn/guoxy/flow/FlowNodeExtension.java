package cn.guoxy.flow;

import java.io.InputStream;
import org.pf4j.ExtensionPoint;

/**
 * 节点扩展
 *
 * @author GuoXiaoyong
 */
public interface FlowNodeExtension extends ExtensionPoint {

  /**
   * 获取节点css内容
   *
   * @return {@code String}
   */
  InputStream getCssInputStream();

  /**
   * 获取节点js内容
   *
   * @return {@code String}
   */
  InputStream getJsInputStream();

  /**
   * 节点标识
   *
   * @return {@code String}
   */
  String name();
}
