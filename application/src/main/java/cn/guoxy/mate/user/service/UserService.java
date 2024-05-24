package cn.guoxy.mate.user.service;

import cn.guoxy.mate.user.CreateUserRequest;
import cn.guoxy.mate.user.User;
import org.springframework.data.domain.Page;

/**
 * 用户服务
 *
 * @author GuoXiaoyong
 */
public interface UserService {
  /**
   * 创建使用者
   *
   * @param request 要求
   * @return {@code User}
   */
  User createUser(CreateUserRequest request);

  /**
   * 列出用户
   *
   * @return {@code Page<User>}
   */
  Page<User> listUsers();
}
