package com.example.demoSpringSecurity.dto;

import lombok.Data;

@Data
public class SignupResponse {
  private String message;
  private Integer id;

  public SignupResponse(String message, Integer id) {
    this.message = message;
    this.id = id;
  }
}
