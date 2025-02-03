package com.nova.colis.controller;

import com.nova.colis.dto.ChatMessageRequestDTO;
import com.nova.colis.dto.ChatMessageResponseDTO;
import com.nova.colis.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis/{colisId}/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * Endpoint pour envoyer un message concernant un colis.
     * L'URL contient l'identifiant du colis.
     * Le body JSON doit contenir :
     * {
     *   "senderId": <Long>,
     *   "senderRole": "ROLE_CLIENT" ou "ROLE_LIVREUR",
     *   "message": "Le contenu du message"
     * }
     */
    @PostMapping("/messages")
    public ResponseEntity<ChatMessageResponseDTO> sendMessage(
            @PathVariable("colisId") Long colisId,
            @RequestBody ChatMessageRequestDTO requestDTO) {
        // Forcer l'association du colis via l'URL (pour plus de sécurité)
        requestDTO.setColisId(colisId);
        ChatMessageResponseDTO responseDTO = chatService.sendMessage(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint pour récupérer la conversation (tous les messages) pour un colis donné.
     * Exemple : GET /api/colis/123/chat/messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageResponseDTO>> getConversation(
            @PathVariable("colisId") Long colisId) {
        List<ChatMessageResponseDTO> conversation = chatService.getConversation(colisId);
        return ResponseEntity.ok(conversation);
    }
}
