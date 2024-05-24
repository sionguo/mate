package cn.guoxy.mate.task;

import cn.guoxy.mate.data.BaseEntity;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 任务列表
 *
 * @author GuoXiaoyong
 */
@Table(name = "task_list")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskList extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;
  private String color;
  private String name;
}
