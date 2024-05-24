package cn.guoxy.mate.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 任务响应
 *
 * @author GuoXiaoyong
 */
@Data
@Schema(description = "任务列表返回值")
public class TaskListResponse implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "任务清单ID", requiredMode = RequiredMode.REQUIRED)
  private String id;

  @Schema(
      description = "任务清单颜色",
      example = "rgba(151, 25, 176, 0.3)",
      requiredMode = RequiredMode.REQUIRED)
  private String color;

  @Schema(description = "任务清单名", example = "📅", requiredMode = RequiredMode.REQUIRED)
  private String name;
}
