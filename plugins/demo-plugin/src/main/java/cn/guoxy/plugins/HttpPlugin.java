package cn.guoxy.plugins;

import cn.guoxy.flow.FlowNodeExtension;
import java.io.InputStream;
import org.pf4j.Extension;

/**
 * http插件
 *
 * @author GuoXiaoyong
 */
@Extension
public class HttpPlugin implements FlowNodeExtension {

  @Override
  public InputStream getCssInputStream() {
    return null;
  }

  @Override
  public InputStream getJsInputStream() {
    return null;
  }

  @Override
  public String name() {
    return "";
  }
}
