package cn.guoxy.mate.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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

  /** 开始日期 */
  @Schema(description = "开始时间")
  private Instant startDate;

  /** 到期日 */
  @Schema(description = "截止时间")
  private Instant dueDate;
}
