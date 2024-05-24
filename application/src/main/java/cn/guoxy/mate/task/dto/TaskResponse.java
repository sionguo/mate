package cn.guoxy.mate.task.dto;

import cn.guoxy.mate.task.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Data;

/**
 * 任务响应
 *
 * @author GuoXiaoyong
 */
@Data
@Schema(description = "任务返回值")
public class TaskResponse implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "任务ID")
  private String id;

  @Schema(description = "创建时间")
  private Instant createDate;

  @Schema(description = "创建者")
  private String createBy;

  @Schema(description = "最后修改时间")
  private Instant lastModifiedDate;

  @Schema(description = "最后修改者")
  private String lastModifiedBy;

  @Schema(description = "任务标题")
  private String title;

  @Schema(description = "任务描述")
  private String description;

  @Schema(description = "任务优先级")
  private TaskPriority priority;

  /** 状态 */
  @Schema(description = "任务状态")
  private String status;

  /** 计划启动 */
  @Schema(description = "计划开始")
  private LocalDate plannedStart;

  /** 计划完成 */
  @Schema(description = "计划完成")
  private LocalDate plannedFinish;

  /** 实际开始 */
  @Schema(description = "实际开始")
  private LocalDate actualStart;

  /** 实际完成 */
  @Schema(description = "实际完成")
  private LocalDate actualFinish;

  /** 收藏 */
  @Schema(description = "收藏")
  private Boolean star;
}
