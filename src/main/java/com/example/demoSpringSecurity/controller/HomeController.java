package com.example.demoSpringSecurity.controller;

import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class HomeController {

  @GetMapping("/")
  public String home() {
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    return "Hello World";
  }

  @GetMapping("/hello")
  public String hello() {
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    return "Hello World !!!!!!!";
  }

  @PostMapping("/")
  public String postHome() {
    return "Hello World";
  }

  @GetMapping("/admin")
  public String admin() {

    return "Hello, admin";
  }

  @PostMapping("/admin")
  public String postAdmin(@RequestBody Object data) {
    System.out.println("here");
    System.out.println(data);
    return "Hello, admin";
  }
}
