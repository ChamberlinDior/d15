package com.nova.colis.repository;

import com.nova.colis.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository pour l'entité Client.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Boolean existsByEmail(String email);
}
