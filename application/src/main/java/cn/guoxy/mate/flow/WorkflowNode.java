package cn.guoxy.mate.flow;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 工作流节点
 *
 * @see Serializable
 * @author Guo XiaoYong
 */
@Data
public class WorkflowNode implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
  private String url;
  private String cssUrl;
}
