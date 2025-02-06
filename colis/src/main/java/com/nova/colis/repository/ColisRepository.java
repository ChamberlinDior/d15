package com.nova.colis.repository;

import com.nova.colis.model.Colis;
import com.nova.colis.model.StatutColis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {
    // Récupérer les colis assignés à un livreur pour un statut donné
    List<Colis> findByLivreurIdAndStatutColis(Long livreurId, StatutColis statutColis);

    // Nouvelle méthode pour récupérer les colis d'un livreur dont le statut est dans une liste donnée
    List<Colis> findByLivreurIdAndStatutColisIn(Long livreurId, List<StatutColis> statuts);
}
