package com.example.demo.controllers;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${env.deploy}")
    private String deployEnv;


    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO  signUpDTO){
        UserDTO userDTO = userService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO , HttpServletResponse response , HttpServletRequest request){
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO , request , response);

        Cookie cookie = new Cookie("refreshToken" , loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);


        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
           String refreshToken =  Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst()
                   .map(Cookie::getValue)
                    .orElseThrow(()-> new AuthenticationServiceException("Refresh Token not found in the cookie"));
           LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
           return ResponseEntity.ok(loginResponseDTO);

    }

//    So now the mechanism is like the user need to login until the refresh token expire which stays alive for atleast one month to 6months
//    access token expires fast but stil the user has a refresh token when are sent with every request in cookies we us that to generate a new access token
//
}
