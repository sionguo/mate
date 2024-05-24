package cn.guoxy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 授权服务器应用程序
 *
 * @author GuoXiaoyong
 */
@SpringBootApplication
public class AuthorizationServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }
}
