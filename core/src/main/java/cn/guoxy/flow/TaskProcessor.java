package cn.guoxy.flow;

import cn.guoxy.spi.NamedSPI;
import java.util.Collections;
import java.util.Set;

/**
 * 任务处理器推荐命名为 XxxTaskProcessor
 *
 * @see NamedSPI
 * @author Guo XiaoYong
 */
public interface TaskProcessor extends NamedSPI {

  @Override
  default String getName() {
    return this.getClass().getSimpleName().replace("TaskProcessor", "");
  }

  /**
   * 过程
   *
   * @param context 上下文
   */
  void process(TaskContext context);

  /**
   * 获取设置
   *
   * @return {@code Set<Setting<?>> }
   */
  default Set<Setting<?>> getParameterSettings() {
    return Collections.emptySet();
  }
}
