package com.nova.colis.controller;

import com.nova.colis.service.FirebaseMessagingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;

    public NotificationController(FirebaseMessagingService firebaseMessagingService) {
        this.firebaseMessagingService = firebaseMessagingService;
    }

    /**
     * Endpoint pour envoyer une notification.
     * Exemple d'appel : POST /api/notifications/send?title=Bonjour&body=Test&token=DEVICE_TOKEN
     */
    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam String token) {
        return firebaseMessagingService.sendNotification(title, body, token);
    }
}
