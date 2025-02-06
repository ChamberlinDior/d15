package com.nova.colis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Active un broker simple sur le préfixe /topic pour diffuser des notifications
        config.enableSimpleBroker("/topic");
        // Les messages envoyés par le client devront être préfixés par /app
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Point d'accès WebSocket avec SockJS pour assurer la compatibilité avec les navigateurs
        registry.addEndpoint("/ws")
                .setAllowedOrigins(
                        "http://localhost:8080",        // Pour tests locaux
                        "http://192.168.1.37:8080",       // Pour IP locale
                        "exp://127.0.0.1:19000",          // Pour Expo Go en développement
                        "http://18.188.74.42:8089"        // Pour production (exemple AWS)
                )
                .withSockJS();
    }
}
