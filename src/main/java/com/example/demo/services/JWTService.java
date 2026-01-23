package com.example.demo.services;

import com.example.demo.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String jwtSeretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSeretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
//        Jwts.builder is for signinvg a new JWT token
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email" , user.getEmail())
                .claim("roles" , Set.of("ADMIN" , "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (5 * 60 * 1000)))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
//        Jwts.parser is for decoding the token
         Claims claims = Jwts.parser()
                 .verifyWith(getSecretKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();

         return Long.valueOf(claims.getSubject());
    }

}
