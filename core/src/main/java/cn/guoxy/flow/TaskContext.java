package cn.guoxy.flow;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

/**
 * 任务上下文
 *
 * @author Guo XiaoYong
 */
@Getter
@ToString
public class TaskContext {

  private final Map<String, Object> parameters;
  private final Map<String, Object> result;
  private final WorkflowExecutor.Node node;

  public TaskContext(WorkflowExecutor.Node node) {
    this.node = node;
    parameters = new HashMap<>();
    result = new HashMap<>();
  }

  /**
   * 添加参数
   *
   * @param key 钥匙
   * @param value 价值
   */
  public void addParameter(String key, Object value) {
    this.parameters.put(key, value);
  }

  /**
   * 添加结果
   *
   * @param key 钥匙
   * @param value 价值
   */
  public void addResult(String key, Object value) {
    this.result.put(key, value);
  }
}
