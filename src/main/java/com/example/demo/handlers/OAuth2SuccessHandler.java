package com.example.demo.handlers;

import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.entities.User;
import com.example.demo.services.JWTService;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTService jwtService;

    private final JsonMapper jsonMapper;
    private final OAuth2AuthorizedClientService authorizedClientService;

    private final RestClient getGithubEmailFromTokenClient;

    @Value("${env.deploy}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        log.info(String.valueOf(token.getCredentials()));


        log.info(String.valueOf(oAuth2User));

        String email = oAuth2User.getAttribute("email");

        // If email is null, fetch from GitHub API
        if (email == null && "github".equals(token.getAuthorizedClientRegistrationId())) {
            email = fetchGitHubPrimaryEmail(token);
        }

        log.info("%s".formatted(fetchGitHubPrimaryEmail(token)));

        log.info("User email: {}", email);

        User user = userService.getUserByEmail(email);

        if (user == null) {
            User newUser = User.builder()
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .build();
            user = userService.save(newUser);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        log.info("access token : %s".formatted(accessToken));

        String frontendUrl = "http://localhost:5003/home.html?token=" + accessToken;

        log.info("redirect frontend url %s".formatted(frontendUrl));
//        getRedirectStrategy().sendRedirect(request , response , frontendUrl );

//        response.sendRedirect(frontendUrl);


        // Send JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LoginResponseDTO loginResponse = new LoginResponseDTO(user.getId(), accessToken, refreshToken);
        response.getWriter().write(jsonMapper.writeValueAsString(loginResponse));


    }

    private String fetchGitHubPrimaryEmail(OAuth2AuthenticationToken authentication) throws JsonProcessingException {
            try {
                OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName()
                );

                if (client == null) {
                    log.error("OAuth2AuthorizedClient not found for user: {}", authentication.getName());
                    return null;
                }

                String accessToken = client.getAccessToken().getTokenValue();

                String emailsJson = getGithubEmailFromTokenClient.get()
                        .uri("/user/emails")
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .body(new ParameterizedTypeReference<String>() {});

                if (emailsJson == null || emailsJson.isBlank()) {
                    log.warn("GitHub API returned empty email response for user: {}", authentication.getName());
                    return null;
                }

                List<Map<String, Object>> emails = jsonMapper.readValue(
                        emailsJson,
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                if (emails == null || emails.isEmpty()) {
                    log.warn("No emails found for GitHub user: {}", authentication.getName());
                    return null;
                }

                log.debug("GitHub emails retrieved: {}",
                        jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(emails));

                // Find primary email, fallback to first available email
                String email = emails.stream()
                        .filter(map -> Boolean.TRUE.equals(map.get("primary")))
                        .findFirst()
                        .or(() -> emails.stream().findFirst())
                        .map(map -> (String) map.get("email"))
                        .orElse(null);

                if (email == null) {
                    log.warn("No valid email found in GitHub response for user: {}", authentication.getName());
                }

                return email;

            } catch (Exception e) {
                log.error("Unexpected error fetching GitHub email for user: {}: {}",
                        authentication.getName(), e.getMessage(), e);
                return null;
            }
        }

    }


