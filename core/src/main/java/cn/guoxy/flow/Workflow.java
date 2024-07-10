package cn.guoxy.flow;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 有向无环图流
 *
 * @author Guo XiaoYong
 */
@Data
public class Workflow {
  private String id;
  private List<Node> nodes;
  private List<Edge> edges;

  /**
   * 节点
   *
   * @see Serializable
   * @author Guo XiaoYong
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Node implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private String id;
    private String type;
    private Map<String, Object> data;
  }

  /**
   * 边
   *
   * @see Serializable
   * @author Guo XiaoYong
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Edge implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private String id;
    private String source;
    private String target;
  }
}
