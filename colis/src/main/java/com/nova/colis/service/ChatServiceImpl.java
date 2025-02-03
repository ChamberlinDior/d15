package com.nova.colis.service;

import com.nova.colis.dto.ChatMessageRequestDTO;
import com.nova.colis.dto.ChatMessageResponseDTO;
import com.nova.colis.model.ChatMessage;
import com.nova.colis.model.Colis;
import com.nova.colis.model.StatutColis;
import com.nova.colis.repository.ChatMessageRepository;
import com.nova.colis.repository.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ColisRepository colisRepository;

    @Override
    public ChatMessageResponseDTO sendMessage(ChatMessageRequestDTO requestDTO) {
        // Vérifier que le colis existe
        Optional<Colis> colisOpt = colisRepository.findById(requestDTO.getColisId());
        if (colisOpt.isEmpty()) {
            throw new IllegalArgumentException("Colis non trouvé");
        }
        Colis colis = colisOpt.get();

        // Vérifier que le colis a un statut autorisant le chat
        if (!(colis.getStatutColis().name().equals("EN_ATTENTE") ||
                colis.getStatutColis().name().equals("EN_COURS_DE_LIVRAISON"))) {
            throw new IllegalArgumentException("Les échanges ne sont autorisés que pour les colis EN_ATTENTE ou EN_COURS_DE_LIVRAISON");
        }

        // Vérifier que l'expéditeur est autorisé :
        // - Si ROLE_CLIENT, l'expéditeur doit être le client du colis.
        // - Si ROLE_LIVREUR, l'expéditeur doit être le livreur assigné.
        Long senderId = requestDTO.getSenderId();
        String senderRole = requestDTO.getSenderRole();
        boolean isAuthorized = false;
        if ("ROLE_CLIENT".equals(senderRole) && senderId.equals(colis.getClientId())) {
            isAuthorized = true;
        }
        if ("ROLE_LIVREUR".equals(senderRole) && colis.getLivreurId() != null && senderId.equals(colis.getLivreurId())) {
            isAuthorized = true;
        }
        if (!isAuthorized) {
            throw new IllegalArgumentException("L'expéditeur n'est pas autorisé à échanger pour ce colis");
        }

        // Créer le message et définir l'horodatage
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setColisId(requestDTO.getColisId());
        chatMessage.setSenderId(senderId);
        chatMessage.setSenderRole(senderRole);
        chatMessage.setMessage(requestDTO.getMessage());
        chatMessage.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        return mapToResponseDTO(savedMessage);
    }

    @Override
    public List<ChatMessageResponseDTO> getConversation(Long colisId) {
        // On peut vérifier que le colis existe si besoin
        List<ChatMessage> messages = chatMessageRepository.findByColisId(colisId);
        return messages.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageResponseDTO mapToResponseDTO(ChatMessage message) {
        ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
        dto.setId(message.getId());
        dto.setColisId(message.getColisId());
        dto.setSenderId(message.getSenderId());
        dto.setSenderRole(message.getSenderRole());
        dto.setMessage(message.getMessage());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}
