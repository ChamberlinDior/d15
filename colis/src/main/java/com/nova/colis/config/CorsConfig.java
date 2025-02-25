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
                        "http://localhost:8080",        // Pour les tests locaux (navigateur, iOS Sim)
                        "http://192.168.1.37:8080",       // Pour une IP locale
                        "exp://127.0.0.1:19000",          // Pour Expo Go en développement
                        "http://3.140.185.230:8080"        // Votre URL publique AWS (ou autre URL de production)
                        // Vous pouvez ajouter d'autres origines si nécessaire
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
}

