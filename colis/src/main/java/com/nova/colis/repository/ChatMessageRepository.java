package com.nova.colis.repository;

import com.nova.colis.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Récupère l'ensemble des messages associés à un colis donné,
     * triés par ordre chronologique croissant (du plus ancien au plus récent).
     *
     * @param colisId l'identifiant du colis
     * @return la liste des messages pour ce colis
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.colisId = :colisId ORDER BY m.timestamp ASC")
    List<ChatMessage> findByColisId(@Param("colisId") Long colisId);
}
