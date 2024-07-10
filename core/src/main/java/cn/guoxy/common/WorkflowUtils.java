package cn.guoxy.common;

import cn.guoxy.flow.Workflow;
import cn.guoxy.flow.Workflow.Edge;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

/**
 * 工作流实用工具
 *
 * @author Guo XiaoYong
 */
public final class WorkflowUtils {
  private WorkflowUtils() {}

  /**
   * 验证工作流
   *
   * @param workflow 工作流程
   * @return boolean
   */
  public static boolean validateWorkflow(Workflow workflow) {
    if (workflow == null) {
      return false;
    }
    // 不允许为空
    if (workflow.getNodes() == null || workflow.getNodes().isEmpty()) {
      return false;
    }
    Set<String> nodeIds = new HashSet<>();
    for (Workflow.Node n : workflow.getNodes()) {
      if (nodeIds.contains(n.getId())) {
        return false;
      }
      nodeIds.add(n.getId());
    }
    Graph<String, DefaultEdge> graph = new DirectedAcyclicGraph<>(DefaultEdge.class);
    try {
      for (Workflow.Node node : workflow.getNodes()) {
        graph.addVertex(node.getId());
      }
      for (Edge edge : workflow.getEdges()) {
        graph.addEdge(edge.getSource(), edge.getTarget());
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
