package com.example.demoSpringSecurity.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Autowired JwtEncoder jwtEncoder;

  /* Generate token based on a Jwt */
  public String generateTokenValue(Authentication auth) {

    List<String> authorities = getAuthorities(auth);

    JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256).build();
    Instant now = Instant.now();
    System.out.println(now.toString());
    JwtClaimsSet jwsClaims =
        JwtClaimsSet.builder()
            .claim("authorities", authorities)
            .subject(auth.getName())
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.MINUTES))
            .issuer("self")
            .build();
    JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, jwsClaims);
    Jwt jws = jwtEncoder.encode(parameters);
    return jws.getTokenValue();
  }

  /* Get Authorithies and convert them to list of authority (in String) */
  private List<String> getAuthorities(Authentication auth) {

    List<String> roles = new ArrayList<>();
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    for (GrantedAuthority grantedAuthority : authorities.stream().toList()) {
      roles.add(grantedAuthority.toString().split("_")[1]);
    }
    return roles;
  }
}
