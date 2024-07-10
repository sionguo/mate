package cn.guoxy.flow;

import lombok.Getter;

/**
 * 执行状态
 *
 * @see Enum
 * @author Guo XiaoYong
 */
@Getter
public enum ExecutionState {
  WAITING("等待调度"),
  RUNNING("运行中"),
  FAILED("失败"),
  SUCCEED("成功");

  /**
   * 描述
   *
   * @see String
   */
  private final String description;

  ExecutionState(String description) {
    this.description = description;
  }
}
