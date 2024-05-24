package cn.guoxy.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class OauthCorsFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(OauthCorsFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO 临时增加Header解决跨域错误问题
    if (logger.isDebugEnabled()) {
      logger.debug("request Origin = {}", request.getHeader(HttpHeaders.ORIGIN));
      logger.debug(
          "response Access-Control-Allow-Origin = {}",
          response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
    if (request.getHeader(HttpHeaders.ORIGIN) != null
        && !response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)) {
      response.addHeader(
          HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
    }
    if (CorsUtils.isCorsRequest(request)) {
      if (request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS) != null
          && !response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)) {
        response.addHeader(
            HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
            request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
      }
      if (request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null
          && !response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)) {
        response.addHeader(
            HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
            request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
      }
      response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    }
    filterChain.doFilter(request, response);
  }
}
