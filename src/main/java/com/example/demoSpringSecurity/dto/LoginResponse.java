package com.example.demoSpringSecurity.dto;

import lombok.Data;

@Data
public class LoginResponse {
  private String message;
  private String jwe;

  public LoginResponse() {}

  public LoginResponse(String message) {
    this.message = message;
  }

  public LoginResponse(String message, String jwe) {
    this.message = message;
    this.jwe = jwe;
  }
}
