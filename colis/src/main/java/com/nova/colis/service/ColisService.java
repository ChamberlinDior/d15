package com.nova.colis.service;

import com.nova.colis.dto.ColisDTO;
import com.nova.colis.dto.ColisRequestDTO;
import java.util.List;

public interface ColisService {

    /**
     * Crée un nouveau colis à partir d'un DTO de requête
     */
    ColisDTO createColis(ColisRequestDTO colisRequestDTO);

    /**
     * Récupère un colis par ID
     */
    ColisDTO getColisById(Long id);

    /**
     * Récupère tous les colis
     */
    List<ColisDTO> getAllColis();

    /**
     * Met à jour un colis existant par son ID
     */
    ColisDTO updateColis(Long id, ColisRequestDTO colisRequestDTO);

    /**
     * Supprime un colis par son ID
     */
    void deleteColis(Long id);

    /**
     * Met à jour uniquement le statut du colis (Patch)
     */
    ColisDTO updateStatutColis(Long id, String nouveauStatut);

    /**
     * Enregistre les informations de paiement du colis (mode de paiement, statut, etc.)
     */
    ColisDTO enregistrerPaiement(Long id, ColisRequestDTO dtoPaiement);
}
