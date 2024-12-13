package com.example.berryshoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public String upLoad() {
        return "/images";
    }

    @Bean
    public String serverURL() {
        return "http://localhost:8080";
    }

    @Bean
    public String serveImageURL() {
        return "api/v1/image/get";
    }

}
