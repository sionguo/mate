package cn.guoxy.flow;

import cn.guoxy.common.nanoid.NanoIdUtils;
import cn.guoxy.common.WorkflowUtils;
import cn.guoxy.exception.WorkflowException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

public class WorkFlowEngine {

  private final WorkflowRepository workflowRepository;
  private final ThreadPoolExecutor taskExecutor;

  public WorkFlowEngine(WorkflowRepository workflowRepository) {
    this.workflowRepository = workflowRepository;
    this.taskExecutor =
        new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors(),
            0,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("task-executor-%d").build());
  }

  /**
   * 部署工作流
   *
   * @param workflow 工作流程
   * @return {@code Workflow }
   */
  public Workflow deployWorkflow(Workflow workflow) {
    if (!WorkflowUtils.validateWorkflow(workflow)) {
      throw new WorkflowException("流程不是一个有效的DAG");
    }
    String id = workflow.getId();
    if (StringUtils.isBlank(id)) {
      workflow.setId(NanoIdUtils.randomNanoId());
    }
    return workflowRepository.save(workflow);
  }

  public void executeWorkflow(String workflowId) {
    Workflow workflow = workflowRepository.findById(workflowId);
    if (workflow == null) {
      return;
    }
    WorkflowExecutor workflowExecutor = new WorkflowExecutor(workflow);
    workflowExecutor.execute(taskExecutor);
  }

  /** 停机 */
  public void shutdown() {
    this.taskExecutor.shutdown();
  }
}
