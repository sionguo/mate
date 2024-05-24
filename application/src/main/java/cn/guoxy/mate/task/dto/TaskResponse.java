package cn.guoxy.mate.task.dto;

import cn.guoxy.mate.task.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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

  @Schema(description = "开始时间")
  private Instant startDate;

  @Schema(description = "截止时间")
  private Instant dueDate;

  @Schema(description = "任务优先级")
  private TaskPriority priority;

  @Schema(description = "是否完成")
  private Boolean done;

  @Schema(description = "完成时间")
  private Instant doneDate;
}
