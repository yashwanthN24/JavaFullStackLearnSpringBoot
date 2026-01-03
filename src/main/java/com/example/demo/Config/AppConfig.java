package com.example.demo.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

//    now this allows ModelMapper to be injected when need by spring ioc controller object creation is managed by spring boot
}
