package cn.guoxy.mate.task.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 列出任务请求
 *
 * @author GuoXiaoyong
 */
@Data
public class ListTaskRequest implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
