package com.nova.colis.service;

import com.nova.colis.dto.ColisDTO;
import com.nova.colis.dto.ColisRequestDTO;
import java.util.List;

public interface ColisService {

    ColisDTO createColis(ColisRequestDTO colisRequestDTO);
    ColisDTO getColisById(Long id);
    List<ColisDTO> getAllColis();

    ColisDTO updateColis(Long id, ColisRequestDTO colisRequestDTO);

    void deleteColis(Long id);

    ColisDTO updateStatutColis(Long id, String nouveauStatut);

    ColisDTO enregistrerPaiement(Long id, ColisRequestDTO dtoPaiement);
}
