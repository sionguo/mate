package cn.guoxy.mate.user;

import cn.guoxy.mate.common.data.BaseEntity;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户
 *
 * @author GuoXiaoyong
 */
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  /** 用户名 */
  private String username;

  /** 电子邮件 */
  private String email;

  /** 密码 */
  private String password;

  /** 帐户已过期 */
  private Boolean accountExpired;

  /** 帐户已锁定 */
  private Boolean accountLocked;

  /** 凭据已过期 */
  private Boolean credentialsExpired;

  /** 启用 */
  private Boolean enabled;
}
