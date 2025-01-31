package com.nova.colis.service;

import com.nova.colis.dto.ClientDTO;
import com.nova.colis.dto.ClientRequestDTO;
import com.nova.colis.exception.ResourceNotFoundException;
import com.nova.colis.model.Client;
import com.nova.colis.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation de l'interface ClientService.
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Inscrit un nouveau client en vérifiant d'abord que l'email n'existe pas déjà.
     */
    @Override
    public ClientDTO registerClient(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Client client = new Client();
        client.setEmail(clientRequestDTO.getEmail());
        if (clientRequestDTO.getPassword() != null && !clientRequestDTO.getPassword().isEmpty()) {
            client.setPassword(passwordEncoder.encode(clientRequestDTO.getPassword()));
        }
        client.setNom(clientRequestDTO.getNom());
        client.setPrenom(clientRequestDTO.getPrenom());
        client.setTelephone(clientRequestDTO.getTelephone());
        client.setAdresse(clientRequestDTO.getAdresse());
        client.setLatitude(clientRequestDTO.getLatitude());
        client.setLongitude(clientRequestDTO.getLongitude());
        client.setRole("ROLE_CLIENT"); // Rôle par défaut

        Client savedClient = clientRepository.save(client);
        return mapToDTO(savedClient);
    }

    /**
     * Permet de connecter un client en vérifiant l'email et le mot de passe.
     */
    @Override
    public ClientDTO login(String email, String password) {
        // 1. Recherche du client par email
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isEmpty()) {
            return null; // Pas de client correspondant à cet email
        }

        Client client = optionalClient.get();
        // 2. Vérification du mot de passe (chiffré en base)
        if (!passwordEncoder.matches(password, client.getPassword())) {
            return null; // Mot de passe incorrect
        }

        // 3. Conversion en DTO et renvoi
        return mapToDTO(client);
    }

    /**
     * Récupère un client par son ID.
     */
    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return mapToDTO(client);
    }

    /**
     * Récupère la liste de tous les clients.
     */
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour les informations d'un client existant.
     */
    @Override
    public ClientDTO updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        if (clientRequestDTO.getEmail() != null && !clientRequestDTO.getEmail().isEmpty()) {
            client.setEmail(clientRequestDTO.getEmail());
        }

        if (clientRequestDTO.getPassword() != null && !clientRequestDTO.getPassword().isEmpty()) {
            client.setPassword(passwordEncoder.encode(clientRequestDTO.getPassword()));
        }

        if (clientRequestDTO.getNom() != null && !clientRequestDTO.getNom().isEmpty()) {
            client.setNom(clientRequestDTO.getNom());
        }

        if (clientRequestDTO.getPrenom() != null && !clientRequestDTO.getPrenom().isEmpty()) {
            client.setPrenom(clientRequestDTO.getPrenom());
        }

        if (clientRequestDTO.getTelephone() != null && !clientRequestDTO.getTelephone().isEmpty()) {
            client.setTelephone(clientRequestDTO.getTelephone());
        }

        if (clientRequestDTO.getAdresse() != null && !clientRequestDTO.getAdresse().isEmpty()) {
            client.setAdresse(clientRequestDTO.getAdresse());
        }

        if (clientRequestDTO.getLatitude() != null) {
            client.setLatitude(clientRequestDTO.getLatitude());
        }

        if (clientRequestDTO.getLongitude() != null) {
            client.setLongitude(clientRequestDTO.getLongitude());
        }

        // Optionnel : mise à jour du rôle si nécessaire
        // if (clientRequestDTO.getRole() != null && !clientRequestDTO.getRole().isEmpty()) {
        //     client.setRole(clientRequestDTO.getRole());
        // }

        Client updatedClient = clientRepository.save(client);
        return mapToDTO(updatedClient);
    }

    /**
     * Supprime un client par son ID.
     */
    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        clientRepository.delete(client);
    }

    /**
     * Méthode utilitaire : convertit une entité Client en DTO.
     */
    private ClientDTO mapToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setEmail(client.getEmail());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setTelephone(client.getTelephone());
        dto.setAdresse(client.getAdresse());
        dto.setLatitude(client.getLatitude());
        dto.setLongitude(client.getLongitude());
        dto.setRole(client.getRole());
        return dto;
    }
}
