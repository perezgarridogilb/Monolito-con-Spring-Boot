package com.company.movie.security.filter;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class TokenJwtConfig {
    public static SecretKey SECRET_KEY;
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    public TokenJwtConfig(@Value("${jwt.secret}") String secret) {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
