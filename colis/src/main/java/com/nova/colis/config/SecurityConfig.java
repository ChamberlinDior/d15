package com.nova.colis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import nécessaire pour HttpMethod
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints pour l'inscription et la connexion des clients
                        .requestMatchers("/api/clients/register").permitAll()
                        .requestMatchers("/api/clients/login").permitAll()

                        // Endpoints pour l'inscription et la connexion des livreurs
                        .requestMatchers("/api/livreurs/register").permitAll()
                        .requestMatchers("/api/livreurs/login").permitAll()

                        // Autoriser la création de colis sans authentification
                        .requestMatchers(HttpMethod.POST, "/api/colis").permitAll()

                        // Autoriser l'accès aux endpoints du chat (ouvert à tous)
                        // Le pattern correspond à /api/colis/{colisId}/chat/**
                        .requestMatchers("/api/colis/*/chat/**").permitAll()

                        // Les autres endpoints colis restent accessibles publiquement
                        .requestMatchers("/api/colis/**").permitAll()

                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
