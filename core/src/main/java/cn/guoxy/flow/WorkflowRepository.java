package cn.guoxy.flow;

/**
 * 工作流存储库
 *
 * @author Guo XiaoYong
 */
public interface WorkflowRepository {
  /**
   * 保存
   *
   * @param workflow 工作流程
   * @return {@code Workflow }
   */
  Workflow save(Workflow workflow);

  /**
   * 按id查找
   *
   * @param id ID
   * @return {@code Workflow }
   */
  Workflow findById(String id);
}
