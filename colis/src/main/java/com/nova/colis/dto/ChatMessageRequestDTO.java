package com.nova.colis.dto;

public class ChatMessageRequestDTO {

    // Identifiant du colis concerné (fourni via l'URL ou le body)
    private Long colisId;
    // Identifiant de l'expéditeur
    private Long senderId;
    // Rôle de l'expéditeur ("ROLE_CLIENT" ou "ROLE_LIVREUR")
    private String senderRole;
    // Contenu du message
    private String message;

    // Getters et Setters
    public Long getColisId() {
        return colisId;
    }

    public void setColisId(Long colisId) {
        this.colisId = colisId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
