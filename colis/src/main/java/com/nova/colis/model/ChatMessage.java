package com.nova.colis.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identifiant du colis associé (clé étrangère vers la table colis)
    @Column(name = "colis_id", nullable = false)
    private Long colisId;

    // Expéditeur
    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "sender_role", nullable = false, length = 50)
    private String senderRole;

    // Contenu du message
    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    // (Optionnel) Image jointe au message
    @Lob
    @Column(name = "photo", nullable = true)
    private byte[] photo;

    // Date et heure d'envoi
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Constructeurs
    public ChatMessage() {
    }

    public ChatMessage(Long colisId, Long senderId, String senderRole, String message, LocalDateTime timestamp, byte[] photo) {
        this.colisId = colisId;
        this.senderId = senderId;
        this.senderRole = senderRole;
        this.message = message;
        this.timestamp = timestamp;
        this.photo = photo;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // equals et hashCode basés sur l'id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
