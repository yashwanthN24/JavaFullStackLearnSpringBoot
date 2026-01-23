package com.example.demo.config;

import com.example.demo.filters.JWTAuthFilter;
import com.example.demo.filters.LoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    private final LoggingFilter loggingFilter;

//

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
          return  httpSecurity
                  .authorizeHttpRequests(auth -> auth
                          .requestMatchers("/posts" , "/auth/**").permitAll() // now posts route is public accessible by all not a protected route
//                          .requestMatchers("/posts/**").hasAnyRole("ADMIN")// protected route but only user with Role ADMIN can access these routes
                          .anyRequest().authenticated())
                  .csrf(csrfConfig -> csrfConfig.disable()) // to disable csrf these two session and csrf we wont woory as we will use JWT authentication thats thne industry standard
                  .sessionManagement(sessionConfig ->
                          sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // to remove session
                  .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                  .addFilterBefore(loggingFilter, JWTAuthFilter.class)
//                  .formLogin(Customizer.withDefaults())
                  .build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normalUser = User
//                .withUsername("tejas")
//                .password(passwordEncoder().encode("tejas@123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser , adminUser);
//    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

}
