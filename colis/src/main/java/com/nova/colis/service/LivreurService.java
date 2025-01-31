package com.nova.colis.service;

import com.nova.colis.dto.LivreurDTO;
import com.nova.colis.dto.LivreurRequestDTO;

import java.util.List;

public interface LivreurService {

    // Inscription
    LivreurDTO registerLivreur(LivreurRequestDTO request);

    // Connexion
    LivreurDTO login(String email, String password);

    // Lecture
    LivreurDTO getLivreurById(Long id);
    List<LivreurDTO> getAllLivreurs();

    // Mise à jour générale
    LivreurDTO updateLivreur(Long id, LivreurRequestDTO request);

    // Mise à jour de la géolocalisation en temps réel
    LivreurDTO updateLocation(Long id, Double latitude, Double longitude);

    // Suppression
    void deleteLivreur(Long id);
}
