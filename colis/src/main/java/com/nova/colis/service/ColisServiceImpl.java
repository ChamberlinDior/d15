package com.nova.colis.service;

import com.nova.colis.dto.ColisDTO;
import com.nova.colis.dto.ColisRequestDTO;
import com.nova.colis.exception.ResourceNotFoundException;
import com.nova.colis.model.*;
import com.nova.colis.repository.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ColisServiceImpl implements ColisService {

    @Autowired
    private ColisRepository colisRepository;

    @Override
    public ColisDTO createColis(ColisRequestDTO dto) {
        // 1. Convertir DTO -> Entité
        Colis colis = mapToEntity(dto);

        // 2. Générer une référence unique ex: "COL-XXXXXX"
        colis.setReferenceColis("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // 3. Calcul du tarif
        calculTarif(colis);

        // 4. Sauvegarde
        Colis saved = colisRepository.save(colis);

        // 5. Retourner le DTO
        return mapToDTO(saved);
    }

    @Override
    public ColisDTO getColisById(Long id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));
        return mapToDTO(colis);
    }

    @Override
    public List<ColisDTO> getAllColis() {
        return colisRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ColisDTO updateColis(Long id, ColisRequestDTO dto) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));

        // Mettre à jour
        updateEntityFromDTO(colis, dto);

        // Recalculer tarif
        calculTarif(colis);

        Colis updated = colisRepository.save(colis);
        return mapToDTO(updated);
    }

    @Override
    public void deleteColis(Long id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));
        colisRepository.delete(colis);
    }

    @Override
    public ColisDTO updateStatutColis(Long id, String nouveauStatut) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));

        StatutColis statutEnum = StatutColis.valueOf(nouveauStatut);
        colis.setStatutColis(statutEnum);

        if (statutEnum == StatutColis.EN_COURS_DE_LIVRAISON) {
            colis.setDatePriseEnCharge(LocalDateTime.now());
        } else if (statutEnum == StatutColis.LIVRE) {
            colis.setDateLivraisonEffective(LocalDateTime.now());
        }

        Colis saved = colisRepository.save(colis);
        return mapToDTO(saved);
    }

    @Override
    public ColisDTO enregistrerPaiement(Long id, ColisRequestDTO dtoPaiement) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));

        if (dtoPaiement.getModePaiement() != null) {
            colis.setModePaiement(dtoPaiement.getModePaiement());
        }
        if (dtoPaiement.getStatutPaiement() != null) {
            colis.setStatutPaiement(dtoPaiement.getStatutPaiement());
        }

        Colis saved = colisRepository.save(colis);
        return mapToDTO(saved);
    }

    /**
     * Calcul du tarif en fonction du type de colis et du poids.
     */
    private void calculTarif(Colis colis) {
        if (colis.getPoids() == null) {
            colis.setPoids(0.0);
        }
        double poids = colis.getPoids();
        double basePrice = 0.0;

        switch (colis.getTypeColis()) {
            case STANDARD:
                if (poids <= 5) basePrice = 3000;
                else if (poids <= 10) basePrice = 4500;
                else if (poids <= 20) basePrice = 7500;
                else basePrice = 11000;
                break;

            case OBJET_DE_VALEUR:
                if (poids <= 5) basePrice = 4000;
                else if (poids <= 10) basePrice = 6000;
                else if (poids <= 20) basePrice = 9500;
                else basePrice = 14000;
                break;

            case VOLUMINEUX:
                if (poids <= 50) basePrice = 15000;
                else if (poids <= 100) basePrice = 25000;
                else basePrice = 40000;
                break;
        }

        // +5% si assurance = true
        if (Boolean.TRUE.equals(colis.getAssurance())) {
            basePrice *= 1.05;
        }

        // ex: 75% -> livreur, 25% -> plateforme
        double livreurShare = basePrice * 0.75;
        double plateformeShare = basePrice * 0.25;

        colis.setPrixTotal(basePrice);
        colis.setFraisLivraison(livreurShare);
        colis.setCommissionPlateforme(plateformeShare);
    }

    /**
     * Convertit un ColisRequestDTO -> entité Colis
     */
    private Colis mapToEntity(ColisRequestDTO dto) {
        Colis c = new Colis();
        updateEntityFromDTO(c, dto);
        return c;
    }

    /**
     * Met à jour une entité existante à partir du DTO
     */
    private void updateEntityFromDTO(Colis c, ColisRequestDTO dto) {
        if (dto.getTypeColis() != null) c.setTypeColis(dto.getTypeColis());
        c.setDescription(dto.getDescription());
        c.setPoids(dto.getPoids());
        c.setDimensions(dto.getDimensions());
        c.setValeurDeclaree(dto.getValeurDeclaree());
        c.setAssurance(dto.getAssurance());

        // Expéditeur
        if (dto.getClientId() != null) c.setClientId(dto.getClientId());
        c.setNomExpediteur(dto.getNomExpediteur());
        c.setTelephoneExpediteur(dto.getTelephoneExpediteur());
        c.setEmailExpediteur(dto.getEmailExpediteur());
        c.setAdresseEnlevement(dto.getAdresseEnlevement());
        c.setVilleDepart(dto.getVilleDepart());

        // Destinataire
        c.setNomDestinataire(dto.getNomDestinataire());
        c.setTelephoneDestinataire(dto.getTelephoneDestinataire());
        c.setEmailDestinataire(dto.getEmailDestinataire());
        c.setAdresseLivraison(dto.getAdresseLivraison());
        c.setVilleDestination(dto.getVilleDestination());

        // Livreur
        c.setLivreurId(dto.getLivreurId());
        c.setNomLivreur(dto.getNomLivreur());
        c.setTelephoneLivreur(dto.getTelephoneLivreur());

        // Statut
        if (dto.getStatutColis() != null) {
            c.setStatutColis(dto.getStatutColis());
        }
        if (dto.getDatePriseEnCharge() != null) {
            c.setDatePriseEnCharge(dto.getDatePriseEnCharge());
        }
        if (dto.getDateLivraisonEstimee() != null) {
            c.setDateLivraisonEstimee(dto.getDateLivraisonEstimee());
        }
        if (dto.getDateLivraisonEffective() != null) {
            c.setDateLivraisonEffective(dto.getDateLivraisonEffective());
        }

        // Paiement
        if (dto.getModePaiement() != null) {
            c.setModePaiement(dto.getModePaiement());
        }
        if (dto.getStatutPaiement() != null) {
            c.setStatutPaiement(dto.getStatutPaiement());
        }

        // Suivi
        c.setHistoriqueSuivi(dto.getHistoriqueSuivi());
        c.setCoordonneesGPS(dto.getCoordonneesGPS());
        c.setPreuveLivraison(dto.getPreuveLivraison());
    }

    /**
     * Convertit l'entité Colis -> DTO
     */
    private ColisDTO mapToDTO(Colis c) {
        ColisDTO dto = new ColisDTO();
        dto.setId(c.getId());
        dto.setReferenceColis(c.getReferenceColis());
        dto.setTypeColis(c.getTypeColis());
        dto.setDescription(c.getDescription());
        dto.setPoids(c.getPoids());
        dto.setDimensions(c.getDimensions());
        dto.setValeurDeclaree(c.getValeurDeclaree());
        dto.setAssurance(c.getAssurance());

        dto.setClientId(c.getClientId());
        dto.setNomExpediteur(c.getNomExpediteur());
        dto.setTelephoneExpediteur(c.getTelephoneExpediteur());
        dto.setEmailExpediteur(c.getEmailExpediteur());
        dto.setAdresseEnlevement(c.getAdresseEnlevement());
        dto.setVilleDepart(c.getVilleDepart());

        dto.setNomDestinataire(c.getNomDestinataire());
        dto.setTelephoneDestinataire(c.getTelephoneDestinataire());
        dto.setEmailDestinataire(c.getEmailDestinataire());
        dto.setAdresseLivraison(c.getAdresseLivraison());
        dto.setVilleDestination(c.getVilleDestination());

        dto.setLivreurId(c.getLivreurId());
        dto.setNomLivreur(c.getNomLivreur());
        dto.setTelephoneLivreur(c.getTelephoneLivreur());

        dto.setStatutColis(c.getStatutColis());
        dto.setDateCreation(c.getDateCreation());
        dto.setDatePriseEnCharge(c.getDatePriseEnCharge());
        dto.setDateLivraisonEstimee(c.getDateLivraisonEstimee());
        dto.setDateLivraisonEffective(c.getDateLivraisonEffective());

        dto.setPrixTotal(c.getPrixTotal());
        dto.setFraisLivraison(c.getFraisLivraison());
        dto.setCommissionPlateforme(c.getCommissionPlateforme());
        dto.setModePaiement(c.getModePaiement());
        dto.setStatutPaiement(c.getStatutPaiement());

        dto.setHistoriqueSuivi(c.getHistoriqueSuivi());
        dto.setCoordonneesGPS(c.getCoordonneesGPS());
        dto.setPreuveLivraison(c.getPreuveLivraison());

        return dto;
    }
}
