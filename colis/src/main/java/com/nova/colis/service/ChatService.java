package com.nova.colis.service;

import com.nova.colis.dto.ChatMessageRequestDTO;
import com.nova.colis.dto.ChatMessageResponseDTO;
import java.util.List;

public interface ChatService {

    /**
     * Envoie un message pour un colis donné.
     * Vérifie que le colis existe, que son statut est EN_ATTENTE ou EN_COURS_DE_LIVRAISON,
     * et que l'expéditeur est autorisé (client ou livreur assigné).
     *
     * @param requestDTO les données du message à envoyer
     * @return le message sauvegardé sous forme de DTO
     */
    ChatMessageResponseDTO sendMessage(ChatMessageRequestDTO requestDTO);

    /**
     * Récupère la conversation (liste de messages) associée à un colis.
     *
     * @param colisId l'identifiant du colis
     * @return la liste des messages pour ce colis
     */
    List<ChatMessageResponseDTO> getConversation(Long colisId);
}
