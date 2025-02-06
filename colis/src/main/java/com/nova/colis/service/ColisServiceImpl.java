package com.nova.colis.service;

import com.nova.colis.dto.ClientDTO;
import com.nova.colis.dto.ColisDTO;
import com.nova.colis.dto.ColisRequestDTO;
import com.nova.colis.dto.LivreurDTO;
import com.nova.colis.exception.ResourceNotFoundException;
import com.nova.colis.model.*;
import com.nova.colis.repository.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ColisServiceImpl implements ColisService {

    @Autowired
    private ColisRepository colisRepository;

    // Injection des services pour obtenir la géolocalisation
    @Autowired
    private ClientService clientService;

    @Autowired
    private LivreurService livreurService;

    @Override
    public ColisDTO createColis(ColisRequestDTO dto) {
        Colis colis = mapToEntity(dto);
        colis.setReferenceColis("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        // Lors de la création, le colis est en attente : on initialise sa géolocalisation avec celle du client
        ClientDTO clientDTO = clientService.getClientById(dto.getClientId());
        if (clientDTO.getLatitude() != null && clientDTO.getLongitude() != null) {
            // Forcer l'utilisation de Locale.US pour obtenir des décimales avec point
            String coords = String.format(Locale.US, "%.6f,%.6f", clientDTO.getLatitude(), clientDTO.getLongitude());
            colis.setCoordonneesGPS(coords);
        }
        calculTarif(colis);
        Colis saved = colisRepository.save(colis);
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
        updateEntityFromDTO(colis, dto);
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

    /**
     * Mise à jour du statut du colis et de sa géolocalisation en fonction du nouveau statut.
     * Pour les statuts RECUPERE et EN_COURS_DE_LIVRAISON, on met à jour la géolocalisation
     * en utilisant les coordonnées actuelles du livreur, en s’assurant de respecter le format "lat,lon".
     */
    @Override
    public ColisDTO updateStatutColis(Long id, String nouveauStatut) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));
        StatutColis statutEnum = StatutColis.valueOf(nouveauStatut);

        if (statutEnum == StatutColis.RECUPERE) {
            if (colis.getLivreurId() == null) {
                throw new IllegalArgumentException("Le colis ne peut être récupéré sans un livreur assigné.");
            }
            // Vérifier si le livreur a déjà un colis actif
            List<StatutColis> statutsActifs = Arrays.asList(StatutColis.RECUPERE, StatutColis.EN_COURS_DE_LIVRAISON);
            List<Colis> colisActifs = colisRepository.findByLivreurIdAndStatutColisIn(colis.getLivreurId(), statutsActifs);
            if (!colisActifs.isEmpty()) {
                throw new IllegalStateException("Ce livreur a déjà un colis en cours de livraison.");
            }
            // Mise à jour de la géolocalisation via la position actuelle du livreur
            LivreurDTO livreurDTO = livreurService.getLivreurById(colis.getLivreurId());
            if (livreurDTO.getLatitudeActuelle() != null && livreurDTO.getLongitudeActuelle() != null) {
                String coords = String.format(Locale.US, "%.6f,%.6f", livreurDTO.getLatitudeActuelle(), livreurDTO.getLongitudeActuelle());
                colis.setCoordonneesGPS(coords);
            }
            colis.setDatePriseEnCharge(LocalDateTime.now());
        } else if (statutEnum == StatutColis.EN_COURS_DE_LIVRAISON) {
            if (colis.getLivreurId() != null) {
                LivreurDTO livreurDTO = livreurService.getLivreurById(colis.getLivreurId());
                if (livreurDTO.getLatitudeActuelle() != null && livreurDTO.getLongitudeActuelle() != null) {
                    String coords = String.format(Locale.US, "%.6f,%.6f", livreurDTO.getLatitudeActuelle(), livreurDTO.getLongitudeActuelle());
                    colis.setCoordonneesGPS(coords);
                }
            }
            colis.setDatePriseEnCharge(LocalDateTime.now());
        } else if (statutEnum == StatutColis.LIVRE) {
            // Une fois le colis livré, on enregistre la date de livraison effective sans modifier les coordonnées GPS initiales
            colis.setDateLivraisonEffective(LocalDateTime.now());
        } else if (statutEnum == StatutColis.EN_ATTENTE) {
            // Pour un colis en attente, on initialise les coordonnées GPS uniquement s'il n'est pas déjà renseigné.
            if (colis.getCoordonneesGPS() == null) {
                ClientDTO clientDTO = clientService.getClientById(colis.getClientId());
                if (clientDTO.getLatitude() != null && clientDTO.getLongitude() != null) {
                    String coords = String.format(Locale.US, "%.6f,%.6f", clientDTO.getLatitude(), clientDTO.getLongitude());
                    colis.setCoordonneesGPS(coords);
                }
            }
        }

        // Le statut est mis à jour quel que soit le cas
        colis.setStatutColis(statutEnum);
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
        if (dtoPaiement.getPaiementInfo() != null) {
            colis.setPaiementInfo(dtoPaiement.getPaiementInfo());
        }
        Colis saved = colisRepository.save(colis);
        return mapToDTO(saved);
    }

    /**
     * Calcul du tarif en fonction du type de colis, du type d’expédition et du poids.
     */
    private void calculTarif(Colis colis) {
        if (colis.getPoids() == null) {
            colis.setPoids(0.0);
        }
        double poids = colis.getPoids();
        double basePrice = 0.0;
        // Le frontend envoie le type d'expédition dans le champ "villeDestination"
        String expeditionType = colis.getVilleDestination();
        switch (colis.getTypeColis()) {
            case STANDARD:
                if ("Urbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 3000;
                    else if (poids <= 10) basePrice = 4500;
                    else if (poids <= 20) basePrice = 7500;
                    else if (poids <= 30) basePrice = 11000;
                } else if ("Interurbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 7500;
                    else if (poids <= 10) basePrice = 10000;
                    else if (poids <= 20) basePrice = 15000;
                    else if (poids <= 30) basePrice = 20000;
                } else if ("International".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 34650;
                    else if (poids <= 10) basePrice = 66300;
                    else if (poids <= 20) basePrice = 130600;
                    else if (poids <= 30) basePrice = 196000;
                }
                break;
            case OBJET_DE_VALEUR:
                if ("Urbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 4000;
                    else if (poids <= 10) basePrice = 6000;
                    else if (poids <= 20) basePrice = 9500;
                    else if (poids <= 30) basePrice = 14000;
                } else if ("Interurbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 8000;
                    else if (poids <= 10) basePrice = 12000;
                    else if (poids <= 20) basePrice = 18000;
                    else if (poids <= 30) basePrice = 25000;
                } else if ("International".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 36382;
                    else if (poids <= 10) basePrice = 69615;
                    else if (poids <= 20) basePrice = 137130;
                    else if (poids <= 30) basePrice = 205800;
                }
                break;
            case VOLUMINEUX:
                if ("Urbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 8000;
                    else if (poids <= 10) basePrice = 12000;
                    else if (poids <= 20) basePrice = 18000;
                    else if (poids <= 30) basePrice = 26000;
                } else if ("Interurbain".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 15000;
                    else if (poids <= 10) basePrice = 20000;
                    else if (poids <= 20) basePrice = 30000;
                    else if (poids <= 30) basePrice = 40000;
                } else if ("International".equalsIgnoreCase(expeditionType)) {
                    if (poids <= 5) basePrice = 65000;
                    else if (poids <= 10) basePrice = 100000;
                    else if (poids <= 20) basePrice = 150000;
                    else if (poids <= 30) basePrice = 250000;
                }
                break;
        }
        if (Boolean.TRUE.equals(colis.getAssurance())) {
            basePrice *= 1.05;
        }
        double livreurShare = basePrice * 0.75;
        double plateformeShare = basePrice * 0.25;
        // Mise à jour des champs de tarification
        colis.setPrixTotal(basePrice);
        colis.setFraisLivraison(livreurShare);
        colis.setCommissionPlateforme(plateformeShare);
    }

    private Colis mapToEntity(ColisRequestDTO dto) {
        Colis c = new Colis();
        updateEntityFromDTO(c, dto);
        return c;
    }

    /**
     * Mise à jour des champs du colis à partir du DTO.
     * Les champs d'adresse (enlevement, livraison et type d'expédition)
     * sont mis à jour uniquement lors de la création ou si le colis est encore en EN_ATTENTE.
     */
    private void updateEntityFromDTO(Colis c, ColisRequestDTO dto) {
        // Champs généraux
        if (dto.getTypeColis() != null) {
            c.setTypeColis(dto.getTypeColis());
        }
        c.setDescription(dto.getDescription());
        c.setPoids(dto.getPoids());
        c.setDimensions(dto.getDimensions());
        c.setValeurDeclaree(dto.getValeurDeclaree());
        c.setAssurance(dto.getAssurance());

        // Expéditeur
        if (dto.getClientId() != null) {
            c.setClientId(dto.getClientId());
        }
        c.setNomExpediteur(dto.getNomExpediteur());
        c.setTelephoneExpediteur(dto.getTelephoneExpediteur());
        c.setEmailExpediteur(dto.getEmailExpediteur());

        // MàJ des adresses uniquement si le colis est en attente ou si elles ne sont pas encore renseignées
        if (c.getStatutColis() == null || c.getStatutColis() == StatutColis.EN_ATTENTE) {
            if (dto.getAdresseEnlevement() != null) {
                c.setAdresseEnlevement(dto.getAdresseEnlevement());
            }
            if (dto.getAdresseLivraison() != null) {
                c.setAdresseLivraison(dto.getAdresseLivraison());
            }
            if (dto.getVilleDestination() != null) {
                c.setVilleDestination(dto.getVilleDestination());
            }
        }

        // Destinataire
        c.setNomDestinataire(dto.getNomDestinataire());
        c.setTelephoneDestinataire(dto.getTelephoneDestinataire());
        c.setEmailDestinataire(dto.getEmailDestinataire());

        // Livreur
        c.setLivreurId(dto.getLivreurId());
        c.setNomLivreur(dto.getNomLivreur());
        c.setTelephoneLivreur(dto.getTelephoneLivreur());

        // Statut et dates
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
        if (dto.getPaiementInfo() != null) {
            c.setPaiementInfo(dto.getPaiementInfo());
        }

        // Suivi
        c.setHistoriqueSuivi(dto.getHistoriqueSuivi());
        // On conserve les coordonnées GPS déjà enregistrées (sinon on enregistre celles passées dans le DTO uniquement si null)
        if (c.getCoordonneesGPS() == null && dto.getCoordonneesGPS() != null) {
            c.setCoordonneesGPS(dto.getCoordonneesGPS());
        }
        c.setPreuveLivraison(dto.getPreuveLivraison());
    }

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
        dto.setPaiementInfo(c.getPaiementInfo());
        return dto;
    }
}
