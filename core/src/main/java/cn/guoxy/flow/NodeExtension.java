package cn.guoxy.flow;

import cn.guoxy.spi.NamedSPI;
import java.io.InputStream;

/**
 * 节点扩展
 *
 * @author GuoXiaoyong
 */
public interface NodeExtension extends NamedSPI {

  /**
   * 获取名称
   *
   * @return {@code String }
   */
  String getName();

  /**
   * 获取ui组件
   *
   * @return {@code UIComponent }
   */
  UiComponent getUiComponent();

  /**
   * 读取资源
   *
   * @param resourcePath 资源路径
   * @return {@code InputStream }
   */
  default InputStream readResource(String resourcePath) {
    return getClass().getClassLoader().getResourceAsStream(resourcePath);
  }

  record UiComponent(String jsPath, String cssPath) {}
}
