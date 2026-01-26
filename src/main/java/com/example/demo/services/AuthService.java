package com.example.demo.services;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    private final SessionService sessionService;


    public LoginResponseDTO login(LoginDTO loginDTO , HttpServletRequest request , HttpServletResponse response) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            sessionService.generateNewSession(user , refreshToken);

            return new LoginResponseDTO(user.getId(), accessToken, refreshToken);

    }

    public LoginResponseDTO refreshToken(String refreshToken) {

//        check if refresh token is valid
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        sessionService.validateSession(refreshToken);

        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId() , accessToken , refreshToken);
    }

    public void logout(String refreshToken){
        sessionService.logoutSession(refreshToken);
    }
}
