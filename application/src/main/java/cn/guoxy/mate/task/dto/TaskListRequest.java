package cn.guoxy.mate.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 创建任务列表要求
 *
 * @author GuoXiaoyong
 */
@Data
@Schema(description = "任务清单请求")
public class TaskListRequest implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @NotBlank
  @Schema(
      description = "color",
      example = "rgba(151, 25, 176, 0.3)",
      requiredMode = RequiredMode.REQUIRED)
  private String color;

  @NotBlank
  @Schema(description = "任务清单名", example = "旅行", requiredMode = RequiredMode.REQUIRED)
  private String name;
}
