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
     */
    @PostMapping("/messages")
    public ResponseEntity<ChatMessageResponseDTO> sendMessage(
            @PathVariable("colisId") Long colisId,
            @RequestBody ChatMessageRequestDTO requestDTO) {
        // Forcer l'association du colis via l'URL
        requestDTO.setColisId(colisId);
        ChatMessageResponseDTO responseDTO = chatService.sendMessage(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint pour récupérer la conversation pour un colis donné.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageResponseDTO>> getConversation(
            @PathVariable("colisId") Long colisId) {
        List<ChatMessageResponseDTO> conversation = chatService.getConversation(colisId);
        return ResponseEntity.ok(conversation);
    }
}
