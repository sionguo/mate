/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.guoxy.auth.authentication;

import java.io.Serial;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * 设备客户端身份验证令牌（设备码模式token）
 *
 * @author GuoXiaoyong
 */
@Transient
public class DeviceClientAuthenticationToken extends OAuth2ClientAuthenticationToken {
  @Serial private static final long serialVersionUID = 1L;

  public DeviceClientAuthenticationToken(
      String clientId,
      ClientAuthenticationMethod clientAuthenticationMethod,
      @Nullable Object credentials,
      @Nullable Map<String, Object> additionalParameters) {
    super(clientId, clientAuthenticationMethod, credentials, additionalParameters);
  }

  public DeviceClientAuthenticationToken(
      RegisteredClient registeredClient,
      ClientAuthenticationMethod clientAuthenticationMethod,
      @Nullable Object credentials) {
    super(registeredClient, clientAuthenticationMethod, credentials);
  }
}
