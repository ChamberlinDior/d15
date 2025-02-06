package com.nova.colis.service;

import com.nova.colis.dto.LivreurDTO;
import com.nova.colis.dto.LivreurRequestDTO;
import com.nova.colis.exception.ResourceNotFoundException;
import com.nova.colis.model.Livreur;
import com.nova.colis.model.Colis;
import com.nova.colis.model.StatutColis;
import com.nova.colis.repository.LivreurRepository;
import com.nova.colis.repository.ColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivreurServiceImpl implements LivreurService {

    @Autowired
    private LivreurRepository livreurRepository;

    // Injection du repository des colis pour mettre à jour les colis assignés au livreur
    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LivreurDTO registerLivreur(LivreurRequestDTO request) {
        if (livreurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre livreur.");
        }

        // Conversion DTO -> Entité
        Livreur livreur = new Livreur();
        livreur.setEmail(request.getEmail());
        // On encode le password si présent
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            livreur.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        livreur.setNom(request.getNom());
        livreur.setPrenom(request.getPrenom());
        livreur.setTelephone(request.getTelephone());
        livreur.setAdresse(request.getAdresse());
        livreur.setNumeroPieceIdentite(request.getNumeroPieceIdentite());
        livreur.setPhoto(request.getPhoto()); // byte[] déjà fourni
        livreur.setLatitudeActuelle(request.getLatitudeActuelle());
        livreur.setLongitudeActuelle(request.getLongitudeActuelle());
        livreur.setRole("ROLE_LIVREUR");

        Livreur saved = livreurRepository.save(livreur);
        return mapToDTO(saved);
    }

    @Override
    public LivreurDTO login(String email, String password) {
        Optional<Livreur> optional = livreurRepository.findByEmail(email);
        if (optional.isEmpty()) {
            return null; // Aucun livreur avec cet email
        }
        Livreur livreur = optional.get();

        // Vérification du mot de passe
        if (!passwordEncoder.matches(password, livreur.getPassword())) {
            return null; // Mot de passe invalide
        }
        return mapToDTO(livreur);
    }

    @Override
    public LivreurDTO getLivreurById(Long id) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur", "id", id));
        return mapToDTO(livreur);
    }

    @Override
    public List<LivreurDTO> getAllLivreurs() {
        return livreurRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LivreurDTO updateLivreur(Long id, LivreurRequestDTO request) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur", "id", id));

        // Mise à jour des champs
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            livreur.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            livreur.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getNom() != null) {
            livreur.setNom(request.getNom());
        }
        if (request.getPrenom() != null) {
            livreur.setPrenom(request.getPrenom());
        }
        if (request.getTelephone() != null) {
            livreur.setTelephone(request.getTelephone());
        }
        if (request.getAdresse() != null) {
            livreur.setAdresse(request.getAdresse());
        }
        if (request.getNumeroPieceIdentite() != null) {
            livreur.setNumeroPieceIdentite(request.getNumeroPieceIdentite());
        }
        if (request.getPhoto() != null && request.getPhoto().length > 0) {
            livreur.setPhoto(request.getPhoto());
        }
        if (request.getLatitudeActuelle() != null) {
            livreur.setLatitudeActuelle(request.getLatitudeActuelle());
        }
        if (request.getLongitudeActuelle() != null) {
            livreur.setLongitudeActuelle(request.getLongitudeActuelle());
        }

        Livreur updated = livreurRepository.save(livreur);
        return mapToDTO(updated);
    }

    /**
     * Mise à jour de la géolocalisation du livreur.
     * Après mise à jour, les colis en cours de livraison assignés à ce livreur
     * voient leur champ coordonneesGPS mis à jour avec la nouvelle position.
     */
    @Override
    public LivreurDTO updateLocation(Long id, Double latitude, Double longitude) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur", "id", id));
        livreur.setLatitudeActuelle(latitude);
        livreur.setLongitudeActuelle(longitude);

        Livreur updated = livreurRepository.save(livreur);

        // Mettre à jour les colis assignés à ce livreur qui sont en cours de livraison
        List<Colis> colisEnCours = colisRepository.findByLivreurIdAndStatutColis(id, StatutColis.EN_COURS_DE_LIVRAISON);
        String nouvellePosition = latitude + "," + longitude;
        for (Colis colis : colisEnCours) {
            colis.setCoordonneesGPS(nouvellePosition);
            colisRepository.save(colis);
        }

        return mapToDTO(updated);
    }

    @Override
    public void deleteLivreur(Long id) {
        Livreur livreur = livreurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur", "id", id));
        livreurRepository.delete(livreur);
    }

    /**
     * Conversion Entité -> DTO
     */
    private LivreurDTO mapToDTO(Livreur livreur) {
        LivreurDTO dto = new LivreurDTO();
        dto.setId(livreur.getId());
        dto.setEmail(livreur.getEmail());
        dto.setNom(livreur.getNom());
        dto.setPrenom(livreur.getPrenom());
        dto.setTelephone(livreur.getTelephone());
        dto.setAdresse(livreur.getAdresse());
        dto.setNumeroPieceIdentite(livreur.getNumeroPieceIdentite());
        dto.setPhoto(livreur.getPhoto());
        dto.setLatitudeActuelle(livreur.getLatitudeActuelle());
        dto.setLongitudeActuelle(livreur.getLongitudeActuelle());
        dto.setRole(livreur.getRole());
        return dto;
    }
}
