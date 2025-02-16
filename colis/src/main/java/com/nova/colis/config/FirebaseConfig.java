package com.nova.colis.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // Charger le fichier JSON depuis src/main/resources/config/serviceAccountKey.json
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("config/serviceAccountKey.json");

        if (serviceAccount == null) {
            throw new RuntimeException("Le fichier serviceAccountKey.json est introuvable dans le dossier resources/config");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://colis-30fd9.firebaseio.com") // Adaptez l'URL à votre projet si nécessaire
                .build();

        // Initialisation de Firebase App
        return FirebaseApp.initializeApp(options);
    }
}
