package cn.guoxy.mate.flow;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流量定义控制器
 *
 * @author Guo XiaoYong
 */
@RestController
public class WorkFlowDefinitionController {
  private final WorkFlowDefinitionService workFlowDefinitionService;

  public WorkFlowDefinitionController(WorkFlowDefinitionService workFlowDefinitionService) {
    this.workFlowDefinitionService = workFlowDefinitionService;
  }

  @GetMapping("/flow/node-types")
  public ResponseEntity<List<WorkflowNode>> nodeTypes() {
    List<WorkflowNode> workflowNodes = workFlowDefinitionService.allFlowNodes();
    return ResponseEntity.ok(workflowNodes);
  }
}
