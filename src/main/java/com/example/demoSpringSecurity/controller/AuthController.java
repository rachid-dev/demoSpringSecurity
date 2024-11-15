package com.example.demoSpringSecurity.controller;

import com.example.demoSpringSecurity.dto.LoginRequest;
import com.example.demoSpringSecurity.dto.LoginResponse;
import com.example.demoSpringSecurity.dto.SignupRequest;
import com.example.demoSpringSecurity.model.DBUser;
import com.example.demoSpringSecurity.repository.DBUserRepository;
import com.example.demoSpringSecurity.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired DBUserRepository userRepository;

  @Autowired BCryptPasswordEncoder passwordEncoder;

  @Autowired AuthenticationManager authenticationManager;

  @Autowired JwtService jwtService;

  @PostMapping("/signup")
  String signup(@RequestBody SignupRequest info) {
    String encodedPassword = passwordEncoder.encode(info.getPassword());
    DBUser user = new DBUser(info.getUsername(), encodedPassword, "ADMIN");
    userRepository.save(user);
    return "Signed up";
  }

  @PostMapping("/login")
  ResponseEntity<LoginResponse> login(@RequestBody LoginRequest credentials) {
    UsernamePasswordAuthenticationToken authReq =
        new UsernamePasswordAuthenticationToken(
            credentials.getUsername(), credentials.getPassword());
    Authentication authRes = authenticationManager.authenticate(authReq);

    String token = jwtService.generateTokenValue(authRes);

    LoginResponse res = new LoginResponse("Logged In", token);
    return new ResponseEntity<LoginResponse>(res, HttpStatus.OK);
  }
}
