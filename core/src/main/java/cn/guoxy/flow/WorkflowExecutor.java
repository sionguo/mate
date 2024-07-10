package cn.guoxy.flow;

import cn.guoxy.common.NanoIdUtils;
import cn.guoxy.flow.Workflow.Edge;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工作流实例
 *
 * @author Guo XiaoYong
 */
@Data
public class WorkflowExecutor {
  private static final Logger logger = LoggerFactory.getLogger(WorkflowExecutor.class);

  private Graph<String, DefaultEdge> graph;
  private String id;
  private String workflowId;
  private ExecutionState executionState;
  private Map<String, Node> id2Node;
  private List<Edge> edges;

  public WorkflowExecutor(Workflow workflow) {
    this.id = NanoIdUtils.randomNanoId();
    this.workflowId = workflow.getId();
    this.graph = new DirectedAcyclicGraph<>(DefaultEdge.class);
    List<Workflow.Node> workflowNodes = workflow.getNodes();

    this.id2Node = new HashMap<>(workflowNodes.size());
    for (Workflow.Node node : workflowNodes) {
      Node instanceNode = new Node(node);
      this.id2Node.put(instanceNode.getId(), instanceNode);
      this.graph.addVertex(node.getId());
    }

    List<Workflow.Edge> workflowEdges = workflow.getEdges();
    this.edges = new ArrayList<>(workflowEdges.size());
    for (Workflow.Edge edge : workflowEdges) {
      this.edges.add(edge);
      this.graph.addEdge(edge.getSource(), edge.getTarget());
    }
  }

  public void execute(ExecutorService executorService) {
    while (true) {
      List<Node> readyNodes =
          this.graph.vertexSet().stream()
              .filter(v -> this.graph.inDegreeOf(v) == 0)
              .map(this.id2Node::get)
              .toList();
      if (readyNodes.isEmpty()) {
        break;
      }
      Map<String, Future<?>> futures = new HashMap<>(readyNodes.size());
      for (Node readyNode : readyNodes) {
        TaskProcessor taskProcessor =
            TaskProcessorFactory.getTaskProcessor(readyNode.getHolder().getType());
        readyNode.setStartTime(Instant.now());
        readyNode.setState(ExecutionState.RUNNING);
        TaskContext context = new TaskContext(SerializationUtils.clone(readyNode));
        Future<Boolean> submit =
            executorService.submit(
                () -> {
                  taskProcessor.process(context);
                },
                true);
        futures.put(readyNode.id, submit);
      }
      boolean success = true;
      for (Entry<String, Future<?>> stringFutureEntry : futures.entrySet()) {
        String key = stringFutureEntry.getKey();
        Future<?> future = stringFutureEntry.getValue();
        try {
          future.get();
          this.id2Node.get(key).setState(ExecutionState.SUCCEED);
          this.id2Node.get(key).setEndTime(Instant.now());
        } catch (InterruptedException | ExecutionException e) {
          this.id2Node.get(key).setState(ExecutionState.FAILED);
          this.id2Node.get(key).setEndTime(Instant.now());
          this.id2Node.get(key).setMessage(e.getMessage());
          success = false;
          break;
        }
        this.graph.removeVertex(key);
      }
      if (!success) {
        break;
      }
    }
  }

  @Data
  public static class Node implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private String id;
    private ExecutionState state;
    private String message;
    private Workflow.Node holder;
    private Instant startTime;
    private Instant endTime;

    public Node(Workflow.Node node) {
      this.id = node.getId();
      this.state = ExecutionState.WAITING;
      this.holder = node;
    }
  }
}
