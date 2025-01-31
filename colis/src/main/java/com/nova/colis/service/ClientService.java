package com.nova.colis.service;

import com.nova.colis.dto.ClientDTO;
import com.nova.colis.dto.ClientRequestDTO;
import java.util.List;

/**
 * Interface définissant les opérations pour la gestion des Clients.
 */
public interface ClientService {

    ClientDTO registerClient(ClientRequestDTO clientRequestDTO);

    ClientDTO getClientById(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO updateClient(Long id, ClientRequestDTO clientRequestDTO);

    void deleteClient(Long id);

    // NOUVEAU ENDPOINT : Permet la connexion
    ClientDTO login(String email, String password);
}
