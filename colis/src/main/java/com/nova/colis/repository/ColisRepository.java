package com.nova.colis.repository;

import com.nova.colis.model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées si nécessaire
    // Ex : Optional<Colis> findByReferenceColis(String reference);
}
