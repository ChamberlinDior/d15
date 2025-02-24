package com.nova.colis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:8080",        // Pour tests locaux
                                "http://192.168.1.37:8080",       // Pour une IP locale
                                "exp://127.0.0.1:19000",          // Pour Expo Go
                                "http://18.223.237.15:8089",      // URL de production via IP
                                "http://ec2-18-223-237-15.us-east-2.compute.amazonaws.com:8089"  // URL de production via DNS
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
