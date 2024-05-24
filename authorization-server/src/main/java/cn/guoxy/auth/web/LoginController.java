package cn.guoxy.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录控制器
 *
 * @author GuoXiaoyong
 */
@Controller
public class LoginController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
