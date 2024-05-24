package cn.guoxy.mate.task;

import cn.guoxy.mate.common.BaseEntity;
import java.io.Serial;
import java.time.LocalDate;
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

  /** 标题 */
  private String title;

  /** 描述 */
  private String description;

  /** 优先级 */
  private TaskPriority priority = TaskPriority.NORMAL;

  /** 状态 */
  private TaskStatus status = TaskStatus.TODO;

  /** 计划启动 */
  private LocalDate plannedStart;

  /** 计划完成 */
  private LocalDate plannedFinish;

  /** 实际开始 */
  private LocalDate actualStart;

  /** 实际完成 */
  private LocalDate actualFinish;

  /** 收藏 */
  private Boolean star;

  /** 任务列表id */
  private String taskListId;
}
