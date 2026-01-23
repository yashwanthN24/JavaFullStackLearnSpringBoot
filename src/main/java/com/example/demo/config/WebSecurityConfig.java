package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
          return  httpSecurity
                  .authorizeHttpRequests(auth -> auth
                          .requestMatchers("/posts").permitAll() // now posts route is public accessible by all not a protected route
                          .requestMatchers("/posts/**").hasAnyRole("ADMIN")// protected route but only user with Role ADMIN can access these routes
                          .anyRequest()
                          .authenticated())
                  .csrf(csrfConfig -> csrfConfig.disable()) // to disable csrf these two session and csrf we wont woory as we will use JWT authentication thats thne industry standard
                  .sessionManagement(sessionConfig ->
                          sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // to remove session
//                  .formLogin(Customizer.withDefaults())
                  .build();
    }

    @Bean
    UserDetailsService myInMemoryUserDetailsService(){
        UserDetails normalUser = User
                .withUsername("tejas")
                .password(passwordEncoder().encode("tejas@123"))
                .roles("USER")
                .build();

        UserDetails adminUser = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser , adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
