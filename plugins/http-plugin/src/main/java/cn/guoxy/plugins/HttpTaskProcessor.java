package cn.guoxy.plugins;

import cn.guoxy.flow.TaskContext;
import cn.guoxy.flow.TaskProcessor;
import com.google.auto.service.AutoService;

/**
 * http插件
 *
 * @author GuoXiaoyong
 */
@AutoService(TaskProcessor.class)
public class HttpTaskProcessor implements TaskProcessor {

  @Override
  public void process(TaskContext context) {}
}
