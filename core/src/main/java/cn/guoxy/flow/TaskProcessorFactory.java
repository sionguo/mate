package cn.guoxy.flow;

import cn.guoxy.spi.NamedSPILoader;

/**
 * 任务处理器工厂
 *
 * @author Guo XiaoYong
 */
public class TaskProcessorFactory {
  private static final NamedSPILoader<TaskProcessor> TASK_PROCESSOR_NAMED_SPI_LOADER =
      new NamedSPILoader<>(TaskProcessor.class);

  public static TaskProcessor getTaskProcessor(String name) {
    return TASK_PROCESSOR_NAMED_SPI_LOADER.lookup(name);
  }
}
