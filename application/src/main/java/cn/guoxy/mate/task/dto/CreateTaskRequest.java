package cn.guoxy.mate.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Data;

/**
 * 创建任务要求
 *
 * @author GuoXiaoyong
 */
@Data
@Schema(description = "创建任务请求")
public class CreateTaskRequest implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "任务标题", example = "标题1")
  @NotBlank
  private String title;

  @Schema(description = "任务描述")
  private String description;

  /** 计划开始 */
  @Schema(description = "计划开始")
  private LocalDate plannedStart;

  /** 计划完成 */
  @Schema(description = "计划完成")
  private LocalDate plannedFinish;

  @Schema(description = "任务列表ID")
  private String taskListId;
}
