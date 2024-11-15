package com.example.demoSpringSecurity.exception;

public class UserAlreadyExistsException extends Exception {

  public UserAlreadyExistsException(String message) {
    super(message);
    // this.message = message;
  }
}
