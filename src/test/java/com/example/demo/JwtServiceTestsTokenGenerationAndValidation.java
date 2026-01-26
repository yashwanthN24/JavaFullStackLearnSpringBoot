package com.example.demo;

import com.example.demo.entities.User;
import com.example.demo.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class JwtServiceTestsTokenGenerationAndValidation {

    @Autowired
    private JWTService jwtService;

    @Test
    public void testGenerateToken(){
        User user = new User(4L , "niketha@gmail.com" , "niki" , "nikiesh" , Set.of() );
        String token = jwtService.generateAccessToken(user);
        System.out.println(token);

        Long userId = jwtService.getUserIdFromToken(token);
        System.out.println(userId);
    }

}
