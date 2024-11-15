package com.example.demoSpringSecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

  @ExceptionHandler(JwtValidationException.class)
  public ResponseEntity<Object> handleJwtValidationException(JwtValidationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
  }
}
