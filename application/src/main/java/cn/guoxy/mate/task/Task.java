package cn.guoxy.mate.task;

import cn.guoxy.mate.data.BaseEntity;
import java.io.Serial;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 任务
 *
 * @author guoxiaoyong
 */
@Table(name = "task")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;
  private String title;
  private String description;
  private Instant startDate;
  private Instant dueDate;
  private TaskPriority priority = TaskPriority.NORMAL;
  private Boolean done = false;
  private Instant doneDate;
  


}
