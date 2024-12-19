package com.example.demo.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


@Component
public class JWTUtil {
  @Value("${jwt_secret}")
  private String secret;

  public String generateToken(String email) {
    return JWT.create()
              .withSubject("User Details")
              .withClaim("email", email)
              .withIssuedAt(new Date())
              .withIssuer("Book Store") // YOUR APPLICATION/PROJECT/COMPANY NAME
              .sign(Algorithm.HMAC256(secret));
  }

  public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User Details")
            .withIssuer("Book Store")
            .build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getClaim("email").asString();
  }
}
