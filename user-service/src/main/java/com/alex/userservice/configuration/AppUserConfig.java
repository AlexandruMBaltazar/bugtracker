package com.alex.userservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppUserConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
