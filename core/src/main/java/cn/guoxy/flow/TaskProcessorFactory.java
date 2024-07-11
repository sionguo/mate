package cn.guoxy.flow;

import cn.guoxy.spi.NamedSPILoader;
import java.util.Collection;

/**
 * 任务处理器工厂
 *
 * @author Guo XiaoYong
 */
public class TaskProcessorFactory {
  private static final NamedSPILoader<TaskProcessor> TASK_PROCESSOR_NAMED_SPI_LOADER =
      new NamedSPILoader<>(TaskProcessor.class);

  /**
   * 获取任务处理器
   *
   * @param name 名称
   * @return {@code TaskProcessor }
   */
  public static TaskProcessor getTaskProcessor(String name) {
    return TASK_PROCESSOR_NAMED_SPI_LOADER.lookup(name);
  }

  /**
   * 获取任务处理器
   *
   * @return {@code Collection<TaskProcessor> }
   */
  public static Collection<TaskProcessor> getTaskProcessors() {
    return TASK_PROCESSOR_NAMED_SPI_LOADER.allServices();
  }
}
