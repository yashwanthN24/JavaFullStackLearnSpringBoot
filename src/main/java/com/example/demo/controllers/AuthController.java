package com.example.demo.controllers;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO  signUpDTO){
        UserDTO userDTO = userService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO , HttpServletResponse response){
        String token = authService.login(loginDTO);

        Cookie cookie = new Cookie("token" , token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }
}
