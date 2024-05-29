package cn.guoxy.plugins;

import cn.guoxy.flow.ProcessNodeExtension;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.pf4j.Extension;

/**
 * http进程节点扩展
 *
 * @author GuoXiaoyong
 */
@Extension
public class HttpProcessNodeExtension implements ProcessNodeExtension {


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
