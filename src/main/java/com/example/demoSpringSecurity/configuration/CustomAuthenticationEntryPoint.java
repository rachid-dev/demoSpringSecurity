package com.example.demoSpringSecurity.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  BearerTokenAuthenticationEntryPoint bearerTokenAuthenticationEntryPoint =
      new BearerTokenAuthenticationEntryPoint();
  BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    if (authException instanceof OAuth2AuthenticationException) {
      bearerTokenAuthenticationEntryPoint.commence(request, response, authException);
      OAuth2Error error = ((OAuth2AuthenticationException) authException).getError();
      response.sendError(HttpStatus.UNAUTHORIZED.value(), error.getDescription());
      return;
    }

    response.setHeader(
        "WWW-Authenticate", "Basic realm=\"" + basicAuthenticationEntryPoint.getRealmName() + "\"");
    response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
  }
}
