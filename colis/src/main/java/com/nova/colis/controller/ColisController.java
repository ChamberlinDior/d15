package com.nova.colis.controller;

import com.nova.colis.dto.ColisDTO;
import com.nova.colis.dto.ColisRequestDTO;
import com.nova.colis.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    @Autowired
    private ColisService colisService;

    /** Créer un nouveau colis (POST /api/colis) */
    @PostMapping
    public ResponseEntity<ColisDTO> createColis(@Valid @RequestBody ColisRequestDTO colisRequestDTO) {
        ColisDTO created = colisService.createColis(colisRequestDTO);
        return ResponseEntity.ok(created);
    }

    /** Récupérer un colis par ID (GET /api/colis/{id}) */
    @GetMapping("/{id}")
    public ResponseEntity<ColisDTO> getColisById(@PathVariable Long id) {
        ColisDTO colisDTO = colisService.getColisById(id);
        return ResponseEntity.ok(colisDTO);
    }

    /** Récupérer tous les colis (GET /api/colis) */
    @GetMapping
    public ResponseEntity<List<ColisDTO>> getAllColis() {
        return ResponseEntity.ok(colisService.getAllColis());
    }

    /** Mettre à jour un colis (PUT /api/colis/{id}) */
    @PutMapping("/{id}")
    public ResponseEntity<ColisDTO> updateColis(@PathVariable Long id,
                                                @Valid @RequestBody ColisRequestDTO dto) {
        ColisDTO updated = colisService.updateColis(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** Supprimer un colis (DELETE /api/colis/{id}) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable Long id) {
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }

    /** Mettre à jour le statut du colis (PATCH /api/colis/{id}/statut?statut=...) */
    @PatchMapping("/{id}/statut")
    public ResponseEntity<ColisDTO> patchStatut(@PathVariable Long id,
                                                @RequestParam("statut") String statut) {
        ColisDTO updated = colisService.updateStatutColis(id, statut);
        return ResponseEntity.ok(updated);
    }

    /** Enregistrer un paiement (POST /api/colis/{id}/paiement) */
    @PostMapping("/{id}/paiement")
    public ResponseEntity<ColisDTO> enregistrerPaiement(@PathVariable Long id,
                                                        @RequestBody ColisRequestDTO dtoPaiement) {
        ColisDTO updated = colisService.enregistrerPaiement(id, dtoPaiement);
        return ResponseEntity.ok(updated);
    }
}
