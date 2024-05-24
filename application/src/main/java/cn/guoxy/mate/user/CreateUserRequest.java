package cn.guoxy.mate.user;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 创建使用者要求
 *
 * @author GuoXiaoyong
 */
@Data
public class CreateUserRequest implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
