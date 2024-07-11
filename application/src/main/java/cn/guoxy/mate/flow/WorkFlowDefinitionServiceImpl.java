package cn.guoxy.mate.flow;

import cn.guoxy.flow.TaskProcessor;
import cn.guoxy.flow.TaskProcessorFactory;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 工作流释义服务实现
 *
 * @see WorkFlowDefinitionService
 * @author Guo XiaoYong
 */
@Service
public class WorkFlowDefinitionServiceImpl implements WorkFlowDefinitionService {

  @Override
  public List<WorkflowNode> allFlowNodes() {
    Collection<TaskProcessor> taskProcessors = TaskProcessorFactory.getTaskProcessors();
    return taskProcessors.stream()
        .map(
            t -> {
              WorkflowNode workflowNode = new WorkflowNode();
              workflowNode.setUrl(t.getUrl());
              workflowNode.setCssUrl(t.getCssUrl());
              return workflowNode;
            })
        .toList();
  }
}
