package com.nova.colis.controller;

import com.nova.colis.dto.LivreurDTO;
import com.nova.colis.dto.LivreurRequestDTO;
import com.nova.colis.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des livreurs.
 * Gère la photo, la pièce d'identité, et la géolocalisation.
 */
@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    @Autowired
    private LivreurService livreurService;

    // 1. Inscription d'un nouveau livreur
    @PostMapping("/register")
    public ResponseEntity<LivreurDTO> register(@Valid @RequestBody LivreurRequestDTO request) {
        LivreurDTO dto = livreurService.registerLivreur(request);
        return ResponseEntity.ok(dto);
    }

    // 2. Connexion d'un livreur
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LivreurRequestDTO request) {
        LivreurDTO dto = livreurService.login(request.getEmail(), request.getPassword());
        if (dto == null) {
            return ResponseEntity.status(401).body("Identifiants invalides pour le livreur.");
        }
        // Vous pouvez générer un token JWT réel si nécessaire
        Map<String, Object> response = new HashMap<>();
        response.put("livreur", dto);
        response.put("token", "fake-livreur-jwt-token");
        return ResponseEntity.ok(response);
    }

    // 3. Récupération d'un livreur par ID
    @GetMapping("/{id}")
    public ResponseEntity<LivreurDTO> getById(@PathVariable Long id) {
        LivreurDTO dto = livreurService.getLivreurById(id);
        return ResponseEntity.ok(dto);
    }

    // 4. Récupération de tous les livreurs
    @GetMapping
    public ResponseEntity<List<LivreurDTO>> getAll() {
        List<LivreurDTO> list = livreurService.getAllLivreurs();
        return ResponseEntity.ok(list);
    }

    // 5. Mise à jour d'un livreur
    @PutMapping("/{id}")
    public ResponseEntity<LivreurDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LivreurRequestDTO request
    ) {
        LivreurDTO updated = livreurService.updateLivreur(id, request);
        return ResponseEntity.ok(updated);
    }

    // 6. Mise à jour de la position GPS (géolocalisation en temps réel)
    @PatchMapping("/{id}/location")
    public ResponseEntity<LivreurDTO> updateLocation(
            @PathVariable Long id,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        LivreurDTO updated = livreurService.updateLocation(id, latitude, longitude);
        return ResponseEntity.ok(updated);
    }

    // 7. Suppression d'un livreur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livreurService.deleteLivreur(id);
        return ResponseEntity.noContent().build();
    }
}
