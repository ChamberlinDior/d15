package com.nova.colis.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    /**
     * Envoie une notification push via FCM.
     *
     * @param title Le titre de la notification.
     * @param body  Le contenu du message.
     * @param token Le device token de l'appareil client.
     * @return La réponse renvoyée par Firebase ou null en cas d'erreur.
     */
    public String sendNotification(String title, String body, String token) {
        // Utilisez le builder pour créer l'objet Notification
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // Construction du message avec l'objet Notification
        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        try {
            // Envoi du message via Firebase Cloud Messaging
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notification envoyée avec succès : " + response);
            return response;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
            return null;
        }
    }
}
